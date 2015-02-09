package com.rci.service;

import java.util.List;

import com.rci.bean.entity.DishType;

public interface IDishTypeService {
	public DishType findDishTypeByNo(String no);
	public List<DishType> findDishTypesByNo(List<String> nos);
	public void rwSaveDishType(DishType dishType);
	public void rwSaveDishTypes(DishType[] dishTypes);
	
	
}
