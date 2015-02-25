package com.rci.ui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

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
		return 13;
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
			return order.getSchemeName();
		case 5:
			return order.getCheckoutTime();
		case 6:
			return order.getPosAmount();
		case 7:
			return order.getMtAmount();
		case 8:
			return order.getDptgAmount();
		case 9:
			return order.getDpshAmount();
		case 10:
			return order.getEleAmount();
		case 11:
			return order.getTddAmount();
		case 12:
			return order.getTotalAmount();
		default:
			break;
		}
		return null;
	}

}
