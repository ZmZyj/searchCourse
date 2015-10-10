package com.miaozhen.withdata.api.service.impl;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.miaozhen.withdata.api.consts.Consts;
import com.miaozhen.withdata.api.dao.UserDao;
import com.miaozhen.withdata.api.entity.User;
import com.miaozhen.withdata.api.service.UserService;
import com.miaozhen.util.AuthenticationUserToken;
import com.miaozhen.util.Principal;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	
	/** 
	* 获取当前登陆用户
	* 
	* @return 
	* @see com.miaozhen.withdata.service.UserService#getCurrent() 
	*/ 
	public User getCurrent() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				try{
					return userDao.find(principal.getId());
				}catch (Exception e) {
					return null;
				}
			}
		}
		return null;
	}
	
	/** 
	 * 获取当前登陆用户
	 * 
	 * @return 
	 * @see com.miaozhen.withdata.service.UserService#getCurrentUserId() 
	 */ 
	public Integer getCurrentUserId() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				try{
					return principal.getId();
				}catch (Exception e) {
					return null;
				}
			}
		}
		return null;
	}
	
	public String getCurrentUsername() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}
	
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public int checkDataBase() {
		return userDao.checkDataBase();
	}

	public boolean isAuthenticated() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			return subject.isAuthenticated();
		}
		return false;
	}
	
	/** 
	* 
	* 
	* @param params
	* @return 
	* @see com.miaozhen.dataAnalysis.service.UserService#modifyPassword(net.sf.json.JSONObject) 
	*/ 
	@Override
	public JSONObject modifyPassword(JSONObject params) {
		User user = getCurrent();
		if(null == user){
			return Consts.JSON_FAIL;
		}
		String oldPwd = params.optString("oldPwd").trim();
		String newPwd = params.optString("newPwd").trim();
		
		if(!DigestUtils.md5Hex(oldPwd).equals(user.getPassword())){
			JSONObject result = JSONObject.fromObject(Consts.JSON_FAIL);
			result.element("message", "原密码错误！");
			return result;
		}
		user.setPassword(DigestUtils.md5Hex(newPwd));
		userDao.merge(user);
		return Consts.JSON_SUCCESS;
	}

	@Override
	public void merge(User user) {
		userDao.merge(user);
	}

	@Override
	public void persist(User user) {
		userDao.persist(user);
	}
	
	@Override
	public void login(User user) {
		Subject currentUser = SecurityUtils.getSubject();  
        currentUser.logout();
        currentUser.login(new AuthenticationUserToken(user, com.miaozhen.consts.Consts.LOGIN_SOURCE_NATIVE));
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}
}

