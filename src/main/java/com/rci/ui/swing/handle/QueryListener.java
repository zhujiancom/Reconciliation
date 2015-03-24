package com.rci.ui.swing.handle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import org.springframework.util.CollectionUtils;

import com.rci.constants.enums.PaymodeType;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionManage;
import com.rci.exceptions.ServiceException;
import com.rci.service.IOrderService;
import com.rci.tools.DateUtil;
import com.rci.tools.SpringUtils;
import com.rci.tools.StringUtility;
import com.rci.ui.model.OrderItemTableModel;
import com.rci.ui.model.OrderTableModel;
import com.rci.ui.vo.OrderItemVO;
import com.rci.ui.vo.OrderVO;

public class QueryListener implements ActionListener,ListSelectionListener {
	private JTable mainTable;
	private JTable subTable;
	private IOrderService orderService;
	private JTextField timeInput;
	private List<OrderVO> orders;
	private JLabel posValue;
	private JLabel mtValue;
	private JLabel tgValue;
	private JLabel shValue;
	private JLabel eleValue;
	private JLabel tddValue;
	
	public QueryListener(JTable mainTable,JTable subTable){
		this.mainTable = mainTable;
		this.subTable = subTable;
		orderService = (IOrderService) SpringUtils.getBean("OrderService");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String time = timeInput.getText();
		try{
			loadOrderData(time);
			posValue.setText(getTotalAmount(PaymodeType.POS).toString());
			mtValue.setText(getTotalAmount(PaymodeType.MT).toString());
			tgValue.setText(getTotalAmount(PaymodeType.DPTG).toString());
			shValue.setText(getTotalAmount(PaymodeType.DPSH).toString());
			eleValue.setText(getTotalAmount(PaymodeType.ELE).toString());
			tddValue.setText(getTotalAmount(PaymodeType.TDD).toString());
			mainTable.setRowSelectionAllowed(true);
			mainTable.setRowSelectionInterval(0, 0);
			mainTable.getSelectionModel().addListSelectionListener(this);
		}catch(ServiceException se){
			JOptionPane.showMessageDialog(null, se.getMessage());
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if(event.getSource() == mainTable.getSelectionModel()
				&& mainTable.getRowSelectionAllowed()){
			int row = mainTable.getSelectedRow();
			if(row != -1){
				String payno = (String) mainTable.getValueAt(row, 1);
				loadItemData(payno);
			}
		}
	}
	
	/**
	 * 装载 order Item 数据
	 * @param payno
	 */
	public void loadItemData(String payno){
		List<OrderItemVO> orderItems = orderService.queryOrderItemVOsByPayno(payno);
		OrderItemTableModel otm = new OrderItemTableModel(orderItems);
		subTable.setModel(otm);
		TableColumnModel cm = subTable.getColumnModel();
		cm.getColumn(0).setHeaderValue("菜品名称");
		cm.getColumn(0).setPreferredWidth(105);
		cm.getColumn(1).setHeaderValue("数量");
		cm.getColumn(1).setPreferredWidth(105);
		cm.getColumn(2).setHeaderValue("退菜数量");
		cm.getColumn(2).setPreferredWidth(75);
		cm.getColumn(3).setHeaderValue("原价");
		cm.getColumn(3).setPreferredWidth(75);
		cm.getColumn(4).setHeaderValue("实收金额");
		cm.getColumn(4).setPreferredWidth(75);
		cm.getColumn(5).setHeaderValue("折扣率");
		cm.getColumn(5).setPreferredWidth(115);
		cm.getColumn(6).setHeaderValue("消费时间");
		cm.getColumn(6).setPreferredWidth(140);
		subTable.setColumnModel(cm);
		subTable.setRowHeight(20);
		subTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	/**
	 * 装载 订单order 数据
	 * @param time
	 * @throws ServiceException
	 */
	public void loadOrderData(String time) throws ServiceException{
		if(!StringUtility.isDateFormated(time,"\\d{8}")){
			ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "日期格式错误");
		}
		Calendar queryDay = Calendar.getInstance();
		queryDay.setTime(DateUtil.str2Date(time,"yyyyMMdd"));
		Calendar currentDay = Calendar.getInstance();
		currentDay.setTime(DateUtil.getCurrentDate());
		if(queryDay.after(currentDay)){
			ExceptionManage.throwServiceException(SERVICE.TIME_FORMAT, "查询时间不能晚于当前时间");
		}
		List<OrderVO> ordervos = orderService.queryOrderVOsByDay(time);
		this.orders = ordervos;
		OrderTableModel otm = new OrderTableModel(orders);
		mainTable.setModel(otm);
		TableColumnModel cm = mainTable.getColumnModel();
		cm.getColumn(0).setHeaderValue("序号");
		cm.getColumn(0).setPreferredWidth(50);
		cm.getColumn(1).setHeaderValue("付款编号");
		cm.getColumn(1).setPreferredWidth(105);
		cm.getColumn(2).setHeaderValue("原价");
		cm.getColumn(2).setPreferredWidth(75);
		cm.getColumn(3).setHeaderValue("实收金额");
		cm.getColumn(3).setPreferredWidth(75);
		cm.getColumn(4).setHeaderValue("折扣方案");
		cm.getColumn(4).setPreferredWidth(115);
		cm.getColumn(5).setHeaderValue("结账时间");
		cm.getColumn(5).setPreferredWidth(140);
		cm.getColumn(6).setHeaderValue("pos机入账");
		cm.getColumn(6).setPreferredWidth(70);
		cm.getColumn(7).setHeaderValue("美团入账");
		cm.getColumn(7).setPreferredWidth(70);
		cm.getColumn(8).setHeaderValue("大众点评团购");
		cm.getColumn(8).setPreferredWidth(105);
		cm.getColumn(9).setHeaderValue("大众点评闪惠");
		cm.getColumn(9).setPreferredWidth(105);
		cm.getColumn(10).setHeaderValue("饿了么入账");
		cm.getColumn(10).setPreferredWidth(75);
		cm.getColumn(11).setHeaderValue("淘点点入账");
		cm.getColumn(11).setPreferredWidth(75);
		cm.getColumn(12).setHeaderValue("总金额");
		cm.getColumn(12).setPreferredWidth(75);
		mainTable.setColumnModel(cm);
		mainTable.setRowHeight(20);
		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	public JTextField getTimeInput() {
		return timeInput;
	}

	public void setTimeInput(JTextField timeInput) {
		this.timeInput = timeInput;
	}
	
	public BigDecimal getTotalAmount(PaymodeType paymode){
		BigDecimal totalAmount = new BigDecimal(BigInteger.ZERO,2);
		if(CollectionUtils.isEmpty(orders)){
			return totalAmount;
		}
		if(PaymodeType.MT.equals(paymode)){
			for(OrderVO order:orders){
				if(order.getMtAmount() != null){
					totalAmount = totalAmount.add(order.getMtAmount());
				}
			}
		}
		if(PaymodeType.POS.equals(paymode)){
			for(OrderVO order:orders){
				if(order.getPosAmount() != null){
					totalAmount = totalAmount.add(order.getPosAmount());
				}
			}
		}
		if(PaymodeType.DPTG.equals(paymode)){
			for(OrderVO order:orders){
				if(order.getDptgAmount() != null){
					totalAmount = totalAmount.add(order.getDptgAmount());
				}
			}
		}
		if(PaymodeType.DPSH.equals(paymode)){
			for(OrderVO order:orders){
				if(order.getDpshAmount() != null){
					totalAmount = totalAmount.add(order.getDpshAmount());
				}
			}
		}
		if(PaymodeType.ELE.equals(paymode)){
			for(OrderVO order:orders){
				if(order.getEleAmount() != null){
					totalAmount = totalAmount.add(order.getEleAmount());
				}
			}
		}
		if(PaymodeType.TDD.equals(paymode)){
			for(OrderVO order:orders){
				if(order.getTddAmount() != null){
					totalAmount = totalAmount.add(order.getTddAmount());
				}
			}
		}
		return totalAmount;
	}

	public JLabel getPosValue() {
		return posValue;
	}

	public void setPosValue(JLabel posValue) {
		this.posValue = posValue;
	}

	public JLabel getMtValue() {
		return mtValue;
	}

	public void setMtValue(JLabel mtValue) {
		this.mtValue = mtValue;
	}

	public JLabel getTgValue() {
		return tgValue;
	}

	public void setTgValue(JLabel tgValue) {
		this.tgValue = tgValue;
	}

	public JLabel getShValue() {
		return shValue;
	}

	public void setShValue(JLabel shValue) {
		this.shValue = shValue;
	}

	public JLabel getEleValue() {
		return eleValue;
	}

	public void setEleValue(JLabel eleValue) {
		this.eleValue = eleValue;
	}

	public JLabel getTddValue() {
		return tddValue;
	}

	public void setTddValue(JLabel tddValue) {
		this.tddValue = tddValue;
	}

}
