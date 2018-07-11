package com.mos.bsd.dao;

import java.util.List;
import java.util.Map;

public interface IBsdStoreDao {
	/**
	 * 
	 * @param storeList
	 * @return
	 */
	List<Map<String, Object>> findDepartmentId(List<Map<String, Object>> storeList);
	/**
	 * 删除临时表中的记录
	 */
	int clearData();
	/**
	 * 插入记录
	 * @param list
	 * @return
	 */
	int insertData(List<Map<String, Object>> list);

}
