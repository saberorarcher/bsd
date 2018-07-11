package com.mos.bsd.service;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.VipGuideEntity;

public interface IBsdVipGuideUpdateService {
	/**
	 * 修改专属导购
	 * @param vipGuide
	 * @return
	 */
	BSDResponse updateData(VipGuideEntity vipGuide);

}
