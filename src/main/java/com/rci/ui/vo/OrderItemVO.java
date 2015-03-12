package com.rci.ui.vo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderItemVO {
	private String dishName;
	
	/* 折扣率 */
	private BigDecimal discountRate;
	
	private BigDecimal price;
	
	/* 点菜数量  */
	private Integer count;
	
	/* 退菜数量 */
	private Integer countback;
	
	/* 折扣金额  */
	private BigDecimal discountAmount;
	
	/* 实际金额   */
	private BigDecimal actualAmount;
	
	/* 点菜时间  */
	private Date consumeTime;

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public Integer getCountback() {
		return countback;
	}

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

	public Date getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}

}
