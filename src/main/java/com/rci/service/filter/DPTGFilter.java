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

/**
 * 大众点评
 * 
 * @author zj
 * 
 */
@Component
public class DPTGFilter extends AbstractFilter {

	/* 标记该订单中是否有套餐 */
//	private boolean suitFlag = false;

//	private Map<SchemeType, Integer> chitMap;

	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(DPTG_NO);
	}

	@Override
	public void doFilter(Order order, List<OrderItemDTO> items,
			FilterChain chain) {
		if (support(order.getPaymodeMapping())) {
			// 1. 有大众点评券
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
					if (item.getPrice().equals(OLD_BIGSUIT_PRICE)
							|| item.getPrice().equals(NEW_BIGSUIT_PRICE)) {
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
					if (item.getPrice().equals(OLD_SMALLSUIT_PRICE)
							|| item.getPrice().equals(NEW_SMALLSUIT_PRICE)) {
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
				if (isNodiscount(dishNo)) {
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
			}
			BigDecimal chitAmount = order.getPaymodeMapping().get(DPTG_NO);
			if(bediscountAmount.compareTo(chitAmount) < 0){
				//如果可打折金额小于代金券实际使用金额，则这单属于异常单
				order.setUnusual(UNUSUAL);
			}
			schemes.putAll(createSchemes(chitAmount, DPTG_NO));
		}
	}

	@Override
	public String getChit() {
		return "大众点评团购";
	}
}
