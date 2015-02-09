package com.rci.service.calculator;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.DiscountScheme;
import com.rci.bean.entity.PostOrderAccount;
import com.rci.service.MoneyCalculateStrategy;

/**
 * 大众点评闪惠
 * @author zj
 *
 */
@Component("DPSHChitStrategy")
public class DPSHChitStrategy extends AbstractStrategy {

	@Override
	public Boolean support(DiscountScheme scheme) {
		if("97".equals(scheme.getsNo())){
			return true;
		}
		return false;
	}

	@Override
	public PostOrderAccount calculate(List<OrderItemDTO> items) {
		return null;
	}

	@Override
	public BigDecimal algorithm(BigDecimal price, BigDecimal count,
			BigDecimal backcount) {
		// TODO Auto-generated method stub
		return null;
	}

}