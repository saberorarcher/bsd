package com.mos.bsd.biz;

import java.util.List;
import java.util.Map;

public interface IBsdCommonBiz {
	/**
	 * 写入通用日志
	 * @param list
	 * @return
	 */
	int saveLog(List<Map<String, String>> list);
}
