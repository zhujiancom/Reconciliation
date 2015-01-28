package com.rci.datafetch;

import java.util.List;

import com.rci.bean.DishDTO;
import com.rci.bean.DishTypeDTO;
import com.rci.bean.OrderDTO;
import com.rci.bean.OrderItemDTO;

public interface IDataFetchService {
	OrderDTO fetchOrderByNo(String orderNo);
	
	List<OrderDTO> fetchAllDayOrders();
	
	List<OrderItemDTO> fetchOrderItemsByOrder(String orderNo);
	
	List<DishDTO> fetchDish();
	
	List<DishTypeDTO> fetchDishType();
}
