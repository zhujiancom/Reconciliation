package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.DataFetchMark;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class FetchMarkDAO extends
		DefaultHibernateDAOFacadeImpl<DataFetchMark, Long> {

}
