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
 * 大众点评闪惠
 * 
 * @author zj
 * 
 */
@Component("DPSHChitStrategy")
public class DPSHChitStrategy implements MoneyCalculateStrategy {
	private transient Log logger = LogFactory.getLog(DPSHChitStrategy.class);

	protected Log logger() {
		if (logger == null) {
			return LogFactory.getLog(DPSHChitStrategy.class);
		} else {
			return logger;
		}
	}

	protected DiscountScheme scheme;

	@Resource(name = "AccountService")
	protected IAccountService accService;

	@Override
	public Boolean support(DiscountScheme scheme) {
		this.scheme = scheme;
		if ("97".equals(scheme.getsNo().trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 大众点评闪惠优惠： 满100减30
	 */
	@Override
	public List<PostOrderAccount> calculate(List<OrderItemDTO> items) {
		logger().debug("---- 大众点评闪惠    -------");
		List<PostOrderAccount> poas = new LinkedList<PostOrderAccount>();
		Account shAccount = accService.getDPSHAccount();
		PostOrderAccount shPoa = new PostOrderAccount();
		BigDecimal postAmount = BigDecimal.ZERO;
		for (OrderItemDTO itemDTO : items) {
			BigDecimal price = itemDTO.getPrice();
			BigDecimal count = itemDTO.getCount();
			BigDecimal backcount = itemDTO.getCountback();
			logger().debug(
					"Billno[" + itemDTO.getBillNo() + "]," + "orderItem["
							+ itemDTO.getDishNo() + "]" + " - price:" + price
							+ ",");
			BigDecimal itemPrice = price.multiply(count).subtract(
					price.multiply(backcount));
			postAmount = postAmount.add(itemPrice);
		}
		BigDecimal virtualamount = algorithm(postAmount);
		shPoa.setAccountId(shAccount.getAid());
		shPoa.setPostAmount(virtualamount);
		poas.add(shPoa);
		return poas;
	}

	private BigDecimal algorithm(BigDecimal totalAmount) {
		BigDecimal result = BigDecimal.ZERO;
		result = totalAmount.subtract(new BigDecimal(30)).multiply(
				BigDecimal.ONE.subtract(scheme.getCommission()).divide(
						new BigDecimal(100))).setScale(2,BigDecimal.ROUND_HALF_DOWN);
		return result;
	}

}
