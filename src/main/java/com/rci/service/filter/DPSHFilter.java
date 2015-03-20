package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Order;

@Component
public class DPSHFilter extends AbstractFilter {

	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(DPSH_NO);
	}

	@Override
	public void doFilter(Order order, List<OrderItemDTO> items,
			FilterChain chain) {
		if(support(order.getPaymodeMapping())){
			
		}
		chain.doFilter(order, items, chain);
	}

	@Override
	public String getChit() {
		return "大众点评闪惠";
	}

}
