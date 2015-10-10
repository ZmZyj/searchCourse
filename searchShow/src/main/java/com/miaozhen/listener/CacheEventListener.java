package com.miaozhen.listener;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * Listener - 缓存
 * 
 * @version 1.0
 */
public class CacheEventListener implements net.sf.ehcache.event.CacheEventListener {
 

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void notifyElementEvicted(Ehcache ehcache, Element element) {
	}

	public void notifyElementExpired(Ehcache ehcache, Element element) {
		 
	}

	public void notifyElementPut(Ehcache ehcache, Element element) throws CacheException {
	}

	public void notifyElementRemoved(Ehcache ehcache, Element element) throws CacheException {
	}

	public void notifyElementUpdated(Ehcache ehcache, Element element) throws CacheException {
	}

	public void notifyRemoveAll(Ehcache ehcache) {
	}

	public void dispose() {
	}

}