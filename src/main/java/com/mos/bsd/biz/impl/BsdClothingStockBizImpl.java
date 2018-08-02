package com.mos.bsd.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mos.bsd.biz.IBsdClothingStockBiz;
import com.mos.bsd.dao.IBsdClothingStockDao;
import com.mos.bsd.dao.IBsdPlanDao;
import com.mos.bsd.dao.IinitialdataDao;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.utils.HttpPostUtils;
@Repository("com.mos.bsd.biz.impl.BsdClothingStockBizImpl")
public class BsdClothingStockBizImpl implements IBsdClothingStockBiz {
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdClothingStockDaoImpl")
	private IBsdClothingStockDao stock_dao;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdClothingStockBizImpl.class);
	
	@Resource(name="com.mos.bsd.dao.impl.InitialdataDaoImpl")
	private IinitialdataDao initDao;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdPlanDaoImpl")
	private IBsdPlanDao planDao;
	
	@Autowired
	private Environment env;
	
	@Override
	public Map<String, List<Map<String, Object>>> getStockData(String department_user_id, String department_id,String uuid) {
		
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, List<Map<String,Object>>> return_map = new HashMap<String, List<Map<String,Object>>>();
		List<Map<String,Object>> errorList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> stockList = new ArrayList<Map<String,Object>>();
		
		Map<String, String> totalMap = new HashMap<String, String>();
//		Map<String, String> detailMap;
		try {
			
			Map<String,Object> stockMap ;
			Map<String,Object> errorMap ;
			
			JSONObject object = null;
			String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/stock/store/getStoreStocksByProperties";
			HttpPostUtils httpPostUtils = new HttpPostUtils();
			JSONObject json = new JSONObject();
			json.put("storeNo", department_user_id);
			
			JSONObject c_jObject = httpPostUtils.postHttp(url, json);
			
			if (c_jObject.getBoolean("success") != null && !c_jObject.getBoolean("success")) {
				logger.debug( c_jObject.getString("errorMessage"));
			}
			
			totalMap.put("interface_name",url );
			totalMap.put("request_data", json.toJSONString());
			totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
			totalMap.put("received_data", c_jObject.toJSONString());
			totalMap.put("uuid",uuid );
			initJson.add(totalMap);
			
			JSONArray array = c_jObject.getJSONArray("data");
			if (array != null && array.size() > 0) {
				for( Object obj :array ) {
					
					object = (JSONObject) obj;
					stockMap = new HashMap<String,Object>();
					errorMap = new HashMap<String,Object>();
					//判断不能为空的参数 1.depot_id 2.clothing_id 3.ava_nums 不能为空  版型默认为0
					String  productNo = String.valueOf(object.get("productNo"));
					String  colorNo = String.valueOf(object.get("colorNo"));
					String  sizeNo = String.valueOf(object.get("sizeNo"));
					String  ava_nums = String.valueOf(object.get("availableNum"));
					
					if( productNo==null||"".equals(productNo)||"null".equals(productNo) ) {
						errorMap.put("errorData", object);
						logger.debug(object.toJSONString());
						errorList.add(errorMap);
						continue;
					}
					if (colorNo == null || "".equals(colorNo) || "null".equals(colorNo)) {
						errorMap.put("errorData", object);
						logger.debug(object.toJSONString());
						errorList.add(errorMap);
						continue;
					}
					if (sizeNo == null || "".equals(sizeNo) || "null".equals(sizeNo)) {
						errorMap.put("errorData", object);
						logger.debug(object.toJSONString());
						errorList.add(errorMap);
						continue;
					}
					if (ava_nums == null || "".equals(ava_nums) || "null".equals(ava_nums)) {
						errorMap.put("errorData", object);
						logger.debug(object.toJSONString());
						errorList.add(errorMap);
						continue;
					}
					
					String clothing_id = productNo+colorNo+sizeNo+"0";
					
					stockMap.put("depot_id", department_id);
					stockMap.put("clothing_id", clothing_id);
					
					stockMap.put("now_nums", String.valueOf(object.get("inventoryNum")).equals("null")?"0":object.get("inventoryNum"));
					stockMap.put("setway_nums", String.valueOf(object.get("deliveryOnwayNum")).equals("null")?"0":object.get("deliveryOnwayNum"));
					stockMap.put("getway_nums", String.valueOf(object.get("allocateOnwayNum")).equals("null")?"0":object.get("allocateOnwayNum"));
					stockMap.put("quota_nums", String.valueOf(object.get("lockedNum")).equals("null")?"0":object.get("lockedNum"));
					stockMap.put("ava_nums", object.get("availableNum"));
					
					stockList.add(stockMap);
				}
			}
			
			return_map.put("list", stockList);
			return_map.put("error", errorList);
			
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		initDao.insertData(initJson);
		return return_map;
		
	}

	@Override
	public int saveDataToX2(List<Map<String, Object>> stockList) {
		return stock_dao.saveDataToX2(stockList);
	}

	@Override
	public int getBaseData(String department_id, String department_user_id, int type) {
		
		String key = "ClothingStockInterface";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cuuid = UUID.randomUUID().toString();
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		
		long ts = System.currentTimeMillis();
		String url = "";
		JSONObject json = new JSONObject();
		if( type == 1) {
			url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/stock/getStoreStocksByTs";
			List<Map<String, Object>> list = planDao.getTampData(key);
			if( list==null||list.size()<=0 ) {
				json.put("ts", sdf.format(new Date(ts)));
			}else {
				json.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("TIME_STAMP"))))));
			}
		}else {
			url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/stock/store/getStoreStocksByProperties";
		}
		
		HttpPostUtils httpPostUtils = new HttpPostUtils();
		json.put("storeNo", department_user_id);
		
		JSONObject c_jObject = httpPostUtils.postHttp(url, json);
		
		//解析json,如果是失败的记录，则直接保存状态为1,不需要读取
		c_jObject.put("department_id", department_id);
		if( c_jObject.containsKey("success")&&!c_jObject.getBoolean("success") ) {
			totalMap.put("status","1" );
		}
		
		totalMap.put("interface_name",url );
		totalMap.put("request_data", json.toJSONString());
		totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
		totalMap.put("received_data", c_jObject.toJSONString());
		totalMap.put("uuid",UUID.randomUUID().toString() );
		totalMap.put("cuuid",cuuid );
		totalMap.put("department_user_id",department_user_id );
		initJson.add(totalMap);

		int count = 0;
		if( initJson!=null && initJson.size()>0 ) {
			count = initDao.insertData(initJson);
		}
		
		return count;
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData(int type) {
		
		String url = "";
		if( type==1 ) {
			url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/stock/getStoreStocksByTs";
		}else{
			url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/stock/store/getStoreStocksByProperties";
		}
		
		return initDao.getInitData("0", url);
		
	}

}
