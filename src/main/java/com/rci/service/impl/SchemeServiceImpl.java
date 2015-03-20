package com.rci.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.Scheme;
import com.rci.constants.enums.SchemeType;
import com.rci.service.BaseService;
import com.rci.service.ISchemeService;

@Service("SchemeService")
public class SchemeServiceImpl extends BaseService<Scheme, Long> implements
		ISchemeService {

	@Override
	public Scheme getScheme(SchemeType type, String paymodeno) {
		DetachedCriteria sdc = DetachedCriteria.forClass(Scheme.class,"s");
		DetachedCriteria pdc = sdc.createAlias("paymode","p");
		pdc.add(Restrictions.eq("p.modeNo", paymodeno));
		sdc.add(Restrictions.eq("s.type", type));
		Scheme scheme = baseDAO.queryUniqueByCriteria(sdc);
		return scheme;
	}

}
