package com.mos.bsd.biz;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdCouponsGroupBiz {
	/**
	 * 获取
	 * @param i  1增量更新用  2.初始化数据使用
	 * @param uuid 
	 * @return
	 */
	Map<String,List<Map<String, Object>>> getcouponsGroupData(String begdate,String enddate, int i, String uuid);
	/**
	 * 将券组同步到x2
	 * @param couponsGroup_list
	 * @return
	 */
	int mergeIntoX2(List<Map<String, Object>> couponsGroup_list);
	/**
	 * 原始数据保存进x2
	 * @param sdate
	 * @param edate
	 * @param type 1表示增量  2表示初始化数据
	 * @return
	 */
	int getBaseData(String sdate, String edate, int type);
	/**
	 * 读取要同步的数据
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();

}
