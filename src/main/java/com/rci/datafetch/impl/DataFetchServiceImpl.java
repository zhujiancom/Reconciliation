package com.rci.datafetch.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.rci.bean.DishDTO;
import com.rci.bean.DishTypeDTO;
import com.rci.bean.OrderDTO;
import com.rci.bean.OrderItemDTO;
import com.rci.bean.PaymodeDTO;
import com.rci.datafetch.IDataFetchService;
import com.rci.datafetch.SQLGen;
import com.rci.mapper.BeanRowMappers;
import com.rci.mapper.ResultSetExtractorImpl;
import com.rci.tools.DateUtil;

@Service("DataFetchService")
public class DataFetchServiceImpl implements IDataFetchService {
	@Resource(name="sqlServerJdbcTemplate")
	private JdbcTemplate sqlServerJdbcTemplate;
	
	@Override
	public OrderDTO fetchOrderByNo(String orderNo) {
		OrderDTO order = new OrderDTO();
		return order;
	}

	@Override
	public List<OrderDTO> fetchAllDayOrders(Date sdate) {
		Date edate = DateUtil.addDays(sdate, 1);
		List<OrderDTO> orders = sqlServerJdbcTemplate.query(SQLGen.QUERY_ORDER,new Object[]{sdate,edate}, new BeanRowMappers<OrderDTO>(OrderDTO.class));
		return orders;
	}

	@Override
	public List<OrderItemDTO> fetchOrderItemsByOrder(String orderNo) {
		List<OrderItemDTO> items = sqlServerJdbcTemplate.query(SQLGen.QUERY_ORDERITEM,new Object[]{orderNo},new BeanRowMappers<OrderItemDTO>(OrderItemDTO.class));
		return items;
	}

	@Override
	public List<DishDTO> fetchDish() {
		List<DishDTO> dishes = sqlServerJdbcTemplate.query(SQLGen.QUERY_DISH, new BeanRowMappers<DishDTO>(DishDTO.class));
		return dishes;
	}

	@Override
	public List<DishTypeDTO> fetchDishType() {
		List<DishTypeDTO> types = sqlServerJdbcTemplate.query(SQLGen.QUERY_DISH_TYPE, new BeanRowMappers<DishTypeDTO>(DishTypeDTO.class));
		List<String> discountDishNos = sqlServerJdbcTemplate.query(SQLGen.QUERY_PROJECTDETAIL_DISHTYPE, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		for(DishTypeDTO type:types){
			if(!discountDishNos.contains(type.getDtNo())){
				type.setNodiscount("N");
			}else{
				type.setNodiscount("Y");
			}
		}
		return types;
	}
	
	public DishTypeDTO fetchDishyTypeByNo(String typeno){
		DishTypeDTO type = (DishTypeDTO) sqlServerJdbcTemplate.query(SQLGen.QUERY_DISH_TYPE_BY_NO,new Object[]{typeno}, new ResultSetExtractorImpl<DishTypeDTO>(DishTypeDTO.class));
		return type;
	}

	@Override
	public List<DishDTO> fetchDishByTypeNo(String typeno) {
		List<DishDTO> dishes = sqlServerJdbcTemplate.query(SQLGen.QUERY_DISH_By_TYPENO,new Object[]{typeno},new BeanRowMappers<DishDTO>(DishDTO.class));
		return dishes;
	}
	
	@Override
	public List<String> fetchSchemeDishTypeNoRef(String schemeNo){
		List<String> dishTypes =  sqlServerJdbcTemplate.query(SQLGen.QUERY_SCHEME_DISHTYPE_REF,new Object[]{schemeNo},new RowMapper<String>(){

			@Override
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("ch_typeno");
			}
			
		});
		return dishTypes;
	}

	@Override
	public List<PaymodeDTO> fetchPaymodes() {
		List<PaymodeDTO> paymodes = sqlServerJdbcTemplate.query(SQLGen.QUERY_PAYMODES, new BeanRowMappers<PaymodeDTO>(PaymodeDTO.class));
		return paymodes;
	}
	
	
}
