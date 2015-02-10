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
 * 美团代金券算法
 * 
 * @author zj
 * 
 */
@Component("MTChitStrategy")
public class MTChitStrategy implements MoneyCalculateStrategy {
	private transient Log logger = LogFactory.getLog(MTChitStrategy.class);

	protected Log logger() {
		if (logger == null) {
			return LogFactory.getLog(MTChitStrategy.class);
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
		if ("99".equals(scheme.getsNo().trim())) {
			return true;
		}
		return false;
	}

	@Override
	public List<PostOrderAccount> calculate(List<OrderItemDTO> items) {
		logger().debug("---- 美团团购-" + scheme.getChitAmount() + " 代金券-------");
		List<PostOrderAccount> poas = new LinkedList<PostOrderAccount>();
		Account mtAccount = accService.getMTAccount();
		Account posAccount = accService.getPOSAccount();
		PostOrderAccount posPoa = new PostOrderAccount();
		PostOrderAccount mtPoa = new PostOrderAccount();
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
		BigDecimal chitamount = scheme.getChitAmount();
		BigDecimal[] result = algorithm(chitamount, postAmount);
		BigDecimal cashamount = result[0];
		BigDecimal virtualamount = result[1];
		posPoa.setAccountId(posAccount.getAid());
		posPoa.setPostAmount(cashamount);
		mtPoa.setAccountId(mtAccount.getAid());
		mtPoa.setPostAmount(virtualamount);
		poas.add(posPoa);
		poas.add(mtPoa);
		return poas;
	}

	private BigDecimal[] algorithm(BigDecimal chitamount, BigDecimal postAmount) {
		BigDecimal[] result = new BigDecimal[2];
		BigDecimal cashamount = BigDecimal.ZERO;
		BigDecimal virtualamount = BigDecimal.ZERO;
		/* 使用50代金券 */
		if (chitamount.intValue() == 50) {
			cashamount = postAmount.subtract(chitamount);
			if (cashamount.intValue() <= 0) {
				cashamount = BigDecimal.ZERO;
			}
			virtualamount = new BigDecimal(35).multiply(BigDecimal.ONE
					.subtract(scheme.getCommission().divide(
							new BigDecimal(100)))).subtract(new BigDecimal(0.05));
		}
		/* 使用100代金券 */
		if (chitamount.intValue() == 100) {
			cashamount = postAmount.subtract(chitamount);
			if (cashamount.intValue() <= 0) {
				cashamount = BigDecimal.ZERO;
			}
			virtualamount = new BigDecimal(70).multiply(BigDecimal.ONE
					.subtract(scheme.getCommission().divide(
							new BigDecimal(100))));
		}
		/* 使用88套餐代金券 */
		if (chitamount.intValue() == 88) {
			cashamount = postAmount.subtract(chitamount);
			if (cashamount.intValue() <= 0) {
				cashamount = BigDecimal.ZERO;
			}
			virtualamount = new BigDecimal(88).multiply(BigDecimal.ONE
					.subtract(scheme.getCommission().divide(
							new BigDecimal(100))));
		}
		result[0] = cashamount;
		result[1] = virtualamount;
		return result;
	}
}
