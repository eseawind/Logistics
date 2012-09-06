package Logistics.Common;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.*;
import java.sql.Date;
import java.sql.Timestamp;

public class Tools {
	public static java.text.DecimalFormat df=new DecimalFormat("0.000");
	public static java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static Set<String> validFreightState=new HashSet<String>(){{add("已创建");add("已发货");add("已到港");add("已签收");add("已中转");add("异常");add("已收回单");}};
	public static Set<String> validPaymentCollectionState=new HashSet<String>(){{add("未收");add("已收到站");add("已收到总部");add("已返回客户");}};
	public static final boolean ifDebug=true;
	public static boolean isStringValid(String s){
		//Pattern p=Pattern.compile("[a-zA-Z0-9_\u2E80-\u9FFF]*",Pattern.UNICODE_CASE);
		Pattern p=Pattern.compile("[^\'\"]*",Pattern.UNICODE_CASE);
		Matcher m=p.matcher(s);
		return m.matches();
	}
	public static void printErr(String errMsg){
		
		System.out.println(errMsg);
	}
	public static Date currDate(){
		return new java.sql.Date(System.currentTimeMillis());
		
	}
	public static Timestamp currTimestamp(){
		
		Timestamp t=new Timestamp(System.currentTimeMillis());
		return t;
	}
	public static Timestamp toTimestamp(java.sql.Date date){
		if(date==null)
			return null;
		Timestamp t=new Timestamp(date.getTime());
		return t;
	}
	
	public static java.util.Date currTime(){
		return new java.util.Date(System.currentTimeMillis());
	}
	public static Calendar currCalendar(){
		return Calendar.getInstance();
	}
	
	public static void print(String msg){
		if(ifDebug==true){
			System.out.println(Tools.currTimestamp().toString()+"\t"+msg);
		}
		else{
			return;
		}
	}
	
	public static void logErr(String errMsg){
		
	}
	
	public static Date firstDate(){
		return Date.valueOf("1900-01-01");
	}
	public static Date lastDate(){
		return Date.valueOf("9999-12-31");
	}
	public static boolean isVoid(String a){
		return a==null  || a.length()==0;
	}
	public static boolean isVoid(Integer a){
		return a==null;
	}
	public static boolean isVoid(Date a){
		return a==null;
	}
	public static boolean isVoid(){
		return true;
	}
	public static String toString(Integer a){
		return a==null?null:a.toString();
	}
	public static String toString(Timestamp ts){
		if(ts==null)
			return "";
		else
			return sdf.format(ts);
	}
	public static String toString(java.sql.Date d){
		if(d==null)
			return "";
		else
			return d.toString();
		
	} 
	public static int diff(java.sql.Date d1,java.sql.Date d2){
		if(d1==null || d2==null){
			return 0;
		}
		return (int)(d1.getTime()/86400000-d2.getTime()/86400000);
	}
	public static String ts(String a){
		if(a==null)
			return "";
		return a;
	}
	public static String ts(String a, int len){
		if(a==null)
			return "";
		if(len<3)
			return a;
		if(a.length()>len){
			return a.substring(0, len-3)+"...";
		}
		return a;
	}
	
	public static String ts(double d){
		return df.format(d);
	}
	public static String ts(Integer i){
		return i==null?"":""+i;
	}
	
	public static String ts(Date d){
		return d==null?"":d.toString();
	}
}
