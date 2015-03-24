package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Order;

public class FilterChain implements CalculateFilter {
	LinkedList<CalculateFilter> filters = new LinkedList<CalculateFilter>();
	int pos = 0;
	BigDecimal balance = BigDecimal.ZERO;
	
	public void addFilter(CalculateFilter filter){
		this.filters.add(filter);
	}
	
	public void addFilters(List<CalculateFilter> filters){
		this.filters.addAll(filters);
	}
	
	
	@Override
	public void doFilter(Order order, List<OrderItemDTO> items,
			FilterChain chain) {
		if(pos == filters.size()){
			return;
		}
		CalculateFilter filter = filters.get(pos);
		pos++;
		filter.doFilter(order, items, chain);
	}

	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getChit() {
		throw new UnsupportedOperationException();
	}
	
	public void reset(){
		this.pos = 0;
		this.balance = BigDecimal.ZERO;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
