package com.rci.service;

import java.util.List;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.DiscountScheme;
import com.rci.bean.entity.PostOrderAccount;

public interface MoneyCalculateStrategy {
	public Boolean support(DiscountScheme scheme);
	
	public List<PostOrderAccount> calculate(List<OrderItemDTO> items);
	
}
