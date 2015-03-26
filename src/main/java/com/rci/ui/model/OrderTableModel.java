package com.rci.ui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.rci.tools.DateUtil;
import com.rci.ui.vo.OrderVO;

public class OrderTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4006879882193678115L;
	private List<OrderVO> orders;
	
	public OrderTableModel(List<OrderVO> orders){
		this.orders = orders;
	}
	
	@Override
	public int getRowCount() {
		return orders.size();
	}

	@Override
	public int getColumnCount() {
		return 16;
	}
	
	public OrderVO getOrderAt(int rowIndex){
		return orders.get(rowIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		OrderVO order = orders.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return ++rowIndex;
		case 1:
			return order.getPayNo();
		case 2:
			return order.getOriginAmount();
		case 3:
			return order.getActualAmount();
		case 4:
			return order.getNodiscountAmount();
		case 5:
			return order.getSchemeName();
		case 6:
			return order.getSingleDiscount();
		case 7:
			return DateUtil.time2Str(order.getCheckoutTime());
		case 8:
			return order.getPosAmount();
		case 9:
			return order.getMtAmount();
		case 10:
			return order.getDptgAmount();
		case 11:
			return order.getDpshAmount();
		case 12:
			return order.getEleAmount();
		case 13:
			return order.getTddAmount();
		case 14:
			return order.getFreeAmount();
		case 15:
			return order.getTotalAmount();
		default:
			break;
		}
		return null;
	}

}
