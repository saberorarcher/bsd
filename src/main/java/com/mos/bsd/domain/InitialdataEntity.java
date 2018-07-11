package com.mos.bsd.domain;

import java.io.Serializable;
import java.sql.Date;
/**
 * 初始化数据实体类
 * @author hao
 *
 */
public class InitialdataEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String interface_name ;
	private Date create_date ;
	private String request_data ;
	private String received_data ;
	private String uuid ;
	private int status ;
	private String cuuid;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInterface_name() {
		return interface_name;
	}
	public void setInterface_name(String interface_name) {
		this.interface_name = interface_name;
	}
	
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getRequest_data() {
		return request_data;
	}
	public void setRequest_data(String request_data) {
		this.request_data = request_data;
	}
	public String getReceived_data() {
		return received_data;
	}
	public void setReceived_data(String received_data) {
		this.received_data = received_data;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCuuid() {
		return cuuid;
	}
	public void setCuuid(String cuuid) {
		this.cuuid = cuuid;
	}
	

}
