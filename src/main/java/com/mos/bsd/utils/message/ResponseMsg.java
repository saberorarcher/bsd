package com.mos.bsd.utils.message;

/**
 * 服务端返回消息
 * 
 * @author hm
 *
 */
public class ResponseMsg extends BaseMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 分页就返回总页数 */
	private int total;
	private boolean status;
	private String errorMsg;
	/** 返回错误码 */
	private String errCode;
	/** 返回错误数据 */
	private String errData;
	private String errStackTrace;
    private String diffTime;
    private String method;
    /**子方法*/
    private String methodSub;
    
	public String getMethodSub() {
		return methodSub;
	}

	public void setMethodSub(String methodSub) {
		this.methodSub = methodSub;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDiffTime() {
		return diffTime;
	}

	public void setDiffTime(String diffTime) {
		this.diffTime = diffTime;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrData() {
		return errData;
	}

	public void setErrData(String errData) {
		this.errData = errData;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrStackTrace() {
		return errStackTrace;
	}

	public void setErrStackTrace(String errStackTrace) {
		this.errStackTrace = errStackTrace;
	}

	@Override
	public String toString() {
		return "ResponseMsg [total=" + total + ", status=" + status + ", errorMsg=" + errorMsg + ", errCode=" + errCode
				+ ", errData=" + errData + ", errStackTrace=" + errStackTrace + ", diffTime=" + diffTime + ", method="
				+ method + ", methodSub=" + methodSub + "]";
	}

}
