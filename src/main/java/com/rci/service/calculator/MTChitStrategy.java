package com.rci.service.calculator;

import java.math.BigDecimal;
import java.util.List;

import com.rci.bean.entity.DiscountScheme;
import com.rci.service.MoneyCalculateStrategy;

/**
 * 美团代金券算法
 * @author zj
 *
 */
public class MTChitStrategy implements MoneyCalculateStrategy {

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
