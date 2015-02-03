package com.rci.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.rci.annotation.ColumnName;

public class OrderDTO {
	private String orderNo;
	
	private String payNo;
	
	/* 开桌时间  */
	private Timestamp openDeskTime;
	
	/* 结账时间  */
	private Timestamp checkoutTime;
	
	/* 票据应收金额    */
	private BigDecimal receivableAmount;

	/* 折扣方案  */
	private String scheme;  //代金券或者是打折
	
//	/* 临时折扣方案  */
//	private BigDecimal tempDiscountRate; //解决收银员自己手动输入打折率
	
	/* 是否有临时折扣方案  */
	private Boolean isTempDiscount;
	
	/* 实收金额   */
	private BigDecimal actualAmount;

	public String getOrderNo() {
		return orderNo;
	}

	@ColumnName("billno")
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayNo() {
		return payNo;
	}

	@ColumnName("payno")
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public Timestamp getOpenDeskTime() {
		return openDeskTime;
	}

	@ColumnName("opendesktime")
	public void setOpenDeskTime(Timestamp openDeskTime) {
		this.openDeskTime = openDeskTime;
	}

	public Timestamp getCheckoutTime() {
		return checkoutTime;
	}
	
	@ColumnName("checkouttime")
	public void setCheckoutTime(Timestamp checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public BigDecimal getReceivableAmount() {
		return receivableAmount;
	}

	@ColumnName("originamount")
	public void setReceivableAmount(BigDecimal receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public String getScheme() {
		return scheme;
	}

	@ColumnName("paymode")
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public Boolean getIsTempDiscount() {
		return isTempDiscount;
	}

	public void setIsTempDiscount(Boolean isTempDiscount) {
		this.isTempDiscount = isTempDiscount;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	@ColumnName("realamount")
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}
}
