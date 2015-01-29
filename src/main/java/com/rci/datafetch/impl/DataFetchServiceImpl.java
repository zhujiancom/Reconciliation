package com.rci.datafetch.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rci.bean.DishDTO;
import com.rci.bean.DishTypeDTO;
import com.rci.bean.OrderDTO;
import com.rci.bean.OrderItemDTO;
import com.rci.datafetch.IDataFetchService;
import com.rci.datafetch.SQLGen;
import com.rci.mapper.BeanRowMapper;

@Service("DataFetchService")
public class DataFetchServiceImpl implements IDataFetchService {
	@Resource(name="sqlServerJdbcTemplate")
	private JdbcTemplate sqlServerJdbcTemplate;
	
	@Override
	public OrderDTO fetchOrderByNo(String orderNo) {
		OrderDTO order = new OrderDTO();
		return null;
	}

	@Override
	public List<OrderDTO> fetchAllDayOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderItemDTO> fetchOrderItemsByOrder(String orderNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DishDTO> fetchDish() {
		List<DishDTO> dishes = sqlServerJdbcTemplate.query(SQLGen.QUERY_DISH, new BeanRowMapper(DishDTO.class));
		return dishes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DishTypeDTO> fetchDishType() {
		List<DishTypeDTO> dish_types = sqlServerJdbcTemplate.query(SQLGen.QUERY_DISH_TYPE, new BeanRowMapper(DishTypeDTO.class));
		return dish_types;
	}

}
