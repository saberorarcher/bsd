package com.mos.bsd.service;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdVipScoreService {

	BSDResponse getData(String sdate, String edate, int type);
	/**
	 * 
	 * @param sdate
	 * @param edate
	 * @param type 1表示增量  2表示
	 * @return
	 */
	BSDResponse getBaseData(int type);
	/**
	 * 读取原始记录 写入到x2
	 * @param initialdataEntity 
	 * @return
	 */
	BSDResponse getLoadData(InitialdataEntity initialdataEntity);
	/**
	 * 查询需要同步的记录
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();

}
