package com.mos.bsd.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author hero
 *
 */
@ApiModel(value = "返回信息", description = "返回信息描述")
public class BSDResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "消息编号", required = true)
	private String msgId;

	@ApiModelProperty(notes = "调用状态:success表示成功;error表示失败", required = true)
	private String status;

	@ApiModelProperty(notes = "返回消息", required = true)
	private String msg;
	
	@ApiModelProperty(notes = "错误数据", required = true)
	private String errorData;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	
}
