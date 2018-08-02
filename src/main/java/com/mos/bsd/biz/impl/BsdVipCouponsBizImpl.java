package com.mos.bsd.biz.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.mos.bsd.biz.IBsdVipCouponsBiz;
import com.mos.bsd.dao.IBsdPlanDao;
import com.mos.bsd.dao.IBsdSaleOrderDao;
import com.mos.bsd.dao.IBsdVipCouponsDao;
import com.mos.bsd.dao.IinitialdataDao;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.utils.HttpPostUtils;
import com.x3.base.core.exception.BusinessException;
@Repository("com.mos.bsd.biz.impl.BsdVipCouponsBizImpl")
public class BsdVipCouponsBizImpl implements IBsdVipCouponsBiz {

	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdSaleOrderDaoImpl")
	private IBsdSaleOrderDao sale_dao;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdVipCouponsDaoImpl")
	private IBsdVipCouponsDao vipCouponsDao;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdPlanDaoImpl")
	private IBsdPlanDao planDao;
	
	@Resource(name="com.mos.bsd.dao.impl.InitialdataDaoImpl")
	private IinitialdataDao initDao;
	
	@Autowired
	private Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdVipCouponsBizImpl.class);
	
	@Override
	public Map<String, List<Map<String,Object>>> getcouponsGroupData(String sdate,String edate,int x,String uuid) {
		String key = "VipCouponsInterface";
		//查询条件限制  日期区间跨度限制:30天 TS距当前时间限制:3天 分页单页条数限制:1000
		List<Map<String,Object>> couponsList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> errorList = new ArrayList<Map<String,Object>>();
		Map<String, List<Map<String,Object>>> retunMap = new HashMap<String, List<Map<String,Object>>>();
		long ts = System.currentTimeMillis();
		
		Map<String,Object> couponsMap ;
		Map<String,Object> errorMap ;
		
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		Map<String, String> detailMap;
		
		try {
			
			JSONObject json = new JSONObject();
			String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getMemberCouponsCountByPara";
			String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getPageMemberCouponsByPara";
			HttpPostUtils httpPostUtils = new HttpPostUtils();
					
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
				
				json.put("beginCouponsDate", beginString);
				json.put("endCouponsDate", endString);
				
			}
			
			JSONObject c_jObject = httpPostUtils.postHttp(url, json);
			JSONObject object = null;
			
			totalMap.put("interface_name",url );
			totalMap.put("request_data", json.toJSONString());
			totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
			totalMap.put("received_data", c_jObject.toJSONString());
			totalMap.put("uuid",uuid );
			initJson.add(totalMap);
			
			if( c_jObject.getBoolean("success")!=null && !c_jObject.getBoolean("success")) {
				throw new BusinessException("BsdVipCouponsBizImpl-01","查询券总数失败!错误信息:"+c_jObject.getString("errorMessage"));
			}
			
			//计算循环次数
			int number = Integer.parseInt(c_jObject.getString("data"));
			int num = number%1000>0?number/1000+1:number/1000;
			
			for( int i=0;i<num;i++ ) {
				
				json.put("offset", i*1000);
				json.put("limit", 1000);

				JSONObject jo = httpPostUtils.postHttp(url1, json);
				
				detailMap = new HashMap<>();
				detailMap.put("interface_name",url1 );
				detailMap.put("request_data", json.toJSONString());
				detailMap.put("create_date", String.valueOf(System.currentTimeMillis()));
				detailMap.put("received_data", jo.toJSONString());
				detailMap.put("uuid",uuid );
				initJson.add(detailMap);
				
				boolean success = jo.getBoolean("success");
				if (!success) {
					throw new BusinessException("BsdVipCouponsBizImpl-02", jo.getString("errorMessage"));
				}
				//转换数据
				JSONArray array = jo.getJSONArray("data");
				if( array!=null && array.size()>0 ) {
					for(Object obj :array) {
						object = (JSONObject) obj;
						couponsMap = new HashMap<>();
						
						//判断不能为空的字段1.coupon_code 2.coupontype_id 3,card_id
						String coupontype_id = String.valueOf(object.get("couponsGroupNo"));
						String coupon_code = String.valueOf(object.get("couponsNo"));
						String card_id = String.valueOf(object.get("memberId"));
						
						if( coupontype_id==null||coupontype_id.equals("")||coupontype_id.equals("null")) {
							errorMap = new HashMap<String,Object>();
							errorMap.put("error", object);
							errorList.add(errorMap);
							logger.debug(object.toJSONString());
							continue;
						}
						
						if( coupon_code==null||coupon_code.equals("")||coupon_code.equals("null")) {
							errorMap = new HashMap<String,Object>();
							errorMap.put("error", object);
							errorList.add(errorMap);
							logger.debug(object.toJSONString());
							continue;
						}
						
						if( card_id==null||card_id.equals("")||card_id.equals("null")) {
							errorMap = new HashMap<String,Object>();
							errorMap.put("error", object);
							errorList.add(errorMap);
							logger.debug(object.toJSONString());
							continue;
						}
						
						couponsMap.put("coupontype_id", object.get("couponsGroupNo"));
//						couponsMap.put("coupontype_id", "Q00001037");
						couponsMap.put("coupon_issue_date", String.valueOf(object.get("couponsDate")).equals("null")?"0":object.get("couponsDate"));
						
						couponsMap.put("coupon_code", object.get("couponsNo"));
						couponsMap.put("couponsSource", object.get("couponsSource"));
						couponsMap.put("scheme_id", String.valueOf(object.get("relativeNo")).equals("null")?"0":object.get("relativeNo"));
						couponsMap.put("card_id", object.get("memberId"));
						couponsMap.put("wechatCardFlag", object.get("wechatCardFlag"));
						couponsMap.put("coupon_used", String.valueOf(object.get("useState")).equals("Y")?"1":"0");
						couponsMap.put("coupon_used_date", String.valueOf(object.get("useDate")).equals("null")?"0":object.get("useDate"));
						couponsMap.put("useStore", String.valueOf(object.get("useStore")).equals("")?"0":object.get("useStore"));
						couponsMap.put("bill_id", String.valueOf(object.get("orderNo")).equals("null")?"0":object.get("orderNo"));
						couponsMap.put("coupon_state", String.valueOf(object.get("validFlag")).equals("Y")?"1":"0");
						
						couponsList.add(couponsMap);
					}
				}
			}
			//获取完总数之后,更新时间戳
			planDao.updateTamp(key, String.valueOf(ts));
			retunMap.put("list", couponsList);
			retunMap.put("error", errorList);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		
		//保存原始数据
		initDao.insertData(initJson);
		return retunMap;
	}

	@Override
	public Map<String,List<Map<String, Object>>> mergeToX2(List<Map<String, Object>> coupons_list) {
		
		Map<String,List<Map<String, Object>>> returnMap = new HashMap<String,List<Map<String, Object>>>();
		
		List <Map<String, Object>> errorDataList = new ArrayList<Map<String, Object>>();
		
		if( coupons_list!=null && coupons_list.size()>0 ) {
			//根据card_id,查询vip_id
			List<Map<String, Object>> vip_list = vipCouponsDao.getVipList(coupons_list);
			
			//循环,取出vip_id
			for( Map<String, Object> change_map:vip_list ) {
				for(Map<String, Object> main_map:coupons_list) {
					if(String.valueOf(main_map.get("card_id")).equals(String.valueOf(change_map.get("CARD_ID")))) {
						main_map.put("vip_id", change_map.get("VIP_ID"));
					}
				}
			}
			//移除
			Iterator<Map<String, Object>> it = coupons_list.iterator();
			while ( it.hasNext() ) {
				Map<String, Object> data = it.next();
				if( !data.containsKey("vip_id") ) {
					errorDataList.add(data);
					logger.error(data.toString());
					it.remove();
				}
			}
			
			//查询department_id
			List<Map<String, Object>> dep_list = vipCouponsDao.getDepList(coupons_list);
			
			//循环,取出记录
			for( Map<String, Object> change_map:dep_list ) {
				for(Map<String, Object> main_map:coupons_list) {
					if(String.valueOf(main_map.get("useStore")).equals(String.valueOf(change_map.get("DEPARTMENT_USER_ID")))) {
						main_map.put("department_id", change_map.get("DEPARTMENT_ID"));
					}else {
						main_map.put("department_id", "0");
					}
				}
			}
			
			//移除
//			Iterator<Map<String, Object>> it1 = coupons_list.iterator();
//			while ( it1.hasNext() ) {
//				Map<String, Object> data = it1.next();
//				if( !data.containsKey("department_id") ) {
//					errorDataList.add(data);
//					logger.debug(data.toString());
//					it1.remove();
//				}
//			}
			
			//取出p0670_id
			List<Map<String, Object>> p0670_list = sale_dao.getP0670_list(coupons_list);
			for( Map<String, Object> change_map:p0670_list ) {
				for(Map<String, Object> main_map:coupons_list) {
					if(String.valueOf(main_map.get("bill_id")).equals(String.valueOf(change_map.get("BILL_ID")))&&!String.valueOf(main_map.get("bill_id")).equals("null")) {
						main_map.put("p0670_id", change_map.get("P0670_ID"));
					}else {
						main_map.put("p0670_id", "0");
					}
				}
			}
			
			//移除
//			Iterator<Map<String, Object>> it2 = coupons_list.iterator();
//			while ( it2.hasNext() ) {
//				Map<String, Object> data = it2.next();
//				if( !data.containsKey("p0670_id") ) {
//					errorDataList.add(data);
//					logger.debug(data.toString());
//					it2.remove();
//				}
//			}
			
			//保存或更新D0240
			vipCouponsDao.mergeD0240(coupons_list);
			
			//更新扩展表
			vipCouponsDao.mergeD0240Extend(coupons_list);
			
			returnMap.put("error", errorDataList);
		}
		return returnMap;
		
	}

	@Override
	public int getBaseData(String sdate, String edate, int type) {
		String key = "VipCouponsInterface";
		String cuuid = UUID.randomUUID().toString();
		//查询条件限制  日期区间跨度限制:30天 TS距当前时间限制:3天 分页单页条数限制:1000
		long ts = System.currentTimeMillis();
		
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		Map<String, String> detailMap;
		
		JSONObject json = new JSONObject();
		String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getMemberCouponsCountByPara";
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getPageMemberCouponsByPara";
		HttpPostUtils httpPostUtils = new HttpPostUtils();
				
		String beginString ="";
		String endString="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//获取时间戳
		if( type==1 ) {
			List<Map<String, Object>> list = planDao.getTampData(key);
			if( list==null||list.size()<=0 ) {
				json.put("ts", sdf.format(new Date(ts)));
			}else {
				json.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("TIME_STAMP"))))));
			}
		}else {
			
			beginString = sdate+" 00:00:00";
			endString = edate+" 00:00:00";
			
			json.put("beginCouponsDate", beginString);
			json.put("endCouponsDate", endString);
			
		}
		
		JSONObject c_jObject = httpPostUtils.postHttp(url, json);
		
		totalMap.put("interface_name",url );
		totalMap.put("request_data", json.toJSONString());
		totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
		totalMap.put("received_data", c_jObject.toJSONString());
		totalMap.put("cuuid",cuuid );
		totalMap.put("uuid",cuuid );
		initJson.add(totalMap);
		
		//计算循环次数
		int number = Integer.parseInt(c_jObject.getString("data"));
		int num = number%1000>0?number/1000+1:number/1000;
		
		logger.info("总数:"+number);
		
		for( int i=0;i<num;i++ ) {
			
			json.put("offset", i*1000);
			json.put("limit", 1000);

			JSONObject jo = httpPostUtils.postHttp(url1, json);
			
			detailMap = new HashMap<>();
			detailMap.put("interface_name",url1 );
			detailMap.put("request_data", json.toJSONString());
			detailMap.put("create_date", String.valueOf(System.currentTimeMillis()));
			detailMap.put("received_data", jo.toJSONString());
			detailMap.put("uuid",UUID.randomUUID().toString());
			detailMap.put("cuuid",cuuid);
			initJson.add(detailMap);
		}
		
		planDao.updateTamp(key, String.valueOf(ts));
		//保存原始数据
		int count = 0;
		if( initJson!=null && initJson.size()>0 ) {
			count = initDao.insertData(initJson);
		}
		
		return count;
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getPageMemberCouponsByPara";
		
		return initDao.getInitData("0", url1);
		
	}

}
