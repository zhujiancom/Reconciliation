package com.rci.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rci.bean.entity.Paymode;
import com.rci.bean.entity.Scheme;
import com.rci.constants.enums.SchemeType;
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
	public Scheme getScheme(SchemeType type, String paymodeno) {
		DetachedCriteria pdc = DetachedCriteria.forClass(Paymode.class,"p");
		DetachedCriteria sdc = pdc.createAlias("schemes","s");
		pdc.add(Restrictions.eq("modeNo", paymodeno));
		sdc.add(Restrictions.eq("type", type.name()));
		Paymode paymode = baseDAO.queryUniqueByCriteria(pdc);
		List<Scheme> schemes = paymode.getSchemes();
		logger().debug("### scheme size is "+schemes.size()+"###");
		return schemes.get(0);
	}

}
