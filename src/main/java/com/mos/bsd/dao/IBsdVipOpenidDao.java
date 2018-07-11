package com.mos.bsd.dao;

import java.util.List;
import java.util.Map;

public interface IBsdVipOpenidDao {
	/**
	 * 更新openId到D0210
	 * @param vipOpenid_list
	 * @return
	 */
	int mergeD0210OpenId(List<Map<String, Object>> vipOpenid_list);
	/**
	 * 保存或更新到j_vipbinding
	 * @param vipOpenid_list
	 * @return
	 */
	int mergeVipbinding(List<Map<String, Object>> vipOpenid_list);
	/**
	 * 根据card_id获取vipid
	 * @param vipOpenid_list
	 * @return
	 */
	List<Map<String, Object>> getVipId(List<Map<String, Object>> vipOpenid_list);

}
