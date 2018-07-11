package com.mos.bsd.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mos.bsd.config.GatewayConfig;
import com.mos.bsd.service.IRMIAuthorityService;
import com.mos.bsd.service.IRMIController;
import com.mos.bsd.utils.BusinessException;
import com.mos.bsd.utils.RestTemplateUtils;
import com.x3.base.core.message.RequestMsg;
import com.x3.base.core.message.ResponseMsg;

@Service
public class RMIControllerImpl implements IRMIController {
	
	private static final Logger logger = LoggerFactory.getLogger(RMIControllerImpl.class);

	@Autowired
	private IRMIAuthorityService authorityService;
	
	@Autowired
	private GatewayConfig gatewayConfig;

	@Override
	public ResponseMsg doService(RequestMsg requestMsg) throws BusinessException {
		if (logger.isDebugEnabled()) {
			logger.debug("entering doService(RequestMsg)");
			logger.debug("requestMsg: " + requestMsg);
		}
		try {
			if(!authorityService.authorityCheck(requestMsg)) {
				if (logger.isDebugEnabled()) {
					logger.debug("exiting doService()");
				}
				return callRMIFailed("权限检查失败，没有权限访问或缺失数据");
			}
			
			String resStr = RestTemplateUtils.post(gatewayConfig.getBaseUrl(), JSON.toJSONString(requestMsg));
			
			if (logger.isDebugEnabled()) {
				logger.debug("exiting doService()");
			}
			return JSON.parseObject(resStr, ResponseMsg.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (logger.isDebugEnabled()) {
				logger.debug("exiting doService()");
			}
			return callRMIFailed("调用RMI中心失败，原因：" + e.getMessage());
		}
	}

	
	private ResponseMsg callRMIFailed(String errorMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering callRMIFailed(String)");
			logger.debug("errorMsg: \"" + errorMsg + "\"");
		}
		ResponseMsg msg = new ResponseMsg();
		msg.setErrCode("E00500");
		msg.setErrorMsg(errorMsg);
		msg.setStatus(false);
		if (logger.isDebugEnabled()) {
			logger.debug("exiting callRMIFailed()");
			logger.debug("returning: " + msg);
		}
		return msg;
	}

}
