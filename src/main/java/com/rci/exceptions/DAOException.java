/**
 * 
 */
package com.rci.exceptions;

import org.springframework.dao.DataAccessException;

/**
 * @Description
 * @author zj
 * @Date 2014年7月11日
 *	
 */
public class DAOException extends DataAccessException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9070583120945315296L;

	private ExceptionConstant.DAO dao;

	public DAOException(String msg) {
		super(msg);
	}

	public DAOException(ExceptionConstant.DAO dao,String msg){
		super(msg);
		this.dao = dao;
	}

	public DAOException(ExceptionConstant.DAO dao,Throwable throwable) {
		super(throwable.getMessage(), throwable);
		this.dao = dao;
	}

	public DAOException(ExceptionConstant.DAO dao,String msg, Throwable throwable) {
		super(msg, throwable);
		this.dao = dao;
	}

	@Override
	public String getMessage(){
		return "["+dao+"]>>>"+this.getRootCause().getMessage();
	}


}
