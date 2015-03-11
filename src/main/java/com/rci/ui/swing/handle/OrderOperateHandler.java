package com.rci.ui.swing.handle;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import org.springframework.util.CollectionUtils;

import com.rci.constants.enums.PaymodeType;
import com.rci.service.IOrderService;
import com.rci.tools.SpringUtils;
import com.rci.ui.model.OrderItemTableModel;
import com.rci.ui.model.OrderTableModel;
import com.rci.ui.vo.OrderItemVO;
import com.rci.ui.vo.OrderVO;

public class OrderOperateHandler{
	private IOrderService orderService;
	private List<OrderVO> orders;
	private JTable table;
	
	public OrderOperateHandler(JTable table){
		this.table = table;
		orderService = (IOrderService) SpringUtils.getBean("OrderService");
	}
	
	public List<OrderVO> getOrders(){
		return orders;
	}
	
	public JTable loadOrderData(String time) {
		List<OrderVO> ordervos = orderService.queryOrderVOsByDay(time);
		this.orders = ordervos;
		OrderTableModel otm = new OrderTableModel(orders);
		table.setModel(otm);
		TableColumnModel cm = table.getColumnModel();
		cm.getColumn(0).setHeaderValue("序号");
		cm.getColumn(0).setPreferredWidth(50);
		cm.getColumn(1).setHeaderValue("付款编号");
		cm.getColumn(1).setPreferredWidth(105);
		cm.getColumn(2).setHeaderValue("原价");
		cm.getColumn(2).setPreferredWidth(75);
		cm.getColumn(3).setHeaderValue("实收金额");
		cm.getColumn(3).setPreferredWidth(75);
		cm.getColumn(4).setHeaderValue("折扣方案");
		cm.getColumn(4).setPreferredWidth(115);
		cm.getColumn(5).setHeaderValue("结账时间");
		cm.getColumn(5).setPreferredWidth(140);
		cm.getColumn(6).setHeaderValue("pos机入账");
		cm.getColumn(6).setPreferredWidth(70);
		cm.getColumn(7).setHeaderValue("美团入账");
		cm.getColumn(7).setPreferredWidth(70);
		cm.getColumn(8).setHeaderValue("大众点评团购");
		cm.getColumn(8).setPreferredWidth(105);
		cm.getColumn(9).setHeaderValue("大众点评闪惠");
		cm.getColumn(9).setPreferredWidth(105);
		cm.getColumn(10).setHeaderValue("饿了么入账");
		cm.getColumn(10).setPreferredWidth(75);
		cm.getColumn(11).setHeaderValue("淘点点入账");
		cm.getColumn(11).setPreferredWidth(75);
		cm.getColumn(12).setHeaderValue("总金额");
		cm.getColumn(12).setPreferredWidth(75);
		table.setColumnModel(cm);
		
		table.setRowHeight(20);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		return table;
	}
	
//	public JTable loadItemData(String payno){
//		JTable table = new JTable();
//		List<OrderItemVO> orderItems = orderService.queryOrderItemVOsByPayno(payno);
//		OrderItemTableModel otm = new OrderItemTableModel(orderItems);
//		table.setModel(otm);
//		TableColumnModel cm = table.getColumnModel();
//		cm.getColumn(0).setHeaderValue("菜品名称");
//		cm.getColumn(0).setPreferredWidth(50);
//		cm.getColumn(1).setHeaderValue("数量");
//		cm.getColumn(1).setPreferredWidth(105);
//		cm.getColumn(2).setHeaderValue("退菜数量");
//		cm.getColumn(2).setPreferredWidth(75);
//		cm.getColumn(3).setHeaderValue("实收金额");
//		cm.getColumn(3).setPreferredWidth(75);
//		cm.getColumn(4).setHeaderValue("折扣率");
//		cm.getColumn(4).setPreferredWidth(115);
//		cm.getColumn(5).setHeaderValue("消费时间");
//		cm.getColumn(5).setPreferredWidth(140);
//		table.setColumnModel(cm);
//		table.setRowHeight(20);
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		return table;
//	}
	
	public BigDecimal getTotalAmount(PaymodeType paymode){
		BigDecimal totalAmount = new BigDecimal(BigInteger.ZERO,2);
		if(CollectionUtils.isEmpty(orders)){
			return totalAmount;
		}
		if(PaymodeType.MT.equals(paymode)){
			for(OrderVO order:orders){
				if(order.getMtAmount() != null){
					totalAmount = totalAmount.add(order.getMtAmount());
				}
			}
		}
		if(PaymodeType.POS.equals(paymode)){
			for(OrderVO order:orders){
				if(order.getPosAmount() != null){
					totalAmount = totalAmount.add(order.getPosAmount());
				}
			}
		}
		if(PaymodeType.DPTG.equals(paymode)){
			for(OrderVO order:orders){
				if(order.getDptgAmount() != null){
					totalAmount = totalAmount.add(order.getDptgAmount());
				}
			}
		}
		if(PaymodeType.DPSH.equals(paymode)){
			for(OrderVO order:orders){
				if(order.getDpshAmount() != null){
					totalAmount = totalAmount.add(order.getDpshAmount());
				}
			}
		}
		if(PaymodeType.ELE.equals(paymode)){
			for(OrderVO order:orders){
				if(order.getEleAmount() != null){
					totalAmount = totalAmount.add(order.getEleAmount());
				}
			}
		}
		if(PaymodeType.TDD.equals(paymode)){
			for(OrderVO order:orders){
				if(order.getTddAmount() != null){
					totalAmount = totalAmount.add(order.getTddAmount());
				}
			}
		}
		return totalAmount;
	}
}
