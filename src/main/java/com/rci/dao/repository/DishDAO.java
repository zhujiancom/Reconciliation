package com.rci.dao.repository;

import org.springframework.stereotype.Repository;

import com.rci.bean.entity.Dish;
import com.rci.dao.impl.DefaultHibernateDAOFacadeImpl;

@Repository
public class DishDAO extends DefaultHibernateDAOFacadeImpl<Dish, Long> {

}
