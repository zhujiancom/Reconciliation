package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.bean.scheme.PairKey;
import com.rci.bean.scheme.SchemeWrapper;
import com.rci.constants.enums.SchemeType;
import com.rci.tools.DigitUtil;

public class TDDFilter extends AbstractFilter {

	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(TDD_NO);
	}

	@Override
	public void doFilter(Order order, List<OrderItemDTO> items,
			FilterChain chain) {
		if(support(order.getPaymodeMapping())){
			Map<PairKey<SchemeType,String>,SchemeWrapper> schemes = order.getSchemes();
			if (CollectionUtils.isEmpty(schemes)) {
				schemes = new HashMap<PairKey<SchemeType,String>,SchemeWrapper>();
				order.setSchemes(schemes);
			}
			BigDecimal onlineAmount = order.getPaymodeMapping().get(TDD_NO);
			BigDecimal actualAmount = BigDecimal.ZERO;
			for(OrderItemDTO item:items){
				BigDecimal singlePrice = item.getPrice();
				BigDecimal count = item.getCount();
				BigDecimal countback = item.getCountback();
				BigDecimal ratepercent = item.getDiscountRate();
				BigDecimal rate = DigitUtil.precentDown(ratepercent, new BigDecimal(100));
				BigDecimal price = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countback)),rate).setScale(0, BigDecimal.ROUND_CEILING);
				actualAmount = actualAmount.add(price);
				if(isSingleDiscount(ratepercent) && (order.getSingleDiscount() == null || !order.getSingleDiscount())){
					order.setSingleDiscount(true);
				}
			}
			if(actualAmount.compareTo(onlineAmount) != 0){
				order.setUnusual(UNUSUAL);
				logger.debug("[--- TDDFilter ---]: 收银机显示金额："+onlineAmount+" , 应该显示金额： "+actualAmount);
			}
			Scheme scheme = new Scheme(SchemeType.VIRTUALPAY,getChit());
			SchemeWrapper wrapper = new SchemeWrapper(scheme);
			wrapper.setTotalAmount(actualAmount);
			PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.VIRTUALPAY,TDD_NO);
			schemes.put(key, wrapper);
		}
		chain.doFilter(order, items, chain);
	}

	@Override
	public String getChit() {
		return "淘点点";
	}

}
