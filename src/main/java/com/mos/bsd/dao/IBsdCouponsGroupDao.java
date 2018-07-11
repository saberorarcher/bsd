package com.mos.bsd.dao;

import java.util.List;
import java.util.Map;

public interface IBsdCouponsGroupDao {
	/**
	 * 插入或更新D0230
	 * @param couponsGroup_list
	 * @return
	 */
	int mergeIntoD0230(List<Map<String, Object>> couponsGroup_list);
	/**
	 * 插入或更新D0239
	 * @param couponsGroup_list
	 * @return
	 */
	int mergeIntoD0239(List<Map<String, Object>> couponsGroup_list);
	/**
	 * 插入D
	 * @param couponsGroup_list
	 * @return
	 */
	int mergeIntoD0230Extend(List<Map<String, Object>> couponsGroup_list);

}
