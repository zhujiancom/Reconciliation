package com.rci.service.strategy;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Dish;
import com.rci.bean.entity.DishType;
import com.rci.bean.entity.PostOrderAccount;
import com.rci.service.IDishService;

public class DPTGStrategyImpl implements CalculateStrategy{
	@Resource(name="DishService")
	private IDishService dishService;

	@Override
	public Boolean support(List<String> paymode) {
		if(paymode.contains("98")){
			return true;
		}
		return false;
	}

	@Override
	public List<PostOrderAccount> calculate(List<OrderItemDTO> items) {
		/* 不能使用代金券的菜品总额  。 即酒水和配料*/
		BigDecimal nodiscountAmount = BigDecimal.ZERO;
		/* 正常菜品，可以使用代金券的总金额  */
		BigDecimal chitAmount = BigDecimal.ZERO;
		return null;
	}
	
	/**
	 * 菜品是否可以打折
	 * @param dishNo
	 * @return
	 */
	private Boolean isNodiscount(String dishNo){
		Dish dish = dishService.findDishByNo(dishNo);
		DishType type = dish.getDishType();
		if(type != null && "N".equalsIgnoreCase(type.getNodiscount())){
			return true;
		}
		return false;
	}

}
