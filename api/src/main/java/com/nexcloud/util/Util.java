package com.nexcloud.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Pattern;

import com.google.gson.Gson;


public class Util {
	
	public static String makeStackTrace(Throwable t){
		if(t == null) return "";
		try{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			t.printStackTrace(new PrintStream(bout));
			bout.flush();
			String error = new String(bout.toByteArray());
	
			return error.replace("\r\n", " ");
		}catch(Exception ex){
		return "";
		}
	}
	
	public static String getToday(String token)
	{
		String sToday = null;
		SimpleDateFormat df   = new SimpleDateFormat("yyyy"+token+"MM"+token+"dd");
		sToday = df.format(new java.util.Date());
		
		return sToday;
	}
	
	/**
	 * 특정token으로 split하는 Method
	 * @param value
	 * @param token
	 * @return String[]
	 */
	public static String [] split (String value, String token) {
	    String[] ret = null;
	    StringTokenizer st = null;
	    int len = 0;
		
		//value = (value==null?"":value.trim());
	    value = (value==null?" ":value);
		st = new StringTokenizer(value, token);
		len = st.countTokens();
		ret = new String[len];
		
		for(int i=0;i<len;i++)
			ret[i] = st.nextToken().trim();	
	    return ret;
	}
	/**
	 * Bean to Json String Convert
	 * 
	 * @param src
	 * @return
	 */
	public static String beanToJson(Object src)
	{
		return new Gson().toJson(src); 
	}
	
	
	public static synchronized String getUUID()
	{
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Json String to Bean  Convert
	 * 
	 * @param src
	 * @return
	 */
	public static <T> T JsonTobean( String json, Class<T> classOfT )
	{
		if( classOfT == null )
			return null;
		
		return new Gson().fromJson(json, classOfT );
	}
	
	public static String changeDate(   String fromDate, String format,
	         int addYear, int addMonth, int addDate,
	         int addHour, int addMinute, int addSecond)
   {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      Date date = null;
      try {
         date = sdf.parse(fromDate);
      } catch (ParseException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      Calendar cal = new GregorianCalendar();
      cal.setTime(date);
      cal.add(Calendar.YEAR, addYear);
      cal.add(Calendar.MONTH, addMonth);
      cal.add(Calendar.DATE, addDate);
      cal.add(Calendar.HOUR_OF_DAY, addHour);
      cal.add(Calendar.MINUTE, addMinute);
      cal.add(Calendar.SECOND, addSecond);
      
      SimpleDateFormat sdf2 = new SimpleDateFormat(format);
      String toDate = sdf2.format(cal.getTime());
      
      return toDate;
   }
	
	public static long changeLongDate(   String fromDate, String format,
	         int addYear, int addMonth, int addDate,
	         int addHour, int addMinute, int addSecond)
  {
     SimpleDateFormat sdf = new SimpleDateFormat(format);
     Date date = null;
     try {
        date = sdf.parse(fromDate);
     } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
     }
     
     Calendar cal = new GregorianCalendar();
     cal.setTime(date);
     cal.add(Calendar.YEAR, addYear);
     cal.add(Calendar.MONTH, addMonth);
     cal.add(Calendar.DATE, addDate);
     cal.add(Calendar.HOUR_OF_DAY, addHour);
     cal.add(Calendar.MINUTE, addMinute);
     cal.add(Calendar.SECOND, addSecond);
     
     return cal.getTime().getTime();
  }
	
	public static long changeLongDate(   String format,
	         int addYear, int addMonth, int addDate,
	         int addHour, int addMinute, int addSecond)
 {
	Date date = new Date();
    
    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    cal.add(Calendar.YEAR, addYear);
    cal.add(Calendar.MONTH, addMonth);
    cal.add(Calendar.DATE, addDate);
    cal.add(Calendar.HOUR_OF_DAY, addHour);
    cal.add(Calendar.MINUTE, addMinute);
    cal.add(Calendar.SECOND, addSecond);
    
    return cal.getTime().getTime();
 }
	
	public static String nvl(String str, String nullStr)
	{
		if (str == null || str.trim().length() == 0)
			return nullStr;
		else
			return str;
	}
	
	/**
	 * Array의 Empty 를 확인
	 * 
	 * @param objs
	 * @return
	 */
	public static boolean isEmptyArray(Object[] objs) {
		return (objs == null || objs.length == 0);
	}

	/**
	 * List 의 Empty를 확인
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmptyList(Collection list) {
		return (list == null || list.isEmpty());
	}

	/**
	 * String의 Empty를 확인
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean isEmpty(String... strs) {
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] == null || "".equals(strs[i]))
				return true;
		}
		return false;
	}
	
	/**
	 * Map 의 Empty 가 아닌지 확인
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isNotEmptyMap(Map map) {
		return !isEmptyMap(map);
	}

	/**
	 * Map 의 Empty 를 확인
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmptyMap(Map map) {
		return (map == null || map.isEmpty());
	}

	/**
	 * List 데이터가 Empty가 아닌지 확인
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isNotEmptyList(Collection list) {
		return !isEmptyList(list);
	}

	/**
	 * String 데이터 들이 Empty 가 아닌지 확인
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean isNotEmpty(String... strs) {
		return !isEmpty(strs);
	}
	
	public static long addDate( 
			int addYear, int addMonth, int addDate,
			int addHour, int addMinute, int addSecond)
	{
		Date date = new Date();
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.YEAR, +addYear);
		cal.add(Calendar.MONTH, +addMonth);
		cal.add(Calendar.DATE, +addDate);
		cal.add(Calendar.HOUR_OF_DAY, +addHour);
		cal.add(Calendar.MINUTE, +addMinute);
		cal.add(Calendar.SECOND, +addSecond);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String toDate = sdf.format(cal.getTime());
		
		long exp = 0l;
		try {
			exp = sdf.parse( toDate ).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return exp;
	}
	
	public static boolean validationIP( String ipAddr )
	{
		String validIp = "^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}$";
		
		if (!Pattern.matches(validIp, ipAddr )) 
			return false;
		else
			return true;
	}
	
	public static String makeSubQuery( String start, String end )
	{
		String sub_query = null;
		if( start != null && end != null && !"".equals(start) && !"".equals(end))
		{
			long diff = Long.parseLong(end) - Long.parseLong(start);
			long hour = diff / (1000*60*60);
			if( hour <= 6  )
				sub_query = "start="+start+"&end="+end+"&step=15";
			else if( hour > 6 && hour <= 12 )
				sub_query = "start="+start+"&end="+end+"&step=20";
			else if( hour > 12 && hour <= 24 )
				sub_query = "start="+start+"&end="+end+"&step=30";
			else if( hour > 24 && hour <= 48 )
				sub_query = "start="+start+"&end="+end+"&step=60";
			else if( hour > 48 && hour <= 120 )
				sub_query = "start="+start+"&end="+end+"&step=120";
			else if( hour > 120 && hour <= 240 )
				sub_query = "start="+start+"&end="+end+"&step=300";
			else if( hour > 240 && hour <= 432 )
				sub_query = "start="+start+"&end="+end+"&step=600";
			else if( hour > 432 && hour <= 600 )
				sub_query = "start="+start+"&end="+end+"&step=900";
			else if( hour > 600 && hour <= 864 )
				sub_query = "start="+start+"&end="+end+"&step=1200";
			else if( hour > 864 && hour <= 1560 )
				sub_query = "start="+start+"&end="+end+"&step=1800";
			else if( hour > 1560 )
				sub_query = "start="+start+"&end="+end+"&step=3600";
		}
		return sub_query;
	}
}

