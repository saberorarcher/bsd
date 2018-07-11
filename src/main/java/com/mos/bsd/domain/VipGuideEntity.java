package com.mos.bsd.domain;

import java.io.Serializable;

public class VipGuideEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private String openid;//绑定微信openid
	private String userid;//专属导购用户
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
}
