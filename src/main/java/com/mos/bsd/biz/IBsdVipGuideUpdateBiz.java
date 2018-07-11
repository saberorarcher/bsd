package com.mos.bsd.biz;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.VipGuideEntity;

public interface IBsdVipGuideUpdateBiz {
	/**
	 * 更新会员专属导购
	 * @param vipGuide
	 * @return
	 */
	int updateVipGuide(VipGuideEntity vipGuide);
	/**
	 * 根据openid查询会员是否存在
	 * @param vipGuide
	 * @return
	 */
	int findVipByOpenId(VipGuideEntity vipGuide);
	/**
	 * 调用接口,查询会员信息
	 * @param vipGuide
	 * @return
	 */
	List<Map<String, Object>> getVipData(VipGuideEntity vipGuide);
	/**
	 * 判断导购和会员是否是统一店铺
	 * @param vipGuide
	 * @return
	 */
	int isSameDepot(VipGuideEntity vipGuide);

}
