package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.account.Account;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class AccountDAO extends DefaultHibernateDAOFacadeImpl<Account, Long> {

}
