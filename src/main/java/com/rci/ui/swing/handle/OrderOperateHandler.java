package com.rci.ui.swing.handle;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import com.rci.service.IOrderService;
import com.rci.tools.SpringUtils;
import com.rci.ui.model.OrderTableModel;
import com.rci.ui.vo.OrderVO;

public class OrderOperateHandler{
	public static JTable loadData(String time) {
		IOrderService orderService = (IOrderService) SpringUtils.getBean("OrderService");
		JTable table = new JTable();
		List<OrderVO> orders = orderService.queryOrderVOsByDay(time);
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
		cm.getColumn(13).setHeaderValue("");
		cm.getColumn(13).setPreferredWidth(75);
		table.setColumnModel(cm);
		
		table.setRowHeight(20);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		table.setPreferredScrollableViewportSize(new Dimension(1210,800));
		return table;
	}

}
