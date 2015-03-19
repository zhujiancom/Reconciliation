package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Dish;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.bean.entity.PostOrderAccount;
import com.rci.bean.entity.account.Account;
import com.rci.bean.scheme.PairKey;
import com.rci.bean.scheme.SchemeWrapper;
import com.rci.constants.enums.SchemeType;
import com.rci.service.IAccountService;
import com.rci.service.IDishService;
import com.rci.service.MoneyCalculateStrategy;
import com.rci.service.filter.CalculateFilter;
import com.rci.service.filter.FilterChain;

@Component("PostAccountCalculator")
public class PostAccountCalculator implements InitializingBean{
	@Autowired
	private List<MoneyCalculateStrategy> calculators;
	@Autowired
	private Mapper beanMapper;
	@Resource(name="DishService")
	private IDishService dishService;
	
	@Autowired
	private List<CalculateFilter> filters;
	private FilterChain chain;
	@Resource(name="AccountService")
	private IAccountService accService;
	
	
	public Order calculate(Order order,List<OrderItemDTO> itemDTOs) {
//		List<PostOrderAccount> accounts = new LinkedList<PostOrderAccount>();
		List<OrderItem> items = new LinkedList<OrderItem>();
		BigDecimal actualAmount = BigDecimal.ZERO;
		for(MoneyCalculateStrategy calculator:calculators){
			if(calculator.support(order.getScheme())){
				List<PostOrderAccount> accounts = calculator.calculate(itemDTOs);
				
				for(PostOrderAccount account:accounts){
					account.setOrder(order);
					actualAmount = actualAmount.add(account.getPostAmount());
				}
				//设置order金额入账的账户
				order.setPostOrderAccounts(accounts);
				break;
			}
		}
		
		for(OrderItemDTO itemDTO:itemDTOs){
			OrderItem item = beanMapper.map(itemDTO, OrderItem.class);
			item.setOrder(order);
			Dish dish = dishService.findDishByNo(itemDTO.getDishNo());
			item.setDish(dish);
			BigDecimal price = itemDTO.getPrice();
			BigDecimal count = itemDTO.getCount();
			BigDecimal backcount = itemDTO.getCountback();
			BigDecimal rate = itemDTO.getDiscountRate();
			BigDecimal itemActualAmount = price.multiply(count).subtract(price.multiply(backcount)).multiply(rate.divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_CEILING);
			item.setActualAmount(itemActualAmount);
			items.add(item);
		}
		//设置order关联的item
		order.setItems(items);
		order.setActualAmount(actualAmount);
		return order;
	}
	
	public Order calculate2(Order order,List<OrderItemDTO> itemDTOs) {
		chain.doFilter(order, itemDTOs, chain);
		StringBuffer schemeName = new StringBuffer();
		Map<PairKey<SchemeType,String>,SchemeWrapper> schemewrappers = order.getSchemes();
		List<PostOrderAccount> accounts = new ArrayList<PostOrderAccount>();
		Map<String,PostOrderAccount> accmapping = new HashMap<String,PostOrderAccount>();
		for(Iterator<Entry<PairKey<SchemeType,String>,SchemeWrapper>> it=schemewrappers.entrySet().iterator();it.hasNext();){
			Entry<PairKey<SchemeType,String>,SchemeWrapper> entry = it.next();
			PairKey<SchemeType,String> key = entry.getKey();
			SchemeWrapper wrapper = entry.getValue();
			schemeName.append(wrapper.getName()).append("-");
			String paymodeno = key.getValue();
			Account account = accService.getAccountByNo(paymodeno);
			PostOrderAccount poa = null;
			if(accmapping.containsKey(paymodeno)){
				poa = accmapping.get(paymodeno);
				BigDecimal postAmount = poa.getPostAmount().add(wrapper.calculatePostAmount());
				poa.setPostAmount(postAmount);
			}else{
				poa = new PostOrderAccount(account.getAid());
				poa.setPostAmount(wrapper.calculatePostAmount());
				poa.setOrder(order);
			}
		}
		for(Iterator<Entry<String,PostOrderAccount>> it=accmapping.entrySet().iterator();it.hasNext();){
			Entry<String,PostOrderAccount> entry = it.next();
			PostOrderAccount poa = entry.getValue();
			accounts.add(poa);
		}
		order.setPostOrderAccounts(accounts);
		order.setSchemeName(schemeName.toString());
		return order;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		chain = new FilterChain();
		chain.addFilters(filters);
	}

}
