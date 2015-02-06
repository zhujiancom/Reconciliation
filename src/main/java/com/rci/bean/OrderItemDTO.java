package com.rci.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.rci.annotation.ColumnName;

public class OrderItemDTO {
	/* 订单编号 */
	private String billNo;
	
	/* 付费编号*/
	private String payNo;
	
	/* 菜品编号 */
	private String dishNo;
	
	/* 套餐编号 */
	private String suitNo;
	
	/* 是否套餐 */
	private String suitFlag;
	
	/* 折扣率 */
	private BigDecimal discountRate;
	
	/* 点菜数量  */
	private BigDecimal count;
	
	/* 退菜数量 */
	private BigDecimal countback;
	
	/* 菜品单价 */
	private BigDecimal price;
	
	/* 折扣金额  */
	private BigDecimal discountAmount;
	
	/* 实际金额   */
	private BigDecimal actualAmount;
	
	/* 点菜时间  */
	private Timestamp consumeTime;

	public String getBillNo() {
		return billNo;
	}

	@ColumnName("billno")
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getPayNo() {
		return payNo;
	}

	@ColumnName("payno")
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getDishNo() {
		return dishNo;
	}

	@ColumnName("dishno")
	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	public String getSuitNo() {
		return suitNo;
	}

	@ColumnName("suitno")
	public void setSuitNo(String suitNo) {
		this.suitNo = suitNo;
	}

	public String getSuitFlag() {
		return suitFlag;
	}

	@ColumnName("suitflag")
	public void setSuitFlag(String suitFlag) {
		this.suitFlag = suitFlag;
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	@ColumnName("discount")
	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	
	public BigDecimal getCount() {
		return count;
	}

	@ColumnName("count")
	public void setCount(BigDecimal count) {
		this.count = count;
	}

	public BigDecimal getPrice() {
		return price;
	}

	@ColumnName("price")
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getCountback() {
		return countback;
	}

	@ColumnName("countback")
	public void setCountback(BigDecimal countback) {
		this.countback = countback;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Timestamp getConsumeTime() {
		return consumeTime;
	}

	@ColumnName("consumeTime")
	public void setConsumeTime(Timestamp consumeTime) {
		this.consumeTime = consumeTime;
	}
}
