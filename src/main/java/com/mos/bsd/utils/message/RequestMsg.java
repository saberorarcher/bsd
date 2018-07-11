package com.mos.bsd.utils.message;

/**
 * 客户端请求消息
 * @author hm
 *
 */
public class RequestMsg extends BaseMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**交易码*/
	private String method;
	
	/**渠道*/
	private String caller;
	
	private String format;
	
	private String paramSig;
	
	private String callTime;
	private String token;
	private String key;

    private int pageIndex;
    private int pageSize;

    /** 用户编码，用户登录时使用的那个编号*/
	private String userCode;
    /** 用户名，用户姓名*/
    private String userName;
    /** 用户密码*/
    private String userPass;
    /** 用户Key，固定4位的用户KEY*/
    private String userKey;
    /** 用户编号，用户的系统编号*/
    private String userId;
    /** 用户类型：0总部，1分支，2POS*/
    private String userType;
	/** 语言编号，用户当前使用的语言*/
    private String languageId;
    /** 机构编号：分支机构/工厂用户所属机构编号*/
    private int branchId;
    /** 用户所属公司编号*/
    private int userCompanyId;
    
	private String methodtype;
    private boolean rmiSuccess;
    
    //本机ip,用来控制哪些ip可以访问
    private String localIp;
    //数据包ID.用于主从备份用，主数据库包ID=0,赋值后从数据库使用
    private long packageId=0;
    
    
    public RequestMsg(){
    	super();
    }
    
    public long getPackageId() {
		return packageId;
	}

	public void setPackageId(long packageId) {
		this.packageId = packageId;
	}

	public String getLocalIp() {
		return localIp;
	}
	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}
	/**
	 * 获取key
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * 设置key
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * 获取methodtype
	 * 执行交易码方式,map得到参数，data得到数据。默认得到数据
	 * @return the methodtype
	 */
	public String getMethodtype() {
		return methodtype;
	}
	/**
	 * 设置methodtype
	 * 执行交易码方式。map得到参数，data得到数据。默认得到数据
	 * @param methodtype the methodtype to set
	 */
	public void setMethodtype(String methodtype) {
		this.methodtype = methodtype;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}


	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return 获取用户编码，用户登录时使用的那个编号
	*/
	public String getUserCode() {
		return userCode;
	}
	
	/**
	* @param 设置用户编码，用户登录时使用的那个编号
	*/
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	/**
	 * @return 获取用户名，用户姓名
	*/
	public String getUserName() {
		return userName;
	}
	
	/**
	* @param 设置用户名，用户姓名
	*/
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return 获取用户密码
	*/
	public String getUserPass() {
		return userPass;
	}
	
	/**
	* @param 设置用户密码
	*/
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	
	/**
	 * @return 获取用户Key，固定4位的用户KEY
	*/
	public String getUserKey() {
		return userKey;
	}
	
	/**
	* @param 设置用户Key，固定4位的用户KEY
	*/
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	
	/**
	 * @return 获取用户编号，用户的系统编号
	*/
	public String getUserId() {
		return userId;
	}
	
	/**
	* @param 设置用户编号，用户的系统编号
	*/
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return 获取用户类型：0总部，1分支，2POS
	*/
	public String getUserType() {
		return userType;
	}
	
	/**
	* @param 设置用户类型：0总部，1分支，2POS
	*/
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * @return 获取语言编号，用户当前使用的语言
	*/
	public String getLanguageId() {
		return languageId;
	}
	
	/**
	* @param 设置语言编号，用户当前使用的语言
	*/
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getParamSig() {
		return paramSig;
	}

	public void setParamSig(String paramSig) {
		this.paramSig = paramSig;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}
	/**
	 * 执行RMI成功否
	 * @return true表示成功
	 */
	public boolean isRmiSuccess() {
		return rmiSuccess;
	}
	/**
	 * 设置远程调用成功表示
	 * @param rmiSuccess
	 */
	public void setRmiSuccess(boolean rmiSuccess) {
		this.rmiSuccess = rmiSuccess;
	}
	/**
	 * @return 获取机构编号：分支机构工厂用户所属机构编号
	*/
	public int getBranchId() {
		return branchId;
	}
	
	/**
	* @param 设置机构编号：分支机构工厂用户所属机构编号
	*/
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	/**
	 * @return 获取用户所属公司编号
	*/
	public int getUserCompanyId() {
		return userCompanyId;
	}
	
	/**
	* @param 设置用户所属公司编号
	*/
	public void setUserCompanyId(int userCompanyId) {
		this.userCompanyId = userCompanyId;
	}

	@Override
	public String toString() {
		return "RequestMsg [method=" + method + ", caller=" + caller + ", format=" + format + ", paramSig=" + paramSig
				+ ", callTime=" + callTime + ", token=" + token + ", key=" + key + ", pageIndex=" + pageIndex
				+ ", pageSize=" + pageSize + ", userCode=" + userCode + ", userName=" + userName + ", userPass="
				+ userPass + ", userKey=" + userKey + ", userId=" + userId + ", userType=" + userType + ", languageId="
				+ languageId + ", branchId=" + branchId + ", userCompanyId=" + userCompanyId + ", methodtype="
				+ methodtype + ", rmiSuccess=" + rmiSuccess + ", localIp=" + localIp + ", packageId=" + packageId + "]";
	}
	
}
