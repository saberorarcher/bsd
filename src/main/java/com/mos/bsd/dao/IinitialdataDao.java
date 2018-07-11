package com.mos.bsd.dao;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.InitialdataEntity;

public interface IinitialdataDao {
	/**
	 * 保存原始数据
	 */
	int insertData(List<Map<String, String>> initJson);
	
	/**
	 * 读取原始数据
	 */
	List <Map<String, InitialdataEntity>> getInitData(String status,String url);
	/**
	 * 修改原始数据读取状态
	 * @param uuid
	 * @param i
	 * @return
	 */
	int updateData(String uuid, int i);
	
}
