package com.mos.bsd.biz.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.mos.bsd.biz.IBsdCommonBiz;
import com.mos.bsd.dao.IBsdCommonDao;

@Repository("com.mos.bsd.biz.impl.BsdCommonBizImpl")
public class BsdCommonBizImpl implements IBsdCommonBiz {

	@Resource(name="com.mos.bsd.dao.impl.BsdCommonDaoImpl")
	private IBsdCommonDao dao;
	
	@Override
	public int saveLog(List<Map<String, String>> list) {
		return dao.saveLog(list);
	}
	
}
