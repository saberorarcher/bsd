package com.mos.bsd.utils;


/**
 * 业务错误处理
 * 
 * @author qdymac
 *
 */
public class BusinessException extends RuntimeException {

	private String errorCode;
	private String errorData;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7605504490270733279L;

	public BusinessException() {
	
	}

	public BusinessException(Throwable e) {
		super(e);
	}

	public BusinessException(String code, String msg) {
		super(code + ":" + msg);
		this.errorCode = code;
	}

	public BusinessException(String code, String msg, Throwable e) {
		super(code + ":" + msg, e);
		this.errorCode = code;
	}

	public BusinessException(String code, String msg, String data) {
		super(code + ":" + msg);
		this.errorCode = code;
		this.errorData = data;
	}
	public BusinessException(String code, String msg, Object data) {
		super(code + ":" + msg);
		this.errorCode = code;
		this.errorData =JsonUtil.toJSONString(data,true);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

//	@Override
//	public Throwable fillInStackTrace() {
//		return this;
//	}
}
