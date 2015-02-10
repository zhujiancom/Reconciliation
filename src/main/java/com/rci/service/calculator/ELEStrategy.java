package com.rci.service.calculator;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.DiscountScheme;
import com.rci.bean.entity.PostOrderAccount;
import com.rci.bean.entity.account.Account;

public class ELEStrategy extends AbstractStrategy {
	private transient Log logger = LogFactory.getLog(ELEStrategy.class);

	protected Log logger() {
		if (logger == null) {
			return LogFactory.getLog(ELEStrategy.class);
		} else {
			return logger;
		}
	}

	@Override
	public Boolean support(DiscountScheme scheme) {
		super.scheme = scheme;
		if ("11".equals(scheme.getsNo())) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<PostOrderAccount> calculate(List<OrderItemDTO> items) {
		logger().debug("---- 饿了么在线支付  -------");
		List<PostOrderAccount> poas = Collections.emptyList();
		Account eleAccount = accService.getELEAccount();
		PostOrderAccount elePoa = new PostOrderAccount();
		BigDecimal postAmount = BigDecimal.ZERO;
		for(OrderItemDTO itemDTO:items){
			BigDecimal price = itemDTO.getPrice();
			BigDecimal count = itemDTO.getCount();
			BigDecimal backcount = itemDTO.getCountback();
			logger().debug("Billno["+itemDTO.getBillNo()+"],"+"orderItem["+itemDTO.getDishNo()+"]"+" - price:"+price+",");
			BigDecimal itemPrice = price.multiply(count).subtract(price.multiply(backcount));
			postAmount = postAmount.add(itemPrice);
		}
		elePoa.setAccountId(eleAccount.getAid());
		elePoa.setPostAmount(postAmount);
		poas.add(elePoa);
		return poas;
	}
}
