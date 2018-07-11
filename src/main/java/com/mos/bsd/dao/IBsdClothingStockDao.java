package com.mos.bsd.dao;

import java.util.List;
import java.util.Map;

public interface IBsdClothingStockDao {
	
	/**
	 * 保存数据
	 * @param stockList
	 * @return
	 */
	int saveDataToX2(List<Map<String, Object>> stockList);

}
