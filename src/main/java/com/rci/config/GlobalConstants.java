/**
 * 
 */
package com.rci.config;

/**
 * @Description
 * @author zj
 * @Date 2014年8月4日
 *	
 */
public class GlobalConstants{
	private GlobalConstants(){}

	public static final String CURRENT_VERSION = "0.0.1";

	public static final String GLOBAL_CONFIG_FILE = "globalConfig.properties";

	/**
	 * DB schema
	 */
	public final static String DEFAULT_ORACLE_SCHEMA="frypan";
	public final static String DEFAULT_MYSQL_SCHEMA="frypan";

	/**
	 * 是否开启定时器
	 */
	public static final boolean DEFAULT_TIMER_ENABLED=false;
	/**
	 * 默认locale
	 */
	public static final String DEFAULT_LOCALE="zh_CN";

	public static final String COMMA=",";

	public static final String LEFT_SLASH = "/";

	public static final String DOT = ".";

	public static final String DASH = "-";

	public static final String SEMICOLON = ";";

	/**
	 * 是否启用在不重启应用时reload配置文件
	 */
	public static final boolean DEFAULT_CONFIGURATION_RELOADABLE=false;

	public static final String PROPERTIES_SUFFIX = ".properties";

	public static final String XML_SUFFIX = ".xml";

	public static final int DEFAULT_PAGESIZE=20;

}
