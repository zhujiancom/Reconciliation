package com.rci.service.filter;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.DishType;
import com.rci.service.IDishService;
import com.rci.service.IPayModeService;

public abstract class AbstractFilter implements CalculateFilter {
	public static final Log logger = LogFactory.getLog(AbstractFilter.class);
	@Resource(name="DishService")
	private IDishService dishService;
	
	@Resource(name = "PayModeService")
	protected IPayModeService paymodeService;
	/**
	 * 菜品是否可以打折
	 * @param dishNo
	 * @return
	 */
	protected Boolean isNodiscount(String dishNo){
		Dish dish = dishService.findDishByNo(dishNo);
		DishType type = dish.getDishType();
		if(type != null && "N".equalsIgnoreCase(type.getNodiscount())){
			return true;
		}
		return false;
	}
	
	protected Boolean isSingleDiscount(BigDecimal rate){
		if(rate.compareTo(new BigDecimal(80)) < 0 || rate.compareTo(new BigDecimal(80))>0){
			return true;
		}
		return false;
	}
}
