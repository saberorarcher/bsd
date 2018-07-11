package com.mos.bsd.service.impl;

import org.springframework.stereotype.Service;

import com.mos.bsd.service.IRMIAuthorityService;
import com.x3.base.core.message.RequestMsg;


@Service
public class RMIAuthorityServiceImpl implements IRMIAuthorityService {

	@Override
	public boolean authorityCheck(RequestMsg requestMsg) {
		return true;
	}

}

