package com.rci.ui.swing.handle;

import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import com.rci.service.IOrderService;
import com.rci.tools.SpringUtils;
import com.rci.ui.model.OrderItemTableModel;
import com.rci.ui.vo.OrderItemVO;

public class SelectionListener implements ListSelectionListener {
	private JTable mainTable;
	private JTable subTable;
	private IOrderService orderService;
	
	public SelectionListener(JTable mainTable,JTable subTable){
		this.mainTable = mainTable;
		this.subTable = subTable;
		orderService = (IOrderService) SpringUtils.getBean("OrderService");
	}
	
	@Override
	public void valueChanged(ListSelectionEvent event) {
		System.out.println("--- coming listener ---");
		if(event.getSource() == mainTable.getSelectionModel()
				&& mainTable.getRowSelectionAllowed()){
			System.out.println("--- coming listener2 ---");
			int row = mainTable.getSelectedRow();
			if(row != -1){
				String payno = (String) mainTable.getValueAt(row, 1);
				loadItemData(payno);
			}
		}

	}
	
	public void loadItemData(String payno){
		List<OrderItemVO> orderItems = orderService.queryOrderItemVOsByPayno(payno);
		OrderItemTableModel otm = new OrderItemTableModel(orderItems);
		subTable.setModel(otm);
		TableColumnModel cm = subTable.getColumnModel();
		cm.getColumn(0).setHeaderValue("菜品名称");
		cm.getColumn(0).setPreferredWidth(50);
		cm.getColumn(1).setHeaderValue("数量");
		cm.getColumn(1).setPreferredWidth(105);
		cm.getColumn(2).setHeaderValue("退菜数量");
		cm.getColumn(2).setPreferredWidth(75);
		cm.getColumn(3).setHeaderValue("实收金额");
		cm.getColumn(3).setPreferredWidth(75);
		cm.getColumn(4).setHeaderValue("折扣率");
		cm.getColumn(4).setPreferredWidth(115);
		cm.getColumn(5).setHeaderValue("消费时间");
		cm.getColumn(5).setPreferredWidth(140);
		subTable.setColumnModel(cm);
		subTable.setRowHeight(20);
		subTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

}
