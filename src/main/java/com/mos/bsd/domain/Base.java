package com.mos.bsd.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class Base implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "渠道(调用者)", required = true,example="SAP")
	private String channel;
	
	@ApiModelProperty(notes = "消息编号", required = true)
	private String msg_id;
	
	@ApiModelProperty(notes = "用户id(X2用户Id)", required = true,example="203030008618")
	private String user_id;
	
	@ApiModelProperty(notes = "用户系统标识(X2用户标识，固定4位)", required = true,example="ZAMN")
	private String user_key;
	
	@ApiModelProperty(notes = "用户姓名(X2用户姓名)", required = true,example="陈真")
	private String user_name;
	
//	@ApiModelProperty(notes = "用户名(X2登录用户名)", required = true,example="cz")
//	private String user_code;

//	@ApiModelProperty(notes = "用户密码(X2登录用户密码)", required = true,example="12345")
//	private String user_password;
	
	/** 
	* 获取 渠道(调用者)
	* @return
	* @returnType String
	*/
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_key() {
		return user_key;
	}

	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

//	public String getUser_code() {
//		return user_code;
//	}
//
//	public void setUser_code(String user_code) {
//		this.user_code = user_code;
//	}
//
//	public String getUser_password() {
//		return user_password;
//	}
//
//	public void setUser_password(String user_password) {
//		this.user_password = user_password;
//	}
	
}
