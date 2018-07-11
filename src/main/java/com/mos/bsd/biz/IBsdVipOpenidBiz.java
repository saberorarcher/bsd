package com.mos.bsd.biz;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdVipOpenidBiz {
	/**
	 * 获取会员绑定openid接口
	 * @param uuid 
	 * @param type 
	 * @param edate 
	 * @param sdate 
	 * @return
	 */
	List<Map<String, Object>> getVipOpenIdData(String sdate, String edate, int type, String uuid);
	/**
	 * 同步openid到x2
	 * @param vipOpenid_list
	 * @return
	 */
	List<Map<String, Object>> mergeToX2(List<Map<String, Object>> vipOpenid_list);
	/**
	 * 获取数据
	 * @param sdate
	 * @param edate
	 * @param type
	 */
	int getVipBaseData(String sdate, String edate, int type);
	/**
	 * 获取要同步的数据
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();

}
