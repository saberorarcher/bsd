package com.mos.bsd.service;

import com.x3.base.core.exception.BusinessException;
import com.x3.base.core.message.RequestMsg;
import com.x3.base.core.message.ResponseMsg;

/**
 * RMI 控制器
 * @author hero
 *
 */
public interface IRMIController {

	/**
	 * 调用RMI注册中心
	 * @param requestMsg
	 * @return
	 * @throws BusinessException
	 */
	public ResponseMsg doService(RequestMsg requestMsg) throws BusinessException;
	
}
