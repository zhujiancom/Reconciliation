/**
 * 
 */
package com.rci.exceptions;

import com.rci.exceptions.ExceptionConstant.DAO;
import com.rci.exceptions.ExceptionConstant.SERVICE;
import com.rci.exceptions.ExceptionConstant.SQL;
import com.rci.exceptions.ExceptionConstant.Tools;

/**
 * @Description
 * @author zj
 * @Date 2014年8月4日
 *	
 */
public class ExceptionManage{
	/**
	 * 
	 * @Function ServiceException Management
	 * @param action
	 * @param msg
	 * @throws ServiceException
	 * @author zj
	 * @Date 2014年8月4日
	 */
	public static void throwServiceException(SERVICE service,String msg) throws ServiceException{
		throw new ServiceException(service,msg);
	}
	public static void throwServiceException(SERVICE service,Throwable cause) throws ServiceException{
		throw new ServiceException(service,cause);
	}
	public static void throwServiceException(SERVICE service,String msg, Throwable cause) throws ServiceException{
		throw new ServiceException(service,msg,cause);
	}

	/**
	 * 
	 * @Function DAOException Management
	 * @param action
	 * @param msg
	 * @throws ServiceException
	 * @author zj
	 * @Date 2014年8月4日
	 */
	public static void throwDAOException(DAO action,String msg) throws ServiceException{
		throw new DAOException(action,msg);
	}
	public static void throwDAOException(DAO action,Throwable cause) throws ServiceException{
		throw new DAOException(action,cause);
	}
	public static void throwDAOException(DAO action,String msg, Throwable cause) throws ServiceException{
		throw new DAOException(action,msg,cause);
	}

	/**
	 * 
	 * @Function ToolsException Management
	 * @param action
	 * @param msg
	 * @throws ServiceException
	 * @author zj
	 * @Date 2014年10月20日
	 */
	public static void throwToolsException(Tools action,String msg) throws ServiceException{
		throw new ToolsException(action,msg);
	}

	public static void throwToolsException(Tools action,Throwable cause) throws ServiceException{
		throw new ToolsException(action,cause);
	}

	public static void throwToolsException(Tools action,String msg, Throwable cause) throws ServiceException{
		throw new ToolsException(action,msg,cause);
	}

	/**
	 * 
	 * @Function SqlException Management
	 * @param action
	 * @param msg
	 * @throws ServiceException
	 * @author zj
	 * @Date 2014年10月20日
	 */
	public static void throwSqlException(SQL action,String msg) throws ServiceException{
		throw new SqlException(action,msg);
	}

	public static void throwSqlException(SQL action,Throwable cause) throws ServiceException{
		throw new SqlException(action,cause);
	}

	public static void throwSqlException(SQL action,String msg, Throwable cause) throws ServiceException{
		throw new SqlException(action,msg,cause);
	}

}
