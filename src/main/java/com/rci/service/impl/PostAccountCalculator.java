package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.DiscountScheme;
import com.rci.service.MoneyCalculateStrategy;

@Service("PostAccountCalculator")
public class PostAccountCalculator{
	@Autowired
	private List<MoneyCalculateStrategy> calculators;
	
	public void calculate(DiscountScheme scheme,List<BigDecimal> dishPrices) {
		for(MoneyCalculateStrategy calculator:calculators){
			if(calculator.support(scheme)){
				calculator.calculate(dishPrices);
			}
		}
	}

}
