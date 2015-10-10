package com.miaozhen.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCacheUtil {
	
	private static final CacheManager cacheManager = CacheManager.getInstance();
	
	/**
	 * 获取缓存中的对象
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object getElement(String cacheName, String key) {
		Element ele = cacheManager.getCache(cacheName).get(key);
		if (null == ele) {
			return null;
		}
		return ele.getObjectValue();
	}

	/**
	 * 将对象写入缓存
	 * 
	 * @param cacheName
	 * @param key
	 * @param obj
	 */
	public static void put(String cacheName, String key, Object obj) {
		Element ele = new Element(key, obj);
		cacheManager.getCache(cacheName).put(ele);
	}
	
	/**
	 * 清除相关缓存
	 * 
	 * @param cacheName
	 * @param key
	 * @param obj
	 */
	public static void remove(String cacheName, String key) {
		Cache cache = cacheManager.getCache(cacheName);
		if(cache.isElementInMemory(key)){
			cache.remove(key);
		}
	}
	
	public static Cache getCache(String cacheName){
		return cacheManager.getCache(cacheName);
	}

}
