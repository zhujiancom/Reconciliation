package com.rci.service;

import com.rci.bean.entity.Scheme;
import com.rci.constants.enums.SchemeType;

public interface ISchemeService {
	public Scheme getScheme(SchemeType type, String paymodeno);
}
