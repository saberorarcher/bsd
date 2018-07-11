package com.mos.bsd.dao;

import java.util.List;
import java.util.Map;

public interface IBsdClothingDao {
	/**
	 * 保存数据到x2临时表
	 * @param list
	 * @return
	 */
	int insertToX2(List<Map<String, Object>> list);

}
