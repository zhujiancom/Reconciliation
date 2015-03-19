package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rci.bean.entity.Dish;
import com.rci.bean.entity.DishType;
import com.rci.bean.entity.Scheme;
import com.rci.bean.scheme.PairKey;
import com.rci.bean.scheme.SchemeWrapper;
import com.rci.constants.enums.SchemeType;
import com.rci.service.IDishService;
import com.rci.service.IPayModeService;

public abstract class AbstractFilter implements CalculateFilter {
	public static final Log logger = LogFactory.getLog(AbstractFilter.class);
	/* 标记该订单中是否有套餐 */
	protected boolean suitFlag = false;
	
	protected Map<SchemeType, Integer> chitMap;
	
	@Resource(name="DishService")
	private IDishService dishService;
	
	@Resource(name = "PayModeService")
	protected IPayModeService paymodeService;
	/**
	 * 菜品是否可以打折
	 * @param dishNo
	 * @return
	 */
	protected Boolean isNodiscount(String dishNo){
		Dish dish = dishService.findDishByNo(dishNo);
		DishType type = dish.getDishType();
		if(type != null && "N".equalsIgnoreCase(type.getNodiscount())){
			return true;
		}
		return false;
	}
	
	protected Boolean isSingleDiscount(BigDecimal rate){
		if(rate.compareTo(new BigDecimal(80)) < 0 || rate.compareTo(new BigDecimal(80))>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 反推计算使用了什么样的代金券
	 * 
	 * @param amount
	 * @param paymodeno
	 * @return
	 */
	protected Map<PairKey<SchemeType,String>,SchemeWrapper> createSchemes(BigDecimal amount, String paymodeno) {
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
				SchemeWrapper schemewrapper = new SchemeWrapper(getChit(),scheme,big_count);
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
				SchemeWrapper schemewrapper = new SchemeWrapper(getChit(),scheme,little_count);
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
			SchemeWrapper schemewrapper = new SchemeWrapper(getChit(),scheme,count);
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
			SchemeWrapper schemewrapper = new SchemeWrapper(getChit(),scheme,count);
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
			SchemeWrapper schemewrapper = new SchemeWrapper(getChit(),scheme,count);
			if(schemes.get(key) != null){
				schemewrapper = schemes.get(key);
				schemewrapper.add(count);
			}else{
				schemes.put(key, schemewrapper);
			}
		}
	}
}
