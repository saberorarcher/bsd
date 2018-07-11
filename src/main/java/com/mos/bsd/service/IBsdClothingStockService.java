package com.mos.bsd.service;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdClothingStockService {
	/**
	 * 获取数据
	 * @param type 
	 * @return
	 */
	BSDResponse getData(int type);
	/**
	 * 获取本地数据
	 * @param type 1表示增量  2表示初始化数据
	 * @param map 
	 * @return
	 */
	BSDResponse getBaseData(int type, Map<String, Object> map);
	
	/**
	 * 获取原始数据
	 * @param type 1增量  2全量
	 * @param initialdataEntity 
	 * @return
	 */
	BSDResponse getLoadBaseData(int type, InitialdataEntity initialdataEntity);
	/**
	 * 获取要同步的数据
	 * @param type
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData(int type);
	/**
	 * 获取要同步的数据
	 * @return
	 */
	List<Map<String, Object>> getStoreList();

}
