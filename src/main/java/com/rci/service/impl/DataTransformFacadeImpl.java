package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.OrderDTO;
import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.datafetch.IDataFetchService;
import com.rci.service.DataTransformFacade;
import com.rci.service.IDishService;
import com.rci.service.IOrderService;
import com.rci.tools.DateUtil;

@Service("DataTransformFacade")
public class DataTransformFacadeImpl implements DataTransformFacade {
	private static final Log logger = LogFactory.getLog(DataTransformFacadeImpl.class);
	
	@Resource(name="DataFetchService")
	private IDataFetchService dataFetch;
	@Autowired
	private Mapper beanMapper;
	@Resource(name="PostAccountCalculator")
	private PostAccountCalculator postCalculator;
	@Resource(name="OrderService")
	private IOrderService orderService;
	@Resource(name="DishService")
	private IDishService dishService;
	
	@Override
	public void accquireOrderInfo(Date date) {
		//1. 根据日期获取当日的所有订单
		List<OrderDTO> orderDTOs = dataFetch.fetchAllDayOrders(DateUtil.wipeoutHMS(date));
		Map<String,Order> container = mergerOrder(orderDTOs);
		//2. 迭代order,获取对应的item信息
		for(Iterator<Entry<String,Order>> it = container.entrySet().iterator();it.hasNext();){
			Entry<String,Order> entry = it.next();
			String key = entry.getKey();
			Order value = entry.getValue();
			value.setDay(DateUtil.date2Str(date, "yyyyMMdd"));
			List<OrderItemDTO> itemDTOs = dataFetch.fetchOrderItemsByOrder(key);
			Order order = postCalculator.calculate(value, itemDTOs);
			//设置关联的order Item
			List<OrderItem> items = new LinkedList<OrderItem>();
			for(OrderItemDTO itemDTO:itemDTOs){
				OrderItem item = beanMapper.map(itemDTO, OrderItem.class);
				item.setOrder(order);
				Dish dish = dishService.findDishByNo(itemDTO.getDishNo());
				item.setDish(dish);
				BigDecimal price = itemDTO.getPrice();
				BigDecimal count = itemDTO.getCount();
				BigDecimal backcount = itemDTO.getCountback();
				BigDecimal rate = itemDTO.getDiscountRate();
				BigDecimal itemActualAmount = price.multiply(count).subtract(price.multiply(backcount)).multiply(rate.divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_CEILING);
				item.setActualAmount(itemActualAmount);
				items.add(item);
			}
			order.setItems(items);
			orderService.rwInsertOrder(order);
		}
	}
	
	/**
	 * 合并订单
	 * @param orderDTOs
	 * @return
	 */
	private Map<String,Order> mergerOrder(List<OrderDTO> orderDTOs){
		Map<String,Order> container = new HashMap<String,Order>();
		if(!CollectionUtils.isEmpty(orderDTOs)){
			for(OrderDTO orderDTO:orderDTOs){
				Order order = null;
				String orderNo = orderDTO.getOrderNo();
				String paymode = orderDTO.getPaymode();
				BigDecimal realAmount = orderDTO.getRealAmount();
				logger.debug("orderno: "+ orderNo +" -> paymode "+paymode);
				//2.1 如果容器中存在订单号重复，记录当前订单的支付方式合并到第一条订单中
				if(container.containsKey(orderNo)){
					order = container.get(orderNo);
					order.addPayMode(paymode,realAmount);
					if(realAmount.compareTo(BigDecimal.ZERO) > 0){
						order.setRealAmount(order.getRealAmount().add(realAmount));
					}
					continue;
				}
				//2.2 如果容器中不存在，则初始化设置订单信息，将其加入容器
				order = beanMapper.map(orderDTO, Order.class);
				order.setOriginPrice(orderDTO.getOriginAmount());
				order.addPayMode(paymode,realAmount);
				container.put(orderNo, order);
			}
		}
		return container;
	}

}
