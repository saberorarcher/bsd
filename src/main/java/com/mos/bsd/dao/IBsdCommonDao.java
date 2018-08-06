package com.mos.bsd.dao;

import java.util.List;
import java.util.Map;

public interface IBsdCommonDao {
	/**
	 * 保存访问日志
	 * @param list
	 * @return
	 */
	int saveLog(List<Map<String, String>> list);

}
