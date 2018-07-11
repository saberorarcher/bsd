package com.mos.bsd.dao;

import java.util.List;
import java.util.Map;

public interface IBsdVipScoreDao {
	/**
	 * 更新vip积分信息
	 * @param vipScore_list
	 * @return
	 */
	int updateVipScore(List<Map<String, Object>> vipScore_list);
	/**
	 * 更新会员扩展信息
	 * @param vipScore_list
	 * @return
	 */
	int updateVipScoreExtend(List<Map<String, Object>> vipScore_list);

}
