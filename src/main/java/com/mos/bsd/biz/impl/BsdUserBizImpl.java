package com.mos.bsd.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mos.bsd.biz.IBsdUserBiz;
import com.mos.bsd.utils.HttpPostUtils;
import com.x3.base.core.exception.BusinessException;
@Repository("com.mos.bsd.biz.impl.BsdUserBizImpl")
public class BsdUserBizImpl implements IBsdUserBiz {

	@Autowired
	private Environment env;
	
	@Override
	public List<Map<String, Object>> getData() {
		List<Map<String,Object>> userList = new ArrayList<Map<String,Object>>();
		Map<String,Object> userMap = new HashMap<>();
		try {
			JSONObject json = new JSONObject();
			//查询时间戳
			String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/user/getMemberDimCountByPara";
			HttpPostUtils httpPostUtils = new HttpPostUtils();
			
			//增量查询
			long ts = System.currentTimeMillis();
			json.put("ts", ts);
			
			JSONObject object = new JSONObject();
			//查询出总数
			JSONObject c_jObject = httpPostUtils.postHttp(url, json);
			
			if( c_jObject.getBoolean("success")!=null && !c_jObject.getBoolean("success")) {
				throw new BusinessException("BsdVipBizImpl-01","查询会员总数失败!错误信息:"+c_jObject.getString("errorMessage"));
			}
			
			JSONArray array = c_jObject.getJSONArray("data");
			if( array!=null && array.size()>0 ) {
				for(Object obj :array) {
					object = (JSONObject) obj;
					userMap.put("userNo", object.getString("userNo"));
					userMap.put("userName", object.getString("userName"));
					userMap.put("data_authority", object.getString("data_authority"));
					
					userList.add(userMap);
				}
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return userList;
	}
	
}
