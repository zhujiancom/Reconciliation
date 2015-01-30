package com.rci.service.impl;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.DishType;
import com.rci.service.BaseService;
import com.rci.service.IDishTypeService;

@Service("DishTypeService")
public class DishTypeServiceImpl extends BaseService<DishType, Long> implements
		IDishTypeService {
	
	@Override
	public void rwSaveDishType(DishType dishType) {
		super.rwCreate(dishType);
	}
	
	

	@Override
	public DishType findDishTypeByNo(String no) {
		return null;
	}

	@Override
	public void rwSaveDishTypes(DishType[] dishTypes) {
		super.rwCreate(dishTypes);
	}

}
