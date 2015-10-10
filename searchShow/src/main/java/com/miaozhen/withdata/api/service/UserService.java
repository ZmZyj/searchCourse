package com.miaozhen.withdata.api.service;

import com.miaozhen.withdata.api.entity.User;

import net.sf.json.JSONObject;

public interface UserService {

	/** 
	* 
	* 
	* @param username
	* @return
	*/ 
	User findByUsername(String username);

	int checkDataBase();
	
	User getCurrent();
	
	Integer getCurrentUserId();

	boolean isAuthenticated();

	JSONObject modifyPassword(JSONObject params);

	void merge(User user);
	
	void persist(User user);
	
	/** 
	* 
	* 
	* @param user
	*/ 
	void login(User user);

	User findByEmail(String email);

}
