package com.mos.bsd.biz;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdVipScoreBiz {
	
	/**
	 * bsd 会员积分接口
	 * @param edate 
	 * @param sdate 
	 * @param i  1定时任务使用 2.初始化数据使用
	 * @param uuid 
	 * @return
	 */
	Map<String,List<Map<String, Object>>> getVipScoreData(String sdate, String edate, int i, String uuid);
	
	/**
	 * 更新vip积分
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
	/**
	 * 
	 * @param sdate
	 * @param edate
	 * @param type 1表示增量  2表示初始化数据
	 */
	int getVipBaseData(String sdate, String edate, int type);
	/**
	 * 获取数据中需要同步的记录
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();

}
