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

	@ColumnName("ch_dishno")
	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}

	public String getDishName() {
		return dishName;
	}

	@ColumnName("vch_dishname")
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public BigDecimal getDishPrice() {
		return dishPrice;
	}

	@ColumnName("num_price1")
	public void setDishPrice(BigDecimal dishPrice) {
		this.dishPrice = dishPrice;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	@ColumnName("dt_build")
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getDishType() {
		return dishType;
	}

	@ColumnName("ch_typeno")
	public void setDishType(String dishType) {
		this.dishType = dishType;
	}
}
