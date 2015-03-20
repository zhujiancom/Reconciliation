package com.rci.service;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service("DataTransformFacade")
public interface DataTransformFacade {
	public void accquireOrderInfo(Date date);
}
