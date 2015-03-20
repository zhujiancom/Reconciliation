package com.rci.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.Paymode;
import com.rci.service.BaseService;
import com.rci.service.IPayModeService;

@Service("PayModeService")
public class PayModeServiceImpl extends BaseService<Paymode, Long> implements
		IPayModeService {
	private transient Log logger = LogFactory.getLog(PayModeServiceImpl.class);

	protected Log logger() {
		if (logger == null) {
			return LogFactory.getLog(PayModeServiceImpl.class);
		} else {
			return logger;
		}
	}

	@Override
	public void rwCreatePayMode(Paymode[] modes) {
		rwCreate(modes);
	}

}
