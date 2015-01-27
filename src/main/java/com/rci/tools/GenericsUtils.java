/**
 * 
 */
package com.rci.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @author zj
 *	
 */
public class GenericsUtils{
	/**
	 * 
	 * @Function 通过反射,获得指定类的父类的泛型参数的实际类型
	 * @param clazz
	 * @param index
	 * @return
	 * @author zj
	 * @Date 2014�?�?2�?
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenericType(Class clazz,int index){
		Type genericType = clazz.getGenericSuperclass();
		//如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
		if (!(genericType instanceof ParameterizedType)) {
			return Object.class;
		}
		/*
		 * 返回表示此类型实际类型参数的Type对象的数�?数组里放的都是对应类型的Class,
		 * 如BuyerServiceBean extends DaoSupport<Buyer,Contact>就返回Buyer和Contact类型
		 */
		Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			throw new RuntimeException("你输入的索引"+ (index<0 ? "不能小于0" : "超出了参数的总数"));
		}
		if(!(params[index] instanceof Class)){
			return Object.class;
		}
		return (Class)params[index];
	}

	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenericType(Class clazz) {
		return getSuperClassGenericType(clazz,0);
	}

	/**
	 * 
	 * @Function 通过反射,获得方法返回值泛型参数的实际类型. 如：public Map<String, Buyer> getNames(){}
	 * @param md
	 * @param index
	 * @return 泛型参数的实际类�? 如果没有实现ParameterizedType接口，即不支持泛型，�?��直接返回<code>Object.class</code>
	 * @author zj
	 * @Date 2014�?�?2�?
	 */
	@SuppressWarnings("rawtypes")
	public static Class getMethodGenericReturnType(Method md,int index){
		Type returnType = md.getGenericReturnType();
		if(returnType instanceof ParameterizedType){
			ParameterizedType type = (ParameterizedType) returnType;
			Type[] typeArguments = type.getActualTypeArguments();
			if (index >= typeArguments.length || index < 0) {
				throw new RuntimeException("你输入的索引"+ (index<0 ? "不能小于0" : "超出了参数的总数"));
			}
			return (Class)typeArguments[index];
		}
		return Object.class;
	}

	@SuppressWarnings("rawtypes")
	public static Class getMethodGenericReturnType(Method md){
		return getMethodGenericReturnType(md,0);
	}

	/**
	 * 
	 * @Function 通过反射,获得Field泛型参数的实际类�? �? public Map<String, Buyer> names;
	 * @param field
	 * @param index
	 * @return
	 * @author zj
	 * @Date 2014�?�?2�?
	 */
	@SuppressWarnings("rawtypes")
	public static Class getFieldGenericType(Field field, int index) {
		Type genericFieldType = field.getGenericType();
		if(genericFieldType instanceof ParameterizedType){
			ParameterizedType aType = (ParameterizedType) genericFieldType;
			Type[] fieldArgTypes = aType.getActualTypeArguments();
			if (index >= fieldArgTypes.length || index < 0) {
				throw new RuntimeException("你输入的索引"+ (index<0 ? "不能小于0" : "超出了参数的总数"));
			}
			return (Class)fieldArgTypes[index];
		}
		return Object.class;
	}

	@SuppressWarnings("rawtypes")
	public static Class getFieldGenericType(Field field) {
		return getFieldGenericType(field, 0);
	}

	/**
	 * 通过反射,获得方法输入参数第index个输入参数的�?��泛型参数的实际类�? �? public void add(Map<String, Buyer> maps, List<String> names){}
	 * @Function
	 * @param method
	 * @param index
	 * @return
	 * @author zj
	 * @Date 2014�?�?2�?
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> getMethodGenericParameterTypes(Method method, int index) {
		List<Class> results = new ArrayList<Class>();
		Type[] genericParameterTypes = method.getGenericParameterTypes();
		if (index >= genericParameterTypes.length ||index < 0) {
			throw new RuntimeException("你输入的索引"+ (index<0 ? "不能小于0" : "超出了参数的总数"));
		}
		Type genericParameterType = genericParameterTypes[index];
		if(genericParameterType instanceof ParameterizedType){
			ParameterizedType aType = (ParameterizedType) genericParameterType;
			Type[] parameterArgTypes = aType.getActualTypeArguments();
			for(Type parameterArgType : parameterArgTypes){
				Class parameterArgClass = (Class) parameterArgType;
				results.add(parameterArgClass);
			}
			return results;
		}
		return results;
	}
}
