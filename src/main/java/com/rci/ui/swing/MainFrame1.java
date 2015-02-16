package com.rci.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.rci.constants.PropertyConstants;
import com.rci.tools.properties.PropertyUtils;

public class MainFrame1 {
	public static final int WIDTH = 950;
	public static final int HEIGHT = 640;
	private JFrame jf;
	private JPanel containerPanel;
	private JPanel formPanel;
	private Component resultPanel;

	public MainFrame1() {
		jf = new JFrame((String) PropertyUtils.getProperties(PropertyConstants.SYSNAME));
		containerPanel = new JPanel();
		jf.setContentPane(containerPanel);
		BorderLayout layout = new BorderLayout(0, 10);
		// jf.setLayout(layout); //layout 要在panel添加组件之前设置
		containerPanel.setLayout(layout);
		formPanel = buildFormPanel();
		resultPanel = buildResultPanel();
		containerPanel.add(formPanel, "North");
		containerPanel.add(resultPanel, BorderLayout.CENTER);

//		 Toolkit tool = Toolkit.getDefaultToolkit();
//		 Dimension dimension = tool.getScreenSize();
		// int x = dimension.width/2-WIDTH/2;
		// int y = dimension.height/2-HEIGHT/2;
		// jf.setLocation(new Point(x,y));

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		jf.setSize(WIDTH, HEIGHT); // 在panel添加组件之后设置
//		jf.setSize(dimension);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
		jf.setVisible(true);
	}

	private JPanel buildFormPanel() {
		JPanel panel = new JPanel();
		// BorderLayout layout = new BorderLayout();
		// panel.setLayout(layout);
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

	private Component buildResultPanel() {
		Object[][] playerInfo = {
					{"zhujian",new Integer(91),new Integer(100),new Integer(191),new Boolean(true)},
					{"zhangshang",new Integer(91),new Integer(100),new Integer(191),new Boolean(true)}
				};
		String[] names = {"姓名","语文","数学","总分","及格"};
//		JTable table = new JTable(playerInfo,names);
		JTable table = new JTable(10,10);
		table.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		table.setPreferredScrollableViewportSize(new Dimension(500,330));
		JScrollPane scrollPane = new JScrollPane(table); //将表格加入到滚动条组件中
		return scrollPane;
	}

	public static void main(String[] args) {
		new MainFrame1();
	}
}
