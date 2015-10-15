package com.test.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**  
 * PropertiesUtil.java
 * @version 1.0
 * @createTime 读取配置文件信息类
 */
public class PropertiesUtil {

	private static String defaultPropertyFilePath = "/test.properties";
	
	private static Map<String,Properties> ppsMap = new HashMap<String,Properties>();
	
	/**
	 * 读取默认文件的配置信息，读key返回value
	 * @param key
	 * @return value
	 */
	public static final String getPropertyValue(String key) {
		Properties pps = getPropertyFile(defaultPropertyFilePath);
		return pps == null ? null : pps.getProperty(key);
	}
	
	/**
	 * 传入filePath读取指定property文件，读key返回value
	 * @param propertyFilePath
	 * @param key
	 * @return value
	 */
	public static String getPropertyValue(String propertyFilePath,String key) {
		if(propertyFilePath == null) {
			propertyFilePath = defaultPropertyFilePath;
		}
		Properties pps = getPropertyFile(propertyFilePath);
		return pps == null ? null : pps.getProperty(key);
	}
	
	/**
	 * 根据path返回property文件，并保存到HashMap中，提高效率
	 * @param propertyFilePath
	 * @return
	 */
	public static Properties getPropertyFile(String propertyFilePath) {
		if(propertyFilePath == null) {
			return null;
		}
		Properties pps = ppsMap.get(propertyFilePath);
		if(pps == null) {
			InputStream in = PropertiesUtil.class.getResourceAsStream(propertyFilePath);
			pps = new Properties();
			try {
				pps.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ppsMap.put(propertyFilePath, pps);
		}
		
		return pps;
	}
}
