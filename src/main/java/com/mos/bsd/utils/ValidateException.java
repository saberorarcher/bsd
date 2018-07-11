package com.mos.bsd.utils;


import com.x3.base.core.util.JsonUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description 数据验证异常
 * @author 陈真
 * @date 2018年5月18日 上午10:38:03
 * @version 1.0
 */
@ApiModel(value = "数据验证异常", description = "数据验证异常信息")
public class ValidateException extends RuntimeException {

	@ApiModelProperty(notes = "消息ID", required = true)
	private String msgId;

	@ApiModelProperty(notes = "外部ID", required = true)
	private String refId;

	@ApiModelProperty(notes = "数据编号", required = true)
	private String dataKey;

	@ApiModelProperty(notes = "验证状态:E表示失败", required = true)
	private String status;

	@ApiModelProperty(notes = "错误编码", required = true)
	private String code;

	@ApiModelProperty(notes = "消息消息", required = true)
	private String msg;

	private static final long serialVersionUID = -7605504490270733279L;

	public ValidateException() {

	}

	public ValidateException(Throwable e) {
		super(e);
	}

	public ValidateException(String code, String msg, Throwable e) {
		super(code + ":" + msg, e);
		this.code = code;
	}

	public ValidateException(String code, String msg) {
		super(code + ":" + msg);
		this.code = code;
		this.msg = msg;
	}

	public ValidateException(String code, Object msg) {
		super(code + ":" + msg);
		this.code = code;
		this.msg = JsonUtil.toJSONString(msg, true);
	}

	public ValidateException(String msgId, String refId, String dataKey, String msg) {
		super(msg);
		this.msgId = msgId;
		this.refId = refId;
		this.dataKey = dataKey;
		this.status = "E";
		this.msg = msg;
	}
	
	/**
	 * @param msgId
	 *            参数id
	 * @param refId
	 *            每条记录的唯一id
	 * @param dataKey
	 *            每条记录的X2内部唯一id
	 * @param msg
	 *            返回内容 创建一个新的实例 ValidateException
	 */
	public ValidateException(String msgId, String refId, String dataKey, String code, String msg) {
		super(msg);
		this.msgId = msgId;
		this.refId = refId;
		this.dataKey = dataKey;
		this.status = "E";
		this.code = code;
		this.msg = msg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
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

	public String getErrorCode() {
		return code;
	}

	public void setErrorCode(String code) {
		this.code = code;
	}

	public String getErrorData() {
		return msg;
	}

	public void setErrorData(String msg) {
		this.msg = msg;
	}

}
