package com.rci.service.impl;

import java.util.List;

import com.rci.bean.entity.Order;
import com.rci.service.BaseService;
import com.rci.service.IOrderService;

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
		// TODO Auto-generated method stub
		return null;
	}

}
