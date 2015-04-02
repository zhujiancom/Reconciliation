package com.rci.ui.model;

import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.rci.tools.DateUtil;
import com.rci.ui.vo.OrderItemVO;

public class OrderItemTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2051152245054314109L;
	private List<OrderItemVO> items = Collections.emptyList();;
	
	public OrderItemTableModel(List<OrderItemVO> items){
		this.items = items;
	}
	
	@Override
	public int getRowCount() {
		return items.size();
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		OrderItemVO item = items.get(rowIndex);
		switch(columnIndex){
		case 0:
			return item.getDishName();
		case 1:
			return item.getCount();
		case 2:
			return item.getCountback();
		case 3:
			return item.getPrice();
		case 4:
			return item.getActualAmount();
		case 5:
			return item.getDiscountRate();
		case 6:
			return DateUtil.time2Str(item.getConsumeTime());
		default:
			break;
		}
		return null;
	}

	public void setRowCount(int rowCount){
		int old = getRowCount();
		items = Collections.emptyList();
		super.fireTableRowsDeleted(rowCount,old-1);
	}
}
