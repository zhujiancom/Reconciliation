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
import com.rci.tools.DigitUtil;

/**
 * pos机现金
 * @author zj
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CashFilter extends AbstractFilter {
	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(BusinessConstant.CASH_NO);
	}

	@Override
	public void generateScheme(Order order, List<OrderItemDTO> items,
			FilterChain chain) {
//		if(support(order.getPaymodeMapping())){
			Map<PairKey<SchemeType,String>,SchemeWrapper> schemes = order.getSchemes();
			if (CollectionUtils.isEmpty(schemes)) {
				schemes = new HashMap<PairKey<SchemeType,String>,SchemeWrapper>();
				order.setSchemes(schemes);
			}
			
			BigDecimal cashAmount = order.getPaymodeMapping().get(BusinessConstant.CASH_NO);
			BigDecimal originAmount = order.getOriginPrice();
			BigDecimal actualAmount = BigDecimal.ZERO;
			for(OrderItemDTO item:items){
				BigDecimal singlePrice = item.getPrice();
				BigDecimal count = item.getCount();
				BigDecimal countback = item.getCountback();
				BigDecimal ratepercent = item.getDiscountRate();
				BigDecimal rate = DigitUtil.precentDown(ratepercent, new BigDecimal(100));
				BigDecimal price = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(singlePrice, count.subtract(countback)),rate);
				actualAmount = actualAmount.add(price);
				if(isSingleDiscount(ratepercent) && (order.getSingleDiscount() == null || !order.getSingleDiscount())){
					order.setSingleDiscount(true);
				}
			}
			//整单结算 向上取整
			actualAmount = actualAmount.setScale(0, BigDecimal.ROUND_CEILING);
			SchemeWrapper wrapper = null;
			if(actualAmount.compareTo(originAmount)<0){
				//可享受8折优惠
				if(cashAmount.compareTo(actualAmount) != 0){
					//如果收银机显示现金收入和计算收入不相符时，报异常
					order.setUnusual(UNUSUAL);
					logger.debug("----【"+order.getOrderNo()+"】:#8折优惠# 收银机显示金额："+cashAmount+" , 应该显示金额： "+actualAmount);
				}
				Scheme scheme = schemeService.getScheme(SchemeType.EIGHTDISCOUNT,BusinessConstant.CASH_NO);
				wrapper = new SchemeWrapper(getChit(),scheme);
				wrapper.setTotalAmount(actualAmount);
				PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.EIGHTDISCOUNT,BusinessConstant.CASH_NO);
				schemes.put(key, wrapper);
			}
			if(actualAmount.compareTo(originAmount) > 0){
				logger.error("----【"+order.getOrderNo()+"】实际金额不应该大于原价");
			}
			if(actualAmount.compareTo(originAmount)==0){
				//无折扣
				if(cashAmount.compareTo(chain.getBalance()) != 0){
					//如果收银机显示现金收入和计算收入不相符时，报异常
					order.setUnusual(UNUSUAL);
					logger.warn("----【"+order.getOrderNo()+"】#无折扣# 收银机显示金额："+cashAmount+" , 应该显示金额： "+actualAmount);
				}
				Scheme scheme = schemeService.getScheme(SchemeType.NODISCOUNT,BusinessConstant.CASH_NO);
				wrapper = new SchemeWrapper(getChit(),scheme);
				wrapper.setTotalAmount(cashAmount);
				PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.NODISCOUNT,BusinessConstant.CASH_NO);
				schemes.put(key, wrapper);
			}
//		}
//		chain.doFilter(order, items, chain);
	}

	@Override
	public String getChit() {
		return "现金";
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}
}
