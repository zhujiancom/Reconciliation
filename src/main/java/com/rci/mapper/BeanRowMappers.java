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

public class BeanRowMappers<T> implements RowMapper<T> {
	private Class<T> clazz;

	public BeanRowMappers(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T obj = null;
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
						method.invoke(obj, rs.getString(columnName.value()));
					}
					if(ptype == Long.class){
						method.invoke(obj, rs.getLong(columnName.value()));
					}
					if(ptype == BigDecimal.class){
						method.invoke(obj, rs.getBigDecimal(columnName.value()));
					}
					if(ptype == Timestamp.class){
						method.invoke(obj, rs.getTimestamp(columnName.value()));
					}
					if(ptype == Integer.class){
						method.invoke(obj, rs.getInt(columnName.value()));
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		} 
		return obj;
	}

}
