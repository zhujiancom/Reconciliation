package com.rci.constants.enums;


public enum SchemeType {
	FIFITY(""),HUNDRED(""),BIG_SUIT_A("59"),BIG_SUIT_B("70"),SMALL_SUIT("58"),SUIT_A("71"),SUIT_B("72"),SUIT_C("73"),SUIT_C2("74"),
	ELE(""),TDD(""),NODISCOUNT(""),EIGHTDISCOUNT(""),ONLINEPAY(""),CASHBACK(""),FREE("");
	
	private String no;
	SchemeType(String no){
		this.no = no;
	}
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public static SchemeType getType(String no){
		for(SchemeType type:SchemeType.values()){
			if(type.getNo().equals(no.trim())){
				return type;
			}
		}
		return null;
	}
}
