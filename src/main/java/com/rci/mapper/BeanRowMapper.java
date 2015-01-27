package com.rci.mapper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;

import com.rci.annotation.ColumnName;

@SuppressWarnings("rawtypes")
public class BeanRowMapper implements RowMapper{
	private Class<?> clazz;

	public BeanRowMapper(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public Object mapRow(ResultSet result, int index) throws SQLException {
		Object obj = null;
		try {
			obj = clazz.newInstance();
			PropertyDescriptor[] pdrs = BeanUtils.getPropertyDescriptors(clazz);
			for (PropertyDescriptor pdr : pdrs) {
				if(pdr.getPropertyType() == Class.class){
					continue;
				}
				Method method = pdr.getWriteMethod();
				ColumnName columnName = method.getAnnotation(ColumnName.class);
				Class<?> ptype = method.getParameterTypes()[0];
				if (columnName != null) {
					if (ptype == String.class) {
						method.invoke(obj, result.getString(columnName.value()));
					}
					if(ptype == Long.class){
						method.invoke(obj, result.getLong(columnName.value()));
					}
					if(ptype == BigDecimal.class){
						method.invoke(obj, result.getBigDecimal(columnName.value()));
					}
					if(ptype == Timestamp.class){
						method.invoke(obj, result.getTimestamp(columnName.value()));
					}
					if(ptype == Integer.class){
						method.invoke(obj, result.getInt(columnName.value()));
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		return obj;
	}

}
