package com.rci.service;

import com.rci.bean.entity.Dish;

public interface IDishService {
	public void rwSaveDish(Dish dish);
	
	public Dish findDishByNo(String no);
	
	public void rwSaveDishes(Dish[] dishes);
}
