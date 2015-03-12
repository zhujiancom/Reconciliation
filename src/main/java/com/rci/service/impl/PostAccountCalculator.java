package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.bean.entity.PostOrderAccount;
import com.rci.service.IDishService;
import com.rci.service.MoneyCalculateStrategy;

@Component("PostAccountCalculator")
public class PostAccountCalculator{
	@Autowired
	private List<MoneyCalculateStrategy> calculators;
	@Autowired
	private Mapper beanMapper;
	@Resource(name="DishService")
	private IDishService dishService;
	
	public Order calculate(Order order,List<OrderItemDTO> itemDTOs) {
//		List<PostOrderAccount> accounts = new LinkedList<PostOrderAccount>();
		List<OrderItem> items = new LinkedList<OrderItem>();
		BigDecimal actualAmount = BigDecimal.ZERO;
		for(MoneyCalculateStrategy calculator:calculators){
			if(calculator.support(order.getScheme())){
				List<PostOrderAccount> accounts = calculator.calculate(itemDTOs);
				
				Set<PostOrderAccount> accountss = new HashSet<PostOrderAccount>();
				
				
				for(PostOrderAccount account:accounts){
					account.setOrder(order);
					actualAmount = actualAmount.add(account.getPostAmount());
					accountss.add(account);
				}
				//设置order金额入账的账户
				order.setPostOrderAccounts(accounts);
				break;
			}
		}
		
//		Set<OrderItem> itemss = new HashSet<OrderItem>();
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
//			itemss.add(item);
		}
		//设置order关联的item
		order.setItems(items);
		order.setActualAmount(actualAmount);
		return order;
	}

}
