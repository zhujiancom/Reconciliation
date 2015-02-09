package com.rci.service.calculator;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.DiscountScheme;
import com.rci.bean.entity.PostOrderAccount;
import com.rci.bean.entity.account.Account;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

/**
 * 店内折扣算法
 * @author zj
 *
 */
@Component("DiscountStrategy")
public class DiscountStrategy extends AbstractStrategy {
	private transient Log logger = LogFactory.getLog(DiscountStrategy.class);
	
	protected Log logger(){
		if(logger == null){
			return LogFactory.getLog(DiscountStrategy.class);
		}else{
			return logger;
		}
	}
	
	@Override
	public PostOrderAccount calculate(List<OrderItemDTO> items) {
		Account account = accService.getPOSAccount();
		PostOrderAccount poa = new PostOrderAccount();
		BigDecimal postAmount = BigDecimal.ZERO;
//		Set<String> discountDishNoContainer = new HashSet<String>();
//		List<DishType> dishTypes = scheme.getDishTypes();
//		for(DishType dishType:dishTypes){
//			List<Dish> dishes = dishType.getDishes();
//			for(Dish dish:dishes){
//				discountDishNoContainer.add(dish.getDishNo());
//			}
//		}
		for(OrderItemDTO itemDTO:items){
			BigDecimal price = itemDTO.getPrice();
			BigDecimal count = itemDTO.getCount();
			BigDecimal backcount = itemDTO.getCountback();
			logger().debug("Billno["+itemDTO.getBillNo()+"],"+"orderItem["+itemDTO.getDishNo()+"]"+" - price:"+price+",");
			postAmount = postAmount.add(algorithm(price,count,backcount));
		}
		poa.setAccountId(account.getAid());
		poa.setPostAmount(postAmount);
		return poa;
	}

	@Override
	public Boolean support(DiscountScheme scheme) {
		super.scheme = scheme;
		if("01".equals(scheme.getsNo()) || "02".equals(scheme.getsNo())){
			return true;
		}
		return false;
	}
	
	@Override
	protected BigDecimal algorithm(BigDecimal price, BigDecimal count,BigDecimal backcount){
		BigDecimal result = price.multiply(count).subtract(price.multiply(backcount)).multiply(scheme.getRate().divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_CEILING);
		return result;
	}
}
