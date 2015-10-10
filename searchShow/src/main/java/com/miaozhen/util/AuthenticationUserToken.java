package com.miaozhen.util;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.miaozhen.withdata.api.entity.User;

/** 
* 该token用于授权后的登陆使用
* 
* @author zhangmin@miaozhen.com
* @date 2015-1-21 下午3:06:07 
* 
*/ 
public class AuthenticationUserToken extends UsernamePasswordToken{
	
	private String source = "login";

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	
	public AuthenticationUserToken(User user){
		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword().toCharArray());
	}
	
	public AuthenticationUserToken(User user, String source){
		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword().toCharArray());
		this.source = source;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

}
