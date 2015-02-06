package com.rci.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
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

	@Override
	public DiscountScheme getSchemeByNo(String sno) {
		DetachedCriteria dc = DetachedCriteria.forClass(DiscountScheme.class);
		dc.add(Restrictions.eq("sNo", sno));
		DiscountScheme scheme = baseDAO.queryUniqueByCriteria(dc);
		return scheme;
	}

	@Override
	public DiscountScheme getSchemeByName(String name) {
		DetachedCriteria dc = DetachedCriteria.forClass(DiscountScheme.class);
		dc.add(Restrictions.eq("sName", name));
		DiscountScheme scheme = baseDAO.queryUniqueByCriteria(dc);
		return scheme;
	}
}
