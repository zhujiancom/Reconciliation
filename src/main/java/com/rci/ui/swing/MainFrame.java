package com.rci.ui.swing;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableColumn;

import com.rci.constants.PropertyConstants;
import com.rci.tools.properties.PropertyUtils;

public class MainFrame{
	public static final Integer WIDTH=600;
	public static final Integer HEIGHT=480;
	private JFrame frame;
	private JPanel containerPanel;
	private JPanel formPanel;
	private JPanel resultPanel;
	
	public MainFrame(){
		frame = new JFrame((String)PropertyUtils.getProperties(PropertyConstants.SYSNAME));
		containerPanel = new JPanel();
		frame.setContentPane(containerPanel);
		formPanel = buildFormPanel();
		resultPanel = buildResultPanel();
		containerPanel.add(formPanel);
		containerPanel.add(resultPanel);
		
//		Toolkit tool = Toolkit.getDefaultToolkit();
//		Dimension dimension = tool.getScreenSize();
//		int x = dimension.width/2-WIDTH/2;
//		int y = dimension.height/2-HEIGHT/2;
//		frame.setLocation(new Point(x,y));
		
		frame.setSize(WIDTH,HEIGHT);
		frame.setLocationRelativeTo(null);  //相对居中
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
//		frame.pack();
		frame.setVisible(true);
	}
	
	private JPanel buildFormPanel(){
		JPanel panel = new JPanel();
//		BorderLayout layout = new BorderLayout();
//		panel.setLayout(layout);
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
	
	private JPanel buildResultPanel(){
		JPanel panel = new JPanel();
		JTable table = new JTable();
		table.setBorder(BorderFactory.createLineBorder(Color.GREEN));
//		table.setAutoCreateColumnsFromModel(true);
		panel.add(table);
		panel.setSize(500, 300);
		panel.setBorder(BorderFactory.createLineBorder(Color.red));
		return panel;
	}
	public static void main(String[] args){
		new MainFrame();
	}
}
