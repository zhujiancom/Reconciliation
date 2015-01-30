package com.rci.service;

import com.rci.bean.entity.DishType;

public interface IDishTypeService {
	public DishType findDishTypeByNo(String no);
	public void rwSaveDishType(DishType dishType);
	public void rwSaveDishTypes(DishType[] dishTypes);
}
