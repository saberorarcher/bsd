package com.mos.bsd.service;

import com.x3.base.core.message.RequestMsg;

/**
 * RMI权限认证服务
 * @author hero
 *
 */
public interface IRMIAuthorityService {
	
	
	public boolean authorityCheck(RequestMsg requestMsg);

}
