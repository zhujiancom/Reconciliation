package com.rci.bean;

import java.sql.Timestamp;

public class DishTypeDTO {
	/* 类型编号  */
	private String dtNo;
	
	/* 类型名称  */
	private String dtName;
	
	/* 类型创建时间  */
	private Timestamp createTime;

	public String getDtNo() {
		return dtNo;
	}

	public void setDtNo(String dtNo) {
		this.dtNo = dtNo;
	}

	public String getDtName() {
		return dtName;
	}

	public void setDtName(String dtName) {
		this.dtName = dtName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
