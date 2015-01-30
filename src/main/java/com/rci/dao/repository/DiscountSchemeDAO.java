package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.DiscountScheme;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class DiscountSchemeDAO extends
		DefaultHibernateDAOFacadeImpl<DiscountScheme, Long> {

}
