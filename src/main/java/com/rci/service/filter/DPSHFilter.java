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
 * 大众点评闪惠
 * @author zj
 *
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DPSHFilter extends AbstractFilter {

	@Override
	public boolean support(Map<String, BigDecimal> paymodeMapping) {
		return paymodeMapping.containsKey(BusinessConstant.DPSH_NO);
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
			BigDecimal nodiscountAmount = BigDecimal.ZERO;
			BigDecimal bediscountAmount = BigDecimal.ZERO;
			BigDecimal payAmount = order.getPaymodeMapping().get(BusinessConstant.DPSH_NO);
			for(OrderItemDTO item:items){
				String dishNo = item.getDishNo();
				BigDecimal singlePrice = item.getPrice();
				BigDecimal count = item.getCount();
				BigDecimal countback = item.getCountback();
				BigDecimal originTotalAmount = DigitUtil.mutiplyDown(singlePrice, count.subtract(countback));
				if (isNodiscount(dishNo)) {
					// 3. 饮料酒水除外
					nodiscountAmount = nodiscountAmount.add(originTotalAmount);
					continue;
				}
				bediscountAmount = bediscountAmount.add(originTotalAmount);
				/* 判断是否有单品折扣  */
				BigDecimal rate = item.getDiscountRate();
				if(isSingleDiscount(rate) && (order.getSingleDiscount() == null || !order.getSingleDiscount())){
					order.setSingleDiscount(true);
				}
			}
			order.setNodiscountAmount(nodiscountAmount);
			Scheme scheme = schemeService.getScheme(SchemeType.CASHBACK,BusinessConstant.DPSH_NO);
			SchemeWrapper wrapper = new SchemeWrapper(getChit(),scheme);
			if(order.getFreeAmount()!=null){
				bediscountAmount = bediscountAmount.subtract(order.getFreeAmount());
			}
			wrapper.setTotalAmount(bediscountAmount);
			if(bediscountAmount.compareTo(payAmount) != 0){  // 如果使用闪惠，则支付金额应该和可打折金额一致
				order.setUnusual(UNUSUAL);
				logger.warn("----【"+order.getOrderNo()+"】支付金额异常----， 实际支付金额："+payAmount+" , 应付金额： "+bediscountAmount);
			}
			PairKey<SchemeType,String> key = new PairKey<SchemeType,String>(SchemeType.CASHBACK,BusinessConstant.DPSH_NO);
			schemes.put(key,wrapper);
			//计算订余额
			BigDecimal balance = chain.getBalance();
			logger.debug("DPSHFilter - balance = "+balance);
			if(balance.compareTo(bediscountAmount) < 0){
				logger.error("----【"+order.getOrderNo()+"】数据异常----,余额:"+balance+" , 需支付金额:"+bediscountAmount+". 余额不能小于需支付金额");
			}
			balance = balance.subtract(bediscountAmount);
			chain.setBalance(balance);
	}

	@Override
	public String getChit() {
		return "大众点评闪惠";
	}

	@Override
	protected Map<SchemeType, Integer> getSuitMap() {
		return null;
	}


}
