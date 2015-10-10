package com.miaozhen.util;

public class StringUtil {
	
	public static String toEmptyStr(Object o){
		if(null == o || (o.toString()).trim().equalsIgnoreCase("null")){
			return "";
		}
		return o.toString().trim();
	}
	
	public static boolean isEmpty(Object str) {
		return null == str || org.springframework.util.StringUtils.isEmpty(str) || "null".equalsIgnoreCase(str.toString());
	}

	public static Integer toEmptyInt(Integer in){
		if(null == in){
			return 0;
		}
		return in;
	}
}
