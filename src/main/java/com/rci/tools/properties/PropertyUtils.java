/**
 * 
 */
package com.rci.tools.properties;

import java.util.Properties;

import com.rci.config.GlobalSettings;

/**
 * @Description
 * @author zj
 * @Date 2014年8月7日
 *	
 */
public class PropertyUtils{
	private static Properties p;
	static{
		loadPropertiesResources(GlobalSettings.getInstance().getPropertyResourcesNames());
	}
	
	private static void loadPropertiesResources(String[] propertiesFiles){
		PathMatchingPropertiesLoader propertiesLoader = new PathMatchingPropertiesLoader(propertiesFiles);
		p = propertiesLoader.loadAllProperties();
	}
	
	public static Object getProperties(String key){
		return p.get(key);
	}
}
