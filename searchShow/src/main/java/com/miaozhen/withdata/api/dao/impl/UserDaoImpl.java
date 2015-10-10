package com.miaozhen.withdata.api.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;


import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.miaozhen.withdata.api.dao.UserDao;
import com.miaozhen.withdata.api.entity.User;
import com.miaozhen.util.StringUtil;

/** 
* 用户dao实现类
* 
* @author zhangmin@miaozhen.com
* @date 2015-1-22 下午3:23:46 
* 
*/ 
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao {
	
	private Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	/** 
	* 根据用户名查找用户
	* 
	* @param username
	* @return 
	* @see com.miaozhen.sociMeter.dao.UserDao#findByUsername(java.lang.String) 
	*/ 
	public User findByUsername(String username) {
		if (StringUtil.isEmpty(username)) {
			return null;
		}
		Session session = openSession();
		try {
			String jpql = "select user from User user where lower(user.username) = lower(:username)";
			List<?> list = session.createQuery(jpql)
					.setFlushMode(FlushMode.COMMIT)
					.setParameter("username", username).list();
			if(null != list && 0 != list.size()){
				return (User) list.get(0);
			}
			return null;
		} catch (NoResultException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		} finally {
			session.close();
		}
	}

	/** 
	* 根据id查找用户
	* 
	* @param id
	* @return 
	* @see com.miaozhen.sociMeter.dao.UserDao#find(java.lang.Integer) 
	*/ 
	@Override
	public User find(Integer id) {
		if (id == null) {
			return null;
		}
		Session session = openSession();
		User entity = null;
		try{
			entity = (User) session.get(User.class, id);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		session.close();
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int checkDataBase() {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_question");
		Session session = openSession();
		List<Object> list = null;
		try{
			Query query = session.createSQLQuery(sql.toString())
					.setFlushMode(FlushMode.COMMIT)
					.setMaxResults(1);
			list = query.list();
		}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		session.close();
		if(null == list || list.isEmpty()){
			return 0;
		}
		int result = Integer.valueOf(list.get(0).toString());
		return result;
	}

	@Override
	public User findByEmail(String email) {
		if (StringUtil.isEmpty(email)) {
			return null;
		}
		Session session = openSession();
		try {
			String jpql = "select user from User user where user.email = :email";
			List<?> list = session.createQuery(jpql)
					.setFlushMode(FlushMode.COMMIT)
					.setParameter("email", email).list();
			if(null != list && 0 != list.size()){
				return (User) list.get(0);
			}
			return null;
		} catch (NoResultException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		} finally {
			session.close();
		}
	}
}
