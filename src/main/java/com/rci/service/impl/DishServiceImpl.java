package com.rci.service.impl;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.Dish;
import com.rci.datafetch.IDataFetchService;
import com.rci.service.BaseService;
import com.rci.service.IDishService;

@Service("DishService")
public class DishServiceImpl extends BaseService<Dish, Long> implements
		IDishService {
	@Resource(name="DataFetchService")
	private IDataFetchService dataFetch;
	@Autowired
	private Mapper beanMapper;
	
	@Override
	public void rwSaveDish(Dish dish) {
		super.rwCreate(dish);
	}

	@Override
	public Dish findDishByNo(String no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rwSaveDishes(Dish[] dishes) {
		super.rwCreate(dishes);
	}
}
