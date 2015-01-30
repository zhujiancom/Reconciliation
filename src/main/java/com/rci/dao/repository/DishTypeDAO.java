package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.DishType;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class DishTypeDAO extends DefaultHibernateDAOFacadeImpl<DishType, Long> {

}
