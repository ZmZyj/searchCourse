package com.miaozhen.withdata.api.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Dao - 基类
 * 
 * @version 1.0
 */
public interface BaseDao<T, ID extends Serializable> {

	/**
	 * 查找实体对象
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	T find(ID id);

	/**
	 * 持久化实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void persist(T entity);

	/**
	 * 合并实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	T merge(T entity);

	/**
	 * 移除实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void remove(T entity);

	/** 
	* 批量提交
	* 
	* @param list
	*/ 
	void saveList(List<T> list);
	
	/** 
	 * 批量更新
	 * 
	 * @param list
	 */ 
	void mergeList(List<T> list);

}