/**
 * 
 */
package com.rci.config;

/**
 * @Description
 * @author zj
 * @Date 2014年8月5日
 *	
 */
public class PropertyConstants{
	private PropertyConstants() {}

	public static final String TIMER_ENABLED = "framework.timer.enabled";
	public static final String LOCALE = "framework.locale";
	public static final String MESSAGE_RESOURCES="framework.i18n.resources";
	public static final String PROPERTY_RESOURCES="framework.properties.resources";

	public static final String CONFIGURATION_RELOADABLE="framework.global.configuration.reloadable";
	public static final String I18N_RELOADABLE="framework.i18n.resources.reloadable";
	/*
	 * 配置文件文件刷新时间
	 */
	public static final String GLOBAL_REFRESH_SECOND="framework.global.configuration.refresh.second";
	public static final String I18N_REFRESH_SECOND="framework.i18n.resources.refresh.second";
	public static final String PROPERTY_REFRESH_SECOND="framework.properties.resources.refresh.second";

	public static final String DEFAULT_PAGESIZE="framework.global.pagination.default_pagesize";
}
