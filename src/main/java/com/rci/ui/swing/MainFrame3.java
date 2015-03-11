package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import com.rci.constants.PropertyConstants;
import com.rci.constants.enums.PaymodeType;
import com.rci.tools.properties.PropertyUtils;
import com.rci.ui.swing.handle.OrderOperateHandler;
import com.rci.ui.swing.handle.SelectionListener;
import com.rci.ui.swing.handle.SystemInitHandler;

public class MainFrame3 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8216786708977859424L;
	
	private JPanel containerPanel;
	private JButton queryBtn;
	private JTextField timeInput;
	private JScrollPane mainScrollPane;
	private JScrollPane subScrollPane;
	private JTable mainTable; //展示order 列表
	private JTable itemTable; //展示 orderItem 列表
	
	private JLabel posValue;
	private JLabel mtValue;
	private JLabel tgValue;
	private JLabel shValue;
	private JLabel eleValue;
	private JLabel tddValue;
	
	public MainFrame3(){
		initComponent();
		queryBtn.registerKeyboardAction(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String time = timeInput.getText();
				OrderOperateHandler handler = new OrderOperateHandler(mainTable);
				mainTable = handler.loadOrderData(time);
				posValue.setText(handler.getTotalAmount(PaymodeType.POS).toString());
				mtValue.setText(handler.getTotalAmount(PaymodeType.MT).toString());
				tgValue.setText(handler.getTotalAmount(PaymodeType.DPTG).toString());
				shValue.setText(handler.getTotalAmount(PaymodeType.DPSH).toString());
				eleValue.setText(handler.getTotalAmount(PaymodeType.ELE).toString());
				tddValue.setText(handler.getTotalAmount(PaymodeType.TDD).toString());
				mainTable.setRowSelectionAllowed(true);
				mainTable.setRowSelectionInterval(0, 0);
				mainTable.getSelectionModel().addListSelectionListener(new SelectionListener(mainTable, itemTable));
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		queryBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String time = timeInput.getText();
				OrderOperateHandler handler = new OrderOperateHandler(mainTable);
				mainTable = handler.loadOrderData(time);
				posValue.setText(handler.getTotalAmount(PaymodeType.POS).toString());
				mtValue.setText(handler.getTotalAmount(PaymodeType.MT).toString());
				tgValue.setText(handler.getTotalAmount(PaymodeType.DPTG).toString());
				shValue.setText(handler.getTotalAmount(PaymodeType.DPSH).toString());
				eleValue.setText(handler.getTotalAmount(PaymodeType.ELE).toString());
				tddValue.setText(handler.getTotalAmount(PaymodeType.TDD).toString());
				mainScrollPane.setViewportView(mainTable);
				
				mainTable.setRowSelectionAllowed(true);
				mainTable.setRowSelectionInterval(0, 0);
			}
		});
		
//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		} catch (ClassNotFoundException | InstantiationException
//				| IllegalAccessException | UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//		}
//		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	private void initComponent() {
		this.setTitle((String) PropertyUtils.getProperties(PropertyConstants.SYSNAME));
		containerPanel = new JPanel();
		this.setContentPane(containerPanel);
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension fullScreen = tool.getScreenSize();
		containerPanel.setPreferredSize(fullScreen);
		BorderLayout layout = new BorderLayout(0, 10);
		containerPanel.setLayout(layout);
		
		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);
		JMenu sysMenu = new JMenu("系统");
		JMenu helpMenu = new JMenu("帮助");
		menubar.add(sysMenu);
		menubar.add(helpMenu);
		JMenuItem dataInit = new JMenuItem("数据初始化");
		JMenuItem settings = new JMenuItem("参数设置");
		JMenuItem helpInfo = new JMenuItem("帮助信息");
		sysMenu.add(dataInit);
		sysMenu.add(settings);
		helpMenu.add(helpInfo);
		SystemInitHandler handler = new SystemInitHandler();
		dataInit.addActionListener(handler.dataInit());
		settings.addActionListener(handler.settings());
		helpMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "帮助信息。。。。。");
			}
		});
		
		JPanel formPanel = new JPanel();
		JLabel rciTime = new JLabel("日期");
		timeInput = new JTextField(10);
		queryBtn = new JButton("查询");
		formPanel.add(rciTime);
		formPanel.add(timeInput);
		formPanel.add(queryBtn);
		formPanel.setVisible(true);
		formPanel.setSize(500, 300);
		formPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		containerPanel.add(formPanel, BorderLayout.NORTH);
		
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(1, 2));
		mainScrollPane = new JScrollPane(); //将表格加入到滚动条组件中
		mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainTable = new JTable();
		mainScrollPane.setViewportView(mainTable);
		subScrollPane = new JScrollPane(); //将表格加入到滚动条组件中
		subScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		itemTable = new JTable();
		subScrollPane.setViewportView(itemTable);
		dataPanel.add(mainScrollPane);
		dataPanel.add(subScrollPane);
		containerPanel.add(dataPanel, BorderLayout.CENTER);
		
		JPanel conclusionPanel = new JPanel();
		this.add(conclusionPanel, BorderLayout.SOUTH);
		GridBagLayout lay = new GridBagLayout();
		conclusionPanel.setLayout(lay);
		
		JLabel pos = new JLabel("收银机入账总额：");
		posValue = new JLabel();
		posValue.setForeground(Color.RED);
		
		JLabel mt = new JLabel("美团入账总额：");
		mtValue = new JLabel();
		mtValue.setForeground(Color.RED);
		
		JLabel tg = new JLabel("大众点评团购入账总额：");
		tgValue = new JLabel();
		tgValue.setForeground(Color.RED);
		
		JLabel sh = new JLabel("大众点评闪惠入账总额：");
		shValue = new JLabel();
		shValue.setForeground(Color.RED);
		
		JLabel ele = new JLabel("饿了么入账总额：");
		eleValue = new JLabel();
		eleValue.setForeground(Color.RED);
		
		JLabel tdd = new JLabel("淘点点入账总额：");
		tddValue = new JLabel();
		tddValue.setForeground(Color.RED);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx=2;
		constraints.weighty=7;
		add(lay,pos,constraints,0,1,1,1);
		add(lay,mt,constraints,0,2,1,1);
		add(lay,tg,constraints,0,3,1,1);
		add(lay,sh,constraints,0,4,1,1);
		add(lay,ele,constraints,0,5,1,1);
		add(lay,tdd,constraints,0,6,1,1);
		
		add(lay,posValue,constraints,2,1,1,1);
		add(lay,mtValue,constraints,2,2,1,1);
		add(lay,tgValue,constraints,2,3,1,1);
		add(lay,shValue,constraints,2,4,1,1);
		add(lay,eleValue,constraints,2,5,1,1);
		add(lay,tddValue,constraints,2,6,1,1);
		
		conclusionPanel.add(pos);
		conclusionPanel.add(posValue);
		conclusionPanel.add(mt);
		conclusionPanel.add(mtValue);
		conclusionPanel.add(tg);
		conclusionPanel.add(tgValue);
		conclusionPanel.add(sh);
		conclusionPanel.add(shValue);
		conclusionPanel.add(ele);
		conclusionPanel.add(eleValue);
		conclusionPanel.add(tdd);
		conclusionPanel.add(tddValue);
	}
	
	private void add(GridBagLayout layout,JComponent c,GridBagConstraints constraints,int x,int y,int w,int h){
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		layout.setConstraints(c, constraints);
	}
}
