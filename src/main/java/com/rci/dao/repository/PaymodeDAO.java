package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.Paymode;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class PaymodeDAO extends DefaultHibernateDAOFacadeImpl<Paymode, Long> {

}
