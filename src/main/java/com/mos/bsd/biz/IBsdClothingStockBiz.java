package com.mos.bsd.biz;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdClothingStockBiz {
	/**
	 * 获取单个门店的库存数据
	 * @param valueOf
	 * @param valueOf2
	 * @param uuid 
	 * @return
	 */
	Map<String, List<Map<String, Object>>> getStockData(String valueOf, String valueOf2, String uuid);
	/**
	 * 保存数据到x3_stock表
	 * @param stockList
	 * @return
	 */
	int saveDataToX2(List<Map<String, Object>> stockList);
	/**
	 * 获取库存接口原始数据存进x2
	 * @param department_id
	 * @param department_user_id
	 * @param type 1增量  2全量
	 * @return
	 */
	int getBaseData(String department_id, String department_user_id, int type);
	/**
	 * 查询需要同步的数据
	 * @param type  1增量  2 全量
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData(int type);

}
