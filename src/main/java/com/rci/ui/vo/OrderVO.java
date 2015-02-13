package com.rci.ui.vo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderVO {
	private Long orderId;
	
	private String payno;
	
	private BigDecimal originAmount;
	
	private BigDecimal actualAmount;
	
	private String schemeName;
	
	private String isTempScheme;
	
	private Date checkoutTime;
	
	private BigDecimal posAmount;
	
	private BigDecimal mtAmount;
	
	private BigDecimal dptgAmount;
	
	private BigDecimal dpshAmount;
	
	private BigDecimal tddAmount;
	
	private BigDecimal eleAmount;
	
	private BigDecimal totalAmount;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getPayno() {
		return payno;
	}

	public void setPayno(String payno) {
		this.payno = payno;
	}

	public BigDecimal getOriginAmount() {
		return originAmount;
	}

	public void setOriginAmount(BigDecimal originAmount) {
		this.originAmount = originAmount;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getIsTempScheme() {
		return isTempScheme;
	}

	public void setIsTempScheme(String isTempScheme) {
		this.isTempScheme = isTempScheme;
	}

	public Date getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public BigDecimal getPosAmount() {
		return posAmount;
	}

	public void setPosAmount(BigDecimal posAmount) {
		this.posAmount = posAmount;
	}

	public BigDecimal getMtAmount() {
		return mtAmount;
	}

	public void setMtAmount(BigDecimal mtAmount) {
		this.mtAmount = mtAmount;
	}

	public BigDecimal getDptgAmount() {
		return dptgAmount;
	}

	public void setDptgAmount(BigDecimal dptgAmount) {
		this.dptgAmount = dptgAmount;
	}

	public BigDecimal getDpshAmount() {
		return dpshAmount;
	}

	public void setDpshAmount(BigDecimal dpshAmount) {
		this.dpshAmount = dpshAmount;
	}

	public BigDecimal getTddAmount() {
		return tddAmount;
	}

	public void setTddAmount(BigDecimal tddAmount) {
		this.tddAmount = tddAmount;
	}

	public BigDecimal getEleAmount() {
		return eleAmount;
	}

	public void setEleAmount(BigDecimal eleAmount) {
		this.eleAmount = eleAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = this.posAmount.add(this.mtAmount)
							.add(this.dptgAmount).add(this.dpshAmount)
							.add(this.tddAmount).add(this.eleAmount);
	}
}
