package com.rci.service.calculator;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.DiscountScheme;
import com.rci.bean.entity.PostOrderAccount;
import com.rci.bean.entity.account.Account;
import com.rci.service.IAccountService;
import com.rci.service.MoneyCalculateStrategy;

/**
 * 店内折扣算法
 * 
 * @author zj
 * 
 */
@Component("DiscountStrategy")
public class DiscountStrategy implements MoneyCalculateStrategy {
	private transient Log logger = LogFactory.getLog(DiscountStrategy.class);

	protected Log logger() {
		if (logger == null) {
			return LogFactory.getLog(DiscountStrategy.class);
		} else {
			return logger;
		}
	}

	protected DiscountScheme scheme;

	@Resource(name = "AccountService")
	protected IAccountService accService;

	@Override
	public List<PostOrderAccount> calculate(List<OrderItemDTO> items) {
		List<PostOrderAccount> poas = new LinkedList<PostOrderAccount>();
		Account account = accService.getPOSAccount();
		PostOrderAccount poa = new PostOrderAccount();
		BigDecimal postAmount = BigDecimal.ZERO;
		for (OrderItemDTO itemDTO : items) {
			BigDecimal price = itemDTO.getPrice();
			BigDecimal count = itemDTO.getCount();
			BigDecimal backcount = itemDTO.getCountback();
			BigDecimal rate = itemDTO.getDiscountRate();
			logger().debug(
					"Billno[" + itemDTO.getBillNo() + "]," + "orderItem["
							+ itemDTO.getDishNo() + "]" + " - price:" + price
							+ ",");
			BigDecimal actualAmount = algorithm(price, rate, count, backcount);
			postAmount = postAmount.add(actualAmount);
		}
		poa.setAccountId(account.getAid());
		poa.setPostAmount(postAmount);
		poas.add(poa);
		return poas;
	}

	@Override
	public Boolean support(DiscountScheme scheme) {
		this.scheme = scheme;
		if ("01".equals(scheme.getsNo().trim()) || "02".equals(scheme.getsNo().trim())) {
			return true;
		}
		return false;
	}

	private BigDecimal algorithm(BigDecimal price,BigDecimal rate, BigDecimal count,
			BigDecimal backcount) {
		if(rate == null){
			rate = new BigDecimal(100);
		}
		BigDecimal result = price.multiply(count).subtract(price.multiply(backcount)).multiply(rate.divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_CEILING);
		return result;
	}
}
