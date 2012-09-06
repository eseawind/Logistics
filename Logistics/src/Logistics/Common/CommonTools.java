package Logistics.Common;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.*;
import java.sql.Date;

public class CommonTools {
	public static Set<String> validFreightState=new HashSet<String>(){{add("已创建");add("已发货");add("已到港");add("已签收");add("已中转");add("异常");add("已收回单");}};
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
		java.util.Date d=new java.util.Date();
		Date res=Date.valueOf("2000-01-01");
		res.setTime(d.getTime());
		return res;
	}
	public static void print(String msg){
		if(ifDebug==true){
			System.out.println(msg);
		}
		else{
			return;
		}
	}
	
	public static void logErr(String errMsg){
		
	}

}
