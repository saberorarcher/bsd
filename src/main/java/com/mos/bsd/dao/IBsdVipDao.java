package com.mos.bsd.dao;

import java.util.List;
import java.util.Map;

public interface IBsdVipDao {
	/**
	 * 写入D0210
	 * @param vipList
	 * @return
	 */
	int insertD0210(List<Map<String, Object>> vipList);
	/**
	 * 写入卡档案
	 * @param vipList
	 * @param i
	 * @return
	 */
	int insertD0180(List<Map<String, Object>> vipList, int i);
	/**
	 * 写入vip卡等级变更表
	 * @param vipList
	 * @return
	 */
	int insertP0290(List<Map<String, Object>> vipList);
	/**
	 * 查询更改的会员等级变更记录
	 * @param vipList
	 * @return
	 */
	List<Map<String, Object>> queryChangeData(List<Map<String, Object>> vipList);
	/**
	 * 测试查询会员
	 * @return
	 */
	List<Map<String, Object>> getVipList();
	/**
	 * 查询department_id
	 * @param vipList
	 * @param i 
	 * @return
	 */
	List<Map<String, Object>> queryDepartmentData(List<Map<String, Object>> vipList, int i);
	/**
	 *	删除临时表中的记录
	 * @param uuid 
	 * @return
	 */
	int cleanTempData(String uuid);
	/**
	 * 插入数据
	 * @param vipList
	 * @param uuid 
	 */
	 int mergeTemData(List<Map<String, Object>> vipList, String uuid);
	 /**
	  * 查询错误数据
	  * @return
	  */
	 List<Map<String, Object>> findWrongData();

}
