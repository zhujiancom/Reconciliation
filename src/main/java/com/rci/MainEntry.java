package com.rci;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rci.ui.swing.MainFrame;


public class MainEntry {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-common.xml","spring/spring-db.xml");
		new MainFrame();
	}

}
