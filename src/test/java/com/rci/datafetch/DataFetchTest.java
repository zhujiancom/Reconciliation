package com.rci.datafetch;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.rci.bean.DishDTO;

@ContextConfiguration({"classpath:spring/spring-db.xml"})
public class DataFetchTest extends AbstractJUnit4SpringContextTests{
	@Resource(name="DataFetchService")
	private IDataFetchService datafetch;

	@Test
	public void testFetchDish() {
		List<DishDTO> dishes = datafetch.fetchDish();
		for(DishDTO dish:dishes){
			System.out.println(dish.getDishName());
		}
	}

}
