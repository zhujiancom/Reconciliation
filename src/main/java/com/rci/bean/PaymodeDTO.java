package com.rci.bean;

import com.rci.annotation.ColumnName;


public class PaymodeDTO {
	private String modeNo;
	
	private String modeName;
	
	private String incomeFlag;

	public String getModeNo() {
		return modeNo;
	}

	@ColumnName("ch_paymodeno")
	public void setModeNo(String modeNo) {
		this.modeNo = modeNo;
	}

	public String getModeName() {
		return modeName;
	}

	@ColumnName("vch_paymode")
	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public String getIncomeFlag() {
		return incomeFlag;
	}

	@ColumnName("ch_incomeflag")
	public void setIncomeFlag(String incomeFlag) {
		this.incomeFlag = incomeFlag;
	}

}
