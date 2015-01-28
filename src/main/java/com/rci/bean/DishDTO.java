package com.rci.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.rci.annotation.ColumnName;

public class DishDTO {
	/* 产品编号  */
	private String dishNo;
	
	/* 产品名称   */
	private String dishName;
	
	/* 产品价格  */
	private BigDecimal dishPrice;
	
	/* 产品添加时间   */
	private Timestamp createTime;
	
	/* 产品类型  */
	private String dishType;

	public String getDishNo() {
		return dishNo;
	}

	@ColumnName("")
	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	public String getDishName() {
		return dishName;
	}

	@ColumnName("")
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public BigDecimal getDishPrice() {
		return dishPrice;
	}

	@ColumnName("")
	public void setDishPrice(BigDecimal dishPrice) {
		this.dishPrice = dishPrice;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	@ColumnName("")
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@ColumnName("")
	public String getDishType() {
		return dishType;
	}

	public void setDishType(String dishType) {
		this.dishType = dishType;
	}
}
