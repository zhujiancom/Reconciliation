package com.rci.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.rci.bean.entity.Order;

@ContextConfiguration({"classpath:spring/spring-db.xml","classpath:spring/spring-common.xml"})
public class OrderServiceTest extends AbstractJUnit4SpringContextTests{
	@Resource(name="OrderService")
	private IOrderService orderService;
	@Test
	public void test1() {
		List<Order> orders = orderService.queryOrdersByDay("20150325");
		orderService.rwDeleteOrders(orders.toArray(new Order[0]));
	}
	
	@Test
	public void test2() {
		orderService.rwDeleteOrders("20150325");
	}

}
