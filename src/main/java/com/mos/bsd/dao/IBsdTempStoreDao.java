package com.mos.bsd.dao;

import java.util.List;
import java.util.Map;

public interface IBsdTempStoreDao {
	/**
	 * 插入数据到临时表
	 * @param list
	 * @return
	 */
	int insertTemp(List<Map<String, Object>> list);

}
