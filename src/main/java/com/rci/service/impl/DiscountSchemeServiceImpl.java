package com.rci.service.impl;

import org.springframework.stereotype.Service;

import com.rci.bean.entity.DiscountScheme;
import com.rci.service.BaseService;
import com.rci.service.IDiscountSchemeService;

@Service("DiscountSchemeService")
public class DiscountSchemeServiceImpl extends
		BaseService<DiscountScheme, Long> implements IDiscountSchemeService {

	@Override
	public void rwSaveDiscountSchemes(DiscountScheme[] schemes) {
		super.rwCreate(schemes);
	}
}
