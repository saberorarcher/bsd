package com.mos.bsd.dao;

import java.util.List;
import java.util.Map;

public interface IBsdVipCouponsDao {
	/**
	 * 会员券写入D0240
	 * @param coupons_list
	 * @return
	 */
	int mergeD0240(List<Map<String, Object>> coupons_list);
	/**
	 * 更新扩展信息表
	 * @param coupons_list
	 * @return
	 */
	int mergeD0240Extend(List<Map<String, Object>> coupons_list);
	/**
	 * 根据card_id查询vip_id
	 * @param coupons_list
	 * @return
	 */
	List<Map<String, Object>> getVipList(List<Map<String, Object>> coupons_list);
	/**
	 * 根据department_user_id 查询department_id
	 * @param coupons_list
	 * @return
	 */
	List<Map<String, Object>> getDepList(List<Map<String, Object>> coupons_list);

}
