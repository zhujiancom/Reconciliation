/**
 * 
 */
package com.rci.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
 * @Description
 * @author zj
 * @Date 2014年7月11日
 *	
 */
public class SqlException extends NestedRuntimeException{
	private ExceptionConstant.SQL sql;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3648506930465797089L;

	/**
	 * @param msg
	 */
	public SqlException(ExceptionConstant.SQL sql,String msg){
		super(msg);
		this.sql = sql;
	}

	public SqlException(ExceptionConstant.SQL sql,Throwable cause){
		super(cause.getMessage(),cause);
		this.sql = sql;
	}

	public SqlException(ExceptionConstant.SQL sql,String msg, Throwable cause){
		super(msg,cause);
		this.sql = sql;
	}

	@Override
	public String getMessage(){
		return "["+sql+"]>>>"+super.getMessage();
	}

}
