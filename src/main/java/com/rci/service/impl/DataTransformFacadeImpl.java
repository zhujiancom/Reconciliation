package com.rci.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.OrderDTO;
import com.rci.bean.OrderItemDTO;
import com.rci.datafetch.IDataFetchService;
import com.rci.service.DataTransformFacade;
import com.rci.tools.DateUtil;

@Service("DataTransformFacade")
public class DataTransformFacadeImpl implements DataTransformFacade {
	@Resource(name="DataFetchService")
	private IDataFetchService dataFetch;
	@Autowired
	private Mapper beanMapper;
	
	@Override
	public void accquireOrderInfo() {
//		List<OrderDTO> orderDTOs = dataFetch.fetchAllDayOrders(DateUtil.wipeoutHMS(new Date()));
		// 1. 获取order 信息
		List<OrderDTO> orderDTOs = dataFetch.fetchAllDayOrders(DateUtil.str2Date("2015-01-22"));
		//2. 迭代order,获取对应的item信息
		if(!CollectionUtils.isEmpty(orderDTOs)){
			for(OrderDTO orderDTO:orderDTOs){
				List<OrderItemDTO> itemDTOs = dataFetch.fetchOrderItemsByOrder(orderDTO.getOrderNo());
				//3. 根据orderItem 补全order 信息
				if(!CollectionUtils.isEmpty(itemDTOs)){
					for(OrderItemDTO itemDTO:itemDTOs){
						
					}
				}
			}
		}
	}

}
