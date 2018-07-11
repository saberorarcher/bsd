package com.mos.bsd.biz;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdVipCouponsBiz {
	
	/**
	 * 获取vip券
	 * @param i  1增量更新使用 2初始化数据使用
	 * @param uuid 
	 * @return
	 */
	Map<String, List<Map<String,Object>>> getcouponsGroupData(String sdate,String edate, int i, String uuid);
	/**
	 * 将vip券记录写入到X2
	 * @param coupons_list
	 * @return
	 */
	Map<String,List<Map<String, Object>>> mergeToX2(List<Map<String, Object>> coupons_list);
	/**
	 * 保存原始记录
	 * @param sdate
	 * @param edate
	 * @param type 1表示增量 2表示初始化数据
	 */
	int getBaseData(String sdate, String edate, int type);
	/**
	 * 获取需要同步的原始数据
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();

}
