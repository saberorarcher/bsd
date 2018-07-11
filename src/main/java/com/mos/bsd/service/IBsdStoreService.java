package com.mos.bsd.service;


import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdStoreService {
	/**
	 * 获取网点 经销商 公司  大区
	 * @param type 
	 * @return
	 */
	BSDResponse getData(String sdate, int type);
	/**
	 * 获取原始数据
	 * @param sdate
	 * @param type
	 * @return
	 */
	BSDResponse getBaseData(String sdate, int type);
	
	/**
	 * 獲取要同步的數據
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();
	/**
	 * 讀取數據并提交到x2
	 * @param initialdataEntity
	 * @return
	 */
	BSDResponse getLoadBaseData(InitialdataEntity initialdataEntity);

}
