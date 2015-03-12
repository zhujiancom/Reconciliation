package com.rci.service;

import com.rci.bean.entity.DiscountScheme;


public interface IDiscountSchemeService {
	void rwSaveDiscountSchemes(DiscountScheme[] schemes);
	
	void rwUpdateDiscountSchemes(DiscountScheme[] schemes);
	
	DiscountScheme getSchemeByNo(String sno);
	
	DiscountScheme getSchemeByName(String name);
}
