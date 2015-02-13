package com.rci.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.rci.tools.DateUtil;

@ContextConfiguration({"classpath:spring/spring-db.xml","classpath:spring/spring-common.xml"})
public class DataTransformFacadeTest extends AbstractJUnit4SpringContextTests{
	@Resource(name="DataTransformFacade")
	private DataTransformFacade dtf;
	
	@Test
	public void testAccquireOrderInfo() {
		dtf.accquireOrderInfo(DateUtil.str2Date("2015-02-07"));
	}

}
