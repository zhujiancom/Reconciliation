package com.rci.datafetch;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.rci.bean.DishDTO;
import com.rci.bean.DishTypeDTO;
import com.rci.bean.OrderDTO;
import com.rci.bean.OrderItemDTO;
import com.rci.tools.DateUtil;

@ContextConfiguration({"classpath:spring/spring-db.xml","classpath:spring/spring-common.xml"})
public class DataFetchTest extends AbstractJUnit4SpringContextTests{
	@Resource(name="DataFetchService")
	private IDataFetchService datafetch;

	@Test
	public void testFetchDish() {
		List<DishDTO> dishes = datafetch.fetchDish();
		for(DishDTO dish:dishes){
			System.out.println(dish.getDishName());
		}
	}

	@Test
	public void testFetchDishType() {
		List<DishTypeDTO> types = datafetch.fetchDishType();
		for(DishTypeDTO type:types){
			System.out.println(type.getDtNo()+" - "+type.getDtName());
		}
	}
	
	@Test
	public void testFetchAllDayOrders(){
		Date sdate = DateUtil.str2Date("2015-01-21");
		List<OrderDTO> orders = datafetch.fetchAllDayOrders(sdate);
		int count = 0;
		for(OrderDTO order:orders){
			System.out.println((++count)+"-"+order.getOrderNo()+"-"+order.getPayNo()+"-"+order.getPaymode());
			List<OrderItemDTO> items = datafetch.fetchOrderItemsByOrder(order.getOrderNo());
			for(OrderItemDTO item:items){
				System.out.println("\t\t\t"+item.getDishNo()+"-"+item.getCount()+"-"+item.getCountback()+"-"+item.getActualAmount());
			}
		}
	}
}
