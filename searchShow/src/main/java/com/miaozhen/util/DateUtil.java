package com.miaozhen.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* 格式化日期的辅助类
* 
* @author zhangmin@miaozhen.com
* @date 2015-3-4 上午11:06:57 
* 
*/ 
public class DateUtil{
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	private static Object timeZoneLock = new Object();

	private static String timeZoneList[][] = null;

	private static Map<String, DateFormat> dateFormatCache = new HashMap<String, DateFormat>();

    private static Map<String,SimpleDateFormat> formatMap = new HashMap<String,SimpleDateFormat>();
	/**
	 * <p>功能描述:[返回当前时间的Timestamp对象]</p>
	 * @return
	 */
	public static  Timestamp getCurDateTime(){
		return  new Timestamp(new java.util.Date().getTime());
		
	}
	
	/**
	 * <p>功能描述:[返回当前时间的yyyyMMddHHmmss  String形式]</p>
	 * @return
	 */
	public static  String getCurTime(){
		String time=getCurDateTime().toString();
		StringBuffer sb=new StringBuffer();
		sb.append(time.substring(0, 4)).append(time.substring(5, 7)).append(time.substring(8, 10))
		.append(time.substring(11, 13)).append(time.substring(14, 16)).append(time.substring(17, 19));
		return  sb.toString();
		
	}
	 
