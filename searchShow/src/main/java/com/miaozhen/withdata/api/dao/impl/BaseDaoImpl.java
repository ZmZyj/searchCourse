package com.miaozhen.withdata.api.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.miaozhen.withdata.api.dao.BaseDao;

/** 
* 用户dao实现类
* 
* @author zhangmin@miaozhen.com
* @date 2015-3-3 下午4:21:21 
* 
*/ 
public abstract class BaseDaoImpl<T, ID extends Serializable> implements BaseDao<T, ID> {
	
	protected Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	/** 实体类类型 */
	private Class<T> entityClass;
	
	@Resource
    protected SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
    /** 
    * 打开连接
    * 
    * @return
    */ 
    protected Session openSession() {
		return this.sessionFactory.openSession();
	}
    
    @SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Type type = getClass().getGenericSuperclass();
		Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
		entityClass = (Class<T>) parameterizedType[0];
	}

	/** 
	* 根据id查找对象
	* 
	* @param id
	* @return 
	* @see com.miaozhen.withdata.dao.BaseDao#find(java.io.Serializable) 
	*/ 
	@SuppressWarnings("unchecked")
	@Override
	public T find(ID id) {
		if (id == null) {
			return null;
		}
		Session session = this.sessionFactory.openSession();
		T entity = null;
		try{
			entity = (T) session.get(entityClass, id);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		session.close();
		return entity;
	}
	
	/** 
	* 更新对象
	* 
	* @param entity
	* @return 
	* @see com.miaozhen.withdata.dao.BaseDao#merge(java.lang.Object) 
	*/ 
	@SuppressWarnings("unchecked")
	@Override
	public T merge(T entity) {
		if (entity == null) {
			return null;
		}
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		T result = null;
		try{
			transaction.begin();
			result = (T) session.merge(entity);
		}catch (Exception e) {
			transaction.rollback();
			logger.error(e.getMessage());
		}
		transaction.commit();
		session.close();
		return result;
	}
	
	/** 
	* 新增对象
	* 
	* @param entity 
	* @see com.miaozhen.withdata.dao.BaseDao#persist(java.lang.Object) 
	*/ 
	@Override
	public void persist(T entity) {
		if (entity == null) {
			return;
		}
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		try{
			transaction.begin();
			session.persist(entity);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			transaction.rollback();
		}
		transaction.commit();
		session.close();
	}
	
	/** 
	* 移除对象
	* 
	* @param entity 
	* @see com.miaozhen.withdata.dao.BaseDao#remove(java.lang.Object) 
	*/ 
	@Override
	public void remove(T entity) {
		if (entity == null) {
			return;
		}
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		try{
			transaction.begin();
			session.delete(entity);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			transaction.rollback();
		}
		transaction.commit();
		session.close();
	}
	
	/** 
	* 批量提交
	* 
	* @param list 
	* @see com.miaozhen.withdata.dao.BaseDao#saveList(java.util.List) 
	*/ 
	@Override
	public void saveList(List<T> list) {
		if(null == list || list.isEmpty()){
			return;
		}
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		try{
			transaction.begin();
			for(T t: list){
				session.save(t);
			}
		}catch (Exception e) {
			transaction.rollback();
			logger.error(e.getMessage());
		}
		transaction.commit();
		session.close();
	}
	
	/** 
	 * 批量提交
	 * 
	 * @param list 
	 * @see com.miaozhen.withdata.dao.BaseDao#saveList(java.util.List) 
	 */ 
	@Override
	public void mergeList(List<T> list) {
		if(null == list || list.isEmpty()){
			return;
		}
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		try{
			transaction.begin();
			for(T t: list){
				session.merge(t);
			}
		}catch (Exception e) {
			transaction.rollback();
			logger.error(e.getMessage());
		}
		transaction.commit();
		session.close();
	}
}
