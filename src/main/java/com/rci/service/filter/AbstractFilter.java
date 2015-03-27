package com.rci.service.filter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	@Resource(name="SchemeService")
	protected ISchemeService schemeService;
	@Override
	public void doFilter(Order order, List<OrderItemDTO> items,
			FilterChain chain) {
		if (support(order.getPaymodeMapping())) {
			generateScheme(order,items,chain);
		}
		chain.doFilter(order, items, chain);
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
		BigDecimal suitAmount = BigDecimal.ZERO;
		// 1.如果有套餐
		if (suitFlag) {
			for(Iterator<Entry<SchemeType, Integer>> it=getSuitMap().entrySet().iterator();it.hasNext();){
				Entry<SchemeType, Integer> entry = it.next();
				SchemeType type = entry.getKey();
				Integer count = entry.getValue();
				Scheme scheme = schemeService.getScheme(type, paymodeno);
				BigDecimal suitPrice = scheme.getPrice();
				suitAmount = suitAmount.add(suitPrice.multiply(new BigDecimal(count)));
				PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(type,paymodeno);
				SchemeWrapper schemewrapper = new SchemeWrapper(getChit(),scheme,count);
				schemes.put(key, schemewrapper);
			}
		}
		BigDecimal leftAmount = amount.subtract(suitAmount);
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
