package com.rci.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class StringUtility extends StringUtils {
	public static String DEFAULT_DATE_PATTERN="\\d{4}-\\d{2}-\\d{2}";
	
	public static boolean isDateFormated(String dateStr){
		return isDateFormated(dateStr,DEFAULT_DATE_PATTERN);
	}
	
	public static boolean isDateFormated(String dateStr,String pattern){
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(dateStr);
		return m.matches();
	}

}
