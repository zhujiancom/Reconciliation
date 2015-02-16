package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.rci.constants.PropertyConstants;
import com.rci.service.IOrderService;
import com.rci.tools.SpringUtils;
import com.rci.tools.properties.PropertyUtils;
import com.rci.ui.model.OrderTableModel;
import com.rci.ui.vo.OrderVO;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4254277479150685575L;
	public static final int WIDTH = 950;
	public static final int HEIGHT = 640;
	private JPanel containerPanel;
	private JComponent formPanel;
	private JComponent resultPanel;
	private IOrderService orderService;
	
//	public void setOrderService(IOrderService orderService) {
//		this.orderService = orderService;
//	}

	public MainFrame(){
		orderService = (IOrderService) SpringUtils.getBean("OrderService");
		this.setTitle((String) PropertyUtils.getProperties(PropertyConstants.SYSNAME));
		containerPanel = new JPanel();
		this.setContentPane(containerPanel);
		BorderLayout layout = new BorderLayout(0, 10);
		containerPanel.setLayout(layout);
		formPanel = buildFormPanel();
		resultPanel = buildResultPanel();
		containerPanel.add(formPanel, "North");
		containerPanel.add(resultPanel, BorderLayout.CENTER);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		this.setSize(WIDTH, HEIGHT); // 在panel添加组件之后设置
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
		this.setVisible(true);
	}
	
	private JComponent buildFormPanel() {
		JPanel panel = new JPanel();
		JLabel rciTime = new JLabel("日期");
		JTextField timeInput = new JTextField(10);
		JButton queryBtn = new JButton("查询");
		panel.add(rciTime);
		panel.add(timeInput);
		panel.add(queryBtn);
		panel.setVisible(true);
		panel.setSize(500, 300);
		panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		return panel;
	}
	
	private JComponent buildResultPanel() {
		Object[][] playerInfo = {
					{"zhujian",new Integer(91),new Integer(100),new Integer(191),new Boolean(true)},
					{"zhangshang",new Integer(91),new Integer(100),new Integer(191),new Boolean(true)}
				};
		String[] columnNames = {"序号","付款编号","原价","实收金额","折扣方案","结账时间","pos机入账","美团入账","大众点评团购","大众点评闪惠","饿了么入账","淘点点入账","总金额"};
//		JTable table = new JTable(playerInfo,names);
		List<OrderVO> orders = orderService.queryOrderVOsByDay("2015-02-07");
		OrderTableModel otm = new OrderTableModel(orders);
		
		JTable table = new JTable(otm);
		TableColumnModel cm = table.getColumnModel();
		cm.getColumn(0).setHeaderValue("序号");
		cm.getColumn(1).setHeaderValue("付款编号");
		cm.getColumn(2).setHeaderValue("原价");
		cm.getColumn(3).setHeaderValue("实收金额");
		cm.getColumn(4).setHeaderValue("折扣方案");
		cm.getColumn(5).setHeaderValue("结账时间");
		cm.getColumn(6).setHeaderValue("pos机入账");
		cm.getColumn(7).setHeaderValue("美团入账");
		cm.getColumn(8).setHeaderValue("大众点评团购");
		cm.getColumn(9).setHeaderValue("大众点评闪惠");
		cm.getColumn(10).setHeaderValue("饿了么入账");
		cm.getColumn(11).setHeaderValue("淘点点入账");
		cm.getColumn(12).setHeaderValue("总金额");
		table.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		table.setPreferredScrollableViewportSize(new Dimension(500,330));
		JScrollPane scrollPane = new JScrollPane(table); //将表格加入到滚动条组件中
		return scrollPane;
	}
}
