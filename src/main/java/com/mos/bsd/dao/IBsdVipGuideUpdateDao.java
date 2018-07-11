package com.mos.bsd.dao;

import com.mos.bsd.domain.VipGuideEntity;

public interface IBsdVipGuideUpdateDao {
	/**
	 * 更新专属导购
	 * @param vipGuide
	 * @return
	 */
	int updateVipGuide(VipGuideEntity vipGuide);
	/**
	 * 判断有无会员
	 * @param vipGuide
	 * @return
	 */
	int findVipByOpenId(VipGuideEntity vipGuide);
	/**
	 * 判断导购和会员是否是同一店铺
	 * @param vipGuide
	 * @return
	 */
	int isSameDepot(VipGuideEntity vipGuide);

}
