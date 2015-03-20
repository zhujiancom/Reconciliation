package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rci.bean.OrderItemDTO;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.PostOrderAccount;
import com.rci.bean.entity.account.Account;
import com.rci.bean.scheme.PairKey;
import com.rci.bean.scheme.SchemeWrapper;
import com.rci.constants.enums.SchemeType;
import com.rci.service.IAccountService;
import com.rci.service.IDishService;
import com.rci.service.filter.CalculateFilter;
import com.rci.service.filter.FilterChain;

@Component("PostAccountCalculator")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PostAccountCalculator implements InitializingBean{
	@Autowired
	private Mapper beanMapper;
	@Resource(name="DishService")
	private IDishService dishService;
	
	@Autowired
	private List<CalculateFilter> filters;
	@Resource(name="FilterChain")
	private FilterChain chain;
	
	@Resource(name="AccountService")
	private IAccountService accService;
	
	public Order calculate(Order order,List<OrderItemDTO> itemDTOs) {
		chain.doFilter(order, itemDTOs, chain);
		chain.reset();
		StringBuffer schemeName = new StringBuffer();
		Map<PairKey<SchemeType,String>,SchemeWrapper> schemewrappers = order.getSchemes();
		List<PostOrderAccount> accounts = new ArrayList<PostOrderAccount>();
		Map<String,PostOrderAccount> accmapping = new HashMap<String,PostOrderAccount>();
		if(schemewrappers != null){
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
					poa = new PostOrderAccount(account.getAccNo());
					poa.setPostAmount(wrapper.calculatePostAmount());
					poa.setOrder(order);
					accmapping.put(paymodeno, poa);
				}
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
		chain.addFilters(filters);
	}

}
