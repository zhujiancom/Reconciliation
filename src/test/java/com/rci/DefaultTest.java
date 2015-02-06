package com.rci;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

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
		
		BigDecimal price = new BigDecimal("38.0");
		BigDecimal count = new BigDecimal("2");
		BigDecimal backcount = new BigDecimal("1");
		
		BigDecimal result = price.multiply(count).subtract(price.multiply(backcount)).multiply(new BigDecimal("80").divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_CEILING);
		System.out.println(result);
		price = price.add(result);
		System.out.println(price);
	}
}
