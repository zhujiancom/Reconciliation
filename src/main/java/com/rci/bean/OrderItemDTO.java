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
	
	/* 折扣率 */
	private BigDecimal discountRate;
	
	/* 点菜数量  */
	private Integer count;
	
	/* 退菜数量 */
	private Integer countback;
	
	/* 折扣金额  */
	private BigDecimal discountAmount;
	
	/* 实际金额   */
	private BigDecimal actualAmount;
	
	/* 消费时间  */
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

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	@ColumnName("discount")
	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	
	public Integer getCount() {
		return count;
	}

	@ColumnName("count")
	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getCountback() {
		return countback;
	}

	@ColumnName("countback")
	public void setCountback(Integer countback) {
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
