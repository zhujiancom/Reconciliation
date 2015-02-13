package com.rci.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.Order;
import com.rci.service.BaseService;
import com.rci.service.IOrderService;
import com.rci.tools.StringUtility;

@Service("OrderService")
public class OrderServiceImpl extends BaseService<Order, Long> implements
		IOrderService {

	@Override
	public Order getOrder(Long pk) {
		return super.get(pk);
	}

	@Override
	public void rwInsertOrder(Order order) {
		rwCreate(order);
	}

	@Override
	public List<Order> queryAllDayOrders() {
		return null;
	}

	@Override
	public List<Order> queryOrdersByDay(String day) {
		if(!StringUtility.isDateFormated(day)){
			System.out.println("日期格式不正确");
			return null;
		}
		return null;
	}
}
