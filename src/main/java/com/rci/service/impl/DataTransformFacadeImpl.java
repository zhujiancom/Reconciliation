package com.rci.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.rci.service.IOrderService;
import com.rci.tools.DateUtil;

@Service("DataTransformFacade")
public class DataTransformFacadeImpl implements DataTransformFacade {
	@Resource(name="DataFetchService")
	private IDataFetchService dataFetch;
	@Autowired
	private Mapper beanMapper;
	@Resource(name="DiscountSchemeService")
	private IDiscountSchemeService schemeService;
	@Resource(name="PostAccountCalculator")
	private PostAccountCalculator postCalculator;
	@Resource(name="OrderService")
	private IOrderService orderService;
	
	@Override
	public void accquireOrderInfo(Date date) {
//		List<OrderDTO> orderDTOs = dataFetch.fetchAllDayOrders(DateUtil.wipeoutHMS(new Date()));
		// 1. 获取order 信息
		List<OrderDTO> orderDTOs = dataFetch.fetchAllDayOrders(DateUtil.str2Date("2015-02-03"));
		Set<String> container = new HashSet<String>();
		//2. 迭代order,获取对应的item信息
		if(!CollectionUtils.isEmpty(orderDTOs)){
			for(OrderDTO orderDTO:orderDTOs){
				String orderNo = orderDTO.getOrderNo();
				String paymode = orderDTO.getPaymode();
				String project = orderDTO.getProject();
				if(container.contains(orderNo)){
					continue;
				}
				DiscountScheme scheme = null;
				if(project == null){ //使用代金券
					scheme = schemeService.getSchemeByNo(paymode);
				}else{//获取折扣方案
					scheme = schemeService.getSchemeByName(project);
				}
				
				Order order = beanMapper.map(orderDTO, Order.class);
				order.setScheme(scheme);
				List<OrderItemDTO> itemDTOs = dataFetch.fetchOrderItemsByOrder(orderDTO.getOrderNo());
				order = postCalculator.calculate(order, itemDTOs);
				orderService.rwInsertOrder(order);
				container.add(orderNo);
			}
		}
	}

}
