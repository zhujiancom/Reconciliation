package com.rci.tools;

import java.math.BigDecimal;

public class DigitUtil {
	/**
	 * 计算两数相乘
	 * @param b1
	 * @param b2
	 * @param precision
	 * @return
	 */
	public static BigDecimal mutiplyUp(BigDecimal b1,BigDecimal b2,int precision){
		return b1.multiply(b2).setScale(precision, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal mutiplyDown(BigDecimal b1,BigDecimal b2,int precision){
		return b1.multiply(b2).setScale(precision, BigDecimal.ROUND_HALF_DOWN);
	}
	public static BigDecimal mutiplyDown(BigDecimal b1,BigDecimal b2){
		return mutiplyDown(b1,b2,2);
	}
	public static BigDecimal mutiplyUp(BigDecimal b1,BigDecimal b2){
		return mutiplyUp(b1,b2,2);
	}
	
	/**
	 * 计算百分比
	 * @param member
	 * @param divisor
	 * @param precision
	 * @return
	 */
	public static BigDecimal precentDown(BigDecimal member,BigDecimal divisor,int precision){
		return member.divide(divisor).setScale(precision, BigDecimal.ROUND_HALF_DOWN);
	}
	
	public static BigDecimal precentUp(BigDecimal member,BigDecimal divisor,int precision){
		return member.divide(divisor).setScale(precision, BigDecimal.ROUND_HALF_UP);
	}
	public static BigDecimal precentDown(BigDecimal member,BigDecimal divisor){
		return precentDown(member,divisor,2);
	}
	public static BigDecimal precentUp(BigDecimal member,BigDecimal divisor){
		return precentUp(member,divisor,2);
	}
	
	/**
	 * 加法运算
	 * @param src
	 * @param d
	 * @param precision
	 * @return
	 */
	public static BigDecimal add(BigDecimal src,Double d,int precision){
		BigDecimal dd = new BigDecimal(d.toString()); 
//		if(d < 0){
//			dd = dd.multiply(new BigDecimal(-1)); 
//		}
		return src.add(dd).setScale(precision, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal add(BigDecimal src,Double d){
		return add(src,d,2);
	}
	
}
