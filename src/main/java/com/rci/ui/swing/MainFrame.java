package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.rci.constants.PropertyConstants;
import com.rci.service.InitSystemService;
import com.rci.tools.SpringUtils;
import com.rci.tools.properties.PropertyUtils;
import com.rci.ui.swing.handle.OrderOperateHandler;
import com.rci.ui.swing.handle.SystemInitHandler;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4254277479150685575L;
	public static final int WIDTH = 950;
	public static final int HEIGHT = 640;
	private static JPanel containerPanel;
	private JComponent formPanel;
	private JButton queryBtn;
	private JButton initBtn;
	private JTextField timeInput;
	private JScrollPane scrollPane;

	public MainFrame(){
		initComponent();
		queryBtn.registerKeyboardAction(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String time = timeInput.getText();
				JTable table = OrderOperateHandler.loadData(time);
				scrollPane.setViewportView(table);
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		queryBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String time = timeInput.getText();
				JTable table = OrderOperateHandler.loadData(time);
				scrollPane.setViewportView(table);
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
		
		//datadisplay panel
		scrollPane = new JScrollPane(); //将表格加入到滚动条组件中
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(550, 300));
		containerPanel.add(scrollPane, BorderLayout.CENTER);
	}
}
