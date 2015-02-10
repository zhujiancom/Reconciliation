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

	/* 支付方式  */
	private String paymode;  //现金或代金券
	
	/* 优惠活动  */
	private String project;  //8折优惠，无折扣
	
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

	public String getPaymode() {
		return paymode;
	}

	@ColumnName("paymode")
	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}

	public String getProject() {
		return project;
	}

	@ColumnName("project")
	public void setProject(String project) {
		this.project = project;
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
