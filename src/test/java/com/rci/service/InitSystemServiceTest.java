package com.rci.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration({"classpath:spring/spring-db.xml","classpath:spring/spring-common.xml"})
public class InitSystemServiceTest extends AbstractJUnit4SpringContextTests{
	@Resource(name="InitSystemService")
	private InitSystemService initService;

	@Test
	public void testInit() {
		initService.init();
	}

}
