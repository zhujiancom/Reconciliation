package com.rci.service.strategy;

import java.util.List;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.PostOrderAccount;

public interface CalculateStrategy {
	public Boolean support(List<String> paymode);
	
	public List<PostOrderAccount> calculate(List<OrderItemDTO> items);
	
}
