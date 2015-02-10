package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
		List<OrderDTO> orderDTOs = dataFetch.fetchAllDayOrders(DateUtil.str2Date("2015-02-07"));
		Set<String> container = new HashSet<String>();
		//2. 迭代order,获取对应的item信息
		if(!CollectionUtils.isEmpty(orderDTOs)){
			for(OrderDTO orderDTO:orderDTOs){
				String orderNo = orderDTO.getOrderNo();
				String paymode = orderDTO.getPaymode();
				String project = orderDTO.getProject();
				BigDecimal realamount = orderDTO.getActualAmount();
				BigDecimal originamount = orderDTO.getReceivableAmount();
				if(container.contains(orderNo)||"XX".equals(paymode)){
					continue;
				}
				DiscountScheme scheme = null;
				
				if("00".equals(paymode) && !realamount.equals(originamount) && ("无折扣".equals(project) || StringUtils.isEmpty(project))){ //
					continue;
				}
				
				if("00".equals(paymode) && realamount.equals(originamount)){ //肯定是饮料，不享受打折
					scheme = schemeService.getSchemeByNo("02");
				}
				if("00".equals(paymode) && !StringUtils.isEmpty(project)){  //开店优惠政策
					scheme = schemeService.getSchemeByName(project);
				}
				if(!"00".equals(paymode)){  //代金券
					scheme = schemeService.getSchemeByNo(paymode);
					scheme.setChitAmount(calculateChitAmount(realamount));
				}
				
//				if("无折扣".equals(project)&&"00".equals(paymode)&&!realamount.equals(originamount)){
//					continue;
//				}
//				if(project == null || ("无折扣".equals(project)&&!"00".equals(paymode))){ //使用代金券
//					scheme = schemeService.getSchemeByNo(paymode);
//					scheme.setChitAmount(calculateChitAmount(realamount));
//				}else{//获取折扣方案
//					scheme = schemeService.getSchemeByName(project);
//				}
				
				Order order = beanMapper.map(orderDTO, Order.class);
				order.setScheme(scheme);
				List<OrderItemDTO> itemDTOs = dataFetch.fetchOrderItemsByOrder(orderDTO.getOrderNo());
				order = postCalculator.calculate(order, itemDTOs);
				orderService.rwInsertOrder(order);
				container.add(orderNo);
			}
		}
	}
	
	private BigDecimal calculateChitAmount(BigDecimal realamount){
		BigDecimal chitamount = null;
		if(realamount.doubleValue() <= 50){
			chitamount = new BigDecimal(50);
		}else if(realamount.doubleValue() == 88){
			chitamount = new BigDecimal(88);
		}else{
			chitamount = new BigDecimal(100);
		}
		return chitamount;
	}

}
