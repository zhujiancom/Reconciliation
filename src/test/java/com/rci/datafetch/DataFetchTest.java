package com.rci.datafetch;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.rci.bean.DishDTO;
import com.rci.bean.DishTypeDTO;

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

	@Test
	public void testFetchDishType() {
		List<DishTypeDTO> types = datafetch.fetchDishType();
		for(DishTypeDTO type:types){
			System.out.println(type.getDtNo()+" - "+type.getDtName());
		}
	}
}
