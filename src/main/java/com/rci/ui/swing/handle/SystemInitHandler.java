package com.rci.ui.swing.handle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.rci.service.InitSystemService;
import com.rci.tools.SpringUtils;

public class SystemInitHandler extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4393899033664657099L;
	private InitSystemService service;
	
	public SystemInitHandler(){
		service = (InitSystemService) SpringUtils.getBean("InitSystemService");
	}

	
	public ActionListener dataInit(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				service.init();
				JOptionPane.showMessageDialog(null, "数据初始化成功！");
			}
		};
	}
	
	public ActionListener settings(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("参数设置");
				frame.setSize(600,480);
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						super.windowClosed(e);
					}
				});
				frame.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
				frame.setVisible(true);
			}
		};
	}
}
