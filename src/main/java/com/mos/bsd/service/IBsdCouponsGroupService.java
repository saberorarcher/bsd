package com.mos.bsd.service;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdCouponsGroupService {
	/**
	 * 获取券组档案
	 * @param type 
	 * @return
	 */
	BSDResponse getData(String begdate,String enddate, int type);
	/**
	 * 
	 * @param type 1表示增量  2表示初始化数据
	 * @return
	 */
	BSDResponse getBaseData(int type);
	/**
	 * 读取未同步的原始数据
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
