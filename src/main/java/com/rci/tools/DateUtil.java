/**
 * 
 */
package com.rci.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Description
 * @author zj
 * @Date 2014年10月21日
 *	
 */
public class DateUtil extends DateUtils{
	private transient Log logger = LogFactory.getLog(DateUtil.class);

	protected Log logger(){
		if(logger == null){
			return LogFactory.getLog(DateUtil.class);
		}else{
			return logger;
		}
	}

	/**
	 * 不可被实例化
	 */
	private DateUtil() {};

	/**
	 * 默认日期格式, yyyy-MM-dd
	 */
	private static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	/**
	 * 默认时间格式, yyyy-MM-dd hh24:mm:ss
	 */
	private static String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取日期字符串
	 * @param date 日期
	 * @return yyyy-MM-dd格式, 中国时间({@link Locale}.PRC)
	 */
	public static String date2Str(Date date) {
		// 使用默认日期格式
		return date2Str(date, DEFAULT_DATE_PATTERN);
	}

	/**
	 * 获取日期字符串
	 * @param date 日期
	 * @param pattern 日期格式
	 * @return 参数pattern指定日期格式, 中国时间({@link Locale}.PRC)
	 */
	public static String date2Str(Date date, String pattern) {
		// 使用上海时间
		return date2Str(date, pattern, TimeZone.getTimeZone("Asia/Shanghai"));
	}

	/**
	 * 获取日期字符串
	 * @param date 日期
	 * @param locale 地区
	 * @return yyyy-MM-dd格式, 参数locale指定地区的时间
	 */
	public static String date2Str(Date date, TimeZone timeZone) {
		return date2Str(date, DEFAULT_DATE_PATTERN, timeZone);
	}

	/**
	 * 获取日期字符串
	 * @param date 日期
	 * @param pattern 格式
	 * @param locale 地区
	 * @return pattern指定格式, locale指定区域的时间
	 */
	public static String date2Str(Date date, String pattern, TimeZone timeZone) {

		String formatedDate = "";
		if (date != null) {
			DateFormat format = new SimpleDateFormat(pattern);
			format.setTimeZone(timeZone);
			formatedDate = format.format(date);
		}

		return formatedDate;
	}

	/**
	 * 获取时间字符串
	 * @param date 时间
	 * @retrn yyyy-MM-dd hh24:mm:ss 格式时间, 中国地区({@link Locale}.PRC)
	 */
	public static String time2Str(Date date) {
		return date2Str(date, DEFAULT_TIME_PATTERN);
	}


	/**
	 * 获取时间字符串
	 * @param date 时间
	 * @param pattern 时间格式
	 * @retrn pattern指定格式时间, 中国地区({@link Locale}.PRC)
	 */
	public static String time2Str(Date date, String pattern) {
		return date2Str(date, pattern, TimeZone.getTimeZone("Asia/Shanghai"));
	}

	/**
	 * 获取时间字符串
	 * @param date 时间
	 * @param locale 地区
	 * @return yyyy-MM-dd hh24:mm:ss 格式时间, locale指定地区
	 */
	public static String time2Str(Date date, TimeZone timeZone) {
		return date2Str(date, DEFAULT_TIME_PATTERN, timeZone);
	}

	/**
	 * 获取时间字符串
	 * @param date 时间
	 * @param pattern 时间格式
	 * @param locale 地区
	 * @return yyyy-MM-dd hh24:mm:ss 格式时间, locale指定地区
	 */
	public static String time2Str(Date date, String pattern, TimeZone timeZone) {
		return date2Str(date, pattern, timeZone);
	}

	/**
	 * 
	 * @Function 获取一个月的最后一天
	 * @param date
	 * @return
	 * @author zj
	 * @Date 2014年10月21日
	 */
	public static Date getLastDayOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 
	 * @Function 获取一个月的第一天
	 * @param date
	 * @return
	 * @author zj
	 * @Date 2014年10月21日
	 */
	public static Date getFirstDayOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 
	 * @Function 获取当前时间
	 * @return
	 * @author zj
	 * @Date 2014年10月21日
	 */
	public static Date getCurrentDate(){
		return getCurrentDate(TimeZone.getDefault());
	}

	/**
	 * 
	 * @Function 指定timezone,获取当前时间
	 * @return
	 * @author zj
	 * @Date 2014年10月21日
	 */
	public static Date getCurrentDate(TimeZone timezone){
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(timezone);
		return cal.getTime();
	}
	
	public static Date str2Date(String datestr){
		return str2Date(datestr,DEFAULT_DATE_PATTERN);
	}
	
	public static Date str2Date(String datestr,String pattern){
		Date date = null;
		if(!StringUtils.isEmpty(datestr)){
			DateFormat format = new SimpleDateFormat(pattern);
			try {
				date = format.parse(datestr);
			} catch (ParseException e) {
				date = null;
			}
		}
		return date;
	}
	
	/**
	 * 去除给定时间的时分秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date wipeoutHMS(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
}
