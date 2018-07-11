package com.mos.bsd.biz;

import java.util.List;
import java.util.Map;

public interface IBsdTempStoreBiz {
	/**
	 * 获取数据
	 * @return
	 */
	List<Map<String, Object>> getData();
	/**
	 * 保存进x2临时表
	 * @param list
	 * @return
	 */
	int insertTemp(List<Map<String, Object>> list);

}
