package com.rci.ui.swing.handle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
//				final DiscountScheme dzdpscheme = schemeService.getSchemeByNo("97");
//				final DiscountScheme dzdpshscheme = schemeService.getSchemeByNo("98");
//				final DiscountScheme mtscheme = schemeService.getSchemeByNo("99");
//				JFrame frame = new JFrame("参数设置");
//				JRootPane panel = frame.getRootPane();
//				JLabel dzdp = new JLabel("大众点评");
//				final JTextField dzdpInput = new JTextField(10);
//				if(dzdpscheme.getCommission() != null){
//					dzdpInput.setText(dzdpscheme.getCommission().toString());
//				}
//				
//				JLabel unit1 = new JLabel("%");
//				JLabel unit2 = new JLabel("%");
//				JLabel mt = new JLabel("美团");
//				final JTextField mtInput = new JTextField(10);
//				if(mtscheme.getCommission() != null){
//					mtInput.setText(mtscheme.getCommission().toString());
//				}
//				FlowLayout layout = new FlowLayout(FlowLayout.CENTER,10,5);
//				panel.setLayout(layout);
//				JPanel p1 = new JPanel();
//				JPanel p2 = new JPanel();
//				panel.add(p1);
//				panel.add(p2);
//				p1.add(dzdp);
//				p1.add(dzdpInput);
//				p1.add(unit1);
//				p2.add(mt);
//				p2.add(mtInput);
//				p2.add(unit2);
//				JButton save = new JButton("返点率设置");
//				panel.add(save);
//				
//				save.addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						String dzdpValue = dzdpInput.getText();
//						String mtValue = mtInput.getText();
//						dzdpscheme.setCommission(new BigDecimal(dzdpValue));
//						dzdpshscheme.setCommission(new BigDecimal(dzdpValue));
//						mtscheme.setCommission(new BigDecimal(mtValue));
//						DiscountScheme[] schemes = new DiscountScheme[3];
//						schemes[0] = dzdpscheme;
//						schemes[1] = dzdpshscheme;
//						schemes[2] = mtscheme;
//						schemeService.rwUpdateDiscountSchemes(schemes);
//						JOptionPane.showMessageDialog(null, "设置成功");
//					}
//				});
//				
//				frame.pack();
//				frame.addWindowListener(new WindowAdapter() {
//					@Override
//					public void windowClosed(WindowEvent e) {
//						super.windowClosed(e);
//					}
//				});
//				frame.setLocationRelativeTo(null); // 相对居中, 在frame设置size之后
//				frame.setVisible(true);
			}
		};
	}
}
