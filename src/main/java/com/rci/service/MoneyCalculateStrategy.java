package com.rci.service;

import java.math.BigDecimal;
import java.util.List;

import com.rci.bean.entity.DiscountScheme;

public interface MoneyCalculateStrategy {
	public Boolean support(DiscountScheme secheme);
	
	public BigDecimal calculate(List<BigDecimal> dishPrices);
}
