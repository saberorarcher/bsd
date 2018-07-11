package com.mos.bsd.dao;

import java.util.List;
import java.util.Map;

public interface IBsdPlanDao {
	/**
	 * 查询时间戳
	 * @param key
	 * @return
	 */
	List<Map<String, Object>> getTampData(String key);
	/**
	 * 更新时间戳
	 * @param key
	 * @param tamp
	 * @return
	 */
	int updateTamp(String key,String tamp);
}
