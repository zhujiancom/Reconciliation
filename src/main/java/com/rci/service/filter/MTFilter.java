package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Order;
import com.rci.bean.scheme.PairKey;
import com.rci.bean.scheme.SchemeWrapper;
import com.rci.constants.enums.SchemeType;

@Component
public class MTFilter extends AbstractFilter {
	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(MT_NO);
	}

	@Override
	public void doFilter(Order order, List<OrderItemDTO> items,
			FilterChain chain) {
		if (support(order.getPaymodeMapping())) {
			/* 不能使用代金券的菜品总额 。 即酒水和配料 */
			BigDecimal nodiscountAmount = BigDecimal.ZERO;
			/* 正常菜品，条件满足使用代金券的总金额 */
			BigDecimal bediscountAmount = BigDecimal.ZERO;
			for (OrderItemDTO item : items) {
				if ("Y".equals(item.getSuitFlag())) {
					// 2.如果是套餐，则过滤
					continue;
				}
				if ("P".equals(item.getSuitFlag())) {
					if (!suitFlag) {
						suitFlag = true;
					}
					// 如果是大份套餐，记录套餐数量
					if (item.getPrice().intValue() == OLD_BIGSUIT_PRICE
							|| item.getPrice().intValue() == NEW_BIGSUIT_PRICE) {
						Integer count = chitMap.get(SchemeType.BIG_SUIT);
						if (count != null) {
							count++;
						} else {
							count = 0;
							count++;
							chitMap.put(SchemeType.BIG_SUIT, count);
						}
					}
					// 如果是小份套餐，记录套餐数量
					if (item.getPrice().intValue() == OLD_SMALLSUIT_PRICE
							|| item.getPrice().intValue() == NEW_SMALLSUIT_PRICE) {
						Integer count = chitMap.get(SchemeType.LITTLE_SUIT);
						if (count != null) {
							count++;
						} else {
							count = 0;
							count++;
							chitMap.put(SchemeType.LITTLE_SUIT, count);
						}
					}
				}
				String dishNo = item.getDishNo();
				BigDecimal originPrice = item.getPrice();
				if (!suitFlag && isNodiscount(dishNo)) {
					// 3. 饮料酒水配菜除外
					nodiscountAmount = nodiscountAmount.add(originPrice);
					continue;
				}
				bediscountAmount = bediscountAmount.add(originPrice);
				
				/* 判断是否有单品折扣  */
				BigDecimal rate = item.getDiscountRate();
				if(isSingleDiscount(rate) &&!order.getSingleDiscount()){
					order.setSingleDiscount(true);
				}
			}
			order.setNodiscountAmount(nodiscountAmount);
			// 分析客户使用了哪些代金券
			Map<PairKey<SchemeType,String>,SchemeWrapper> schemes = order.getSchemes();
			if (CollectionUtils.isEmpty(schemes)) {
				schemes = new HashMap<PairKey<SchemeType,String>,SchemeWrapper>();
				order.setSchemes(schemes);
			}
			BigDecimal chitAmount = order.getPaymodeMapping().get(MT_NO);
			if(bediscountAmount.compareTo(chitAmount) < 0){
				//如果可打折金额小于代金券实际使用金额，则这单属于异常单
				order.setUnusual(UNUSUAL);
			}
			schemes.putAll(createSchemes(chitAmount, MT_NO));
		}
		chain.doFilter(order, items, chain);
	}

	@Override
	public String getChit() {
		return "美团";
	}

}
