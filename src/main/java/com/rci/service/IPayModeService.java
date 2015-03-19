package com.rci.service;

import com.rci.bean.entity.Paymode;
import com.rci.bean.entity.Scheme;
import com.rci.constants.enums.SchemeType;

public interface IPayModeService {
	public Scheme getScheme(SchemeType type,String paymodeno);
	
	public void rwCreatePayMode(Paymode[] modes);
}
