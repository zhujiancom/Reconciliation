package com.rci.service.calculator;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.DiscountScheme;
import com.rci.bean.entity.PostOrderAccount;
import com.rci.service.IAccountService;
import com.rci.service.MoneyCalculateStrategy;

public abstract class AbstractStrategy implements MoneyCalculateStrategy {
	protected DiscountScheme scheme;
	
	@Resource(name="AccountService")
	protected IAccountService accService;
	
	@Override
	public PostOrderAccount calculate(List<OrderItemDTO> items) {
		return null;
	}

	protected abstract BigDecimal algorithm(BigDecimal price, BigDecimal count,BigDecimal backcount);

}
