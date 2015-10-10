package com.miaozhen.util;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.miaozhen.consts.Consts;
import com.miaozhen.withdata.api.entity.User;
import com.miaozhen.withdata.api.service.UserService;

public class AuthenticationRealm extends AuthorizingRealm {
	
	@Resource(name = "userService")
	private UserService userService;
	
	/**
	 * 获取认证信息
	 * 
	 * @param token
	 *            令牌
	 * @return 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {
		String username = null;
		String password = null;
		String source = Consts.LOGIN_SOURCE_LOGIN;
		if(token instanceof AuthenticationUserToken){
			AuthenticationUserToken authenticationToken = (AuthenticationUserToken) token;
			username = authenticationToken.getUsername();
			password = new String(authenticationToken.getPassword());
			source = authenticationToken.getSource();
		}else{
			UsernamePasswordToken authenticationToken = (UsernamePasswordToken) token;
			username = authenticationToken.getUsername();
			password = new String(authenticationToken.getPassword());
		}
		if (username != null) {
			User user = userService.findByUsername(username);
			if (user == null || !user.getIsEnabled()) {
				throw new UnknownAccountException();
			}
			if (!(DigestUtils.md5Hex(password).equals(user.getPassword()) || 
					(Consts.LOGIN_SOURCE_NATIVE.equals(source) && password.equals(user.getPassword())))) {
				throw new IncorrectCredentialsException();
			}
			return new SimpleAuthenticationInfo(new Principal(user.getId(), username), password, getName());
		}
		throw new UnknownAccountException();
	}

	/**
	 * 获取授权信息
	 * 
	 * @param principals
	 *            principals
	 * @return 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		if (principal != null) {
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
			return authorizationInfo;
		}
		return null;
	}

}