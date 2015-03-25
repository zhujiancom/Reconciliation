package com.rci.ui.model;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.rci.ui.vo.OrderVO;

public class OrderTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4935140318205918006L;
	
	public void markRed(){
		TableColumnModel tcm = this.getColumnModel();
		for(int i=0;i<tcm.getColumnCount();i++){
			TableColumn tc = tcm.getColumn(i);
			tc.setCellRenderer(new RowMarkReadRenderer());
		}
	}
	
	public void setHeaderLabel(){
		TableColumnModel cm = this.getColumnModel();
		cm.getColumn(0).setHeaderValue("序号");
		cm.getColumn(0).setPreferredWidth(50);
		cm.getColumn(1).setHeaderValue("付款编号");
		cm.getColumn(1).setPreferredWidth(105);
		cm.getColumn(2).setHeaderValue("原价");
		cm.getColumn(2).setPreferredWidth(75);
		cm.getColumn(3).setHeaderValue("实收金额");
		cm.getColumn(3).setPreferredWidth(75);
		cm.getColumn(4).setHeaderValue("不可打折金额");
		cm.getColumn(4).setPreferredWidth(75);
		cm.getColumn(5).setHeaderValue("折扣方案");
		cm.getColumn(5).setPreferredWidth(135);
		cm.getColumn(6).setHeaderValue("有临时折扣方案");
		cm.getColumn(6).setPreferredWidth(75);
		cm.getColumn(7).setHeaderValue("结账时间");
		cm.getColumn(7).setPreferredWidth(140);
		cm.getColumn(8).setHeaderValue("pos机入账");
		cm.getColumn(8).setPreferredWidth(70);
		cm.getColumn(9).setHeaderValue("美团入账");
		cm.getColumn(9).setPreferredWidth(70);
		cm.getColumn(10).setHeaderValue("大众点评团购");
		cm.getColumn(10).setPreferredWidth(105);
		cm.getColumn(11).setHeaderValue("大众点评闪惠");
		cm.getColumn(11).setPreferredWidth(105);
		cm.getColumn(12).setHeaderValue("饿了么入账");
		cm.getColumn(12).setPreferredWidth(75);
		cm.getColumn(13).setHeaderValue("淘点点入账");
		cm.getColumn(13).setPreferredWidth(75);
		cm.getColumn(14).setHeaderValue("总金额");
		cm.getColumn(14).setPreferredWidth(75);
	}
	
	private class RowMarkReadRenderer extends DefaultTableCellRenderer{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4436771966347867353L;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			OrderTableModel tm = (OrderTableModel) table.getModel();
			OrderVO order = tm.getOrderAt(row);
			if(order.getUnusual() == 1){
				setBackground(Color.RED);
				setForeground(Color.WHITE);
			}else{
				setBackground(Color.WHITE);
				setForeground(Color.BLACK);
			}
			if(order.getUnusual() == 1 && isSelected){
				table.setSelectionBackground(Color.RED);
				table.setSelectionForeground(Color.WHITE);
			}else{
				table.setSelectionBackground(UIManager.getColor("Table.selectionBackground"));
				table.setSelectionForeground(UIManager.getColor("Table.selectionForeground"));
			}
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
					row, column);
		}
		
	}

}
