package com.miaozhen.withdata.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/** 
* user entity
* 
* @author zhangmin@miaozhen.com
* @date 2014-10-23 10:11:11 am 
* 
*/ 
@Entity
@Table(name = "t_user")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_user_sequence")
public class User implements Serializable{

	/** 
	* @Fields serialVersionUID : seri id
	*/ 
	private static final long serialVersionUID = 1112488211636067854L;
	//主键id
	private Integer id;
	//创建时间
	private Date createDate;
	//修改时间
	private Date modifyDate;
	//登录名
	private String username;
	//密码（md5加密）
	private String password;
	//显示名称
	private String name;
	//电话
	private String phone;
	//邮箱
	private String email;
	//公司
	private String corp;
	//是否启用
	private Boolean isEnabled;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the createDate
	 */
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the modifyDate
	 */
	@Column(name="modify_date")
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the corp
	 */
	public String getCorp() {
		return corp;
	}

	/**
	 * @param corp the corp to set
	 */
	public void setCorp(String corp) {
		this.corp = corp;
	}
	
	/**
	 * 获取是否启用
	 * 
	 * @return 是否启用
	 */
	@NotNull
	@Column(name="is_enabled", nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * 设置是否启用
	 * 
	 * @param isEnabled
	 *            是否启用
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}
