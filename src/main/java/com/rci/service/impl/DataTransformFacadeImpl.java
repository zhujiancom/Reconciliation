package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.OrderDTO;
import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.DiscountScheme;
import com.rci.bean.entity.Order;
import com.rci.datafetch.IDataFetchService;
import com.rci.service.DataTransformFacade;
import com.rci.service.IDiscountSchemeService;
import com.rci.tools.DateUtil;

@Service("DataTransformFacade")
public class DataTransformFacadeImpl implements DataTransformFacade {
	@Resource(name="DataFetchService")
	private IDataFetchService dataFetch;
	@Autowired
	private Mapper beanMapper;
	@Resource(name="DiscountSchemeService")
	private IDiscountSchemeService schemeService;
	
	@Override
	public void accquireOrderInfo(Date date) {
//		List<OrderDTO> orderDTOs = dataFetch.fetchAllDayOrders(DateUtil.wipeoutHMS(new Date()));
		// 1. 获取order 信息
		List<OrderDTO> orderDTOs = dataFetch.fetchAllDayOrders(DateUtil.str2Date("2015-02-03"));
		Map<String,Order> map = new HashMap<String,Order>();
		//2. 迭代order,获取对应的item信息
		if(!CollectionUtils.isEmpty(orderDTOs)){
			for(OrderDTO orderDTO:orderDTOs){
				String orderNo = orderDTO.getOrderNo();
				String paymode = orderDTO.getPaymode();
				String project = orderDTO.getProject();
				if("00".equals(paymode) && (project == null || "无折扣".equals(project))){ 
					DiscountScheme scheme = schemeService.getSchemeByNo("01");
				}
				
				List<OrderItemDTO> itemDTOs = dataFetch.fetchOrderItemsByOrder(orderDTO.getOrderNo());
				//3. 根据orderItem 补全order 信息
				if(!CollectionUtils.isEmpty(itemDTOs)){
					for(OrderItemDTO itemDTO:itemDTOs){
						
					}
				}
				
				Order order = beanMapper.map(orderDTO, Order.class);
				
				// 合并多种支付方式的order
				if(map.containsKey(orderNo)){
					Order preOrder = map.get(orderNo);
					BigDecimal currentAmount = preOrder.getActualAmount();
					preOrder.setActualAmount(currentAmount.add(order.getActualAmount()));
					map.put(orderNo, preOrder);
				}else{
					map.put(orderNo, order);
				}
			}
		}
	}

}
