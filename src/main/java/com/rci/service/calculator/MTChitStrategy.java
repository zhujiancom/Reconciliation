package com.rci.service.calculator;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.DiscountScheme;
import com.rci.bean.entity.PostOrderAccount;

/**
 * 美团代金券算法
 * @author zj
 *
 */
@Component("MTChitStrategy")
public class MTChitStrategy extends AbstractStrategy{

	@Override
	public PostOrderAccount calculate(List<OrderItemDTO> items) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean support(DiscountScheme scheme) {
		if("99".equals(scheme.getsNo())){
			return true;
		}
		return false;
	}

	@Override
	public BigDecimal algorithm(BigDecimal price, BigDecimal count,
			BigDecimal backcount) {
		// TODO Auto-generated method stub
		return null;
	}

}
