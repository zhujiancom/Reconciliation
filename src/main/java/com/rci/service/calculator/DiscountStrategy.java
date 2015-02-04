package com.rci.service.calculator;

import java.math.BigDecimal;
import java.util.List;

import com.rci.bean.entity.DiscountScheme;
import com.rci.service.MoneyCalculateStrategy;

/**
 * 店内折扣算法
 * @author zj
 *
 */
public class DiscountStrategy implements MoneyCalculateStrategy {

	@Override
	public BigDecimal calculate(List<BigDecimal> dishPrices) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean support(DiscountScheme secheme) {
		// TODO Auto-generated method stub
		return null;
	}

}
