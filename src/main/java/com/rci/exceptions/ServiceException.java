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
public class ServiceException extends NestedRuntimeException{
	private ExceptionConstant.SERVICE service;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3648506930465797089L;

	/**
	 * @param msg
	 */
	public ServiceException(ExceptionConstant.SERVICE service,String msg){
		super(msg);
		this.service = service;
	}

	public ServiceException(ExceptionConstant.SERVICE service,Throwable cause){
		super(cause.getMessage(),cause);
		this.service = service;
	}

	public ServiceException(ExceptionConstant.SERVICE service,String msg, Throwable cause){
		super(msg,cause);
		this.service = service;
	}

	@Override
	public String getMessage(){
		return "["+service+"]>>>"+super.getMessage();
	}

}
