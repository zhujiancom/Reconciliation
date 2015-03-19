package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.bean.scheme.PairKey;
import com.rci.bean.scheme.SchemeWrapper;
import com.rci.constants.enums.SchemeType;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.tools.DigitUtil;

@Component
public class CashFilter extends AbstractFilter {
	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(CASH_NO);
	}

	@Override
	public void doFilter(Order order, List<OrderItemDTO> items,
			FilterChain chain) {
		Map<PairKey<SchemeType,String>,SchemeWrapper> schemes = order.getSchemes();
		if (CollectionUtils.isEmpty(schemes)) {
			schemes = new HashMap<PairKey<SchemeType,String>,SchemeWrapper>();
		}
		
		BigDecimal cashAmount = order.getPaymodeMapping().get(CASH_NO);
		BigDecimal originAmount = order.getOriginPrice();
		BigDecimal actualAmount = BigDecimal.ZERO;
		for(OrderItemDTO item:items){
			BigDecimal singlePrice = item.getPrice();
			BigDecimal count = item.getCount();
			BigDecimal countback = item.getCountback();
			BigDecimal ratepercent = item.getDiscountRate();
			BigDecimal rate = BigDecimal.ONE.subtract(DigitUtil.precentDown(ratepercent, new BigDecimal(100)));
			BigDecimal price = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countback)),rate).setScale(0, BigDecimal.ROUND_CEILING);
			actualAmount = actualAmount.add(price);
			if(isSingleDiscount(ratepercent) &&!order.getSingleDiscount()){
				order.setSingleDiscount(true);
			}
		}
		SchemeWrapper wrapper = null;
		if(actualAmount.compareTo(originAmount)<0){
			//可享受8折优惠
			if(cashAmount.compareTo(actualAmount) != 0){
				//如果收银机显示现金收入和计算收入不相符时，报异常
				order.setUnusual(UNUSUAL);
				logger.debug("[--- CashFilter ---]:#8折优惠# 收银机显示金额："+cashAmount+" , 应该显示金额： "+actualAmount);
			}
			Scheme scheme = paymodeService.getScheme(SchemeType.EIGHTDISCOUNT,CASH_NO);
			wrapper = new SchemeWrapper(getChit(),scheme);
			wrapper.setTotalAmount(actualAmount);
			PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.EIGHTDISCOUNT,CASH_NO);
			schemes.put(key, wrapper);
		}
		if(actualAmount.compareTo(originAmount) > 0){
			ExceptionManage.throwServiceException(SERVICE.DATA_ERROR, "数据错误");
		}
		if(actualAmount.compareTo(originAmount)==0){
			//无折扣
			if(cashAmount.compareTo(actualAmount) != 0){
				//如果收银机显示现金收入和计算收入不相符时，报异常
				order.setUnusual(UNUSUAL);
				logger.debug("[--- CashFilter ---]:#无折扣# 收银机显示金额："+cashAmount+" , 应该显示金额： "+actualAmount);
			}
			Scheme scheme = paymodeService.getScheme(SchemeType.NODISCOUNT,CASH_NO);
			wrapper = new SchemeWrapper(getChit(),scheme);
			wrapper.setTotalAmount(actualAmount);
			PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.NODISCOUNT,CASH_NO);
			schemes.put(key, wrapper);
		}
	}

	@Override
	public String getChit() {
		return "现金";
	}

}
