package com.rci;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.rci.tools.DigitUtil;

public class DefaultTest {

	@Test
	public void testCalendar(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		System.out.println(c.getTime());
	}
	
	@Test
	public void testMath(){
//		System.out.println("Math floor : "+Math.floor(2.1));
//		System.out.println("Math ceil : "+Math.ceil(2.1));
//		System.out.println("BigDecimal scale : "+new BigDecimal("2.1").setScale(0, BigDecimal.ROUND_HALF_UP));
//		System.out.println("BigDecimal scale : "+new BigDecimal("2.6").setScale(0, BigDecimal.ROUND_CEILING));
		BigDecimal postAmount = new BigDecimal(0);
		BigDecimal price = new BigDecimal(98);
		BigDecimal count = new BigDecimal("1");
		BigDecimal backcount = new BigDecimal("1");
		BigDecimal commission = new BigDecimal(1.00);
		BigDecimal spread = new BigDecimal("-0.1");
		BigDecimal rate = BigDecimal.ONE.subtract(DigitUtil.precentDown(commission, new BigDecimal(100)));
//		postAmount = DigitUtil.mutiplyDown(DigitUtil.add(DigitUtil.mutiplyDown(price, rate),-0.1),count);
		postAmount = DigitUtil.mutiplyDown(DigitUtil.mutiplyDown(price, rate).add(spread),count);
	
		System.out.println(postAmount);
//		
//		BigDecimal result = price.multiply(count).subtract(price.multiply(backcount)).multiply(new BigDecimal("80").divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_CEILING);
//		System.out.println(result);
//		price = price.add(result);
//		System.out.println(price);
		
//		BigDecimal bd = new BigDecimal(100);
//		System.out.println(bd.intValue() == 100);
	}
}
