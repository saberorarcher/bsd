package com.mos.bsd.service;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdSaleOrderService {
	/**
	 * 获取数据
	 * @param type 
	 * @return
	 */
	BSDResponse getData(String sdate,String edate, int type);
	/**
	 * 
	 * @param type 1表示增量  2表示初始化数据
	 * @param map 
	 * @return
	 */
	BSDResponse getBaseData(int type, Map<String, Object> map);
	/**
	 * 获取数据  并保存进x2
	 * @param initialdataEntity 
	 * @return
	 */
	BSDResponse getLoadBaseData(InitialdataEntity initialdataEntity);
	/**
	 * 获取要同步的数据
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();
	/**
	 * 获取要同步的店铺
	 * @return
	 */
	List<Map<String, Object>> getStoreList();
	/**
	 * 查询未读取的数据
	 * @return
	 */
	List<Map<String, Object>> getTemData();
	/**
	 * 读取数据，并保存（从临时表）
	 * @param map
	 * @return
	 */
	BSDResponse getLoadTemData(Map<String, Object> map);

}
