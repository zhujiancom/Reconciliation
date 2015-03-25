package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Dish;
import com.rci.bean.entity.DishType;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.Scheme;
import com.rci.bean.scheme.PairKey;
import com.rci.bean.scheme.SchemeWrapper;
import com.rci.constants.enums.SchemeType;
import com.rci.service.IDishService;
import com.rci.service.ISchemeService;

public abstract class AbstractFilter implements CalculateFilter {
	public static final Log logger = LogFactory.getLog(AbstractFilter.class);
	
	@Resource(name="DishService")
	private IDishService dishService;
	
//	@Resource(name = "PayModeService")
//	protected IPayModeService paymodeService;
	
	@Resource(name="SchemeService")
	protected ISchemeService schemeService;
	@Override
	public void doFilter(Order order, List<OrderItemDTO> items,
			FilterChain chain) {
		BigDecimal originAmount = order.getOriginPrice();
		BigDecimal balance = chain.getBalance();
		if(balance.compareTo(BigDecimal.ZERO) == 0){
			logger.debug("balance = "+balance.intValue());
			chain.setBalance(originAmount);
		}
		generateScheme(order,items,chain);
	}

	protected abstract void generateScheme(Order order, List<OrderItemDTO> items,FilterChain chain);

	/**
	 * 菜品是否可以打折
	 * @param dishNo
	 * @return
	 */
	protected Boolean isNodiscount(String dishNo){
		Dish dish = dishService.findDishByNo(dishNo);
		DishType type = dish.getDishType();
		if(type != null && "N".equalsIgnoreCase(type.getBeDiscount())){
			return true;
		}
		return false;
	}
	
	protected Boolean isSingleDiscount(BigDecimal rate){
		if(rate.compareTo(new BigDecimal(80)) == 0 || rate.compareTo(new BigDecimal(100)) == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 反推计算使用了什么样的代金券
	 * 
	 * @param amount
	 * @param paymodeno
	 * @return
	 */
	protected Map<PairKey<SchemeType,String>,SchemeWrapper> createSchemes(BigDecimal amount, String paymodeno,boolean suitFlag) {
		Map<PairKey<SchemeType,String>,SchemeWrapper> schemes = new HashMap<PairKey<SchemeType,String>,SchemeWrapper>();
		BigDecimal bigSuitAmount = BigDecimal.ZERO;
		BigDecimal littleSuitAmount = BigDecimal.ZERO;
		// 1.如果有套餐
		if (suitFlag) {
			Integer big_count = getSuitMap().get(SchemeType.BIG_SUIT);
			Integer little_count = getSuitMap().get(SchemeType.LITTLE_SUIT);
			if (big_count != null && big_count != 0) {
				// 1.1 如果有大份套餐
				Scheme scheme = schemeService.getScheme(SchemeType.BIG_SUIT,paymodeno);
				BigDecimal bigSuitPrice = scheme.getPrice();
				bigSuitAmount = bigSuitAmount.add(bigSuitPrice.multiply(new BigDecimal(big_count)));
				PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.BIG_SUIT,paymodeno);
				SchemeWrapper schemewrapper = new SchemeWrapper(getChit(),scheme,big_count);
//				if(schemes.get(key) != null){
//					schemewrapper = schemes.get(key);
//					schemewrapper.add(big_count);
//				}else{
//					schemes.put(key, schemewrapper);
//				}
				schemes.put(key, schemewrapper);
			}
			if (little_count != null && little_count != 0) {
				// 1.2 如果有小份套餐
				Scheme scheme = schemeService.getScheme(SchemeType.LITTLE_SUIT, paymodeno);
				BigDecimal littleSuitPrice = scheme.getPrice();
				littleSuitAmount = littleSuitAmount.add(littleSuitPrice.multiply(new BigDecimal(little_count)));
				PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.LITTLE_SUIT,paymodeno);
				SchemeWrapper schemewrapper = new SchemeWrapper(getChit(),scheme,little_count);
//				if(schemes.get(key) != null){
//					schemewrapper = schemes.get(key);
//					schemewrapper.add(little_count);
//				}else{
//					schemes.put(key, schemewrapper);
//				}
				schemes.put(key, schemewrapper);
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
			PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.HUNDRED,paymodeno);
			SchemeWrapper schemewrapper = null;
			if(schemes.get(key) != null){
				schemewrapper = schemes.get(key);
				schemewrapper.increasement();
			}else{
				Scheme scheme = schemeService.getScheme(SchemeType.HUNDRED,paymodeno);
				schemewrapper = new SchemeWrapper(getChit(),scheme,1);
				schemes.put(key, schemewrapper);
			}
			amount = amount - 100;
		}else if (50 < amount && amount <= 100) {
			// 金额在50-100之间，使用100元代金券
			PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.HUNDRED,paymodeno);
			SchemeWrapper schemewrapper = null;
			if(schemes.get(key) != null){
				schemewrapper = schemes.get(key);
				schemewrapper.increasement();
			}else{
				Scheme scheme = schemeService.getScheme(SchemeType.HUNDRED,paymodeno);
				schemewrapper = new SchemeWrapper(getChit(),scheme,1);
				schemes.put(key, schemewrapper);
			}
			amount = amount - 100;
		}else if(amount >0 && amount <= 50){
			// 金额在小于等于50，使用50元代金券
			PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.FIFITY,paymodeno);
			SchemeWrapper schemewrapper = null;
			if(schemes.get(key) != null){
				schemewrapper = schemes.get(key);
				schemewrapper.increasement();
			}else{
				Scheme scheme = schemeService.getScheme(SchemeType.FIFITY,paymodeno);
				schemewrapper = new SchemeWrapper(getChit(),scheme,1);
				schemes.put(key, schemewrapper);
			}
			amount = amount - 50;
		}
		loopSchemes(amount,schemes,paymodeno);
	}
	
	protected abstract Map<SchemeType, Integer> getSuitMap();
}
