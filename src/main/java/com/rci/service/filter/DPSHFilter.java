package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.bean.scheme.SchemeWrapper;
import com.rci.constants.enums.SchemeType;
import com.rci.tools.DigitUtil;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DPSHFilter extends AbstractFilter {

	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(DPSH_NO);
	}

	@Override
	public void generateScheme(Order order, List<OrderItemDTO> items,
			FilterChain chain) {
		if(support(order.getPaymodeMapping())){
			BigDecimal totalAmount = BigDecimal.ZERO;
			BigDecimal payAmount = order.getPaymodeMapping().get(DPSH_NO);
			for(OrderItemDTO item:items){
				BigDecimal singlePrice = item.getPrice();
				BigDecimal count = item.getCount();
				BigDecimal countback = item.getCountback();
				BigDecimal itemPrice = DigitUtil.mutiplyDown(singlePrice, count.subtract(countback));
				totalAmount = totalAmount.add(itemPrice);
			}
			Scheme scheme = schemeService.getScheme(SchemeType.CASHBACK,DPSH_NO);
			SchemeWrapper wrapper = new SchemeWrapper(getChit(),scheme);
			wrapper.setTotalAmount(totalAmount);
			if(totalAmount.compareTo(payAmount) != 0){
				order.setUnusual(UNUSUAL);
			}
		}
		chain.doFilter(order, items, chain);
	}

	@Override
	public String getChit() {
		return "大众点评闪惠";
	}

	@Override
	protected Map<SchemeType, Integer> getChitMap() {
		// TODO Auto-generated method stub
		return null;
	}


}
