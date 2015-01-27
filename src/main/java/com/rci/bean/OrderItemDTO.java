package com.rci.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderItemDTO {
	/* 菜品编号 */
	private String dishNo;
	
	/* 折扣率 */
	private BigDecimal discountRate;
	
	/* 数量  */
	private Integer count;
	
	/* 折扣金额  */
	private BigDecimal discountAmount;
	
	/* 实际金额   */
	private BigDecimal actualAmount;
	
	/* 消费时间  */
	private Timestamp consumeTime;

	public String getDishNo() {
		return dishNo;
	}

	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public void setConsumeTime(Timestamp consumeTime) {
		this.consumeTime = consumeTime;
	}
}
