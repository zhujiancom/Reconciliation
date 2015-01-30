package com.rci.service;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rci.bean.DishDTO;
import com.rci.bean.DishTypeDTO;
import com.rci.bean.entity.DiscountScheme;
import com.rci.bean.entity.Dish;
import com.rci.bean.entity.DishType;
import com.rci.datafetch.IDataFetchService;

@Service("InitSystemService")
public class InitSystemService {
	@Resource(name="DataFetchService")
	private IDataFetchService dataFetch;
	@Autowired
	private Mapper beanMapper;
	@Resource(name="DishService")
	private IDishService dishService;
	@Resource(name="DishTypeService")
	private IDishTypeService dishTypeService;
	@Resource(name="DiscountSchemeService")
	private IDiscountSchemeService schemeService;
	
	public void init(){
		//初始化菜品
		List<DishTypeDTO> typeDTOs = dataFetch.fetchDishType();
		DishType[] types = new DishType[typeDTOs.size()];
		for(int i=0;i<typeDTOs.size();i++){
			DishTypeDTO typeDTO = typeDTOs.get(i);
			DishType type = beanMapper.map(typeDTO, DishType.class);
			List<DishDTO> dishDTOs = dataFetch.fetchDishByTypeNo(type.getDtNo());
			List<Dish> dishes = new LinkedList<Dish>();
			for(DishDTO dishDTO:dishDTOs){
				Dish dish = beanMapper.map(dishDTO, Dish.class);
				dish.setDishType(type);
				dishes.add(dish);
				dishService.rwSaveDish(dish);
			}
			type.setDishes(dishes);
			types[i] = type;
		}
		
		//初始化折扣信息
		List<DiscountScheme> schemes = dataFetch.fetchDiscountInfo();
		schemeService.rwSaveDiscountSchemes(schemes.toArray(new DiscountScheme[0]));
	}
	
}
