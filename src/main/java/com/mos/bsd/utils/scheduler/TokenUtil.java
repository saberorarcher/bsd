package com.mos.bsd.utils.scheduler;

public class TokenUtil {
	
	private String token;

	private TokenUtil() {}
	
	private static final TokenUtil tokenUtil = new TokenUtil();  
	
	//静态工厂方法   
    public static TokenUtil getInstance() {  
        return tokenUtil;  
    }
    
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
