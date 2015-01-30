package com.rci.bean;

import com.rci.annotation.ColumnName;


public class DishTypeDTO {
	/* 类型编号  */
	private String dtNo;
	
	/* 类型名称  */
	private String dtName;
	
	public String getDtNo() {
		return dtNo;
	}

	@ColumnName("ch_typeno")
	public void setDtNo(String dtNo) {
		this.dtNo = dtNo;
	}

	public String getDtName() {
		return dtName;
	}

	@ColumnName("vch_typename")
	public void setDtName(String dtName) {
		this.dtName = dtName;
	}
}
