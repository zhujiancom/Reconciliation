package com.rci.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.rci.annotation.ColumnName;
import com.rci.bean.entity.DiscountScheme;

public class OrderDTO {
	private String orderNo;
	
	/* 开桌时间  */
	private Timestamp openDeskTime;
	
	/* 结账时间  */
	private Timestamp checkoutTime;
	
	/* 票据应收金额    */
	private BigDecimal receivableAmount;

	/* 折扣方案  */
	private DiscountScheme scheme;  //代金券或者是打折
	
//	/* 临时折扣方案  */
//	private BigDecimal tempDiscountRate; //解决收银员自己手动输入打折率
	
	/* 是否有临时折扣方案  */
	private Boolean isTempDiscount;
	
	/* 实收金额   */
	private BigDecimal actualAmount;

	public String getOrderNo() {
		return orderNo;
	}

	@ColumnName("order_no")
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Timestamp getOpenDeskTime() {
		return openDeskTime;
	}

	@ColumnName("open_desk_time")
	public void setOpenDeskTime(Timestamp openDeskTime) {
		this.openDeskTime = openDeskTime;
	}

	public Timestamp getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(Timestamp checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public BigDecimal getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(BigDecimal receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public DiscountScheme getScheme() {
		return scheme;
	}

	public void setScheme(DiscountScheme scheme) {
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

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}
}
