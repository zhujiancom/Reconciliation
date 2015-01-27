/**
 * 
 */
package com.rci.exceptions;

/**
 * @Description
 * @author zj
 * @Date 2014年7月21日
 *	
 */
public class ToolsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4738869748698109647L;

	private ExceptionConstant.Tools toolName;

	public ToolsException(String msg) {
		super(msg);
	}

	public ToolsException(ExceptionConstant.Tools toolName,String msg){
		super(msg);
		this.toolName = toolName;
	}

	public ToolsException(ExceptionConstant.Tools toolName,Throwable throwable) {
		super(throwable.getMessage(),throwable);
		this.toolName = toolName;
	}

	public ToolsException(ExceptionConstant.Tools toolName,String msg, Throwable throwable) {
		super(msg, throwable);
		this.toolName = toolName;
	}
	@Override
	public String getMessage(){
		return "["+toolName+"]>>>"+super.getMessage();
	}
}
