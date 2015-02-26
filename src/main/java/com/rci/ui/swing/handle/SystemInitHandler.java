package com.rci.ui.swing.handle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class SystemInitHandler extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4393899033664657099L;

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setSize(600,480);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
			}
			
		});
		this.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
		this.setVisible(true);
	}

}
