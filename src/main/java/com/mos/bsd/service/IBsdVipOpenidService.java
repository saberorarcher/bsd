package com.mos.bsd.service;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdVipOpenidService {
	/**
	 * 获取数据
	 * @param type 
	 * @param edate 
	 * @param sdate 
	 * @return
	 */
	BSDResponse getData(String sdate, String edate, int type);
	/**
	 * 保存原始数据进临时表
	 * @param type 1表示增量  2表示初始化数据
	 * @return
	 */
	BSDResponse getBaseData(int type);
	
	/**
	 * 读取openid并保存到x2
	 * @param initialdataEntity 
	 * @return
	 */
	BSDResponse getLoadBaseData(InitialdataEntity initialdataEntity);
	/**
	 * 获取要同步的数据
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();

}
