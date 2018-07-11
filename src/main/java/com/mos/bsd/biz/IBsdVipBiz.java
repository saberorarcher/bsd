package com.mos.bsd.biz;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdVipBiz {
	/**
	 * 访问bsd接口  获取数据
	 * @param i 1增量使用 2初始化数据使用
	 * @param uuid 
	 * @return
	 */
	Map<String,List<Map<String, Object>>> getVipData(String sdate,String edate, int i, String uuid);
	/**
	 * 写入D0210
	 * @param vipList
	 * @return
	 */
	int insertD0210(List<Map<String, Object>> vipList);
	/**
	 * 写入D0180
	 * @param vipList
	 * @return
	 */
	int insertD0180(List<Map<String, Object>> vipList);
	/**
	 * 写入VIP卡等级变更表
	 * @param vipList
	 * @param uuid 
	 * @return
	 */
	int insertP0290(List<Map<String, Object>> vipList, String uuid);
	/**
	 * 测试事务是否生效
	 * @return
	 */
	
	List<Map<String, Object>> getVipList();
	/**
	 * 根据department_user_id查询department_id
	 * @param vipList
	 * @return
	 */
	Map<String,List<Map<String, Object>>> getDepartmentId(List<Map<String, Object>> vipList);
	/**
	 * 
	 * @param sdate
	 * @param edate
	 * @param type 1表示增量  2表示初始化数据
	 */
	int getVipBaseData(String sdate, String edate, int type);
	void findClobData();
	
	/**
	 * 获取要同步的数据
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();
	/**
	 * 获取错误数据
	 * @return
	 */
	List<Map<String, Object>> getWrongData();

}
