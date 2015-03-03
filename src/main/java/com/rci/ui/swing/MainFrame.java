package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
import com.rci.constants.enums.PaymodeType;
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
	private JLabel posValue;
	private JLabel mtValue;
	private JLabel tgValue;
	private JLabel shValue;
	private JLabel eleValue;
	private JLabel tddValue;

	public MainFrame(){
		initComponent();
		queryBtn.registerKeyboardAction(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String time = timeInput.getText();
				OrderOperateHandler handler = new OrderOperateHandler();
				JTable table = handler.loadData(time);
				posValue.setText(handler.getTotalAmount(PaymodeType.POS).toString());
				mtValue.setText(handler.getTotalAmount(PaymodeType.MT).toString());
				tgValue.setText(handler.getTotalAmount(PaymodeType.DPTG).toString());
				shValue.setText(handler.getTotalAmount(PaymodeType.DPSH).toString());
				eleValue.setText(handler.getTotalAmount(PaymodeType.ELE).toString());
				tddValue.setText(handler.getTotalAmount(PaymodeType.TDD).toString());
				scrollPane.setViewportView(table);
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		queryBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String time = timeInput.getText();
				OrderOperateHandler handler = new OrderOperateHandler();
				JTable table = handler.loadData(time);
				posValue.setText(handler.getTotalAmount(PaymodeType.POS).toString());
				mtValue.setText(handler.getTotalAmount(PaymodeType.MT).toString());
				tgValue.setText(handler.getTotalAmount(PaymodeType.DPTG).toString());
				shValue.setText(handler.getTotalAmount(PaymodeType.DPSH).toString());
				eleValue.setText(handler.getTotalAmount(PaymodeType.ELE).toString());
				tddValue.setText(handler.getTotalAmount(PaymodeType.TDD).toString());
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
		JPanel conclusionPanel = new JPanel();
		GridBagLayout lay = new GridBagLayout();
		conclusionPanel.setLayout(lay);
		
		JLabel pos = new JLabel("收银机入账总额：");
		pos.setBorder(BorderFactory.createLineBorder(Color.green));
		posValue = new JLabel();
		posValue.setForeground(Color.RED);
		posValue.setBorder(BorderFactory.createLineBorder(Color.green));

		
		JLabel mt = new JLabel("美团入账总额：");
		mt.setBorder(BorderFactory.createLineBorder(Color.green));
		mtValue = new JLabel();
		mtValue.setForeground(Color.RED);
		mtValue.setBorder(BorderFactory.createLineBorder(Color.green));
		
		JLabel tg = new JLabel("大众点评团购入账总额：");
		tg.setBorder(BorderFactory.createLineBorder(Color.green));
		tgValue = new JLabel();
		tgValue.setForeground(Color.RED);
		tgValue.setBorder(BorderFactory.createLineBorder(Color.green));
		
		JLabel sh = new JLabel("大众点评闪惠入账总额：");
		sh.setBorder(BorderFactory.createLineBorder(Color.green));
		shValue = new JLabel();
		shValue.setForeground(Color.RED);
		shValue.setBorder(BorderFactory.createLineBorder(Color.green));
		
		JLabel ele = new JLabel("饿了么入账总额：");
		ele.setBorder(BorderFactory.createLineBorder(Color.green));
		eleValue = new JLabel();
		eleValue.setForeground(Color.RED);
		eleValue.setBorder(BorderFactory.createLineBorder(Color.green));
		
		JLabel tdd = new JLabel("淘点点入账总额：");
		tdd.setBorder(BorderFactory.createLineBorder(Color.green));
		tddValue = new JLabel();
		tddValue.setForeground(Color.RED);
		tddValue.setBorder(BorderFactory.createLineBorder(Color.green));
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.EAST;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx = 1;
		constraints.weighty = 4;
		
		add(conclusionPanel,pos,constraints,0,0,1,1);
		add(conclusionPanel,posValue,constraints,1,0,1,1);
		add(conclusionPanel,mt,constraints,0,1,1,1);
		add(conclusionPanel,mtValue,constraints,1,1,1,1);
		add(conclusionPanel,tg,constraints,0,2,1,1);
		add(conclusionPanel,tgValue,constraints,1,2,1,1);
		add(conclusionPanel,sh,constraints,0,3,1,1);
		add(conclusionPanel,shValue,constraints,1,3,1,1);
		add(conclusionPanel,ele,constraints,0,4,1,1);
		add(conclusionPanel,eleValue,constraints,1,4,1,1);
		add(conclusionPanel,tdd,constraints,0,5,1,1);
		add(conclusionPanel,tddValue,constraints,1,5,1,1);
		
		
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
		containerPanel.add(conclusionPanel, BorderLayout.SOUTH);
	}
	
	private void add(JComponent main,JComponent c,GridBagConstraints constraints,int x,int y,int w,int h){
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		GridBagLayout layout = (GridBagLayout)main.getLayout();
		layout.setConstraints(c, constraints);
	}
}
