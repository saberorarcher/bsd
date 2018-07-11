package com.mos.bsd.service;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdVipService {

	BSDResponse getData(String sdate,String edate, int type);
	/**
	 * 获取并保存 原始数据
	 * @param type 1表示增量  2表示初始化数据时使用
	 * @return
	 */
	BSDResponse getBaseData(int type);
	
	void name();
	
	/**
	 * 读取原始文件并保存
	 * @param entity 
	 * @return
	 */
	BSDResponse getLoadData(InitialdataEntity entity);
	/**
	 * 获取数据
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();
	/**
	 * 查询错误数据
	 * @param entity
	 * @return
	 */
	BSDResponse getWrongData(InitialdataEntity entity);
}
