package com.rci.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration({"classpath:spring/spring-db.xml","classpath:spring/spring-common.xml"})
public class DataTransformFacadeTest extends AbstractJUnit4SpringContextTests{
	@Resource(name="DataTransformFacade")
	private DataTransformFacade dtf;
	
	@Test
	public void testAccquireOrderInfo() {
		dtf.accquireOrderInfo(new Date());
	}

}
