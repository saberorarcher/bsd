package com.mos.bsd.biz;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdClothingBiz {
	/**
	 * 获取商品档案
	 * @param i 1自增 2设定时间
	 * @param uuid 
	 * @return list
	 */
	Map<String, List<Map<String, Object>>> getClothingData(int i, String uuid);
	
	/**
	 * 获取接口数据
	 * @return
	 */
	List<Map<String, Object>> getData();
	/**
	 * 存入接口临时表
	 * @param list
	 * @return
	 */
	int insertToX2(List<Map<String, Object>> list);
	/**
	 * 获取原始数据
	 * @param type
	 * @return
	 */
	int getClothingBaseData(int type);
	/**
	 * 从数据库中取得原始数据
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();
	/**
	 * 修改已经读取的数据状态
	 * @param string
	 * @param i
	 * @return
	 */
	int updateData(String string, int i);

}
