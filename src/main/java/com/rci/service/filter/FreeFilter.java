package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.bean.scheme.PairKey;
import com.rci.bean.scheme.SchemeWrapper;
import com.rci.constants.BusinessConstant;
import com.rci.constants.enums.SchemeType;

/**
 * 免单
 * @author zj
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FreeFilter extends AbstractFilter {

	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(BusinessConstant.FREE_NO);
	}

	@Override
	public String getChit() {
		return "免单";
	}

	@Override
	protected void generateScheme(Order order, List<OrderItemDTO> items,
			FilterChain chain) {
		Map<PairKey<SchemeType,String>,SchemeWrapper> schemes = order.getSchemes();
		if (CollectionUtils.isEmpty(schemes)) {
			schemes = new HashMap<PairKey<SchemeType,String>,SchemeWrapper>();
			order.setSchemes(schemes);
		}
		BigDecimal freeAmount = order.getPaymodeMapping().get(BusinessConstant.FREE_NO);
		order.setFreeAmount(freeAmount);
		
		Scheme scheme = new Scheme(SchemeType.FREE,getChit());
		SchemeWrapper wrapper = new SchemeWrapper(scheme);
		wrapper.setTotalAmount(freeAmount);
		PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.FREE,BusinessConstant.FREE_NO);
		schemes.put(key, wrapper);
		//计算订余额
		BigDecimal balance = chain.getBalance();
		logger.debug("FreeFilter - balance = "+balance);
		if(balance.compareTo(freeAmount) < 0){
			logger.error("----【"+order.getOrderNo()+"】:免单金额超出了原价");
			order.setUnusual(UNUSUAL);
		}
		balance = balance.subtract(freeAmount);
		chain.setBalance(balance);
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}

}
