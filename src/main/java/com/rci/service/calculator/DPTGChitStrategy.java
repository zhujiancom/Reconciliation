package com.rci.service.calculator;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.DiscountScheme;
import com.rci.bean.entity.PostOrderAccount;
import com.rci.bean.entity.account.Account;
import com.rci.service.MoneyCalculateStrategy;

/**
 * 大众点评团购
 * 
 * @author zj
 * 
 */
@Component("DPTGChitStragegy")
public class DPTGChitStrategy extends AbstractStrategy {
	private transient Log logger = LogFactory.getLog(DPTGChitStrategy.class);

	protected Log logger() {
		if (logger == null) {
			return LogFactory.getLog(DPTGChitStrategy.class);
		} else {
			return logger;
		}
	}

	@Override
	public Boolean support(DiscountScheme scheme) {
		if ("98".equals(scheme.getsNo())) {
			return true;
		}
		return false;
	}

	@Override
	public PostOrderAccount calculate(List<OrderItemDTO> items) {
		Account account = accService.getDPTGAccount();
		PostOrderAccount poa = new PostOrderAccount();
		BigDecimal postAmount = BigDecimal.ZERO;
		for(OrderItemDTO itemDTO:items){
			BigDecimal price = itemDTO.getPrice();
			BigDecimal count = itemDTO.getCount();
			BigDecimal backcount = itemDTO.getCountback();
			logger().debug("Billno["+itemDTO.getBillNo()+"],"+"orderItem["+itemDTO.getDishNo()+"]"+" - price:"+price+",");
			postAmount = postAmount.add(algorithm(price,count,backcount));
		}
		poa.setAccountId(account.getAid());
		poa.setPostAmount(postAmount);
		return poa;
	}

	@Override
	protected BigDecimal algorithm(BigDecimal price, BigDecimal count,
			BigDecimal backcount) {
		return null;
	}

}
