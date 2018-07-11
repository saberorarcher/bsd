package com.mos.bsd.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mos.bsd.biz.IBsdTempStoreBiz;
import com.mos.bsd.dao.IBsdTempStoreDao;
import com.mos.bsd.utils.HttpPostUtils;
import com.x3.base.core.exception.BusinessException;

@Repository("com.mos.bsd.biz.impl.BsdTempStoreBizImpl")
public class BsdTempStoreBizImpl implements IBsdTempStoreBiz {

	@Autowired
	private Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdTempStoreBizImpl.class);
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdTempStoreDaoImpl")
	private IBsdTempStoreDao dao;
	
	@Override
	public List<Map<String, Object>> getData() {
		
		List<Map<String,Object>> storeList = new ArrayList<Map<String,Object>>();
		HttpPostUtils httpPostUtils = new HttpPostUtils();
		String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/store/getChangedStoreByTs";//http地址
		Map<String,Object> storeMap;
		JSONObject object;
		JSONObject condition = new JSONObject();
//		long ts = System.currentTimeMillis();
		condition.put("ts", "2016-03-31 00:00:00");
		
		try {
			JSONObject jo = httpPostUtils.postHttp(url, condition);

			boolean success = jo.getBoolean("success");
			if (!success) {
				throw new BusinessException("BsdStoreBizImpl-01", "获取数据失败,错误信息:" + jo.getString("errorMessage"));
			}

			JSONArray array = jo.getJSONArray("data");
			if (array != null && array.size() > 0) {
				for (Object obj : array) {
					object = (JSONObject) obj;
					storeMap = new HashMap<>();
					
					storeMap.put("storeNo", object.get("storeNo"));
					storeMap.put("storeName", object.get("storeName"));
					storeMap.put("customerNo", object.get("customerNo"));
					storeMap.put("customerName", object.get("customerName"));
					storeMap.put("corpNo", object.get("corpNo"));
					storeMap.put("corpName", object.get("corpName"));
					storeMap.put("areaNo", object.get("areaNo"));
					storeMap.put("areaName", object.get("areaName"));
					storeMap.put("storeNatureId", object.get("storeNatureId"));
					storeMap.put("storeForm", object.get("storeForm"));
					storeMap.put("channelType", object.get("channelType"));
					storeMap.put("corpIsAgent", object.get("corpIsAgent"));
					storeMap.put("brandNo", object.get("brandNo"));
					
					storeList.add(storeMap);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return storeList;
	}

	@Override
	public int insertTemp(List<Map<String, Object>> list) {
		return dao.insertTemp(list);
	}

}
