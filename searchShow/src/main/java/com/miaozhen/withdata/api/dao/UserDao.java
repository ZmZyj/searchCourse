package com.miaozhen.withdata.api.dao;

import com.miaozhen.withdata.api.entity.User;

public interface UserDao extends BaseDao<User, Integer>{

	/** 
	* 
	* 
	* @param username
	* @return
	*/ 
	User findByUsername(String username);

	/** 
	* 
	* 
	* @return
	*/ 
	int checkDataBase();

	User findByEmail(String email);
}
