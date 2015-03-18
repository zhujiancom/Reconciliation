/**
 * 
 */
package com.rci.exceptions;

/**
 * @Description
 * @author zj
 * @Date 2014年7月24日
 *	
 */
public class ExceptionConstant{
	public enum DAO{
		CREATE,UPDATE,DELETE,QUERY,SQL,BATCH_SAVE,BATCH_UPDATE,BATCH_DELETE,GETALL
	}

	public enum SERVICE{
		LOGIN,LOGOUT,TIME_FORMAT,DATA_ERROR
	}

	public enum Tools{
		LOAD_GLOBAL_PROPERTIES,LOAD_MESSAGE_RESOURCE,LOAD_PROPERTIES_RESOURCE,
		FTP_UPLOAD_ERROR,FTP_DOWNLOAD_ERROR,FTP_CONNECT_ERROR,FTP_DISCONNECT_ERROR
	}

	public enum SQL{
		GENERATOR_SQL
	}
}
