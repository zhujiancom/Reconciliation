package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Order;
import com.rci.constants.PropertyConstants;
import com.rci.tools.properties.PropertyUtils;

public interface CalculateFilter {
	public static final String CASH_NO=(String) PropertyUtils.getProperties(PropertyConstants.CASH);
	public static final String DPTG_NO=(String) PropertyUtils.getProperties(PropertyConstants.DPTG);
	public static final String DPSH_NO=(String) PropertyUtils.getProperties(PropertyConstants.DPSH);
	public static final String MT_NO=(String) PropertyUtils.getProperties(PropertyConstants.MT);
	public static final String ELE_NO=(String) PropertyUtils.getProperties(PropertyConstants.ELE);
	public static final String TDD_NO=(String) PropertyUtils.getProperties(PropertyConstants.TDD);
	
	public static final Integer OLD_BIGSUIT_PRICE=(Integer)PropertyUtils.getProperties(PropertyConstants.BIGSUIT_1);
	public static final Integer NEW_BIGSUIT_PRICE=(Integer)PropertyUtils.getProperties(PropertyConstants.BIGSUIT_2);
	public static final Integer OLD_SMALLSUIT_PRICE=(Integer)PropertyUtils.getProperties(PropertyConstants.SMALLSUIT_1);
	public static final Integer NEW_SMALLSUIT_PRICE=(Integer)PropertyUtils.getProperties(PropertyConstants.SMALLSUIT_2);
	
	public static final Integer NORMAL=0;
	public static final Integer UNUSUAL=1;
	
	boolean support(Map<String,BigDecimal> paymodeMapping);
	
	void doFilter(Order order,List<OrderItemDTO> items,FilterChain chain);
}
