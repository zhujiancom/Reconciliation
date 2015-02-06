package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

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
		List<PostOrderAccount> accounts = new LinkedList<PostOrderAccount>();
		List<OrderItem> items = new LinkedList<OrderItem>();
		BigDecimal actualAmount = BigDecimal.ZERO;
		for(MoneyCalculateStrategy calculator:calculators){
			if(calculator.support(order.getScheme())){
				PostOrderAccount account = calculator.calculate(itemDTOs);
				account.setOrder(order);
				actualAmount.add(account.getPostAmount());
				accounts.add(account);
			}
		}
		//设置order金额入账的账户
		order.setPostOrderAccounts(accounts);
		for(OrderItemDTO itemDTO:itemDTOs){
			OrderItem item = beanMapper.map(itemDTO, OrderItem.class);
			item.setOrder(order);
			Dish dish = dishService.findDishByNo(itemDTO.getDishNo());
			item.setDish(dish);
			items.add(item);
		}
		//设置order关联的item
		order.setItems(items);
		order.setActualAmount(actualAmount);
		return order;
	}

}
