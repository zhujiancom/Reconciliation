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

/**
 * 大众点评
 * 
 * @author zj
 * 
 */
@Component
public class DPTGFilter extends AbstractFilter {

	/* 标记该订单中是否有套餐 */
	private boolean suitFlag = false;

	private Map<SchemeType, Integer> chitMap;

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

	/**
	 * 反推计算使用了什么样的代金券
	 * 
	 * @param amount
	 * @param paymodeno
	 * @return
	 */
	private Map<PairKey<SchemeType,String>,SchemeWrapper> createSchemes(BigDecimal amount, String paymodeno) {
		Map<PairKey<SchemeType,String>,SchemeWrapper> schemes = new HashMap<PairKey<SchemeType,String>,SchemeWrapper>();
		BigDecimal bigSuitAmount = BigDecimal.ZERO;
		BigDecimal littleSuitAmount = BigDecimal.ZERO;
		// 1.如果有套餐
		if (suitFlag) {
			Integer big_count = chitMap.get(SchemeType.BIG_SUIT);
			Integer little_count = chitMap.get(SchemeType.LITTLE_SUIT);
			if (big_count != null && big_count != 0) {
				// 1.1 如果有大份套餐
				Scheme scheme = paymodeService.getScheme(SchemeType.BIG_SUIT,
						paymodeno);
				BigDecimal bigSuitPrice = scheme.getPrice();
				bigSuitAmount = bigSuitAmount.add(bigSuitPrice
						.multiply(new BigDecimal(big_count)));
				PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.BIG_SUIT,paymodeno);
				SchemeWrapper schemewrapper = new SchemeWrapper("大众点评",scheme,big_count);
				if(schemes.get(key) != null){
					schemewrapper = schemes.get(key);
					schemewrapper.add(big_count);
				}else{
					schemes.put(key, schemewrapper);
				}
			}
			if (little_count != null && little_count != 0) {
				// 1.2 如果有小份套餐
				Scheme scheme = paymodeService.getScheme(
						SchemeType.LITTLE_SUIT, paymodeno);
				BigDecimal littleSuitPrice = scheme.getPrice();
				littleSuitAmount = littleSuitAmount.add(littleSuitPrice
						.multiply(new BigDecimal(little_count)));
				PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.LITTLE_SUIT,paymodeno);
				SchemeWrapper schemewrapper = new SchemeWrapper("大众点评",scheme,little_count);
				if(schemes.get(key) != null){
					schemewrapper = schemes.get(key);
					schemewrapper.add(little_count);
				}else{
					schemes.put(key, schemewrapper);
				}
			}
		}
		BigDecimal leftAmount = amount.subtract(bigSuitAmount).subtract(littleSuitAmount);
		loopSchemes(leftAmount.intValue(),schemes,paymodeno);
		return schemes;
	}

	private void loopSchemes(Integer amount, Map<PairKey<SchemeType,String>,SchemeWrapper> schemes,String paymodeno) {
		if(amount <= 0){
			return;
		}
		if (amount > 100) {
			// 金额在大于100，使用100元代金券
			Scheme scheme = paymodeService.getScheme(SchemeType.HUNDRED,paymodeno);
			Integer count = chitMap.get(SchemeType.HUNDRED);
			if(count != null && count != 0){
				count++;
			}else{
				count = 0;
				count++;
				chitMap.put(SchemeType.HUNDRED, count);
			}
			PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.HUNDRED,paymodeno);
			SchemeWrapper schemewrapper = new SchemeWrapper("大众点评",scheme,count);
			if(schemes.get(key) != null){
				schemewrapper = schemes.get(key);
				schemewrapper.add(count);
			}else{
				schemes.put(key, schemewrapper);
			}
			amount = amount - 100;
			loopSchemes(amount,schemes,paymodeno);
		}
		if (50 < amount && amount <= 100) {
			// 金额在50-100之间，使用100元代金券
			Scheme scheme = paymodeService.getScheme(SchemeType.HUNDRED,paymodeno);
			Integer count = chitMap.get(SchemeType.HUNDRED);
			if(count != null && count != 0){
				count++;
			}else{
				count = 0;
				count++;
				chitMap.put(SchemeType.HUNDRED, count);
			}
			PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.HUNDRED,paymodeno);
			SchemeWrapper schemewrapper = new SchemeWrapper("大众点评",scheme,count);
			if(schemes.get(key) != null){
				schemewrapper = schemes.get(key);
				schemewrapper.add(count);
			}else{
				schemes.put(key, schemewrapper);
			}
		}
		if(amount <= 50){
			// 金额在小于，使用100元代金券
			Scheme scheme = paymodeService.getScheme(SchemeType.FIFITY,paymodeno);
			Integer count = chitMap.get(SchemeType.FIFITY);
			if(count != null && count != 0){
				count++;
			}else{
				count = 0;
				count++;
				chitMap.put(SchemeType.FIFITY, count);
			}
			PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.FIFITY,paymodeno);
			SchemeWrapper schemewrapper = new SchemeWrapper("大众点评",scheme,count);
			if(schemes.get(key) != null){
				schemewrapper = schemes.get(key);
				schemewrapper.add(count);
			}else{
				schemes.put(key, schemewrapper);
			}
		}
	}

}
