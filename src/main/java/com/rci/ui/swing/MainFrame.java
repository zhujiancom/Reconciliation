package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableColumnModel;

import com.rci.constants.PropertyConstants;
import com.rci.service.IOrderService;
import com.rci.service.InitSystemService;
import com.rci.tools.SpringUtils;
import com.rci.tools.properties.PropertyUtils;
import com.rci.ui.model.OrderTableModel;
import com.rci.ui.swing.handle.SystemInitHandler;
import com.rci.ui.vo.OrderVO;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4254277479150685575L;
	public static final int WIDTH = 950;
	public static final int HEIGHT = 640;
	private static JPanel containerPanel;
	private JComponent formPanel;
	private IOrderService orderService;
	private JButton queryBtn;
	private JButton initBtn;
	private JTable table;
	private JTextField timeInput;
//	private JOptionPane tips;

	public MainFrame(){
		orderService = (IOrderService) SpringUtils.getBean("OrderService");
		initComponent();
		queryBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String time = timeInput.getText();
				List<OrderVO> orders = orderService.queryOrderVOsByDay(time);
				OrderTableModel otm = new OrderTableModel(orders);
				table.setModel(otm);
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
				cm.getColumn(13).setHeaderValue("");
				table.setColumnModel(cm);
				table.setBorder(BorderFactory.createLineBorder(Color.GREEN));
				table.setPreferredScrollableViewportSize(new Dimension(500,330));
			}
		});
		
		initBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InitSystemService initService = (InitSystemService) SpringUtils.getBean("InitSystemService");
				initService.init();
				JOptionPane.showMessageDialog(null, "初始化成功");
			}
		});
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		this.setSize(WIDTH, HEIGHT); // 在panel添加组件之后设置
	}
	
	private void initComponent(){
		this.setTitle((String) PropertyUtils.getProperties(PropertyConstants.SYSNAME));
		containerPanel = new JPanel();
		this.setContentPane(containerPanel);
		BorderLayout layout = new BorderLayout(0, 10);
		containerPanel.setLayout(layout);
		
		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);
		JMenu sysMenu = new JMenu("系统");
		JMenu helpMenu = new JMenu("帮助");
		menubar.add(sysMenu);
		menubar.add(helpMenu);
		JMenuItem sysInit = new JMenuItem("系统初始化");
		JMenuItem helpInfo = new JMenuItem("帮助信息");
		sysMenu.add(sysInit);
		helpMenu.add(helpInfo);
		sysInit.addActionListener(new SystemInitHandler());
		
		formPanel = new JPanel();
		initBtn = new JButton("系统初始化");
		JLabel rciTime = new JLabel("日期");
		timeInput = new JTextField(10);
		queryBtn = new JButton("查询");
		formPanel.add(initBtn);
		formPanel.add(rciTime);
		formPanel.add(timeInput);
		formPanel.add(queryBtn);
		formPanel.setVisible(true);
		formPanel.setSize(500, 300);
		formPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		containerPanel.add(formPanel, BorderLayout.NORTH);
		
		//resultPanel
		table = new JTable();
		table.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		table.setPreferredScrollableViewportSize(new Dimension(500,330));
		JScrollPane scrollPane = new JScrollPane(table); //将表格加入到滚动条组件中
		containerPanel.add(scrollPane, BorderLayout.CENTER);
	}
}
