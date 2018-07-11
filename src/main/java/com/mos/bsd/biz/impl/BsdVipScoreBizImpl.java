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
import com.mos.bsd.biz.IBsdVipScoreBiz;
import com.mos.bsd.dao.IBsdPlanDao;
import com.mos.bsd.dao.IBsdVipScoreDao;
import com.mos.bsd.dao.IinitialdataDao;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.utils.HttpPostUtils;
import com.x3.base.core.exception.BusinessException;

@Repository("com.mos.bsd.biz.impl.BsdVipScoreBizImpl")
public class BsdVipScoreBizImpl implements IBsdVipScoreBiz {

	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdVipScoreDaoImpl")
	private IBsdVipScoreDao dao;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdPlanDaoImpl")
	private IBsdPlanDao planDao;
	
	@Autowired
	private Environment env;
	
	@Resource(name="com.mos.bsd.dao.impl.InitialdataDaoImpl")
	private IinitialdataDao initDao;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdVipScoreBizImpl.class);
	
	@Override
	public Map<String,List<Map<String, Object>>> getVipScoreData(String sdate,String edate,int x,String uuid) {
		String key = "VipScoreInterface";
		Map<String, List<Map<String,Object>>> return_map = new HashMap<String, List<Map<String,Object>>>();
		List<Map<String,Object>> vipScoreList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> errorList = new ArrayList<Map<String,Object>>();
		Map<String,Object> vipScoreMap ;
		Map<String,Object> errorMap ;
		
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		Map<String, String> detailMap;
		
		long ts = System.currentTimeMillis();
		try {

			// 调用接口 获取数据  (日期区间跨度限制30天,TS距当前时间限制3天,分页单页条数限制10000)
			JSONObject object = null;
			
			JSONObject json = new JSONObject();
			
			String beginString ="";
			String endString="";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			//获取时间戳
			if( x==1 ) {
				List<Map<String, Object>> list = planDao.getTampData(key);
				if( list==null||list.size()<=0 ) {
					json.put("ts", sdf.format(new Date(ts)));
				}else {
					json.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("TIME_STAMP"))))));
				}
			}else {
				
				beginString = sdate+" 00:00:00";
				endString = edate+" 00:00:00";
				
				json.put("beginTs", beginString);
				json.put("endTs", endString);
				
			}
			
			String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getMemberPointCountByPara";
			String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getPageMemberPointByPara";
			
			HttpPostUtils httpPostUtils = new HttpPostUtils();
		
			// 调用web service提供的方法
			JSONObject count_json = httpPostUtils.postHttp(url, json);
			
			totalMap.put("interface_name",url );
			totalMap.put("request_data", json.toJSONString());
			totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
			totalMap.put("received_data", count_json.toJSONString());
			totalMap.put("uuid", uuid);
			initJson.add(totalMap);
			
			if(!count_json.getBoolean("success")) {
				throw new BusinessException("BsdVipScoreBizImpl-01","查询积分总数失败!错误信息:"+count_json.getString("errorMessage"));
			}
			
			int count = Integer.parseInt(count_json.getString("data"));
			//计算需要循环的次数
			int num = count/10000;
			num = count%10000>0?num+1:num;
			
			for( int i=0;i<num;i++ ) {
//				String result = service.getPageMemberPointByPara(qry_condition, (num-1)*10000, num*10000);
				json.put("offset", i*10000);
				json.put("limit", 10000);
				
				JSONObject jo = httpPostUtils.postHttp(url1, json);
				
				detailMap = new HashMap<>();
				detailMap.put("interface_name",url1 );
				detailMap.put("request_data", json.toJSONString());
				detailMap.put("create_date", String.valueOf(System.currentTimeMillis()));
				detailMap.put("received_data", jo.toJSONString());
				detailMap.put("uuid", uuid);
				initJson.add(detailMap);
				
				boolean success = jo.getBoolean("success");
				if (!success) {
					throw new BusinessException("BsdClothingBizImpl-02", jo.getString("errorMessage"));
				}
				
				JSONArray array = jo.getJSONArray("data");
				if( array!=null && array.size()>0 ) {
					for(Object obj :array) {
						object = (JSONObject) obj;
						vipScoreMap = new HashMap<>();
						errorMap = new HashMap<>();
						
						//判断非空的参数
						String card_id = String.valueOf(object.get("memberId"));
						if(card_id==null || "".equals(card_id)||"null".equals(card_id)) {
							errorMap.put("errorData", object);
							errorList.add(errorMap);
							logger.debug(object.toJSONString());
							continue;
						}
						
						vipScoreMap.put("card_id", object.get("memberId"));
						vipScoreMap.put("department_id", object.get("storeNo"));
						vipScoreMap.put("vip_retail_cnt", String.valueOf(object.get("totalTimes")).equals("null")?"0":object.get("totalTimes"));
						vipScoreMap.put("totalNum", String.valueOf(object.get("totalNum")).equals("null")?"0":object.get("totalNum"));
						vipScoreMap.put("vip_retail_sums", String.valueOf(object.get("totalAmount")).equals("null")?"0":object.get("totalAmount"));
						vipScoreMap.put("vip_retail_score", String.valueOf(object.get("totalPoint")).equals("null")?"0":object.get("totalPoint"));
						vipScoreMap.put("usedPoint", String.valueOf(object.get("usedPoint")).equals("null")?"0":object.get("usedPoint"));
						vipScoreMap.put("adjustPoint", String.valueOf(object.get("adjustPoint")).equals("null")?"0":object.get("adjustPoint"));
						vipScoreMap.put("vip_score", String.valueOf(object.get("canusePoint")).equals("null")?"0":object.get("canusePoint"));
						vipScoreMap.put("pointAbnormalTimes", String.valueOf(object.get("pointAbnormalTimes")).equals("null")?"0":object.get("pointAbnormalTimes"));
						vipScoreMap.put("vip_lastbuy_date", String.valueOf(object.get("lastDate")).equals("null")?"0":object.get("lastDate"));
						vipScoreMap.put("lastNum", String.valueOf(object.get("lastNum")).equals("null")?"0":object.get("lastNum"));
						vipScoreMap.put("lastAmount", String.valueOf(object.get("lastAmount")).equals("null")?"0":object.get("lastAmount"));
						
						vipScoreList.add(vipScoreMap);
					}
				}
			}
			//获取完总数之后,更新时间戳
			planDao.updateTamp(key, String.valueOf(ts));
			return_map.put("list", vipScoreList);
			return_map.put("error", errorList);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		
		//保存原始数据
		initDao.insertData(initJson);
		return return_map;
	}

	@Override
	public int updateVipScore(List<Map<String, Object>> vipScore_list) {
		return dao.updateVipScore(vipScore_list);
	}

	@Override
	public int updateVipScoreExtend(List<Map<String, Object>> vipScore_list) {
		return dao.updateVipScoreExtend(vipScore_list);
	}

	@Override
	public int getVipBaseData(String sdate, String edate, int type) {
		String key = "VipScoreInterface";
		String cuuid = UUID.randomUUID().toString();
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		Map<String, String> detailMap;
		
		JSONObject json = new JSONObject();
		
		String beginString ="";
		String endString="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long ts = System.currentTimeMillis();
		
		//获取时间戳
		if( type==1 ) {
			List<Map<String, Object>> list = planDao.getTampData(key);
			if( list==null||list.size()<=0 ) {
				json.put("ts", sdf.format(new Date(ts)));
			}else {
				json.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("time_stamp"))))));
			}
		}else {
			
			beginString = sdate+" 00:00:00";
			endString = edate+" 00:00:00";
			
			json.put("beginTs", beginString);
			json.put("endTs", endString);
			
		}
		
		String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getMemberPointCountByPara";
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getPageMemberPointByPara";
		
		HttpPostUtils httpPostUtils = new HttpPostUtils();
		JSONObject count_json = httpPostUtils.postHttp(url, json);
		
		totalMap.put("interface_name",url );
		totalMap.put("request_data", json.toJSONString());
		totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
		totalMap.put("received_data", count_json.toJSONString());
		totalMap.put("uuid", cuuid);
		totalMap.put("cuuid", cuuid);
		initJson.add(totalMap);
		
		int count = Integer.parseInt(count_json.getString("data"));
		logger.info(String.valueOf(count));
		//计算需要循环的次数
		int num = count%10000>0?count/10000+1:count/10000;
		
		for( int i=0;i<num;i++ ) {
			json.put("offset", i*10000);
			json.put("limit", 10000);
			
			JSONObject jo = httpPostUtils.postHttp(url1, json);
			
			boolean success = jo.getBoolean("success");
			
			logger.info(String.valueOf(success));
			
			detailMap = new HashMap<>();
			detailMap.put("interface_name",url1 );
			detailMap.put("request_data", json.toJSONString());
			detailMap.put("create_date", String.valueOf(System.currentTimeMillis()));
			detailMap.put("received_data", jo.toJSONString());
			detailMap.put("uuid", UUID.randomUUID().toString());
			detailMap.put("cuuid", cuuid);
			initJson.add(detailMap);
		}
		planDao.updateTamp(key, String.valueOf(ts));
		//保存原始数据
		int count1 = 0;
		if( initJson!=null && initJson.size()>0 ) {
			count1 = initDao.insertData(initJson);
		}
		
		return count1;
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getPageMemberPointByPara";
		
		return initDao.getInitData("0", url1);
	}

}
