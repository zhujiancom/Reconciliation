package com.rci;

import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rci.ui.swing.MainFrame;


public class MainEntry {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-common.xml","spring/spring-db.xml");
		JFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
		frame.setVisible(true);
//		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

}