	/**
	 * <p>功能描述:[返回日期为1770-01-01 00:00:00.x]</p>
	 * @return
	 */
	public static Timestamp getDefaultDateTime(){
		Calendar c = Calendar.getInstance();
		c.set(1770, 0, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);
		return new java.sql.Timestamp(c.getTime().getTime());
	}
	/**
	 * <p>功能描述:[输入指定格式(yyyy-M-d HH:mm:ss)的字符串返回格式化后的Timestamp对象]</p>
	 * @param parseStr
	 * @return
	 */
	public static Timestamp getParseDateTime(String parseStr){
		return getParseDate("yyyy-M-d HH:mm:ss",parseStr);
	} 
	/**
	 * <p>功能描述:[输入指定格式(yyyy-M-d)的字符串返回格式化后的Timestamp对象]</p>
	 * @param parseStr
	 * @return
	 */
	public static Timestamp getParseDate(String parseStr){
		return getParseDate("yyyy-M-d",parseStr);
	}
	/**
	 * <p>功能描述:[指定格式，指定字符串返回Timestamp类型]</p>
	 * @param parseStr
	 * @return
	 */
	public static Timestamp getParseDate(String pattern,String parseStr){
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return new Timestamp(df.parse(parseStr).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("格式化日期错误"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * <p>功能描述:[取得当前日期的字符串格式,格式为YYYY-MM-DD]</p>
	 * @return
	 */
	public static String getCurDate(){
		//System.out.println(getCurDateTime().toString());
		return getCurDateTime().toString().substring(0,16);
	}
	/**
	 * <p>功能描述:[取得指定日期的字符串格式,格式为YYYY-MM-DD]</p>
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getNextDate(int year,int month,int day){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, year);
		c.add(Calendar.MONTH, month);
		c.add(Calendar.DAY_OF_MONTH, day);
		return new Timestamp(c.getTime().getTime()).toString().substring(0, 10);
		
	}
	
	/**
	 * 取得指定日期增加/减少（n为负数时）n天后的日期
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date add(Date date,int n) {
		if(date == null) return null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.DATE,n);
		return gc.getTime();
	}
	public static String getMonthFirstasStr(Date date) {
		if(date == null) return null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, 1);  
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(gc.getTime()); 
	}
	public static String  getMonthEndasStr(Date date) {
		if(date == null) return null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);  
		gc.add(Calendar.MONTH, 1);  
	    gc.set(Calendar.DAY_OF_MONTH, 0);  
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(gc.getTime());
	}
	
	public static Date getMonthFirst(Date date) {
		if(date == null) return null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, 1);  
		return gc.getTime();  
	} 
	public static Date  getMonthEnd(Date date) {
		if(date == null) return null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);  
		gc.add(Calendar.MONTH, 1);  
	    gc.set(Calendar.DAY_OF_MONTH, 0); 
		return gc.getTime();
	}
	public static Date getNextMonthFirst(Date date) {
		if(date == null) return null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);    
		gc.add(Calendar.MONTH,1);//减一个月   
		gc.set(Calendar.DATE, 1);//把日期设置为当月第一天    
	       
		return gc.getTime();
	}
	public static Date getPreviousMonthFirst(Date date) {
		if(date == null) return null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);    
		gc.add(Calendar.MONTH,-1);//减一个月   
		gc.set(Calendar.DATE, 1);//把日期设置为当月第一天    
	       
		return gc.getTime();
	}
	public static int getMonthDays(Date date) {
		if(date == null) return 0;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.getActualMaximum(Calendar.DAY_OF_MONTH);
		 
	}
	/**
	 * 取得当前日期增加/减少（n为负数时）n天后的日期
	 * @param n
	 * @return
	 */
	public static Date add(int n) {
		return add(new Date(),n);
	}
	/** 
     * 接受YYYY-MM-DD的日期字符串参数,返回两个日期相差的天数 
     * @param start 
     * @param end 
     * @return 
     * @throws ParseException 
     */  
    public static long getDistDates(String start,String end) throws ParseException    
    {  
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
          Date startDate = sdf.parse(start);     
          Date endDate = sdf.parse(end);  
          return getDistDates(startDate,endDate);  
    }    
    
	/** 
     * 返回两个日期相差的天数  */
	public static long getDistDates(Date startDate,Date endDate)    
    {  
        long totalDate = 0;  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(startDate);  
        long timestart = calendar.getTimeInMillis();  
        calendar.setTime(endDate);  
        long timeend = calendar.getTimeInMillis();  
        totalDate = Math.abs((timeend - timestart))/(1000*60*60*24);  
        return totalDate;  
    } 
	public static Date parseDateStr(String str, String format) {
	        if(str==null||"".equals(str)){
	        	return null;
	        }
	    	SimpleDateFormat sdf = formatMap.get(format);
	        if (null == sdf) {
	            sdf = new SimpleDateFormat(format, Locale.ENGLISH);
	            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	            formatMap.put(format, sdf);
	        }
	        try {
	            synchronized(sdf){
	                // SimpleDateFormat is not thread safe
	                return sdf.parse(str);
	            }
	        } catch (ParseException pe) {
	           return null;
	        }
	    }
	/**
	 * 根据指定日期格式将给出的日期字符串dateStr转换成一个日期对象
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateStr,String pattern) {
		if(dateStr == null || dateStr.length()==0 || pattern == null || pattern.length() == 0) return null;
		DateFormat fmt = new SimpleDateFormat(pattern);
		Date result = null;
		try {
			result = fmt.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 将特定格式（yyyy-MM-dd HH:mm:ss）的字符串转换成日期对象
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr,"yyyy-MM-dd HH:mm:ss");
	}
	public static Date parseDateM(String dateStr) {
		return parseDate(dateStr,"yyyy-MM");
	}
	/**
	 * 将特定格式（yyyy-MM-dd）的字符串转换成日期对象
	 * @param dateStr
	 * @return
	 */
	public static Date parseShortDate(String dateStr) {
		return parseDate(dateStr,"yyyy-MM-dd");
	}
	public static Date parseShortDate1(String dateStr) {
		return parseDate(dateStr,"yyyy/MM/dd");
	}
	public static String asHtml(Date d) {
		return asHtml(d, TimeZone.getDefault());
	}
	
	public static String asHtml(Date date, TimeZone timeZone) {
		String key = timeZone.getID();
		DateFormat formatter = (DateFormat) dateFormatCache.get(key);
		if (formatter == null) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formatter.setTimeZone(timeZone);
			dateFormatCache.put(key, formatter);
		}
		synchronized (formatter) {
			String s = formatter.format(date);
			return s;
		}
	}
	
	/**
	 * <p>功能描述:[输入指定格式(yyyy-M-d HH:mm:ss)的字符串返回格式化后的Timestamp对象]</p>
	 * @param parseStr
	 * @return
	 */
	public static String asString(Date date) {
		if(date==null)return "";
		String key = "asString";
		DateFormat formatter = (DateFormat) dateFormatCache.get(key);
		if (formatter == null) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateFormatCache.put(key, formatter);
		}
		return formatter.format(date);
	} 
	public static String asweiboString(Date date) {
		if(date==null)return "";
		String key = "asString";
		DateFormat formatter = (DateFormat) dateFormatCache.get(key);
		if (formatter == null) {
			formatter = new SimpleDateFormat("MM月dd日 HH:mm");
			dateFormatCache.put(key, formatter);
		}
		return formatter.format(date);
	}
	/**
	 * 将时转换为分
	 */
	public static int getmin(String mins)
	{
		int min=0;
		String[] str=mins.split(":");
		min=Integer.parseInt(str[0])*60+Integer.parseInt(str[1]);
		return min;
	}
	/**
	 * 将时转换为分
	 */
	public static int getmin(Date date)
	{
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		String mins=formatter.format(date); 
		int min=0;
		String[] str=mins.split(":");
		min=Integer.parseInt(str[0])*60+Integer.parseInt(str[1]);
		return min;
	}
	/**
	 * 将时转换为分
	 * 
	 */
	public String gethour(int min,int min1)
	{
		String hour="";
		int j=0;
		int i=(min1-min)/60; 
		 j=(min1-min)%60;
		hour=i+"时"+j+"分";
		return hour;
	}
	public static String asShortString(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(null == date)return "";
			return formatter.format(date);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	public static String asShortString(Date date, String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		try {
			if(null == date)return "";
			return formatter.format(date);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	public static String asShortStringMMDD(Date date) {
		DateFormat formatter = new SimpleDateFormat("MM.dd");
		try {
			if(null == date)return "";
			return formatter.format(date);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	public static String asShortString2(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(date);
	}
	public static String asShortString1(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		return formatter.format(date);
	}
	public static String asShortStringM(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM");
		return formatter.format(date);
	}

	public static String asShortString(Date date, TimeZone timeZone) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setTimeZone(timeZone);
		return formatter.format(date);
	}

	public static String asNameSuffix(Date date, TimeZone timeZone) {
		DateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
		formatter.setTimeZone(timeZone);
		return formatter.format(date);
	}

	public static Date getDate(int time) {
		return new Date((long) time * 1000L);
	}

	public static int currentTimeSeconds() {
		return (int) (System.currentTimeMillis() / 1000L);
	}

	public static int secondsAfter(Date a, Date b) {
		return (int) ((a.getTime() - b.getTime()) / 1000L);
	}

	public static int secondsBefore(Date a, Date b) {
		return secondsAfter(b, a);
	}

	public static Date getDate(int yy, int mm, int dd) {
		return (new GregorianCalendar(yy, mm - 1, dd)).getTime();
	}

	public static int unixTimeStamp() {
		return (int) (System.currentTimeMillis() / 1000L);
	}

	public static int unixTimeStamp(Date date) {
		return (int) (date.getTime() / 1000L);
	}

	public static String[][] getTimeZoneList() {
		synchronized (timeZoneLock) {
			if (timeZoneList == null) {
				Date now = new Date();
				String timeZoneIDs[] = TimeZone.getAvailableIDs();
				timeZoneList = new String[timeZoneIDs.length][2];
				for (int i = 0; i < timeZoneList.length; i++) {
					String zoneID = timeZoneIDs[i];
					timeZoneList[i][0] = zoneID;
					timeZoneList[i][1] = getTimeZoneName(zoneID, now, Locale
							.getDefault());
				}

			}
		}
		return timeZoneList;
	}

	private static String getTimeZoneName(String zoneID, Date now, Locale locale) {
		TimeZone zone = TimeZone.getTimeZone(zoneID);
		StringBuffer buf = new StringBuffer();
		int offset = zone.getRawOffset();
		if (zone.inDaylightTime(now) && zone.useDaylightTime())
			offset = (int) ((long) offset + 0x36ee80L);
		if (offset < 0)
			buf.append("(GMT-");
		else
			buf.append("(GMT+");
		offset = Math.abs(offset);
		int hours = offset / 0x36ee80;
		int minutes = (offset % 0x36ee80) / 60000;
		if (hours < 10)
			buf.append("0").append(hours).append(":");
		else
			buf.append(hours).append(":");
		if (minutes < 10)
			buf.append("0").append(minutes);
		else
			buf.append(minutes);
		buf.append(") ").append(zoneID).append(" ");
		buf.append(zone.getDisplayName(true, 0, locale));
		return buf.toString();
	}
	
	public String dateToString(Date dt)
    {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str = "";
        try
        {
            str = sdFormat.format(dt);
        }
        catch(Exception e)
        {
            String s = "";
            return s;
        }
        if(str.equals("1900-01-01 00:00"))
            str = "";
        return str;
    }
	
	public String dateToString(Date dt, String strFormat)
    {
        SimpleDateFormat sdFormat = new SimpleDateFormat(strFormat);
        String str = "";
        try
        {
            str = sdFormat.format(dt);
        }
        catch(Exception e)
        {
            String s = "";
            return s;
        }
        if(str.equals("1900-01-01 00:00"))
            str = "";
        return str;
    }
	/**
	 * 将微信消息中Createtime转换标准时间时间(yyyy-MM-dd HH:mm:ss)
	 * @param createtime
	 * @return
	 */
	public static String fomatTime(String createtime){
		long msgCreatTime=Long.parseLong(createtime)*1000L;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(new Date(msgCreatTime));
	}
	/**
	 * 获取long类型日期，微信中使用String
	 * @return
	 */
	
	public static String getCurDateforXML(){
		return String.valueOf(new Date().getTime());
	}
	public static String getWeekDay(Calendar c){
	    if(c == null){
	     return "周一";
	    }
	    if(Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)){
	     return "周一";
	    }
	    if(Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)){
	     return "周二";
	    }
	    if(Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)){
	     return "周三";
	    }
	    if(Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)){
	     return "周四";
	    }
	    if(Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)){
	     return "周五";
	    }
	    if(Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)){
	     return "周六";
	    }
	    if(Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)){
	     return "周日";
	    }
	    return "周一";
	 }
	public static int getMondayPlus(){
		Calendar calendar = Calendar.getInstance();  
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek==1){
			return -6;
		}else{
			return 2-dayOfWeek;
		}  
	} 
	public static int getMondayUp(){
		Calendar calendar = Calendar.getInstance();  
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek==1){
			return 6;
		}else{
			return dayOfWeek-1;
		}  
	} 
	public static Calendar getLastWeekMonday(){
		Calendar calendar = getCurrenMonday();  
		calendar.add(Calendar.DATE, -7);
		Date mondy=calendar.getTime();
		Calendar calendarDate = Calendar.getInstance(); 
		calendarDate.setTime(mondy); 
		return calendarDate;
	}
	public static Calendar getLastWeekSunday(){ 
		Calendar calendar = getCurrenMonday();  
		calendar.add(Calendar.DATE, -1);
		Date mondy=calendar.getTime();
		Calendar calendarDate = Calendar.getInstance(); 
		calendarDate.setTime(mondy); 
		return calendarDate;
	}
	public static Calendar getCurrenMonday(){
		int mondayPlus=getMondayPlus();
		Calendar calendar = Calendar.getInstance(); 
		calendar.add(Calendar.DATE, mondayPlus);
		Date mondy=calendar.getTime();
		Calendar calendarDate = Calendar.getInstance(); 
		calendarDate.setTime(mondy); 
		return calendarDate;
	}
	public static Calendar getCurrenSunday(){ 
		Calendar calendarDate = Calendar.getInstance();  
		calendarDate.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		calendarDate.add(Calendar.WEEK_OF_YEAR, 1); 
		return calendarDate;
	}
	public static Calendar getLastMonthFirstDay(){ 
		Calendar calendarDate = getCurrenMonthFirstDay(); 
		calendarDate.add(Calendar.MONTH, -1);
		return calendarDate;
	}
	public static Calendar getLastMonthEndDay(){ 
		Calendar calendarDate = getCurrenMonthFirstDay(); 
		calendarDate.add(Calendar.DATE, -1);
		return calendarDate;
	}
	
	public static Calendar getCurrenMonthFirstDay(){ 
		Calendar calendarDate = Calendar.getInstance(); 
		calendarDate.set(Calendar.DAY_OF_MONTH, 1);
		return calendarDate;
	}
	public static Calendar getCurrenMonthEndDay(){ 
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.set(Calendar.DAY_OF_MONTH, 1); 
		calendarDate.add(Calendar.MONTH, 1);  
		calendarDate.add(Calendar.DAY_OF_MONTH, -1);
		return calendarDate;
	}
	public static Calendar getCurrenYesterDay(){ 
		Calendar calendarDate = Calendar.getInstance(); 
		calendarDate.add(Calendar.DATE, -1);
		return calendarDate;
	}
	
	public static Date getTodayStartTime(){
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MINUTE, 0);
		return todayStart.getTime();
	}
	
	public static Date getDateStartTime(Date date){
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(date);
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}
	
	public static List<String> getDateStringList(Date start, Date end, String format){
		List<String> result = new ArrayList<String>();
		if(null == start || null == end || start.after(end)){
			return result;
		}
		while(end.after(start) || end.equals(start)){
			DateFormat formatter = new SimpleDateFormat(format);
			result.add(formatter.format(start));
			start = add(start, 1);
		}
		return result;
	}
	
	public static int dayBetween(Date start, Date end){
		Date startT = getDateStartTime(start);
		Date endT = getDateStartTime(end);
		long diff = endT.getTime() - startT.getTime();
		return Double.valueOf((diff/(1000 * 60 * 60 * 24))).intValue();
	}
	
	public static String asString(Date date, String format){
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	
}
