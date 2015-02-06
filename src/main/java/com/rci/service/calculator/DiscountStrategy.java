package com.rci.service.calculator;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.DiscountScheme;
import com.rci.bean.entity.PostOrderAccount;
import com.rci.bean.entity.account.Account;

/**
 * 店内折扣算法
 * @author zj
 *
 */
@Component("DiscountStrategy")
public class DiscountStrategy extends AbstractStrategy {
	@Override
	public PostOrderAccount calculate(List<OrderItemDTO> items) {
		Account account = accService.getPOSAccount();
		PostOrderAccount poa = new PostOrderAccount();
		BigDecimal postAmount = BigDecimal.ZERO;
		for(OrderItemDTO itemDTO:items){
			BigDecimal price = itemDTO.getPrice();
			BigDecimal count = itemDTO.getCount();
			BigDecimal backcount = itemDTO.getCountback();
			postAmount = postAmount.add(algorithm(price,count,backcount));
		}
		poa.setAccountId(account.getAid());
		poa.setPostAmount(postAmount);
		return null;
	}

	@Override
	public Boolean support(DiscountScheme scheme) {
		super.scheme = scheme;
		if("01".equals(scheme.getsNo()) || "02".equals(scheme.getsNo())){
			return true;
		}
		return false;
	}
	
	@Override
	protected BigDecimal algorithm(BigDecimal price, BigDecimal count,BigDecimal backcount){
		BigDecimal result = price.multiply(count).subtract(price.multiply(backcount)).multiply(scheme.getRate().divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_CEILING);
		return result;
	}
}
