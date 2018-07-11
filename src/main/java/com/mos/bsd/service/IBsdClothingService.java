package com.mos.bsd.service;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;



public interface IBsdClothingService {

	BSDResponse getData(int type);
	/**
	 * 初始化数据
	 * @param type
	 * @return
	 */
	BSDResponse getBaseData(int type);
	/**
	 * 从数据库中取得未同步的原始数据
	 * @return
	 */
	BSDResponse getBaseData(InitialdataEntity entity);
	/**
	 * 获取要同步的数据
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();
	/**
	 * 更新读取数据的状态
	 * @param uuid
	 * @param i
	 */
	void updateData(String uuid, int i);

}
