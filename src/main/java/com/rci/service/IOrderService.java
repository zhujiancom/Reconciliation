package com.rci.service;

import java.util.List;

import com.rci.bean.entity.Order;
import com.rci.ui.vo.OrderItemVO;
import com.rci.ui.vo.OrderVO;

public interface IOrderService {
	Order getOrder(Long pk);
	
	void rwInsertOrder(Order order);
	
	List<Order> queryAllDayOrders();
	
	List<Order> queryOrdersByDay(String day);
	
	List<OrderVO> queryOrderVOsByDay(String day);
	
	List<OrderItemVO> queryOrderItemVOsByPayno(String payno);
}
