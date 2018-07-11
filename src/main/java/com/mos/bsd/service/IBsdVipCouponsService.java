package com.mos.bsd.service;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdVipCouponsService {
	/**
	 * 获取vip券
	 * @param type 
	 * @return
	 */
	BSDResponse getData(String sdate,String edate, int type);
	/**
	 * 
	 * @param type 1表示增量  2表示初始化数据
	 * @return
	 */
	BSDResponse getBaseData(int type);
	/**
	 * 读取原始数据,并写入x2
	 * @param initialdataEntity 
	 * @return
	 */
	BSDResponse getLoadData(InitialdataEntity initialdataEntity);
	/**
	 * 获取需要同步的数据
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();

}
