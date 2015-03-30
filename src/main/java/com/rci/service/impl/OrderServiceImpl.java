package com.rci.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rci.bean.entity.DataFetchMark;
import com.rci.bean.entity.Order;
import com.rci.bean.entity.OrderItem;
import com.rci.bean.entity.PostOrderAccount;
import com.rci.constants.BusinessConstant;
import com.rci.service.BaseService;
import com.rci.service.DataTransformFacade;
import com.rci.service.IFetchMarkService;
import com.rci.service.IOrderService;
import com.rci.tools.DateUtil;
import com.rci.ui.vo.OrderItemVO;
import com.rci.ui.vo.OrderVO;

@Service("OrderService")
public class OrderServiceImpl extends BaseService<Order, Long> implements
		IOrderService {
	@Autowired
	private Mapper beanMapper;
	@Resource(name="FetchMarkService")
	private IFetchMarkService markService;
	@Resource(name="DataTransformFacade")
	private DataTransformFacade dataTransform;
	
	@Override
	public Order getOrder(Long pk) {
		return super.get(pk);
	}

	@Override
	public void rwInsertOrder(Order order) {
		rwCreate(order);
	}

	@Override
	public List<Order> queryAllDayOrders() {
		return null;
	}

	@Override
	public List<Order> queryOrdersByDay(String day) {
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add(Restrictions.eq("day", day)).addOrder(org.hibernate.criterion.Order.asc("checkoutTime"));
		List<Order> orders = baseDAO.queryListByCriteria(dc);
		return orders;
	}
	
	@Override
	public List<OrderVO> queryOrderVOsByDay(String day) {
		List<OrderVO> vos = new LinkedList<OrderVO>();
		DataFetchMark mark = markService.getMarkRecordByDay(day);
		if(mark == null){
			dataTransform.accquireOrderInfo(DateUtil.str2Date(day,"yyyyMMdd"));
			markService.rwOrderMark(day);
		}else{
			Date savepoint = mark.getSavepoint();
			Date current = DateUtil.getCurrentDate();
			//当前查询时间在savepoint 之后，则作增量查询
			if(current.after(savepoint)){
				
			}
		}
		
		List<Order> orders = queryOrdersByDay(day);
		if(!CollectionUtils.isEmpty(orders)){
			for(Order order:orders){
				OrderVO vo = beanMapper.map(order, OrderVO.class);
				List<PostOrderAccount> poas = order.getPostOrderAccounts();
				BigDecimal totalAmount = BigDecimal.ZERO;
				for(PostOrderAccount account:poas){
					BigDecimal postAmount = account.getPostAmount();
					if(BusinessConstant.CASH_NO.equals(account.getAccountNo())){
						vo.setPosAmount(postAmount);
					}
					if(BusinessConstant.MT_NO.equals(account.getAccountNo())){
						vo.setMtAmount(postAmount);
					}
					if(BusinessConstant.DPTG_NO.equals(account.getAccountNo())){
						vo.setDptgAmount(postAmount);
					}
					if(BusinessConstant.DPSH_NO.equals(account.getAccountNo())){
						vo.setDpshAmount(postAmount);
					}
					if(BusinessConstant.ELE_NO.equals(account.getAccountNo())){
						vo.setEleAmount(postAmount);
					}
					if(BusinessConstant.TDD_NO.equals(account.getAccountNo())){
						vo.setTddAmount(postAmount);
					}
					totalAmount = totalAmount.add(postAmount);
				}
				if(vo.getFreeAmount() != null){
					totalAmount = totalAmount.subtract(vo.getFreeAmount());	
				}
				vo.setSchemeName(order.getSchemeName());
				vo.setTotalAmount(totalAmount);
				vos.add(vo);
			}
		}
		return vos;
	}

	@Override
	public List<OrderItemVO> queryOrderItemVOsByPayno(String payno) {
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add(Restrictions.eq("payNo", payno));
		Order order = baseDAO.queryUniqueByCriteria(dc);
		List<OrderItem> items = order.getItems();
		List<OrderItemVO> vos = new ArrayList<OrderItemVO>();
		if(!CollectionUtils.isEmpty(items)){
			for(OrderItem item:items){
				OrderItemVO vo = beanMapper.map(item, OrderItemVO.class);
				vo.setDishName(item.getDish().getDishName());
				vos.add(vo);
			}
		}
		return vos;
	}
}
