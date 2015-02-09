package com.rci.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
		DetachedCriteria dc = DetachedCriteria.forClass(DishType.class);
		dc.add(Restrictions.eq("dtNo", no));
		return baseDAO.queryUniqueByCriteria(dc);
	}

	@Override
	public void rwSaveDishTypes(DishType[] dishTypes) {
		super.rwCreate(dishTypes);
	}

	@Override
	public List<DishType> findDishTypesByNo(List<String> nos) {
		if(CollectionUtils.isEmpty(nos)){
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(DishType.class);
		dc.add(Restrictions.in("dtNo", nos));
		List<DishType> dishTypes = baseDAO.queryListByCriteria(dc);
		return dishTypes;
	}

}
