/**
 * 
 */
package com.rci.config;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.rci.exceptions.ExceptionConstant.Tools;
import com.rci.exceptions.ExceptionManage;

/**
 * @Description
 * @author zj
 * @Date 2014年8月4日
 */
public class GlobalSettings{
	private final Log logger = LogFactory.getLog(GlobalSettings.class);
	public static final String ORACLE_DB_SCHEMA = GlobalConstants.DEFAULT_ORACLE_SCHEMA;
	public static final String MYSQL_DB_SCHEMA = GlobalConstants.DEFAULT_MYSQL_SCHEMA;
	private String loadedByFileName;
	private boolean timerEnabled = GlobalConstants.DEFAULT_TIMER_ENABLED;
	private String defaultLocale = GlobalConstants.DEFAULT_LOCALE;
	private boolean reloadable = GlobalConstants.DEFAULT_CONFIGURATION_RELOADABLE;
	private PropertiesConfiguration configuration;

	private static class GlobalSettingsHolder{
		private static GlobalSettings instance = new GlobalSettings();
	}

	public static GlobalSettings getInstance(){
		return GlobalSettingsHolder.instance;
	}

	static GlobalSettings createNew(){
		return new GlobalSettings();
	}

	private GlobalSettings(){
		loadGlobalSettings();
	}

	public PropertiesConfiguration getConfiguration(){
		return configuration;
	}

	protected String getLoadedByFileName(){
		return loadedByFileName;
	}

	public boolean isTimerEnabled(){
		try{
			boolean timerEnabledTemp = configuration.getBoolean(PropertyConstants.TIMER_ENABLED);
			timerEnabled = timerEnabledTemp;
		}
		catch(NoSuchElementException e){
			logger.warn(e);
		}
		return timerEnabled;
	}

	public boolean isReloadable(){
		try{
			boolean reloadableTemp = configuration.getBoolean(PropertyConstants.CONFIGURATION_RELOADABLE);
			reloadable = reloadableTemp;
		}
		catch(NoSuchElementException e){
			logger.warn(e);
		}
		return reloadable;
	}

	public boolean isI18nReloadable(){
		boolean i18nReload = false;
		try{
			boolean reloadableTemp = configuration.getBoolean(PropertyConstants.I18N_RELOADABLE);
			i18nReload = reloadableTemp;
		}
		catch(NoSuchElementException e){
			logger.warn(e);
		}
		return i18nReload;
	}

	private synchronized void loadGlobalSettings(){
		String propFileName = GlobalConstants.GLOBAL_CONFIG_FILE;
		logger.info("Trying to load global configuration file: " + propFileName);
		try{
			Resource res = new ClassPathResource(propFileName);
			URL url = res.getURL();
			configuration = new PropertiesConfiguration(url);
			if(isReloadable()){
				FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
				logger.debug("---------- refresh interval : "+configuration.getInt(PropertyConstants.GLOBAL_REFRESH_SECOND));
				strategy.setRefreshDelay(1000 * configuration
						.getInt(PropertyConstants.GLOBAL_REFRESH_SECOND));
				configuration.setReloadingStrategy(strategy);
			}
			if(logger.isInfoEnabled()){
				logger.info("Load " + propFileName + "from " + res.getURL());
			}
		}
		catch(IOException ioe){
			ExceptionManage.throwToolsException(Tools.LOAD_GLOBAL_PROPERTIES,"Problem loading global properties from URL ["+ propFileName + "] ",ioe);
		}
		catch(ConfigurationException ce){
			logger.error(ce);
		}
		loadedByFileName = propFileName;
		logger.info("Finished configuring global properties");
	}

	public Locale getDefaultLocale(){
		try{
			String defaultLocaleTemp = configuration.getString(PropertyConstants.LOCALE);
			if(!StringUtils.isEmpty(defaultLocaleTemp)){
				defaultLocale = defaultLocaleTemp;
			}
		}
		catch(NoSuchElementException nee){
			logger.warn(nee);
		}
		catch(Exception e){
			logger.debug(e);
		}
		String[] parts = org.springframework.util.StringUtils
				.tokenizeToStringArray(defaultLocale,"_ ",false,false);
		String language = (parts.length > 0 ? parts[0] : "");
		String country = (parts.length > 1 ? parts[1] : "");
		String variant = "";
		if(parts.length >= 2){
			int endIndexOfCountryCode = defaultLocale.indexOf(country)
					+ country.length();
			variant = StringUtils.trim(defaultLocale
					.substring(endIndexOfCountryCode));
			if(variant.startsWith("_")){
				variant = org.springframework.util.StringUtils
						.trimLeadingCharacter(variant,'_');
			}
		}
		return new Locale(language,country,variant);
	}

	/**
	 * @Function 获取配置的国际化资源文件
	 * @return
	 * @author zj
	 * @Date 2014年8月7日
	 */
	public String[] getMessageResourcesNames(){
		List<Object> list = configuration
				.getList(PropertyConstants.MESSAGE_RESOURCES);
		return list.toArray(new String[0]);
	}

	/**
	 * @Function 国际化资源文件刷新间隔时间
	 * @return
	 * @author zj
	 * @Date 2014年8月11日
	 */
	public int getMessageReloadInterval(){
		try{
			return configuration.getInt(PropertyConstants.I18N_REFRESH_SECOND);
		}catch(NoSuchElementException nee){
			logger.warn(nee);
			return -1;
		}
	}

	/**
	 * @Function 属性配置文件刷新间隔时间
	 * @return
	 * @author zj
	 * @Date 2014年8月11日
	 */
	public int getPropertyReloadInterval(){
		try{
			return configuration.getInt(PropertyConstants.PROPERTY_REFRESH_SECOND);
		}catch(NoSuchElementException nee){
			logger.warn(nee);
			return -1;
		}
	}

	/**
	 * @Function 获取所有配置的资源文件整合到一起
	 * @return
	 * @author zj
	 * @Date 2014年8月7日
	 */
	public String[] getPropertyResourcesNames(){
		List<Object> list = configuration
				.getList(PropertyConstants.PROPERTY_RESOURCES);
		return list.toArray(new String[0]);
	}

	public int getDefaultPageSize(){
		try{
			return configuration.getInt(PropertyConstants.DEFAULT_PAGESIZE);
		}catch(Exception e){
			logger.warn(e);
			return GlobalConstants.DEFAULT_PAGESIZE;
		}
	}
}
