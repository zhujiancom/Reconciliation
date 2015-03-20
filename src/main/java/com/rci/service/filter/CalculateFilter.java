package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Order;
import com.rci.constants.BusinessConstant;

public interface CalculateFilter {
	public static final String CASH_NO=BusinessConstant.CASH_NO;
	public static final String DPTG_NO=BusinessConstant.DPTG_NO;
	public static final String DPSH_NO=BusinessConstant.DPSH_NO;
	public static final String MT_NO=BusinessConstant.MT_NO;
	public static final String ELE_NO=BusinessConstant.ELE_NO;
	public static final String TDD_NO=BusinessConstant.TDD_NO;
	
//	public static final Integer OLD_BIGSUIT_PRICE=(Integer)PropertyUtils.getProperties(PropertyConstants.BIGSUIT_1);
//	public static final Integer NEW_BIGSUIT_PRICE=(Integer)PropertyUtils.getProperties(PropertyConstants.BIGSUIT_2);
//	public static final Integer OLD_SMALLSUIT_PRICE=(Integer)PropertyUtils.getProperties(PropertyConstants.SMALLSUIT_1);
//	public static final Integer NEW_SMALLSUIT_PRICE=(Integer)PropertyUtils.getProperties(PropertyConstants.SMALLSUIT_2);
	
	public static final Integer OLD_BIGSUIT_PRICE=88;
	public static final Integer NEW_BIGSUIT_PRICE=98;
	public static final Integer OLD_SMALLSUIT_PRICE=40;
	public static final Integer NEW_SMALLSUIT_PRICE=50;
	
	public static final Integer NORMAL=0;
	public static final Integer UNUSUAL=1;
	
	boolean support(Map<String,BigDecimal> paymodeMapping);
	
	void doFilter(Order order,List<OrderItemDTO> items,FilterChain chain);
	
	String getChit();
}
