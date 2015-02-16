package com.rci.ui.swing;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.rci.constants.PropertyConstants;
import com.rci.tools.properties.PropertyUtils;

public class TestSwing {
	public static final int WIDTH=600;
	public static final int HEIGHT=480;
	public static void main(String[] args) {
		JFrame jf = new JFrame((String)PropertyUtils.getProperties(PropertyConstants.SYSNAME));
		JPanel mainPanel = new JPanel();
		jf.setContentPane(mainPanel);
		JButton b1 = new JButton("生活");
		JButton b2 = new JButton("生活");
		JButton b3 = new JButton("生活");
		JButton b4 = new JButton("生活");
		JButton b5 = new JButton("生活");
		BorderLayout lay = new BorderLayout();
		jf.setLayout(lay);
		mainPanel.add(b1,"North");
		mainPanel.add(b2,"South");
		mainPanel.add(b3,"West");
		mainPanel.add(b4,"East");
		mainPanel.add(b5,"Center");
		jf.setSize(WIDTH,HEIGHT);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
//		jf.pack();
	}

}
