package com.mos.bsd.service;

import com.mos.bsd.domain.BSDResponse;

public interface IBsdUpdateVipInfoService {
	/**
	 * 更新会员信息
	 * @param openid 
	 * @return
	 */
	BSDResponse getData(String openid);

}
