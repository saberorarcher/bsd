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
import com.mos.bsd.biz.IBsdCouponsGroupBiz;
import com.mos.bsd.dao.IBsdCouponsGroupDao;
import com.mos.bsd.dao.IBsdPlanDao;
import com.mos.bsd.dao.IinitialdataDao;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.utils.HttpPostUtils;
import com.x3.base.core.exception.BusinessException;

@Repository("com.mos.bsd.biz.impl.BsdCouponsGroupBizImpl")
public class BsdCouponsGroupBizImpl implements IBsdCouponsGroupBiz {
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdCouponsGroupDaoImpl")
	private IBsdCouponsGroupDao couponsGroupDao;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdPlanDaoImpl")
	private IBsdPlanDao planDao;
	
	@Resource(name="com.mos.bsd.dao.impl.InitialdataDaoImpl")
	private IinitialdataDao initDao;
	
	@Autowired
	private Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdCouponsGroupBizImpl.class);
	
	@Override
	public Map<String,List<Map<String, Object>>> getcouponsGroupData( String begdate,String enddate,int x,String uuid ) {
		String key = "VipCouponsGroupInterface";
		//限制条件 日期区间跨度限制:30天,TS距当前时间限制:3天,分页单页条数限制:1000
		Map<String,List<Map<String, Object>>> returnMap= new HashMap<String,List<Map<String, Object>>>();
		long ts = System.currentTimeMillis();
		
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		Map<String, String> detailMap;
		
		List<Map<String,Object>> couponsGroupList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> errorGroupList = new ArrayList<Map<String,Object>>();
		Map<String,Object> couponsGroupMap ;
		Map<String,Object> errorGroupMap ;
		try {
			JSONObject json = new JSONObject();
			String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getCouponsGroupCountByPara";
			String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getPageCouponsGroupByPara";
			HttpPostUtils httpPostUtils = new HttpPostUtils();	
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String beginString ="";
			String endString="";
			
			//获取时间戳
			if( x==1 ) {
				List<Map<String, Object>> list = planDao.getTampData(key);
				if( list==null||list.size()<=0 ) {
					json.put("ts", sdf.format(new Date(ts)));
				}else {
					json.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("TIME_STAMP"))))));
				}
			}else {
				
				beginString = begdate+" 00:00:00";
				endString = enddate+" 00:00:00";
				
				//转换成时间戳
				json.put("beginCreateDate", beginString);
				json.put("endCreateDate", endString);
				
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
				throw new BusinessException("BsdCouponsGroupBizImpl-01","查询券总数失败!错误信息:"+c_jObject.getString("errorMessage"));
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
					throw new BusinessException("BsdCouponsGroupBizImpl-01", jo.getString("errorMessage"));
				}
				//转换数据
				JSONArray array = jo.getJSONArray("data");
				if( array!=null && array.size()>0 ) {
					for(Object obj :array) {
						object = (JSONObject) obj;
						couponsGroupMap = new HashMap<String,Object>();
						
						//判断不能为空的数据
						String coupontype_id = String.valueOf(object.get("couponsGroupNo"));
						if( coupontype_id==null||coupontype_id.equals("")||coupontype_id.equals("null") ) {
							errorGroupMap = new HashMap<String,Object>();
							errorGroupMap.put("errorData", object);
							errorGroupList.add(errorGroupMap);
							continue;
						}
						
						String couponsGroupName = String.valueOf(object.get("couponsGroupName"));
						if( couponsGroupName==null||couponsGroupName.equals("")||couponsGroupName.equals("null") ) {
							errorGroupMap = new HashMap<String,Object>();
							errorGroupMap.put("errorData", object);
							errorGroupList.add(errorGroupMap);
							continue;
						}
						
						
						couponsGroupMap.put("coupontype_id", object.get("couponsGroupNo"));
						couponsGroupMap.put("coupontype_name", object.get("couponsGroupName"));
						couponsGroupMap.put("coupontype_sort", object.get("couponsType"));
						couponsGroupMap.put("coupon_valid_begdate", object.get("beginDate"));
						couponsGroupMap.put("coupon_valid_enddate", object.get("endDate"));
						couponsGroupMap.put("coupon_value", object.get("couponsAmount"));
						couponsGroupMap.put("couponsDiscount", object.get("couponsDiscount"));
						couponsGroupMap.put("birthdayCouponsDiscount", object.get("birthdayCouponsDiscount"));
						couponsGroupMap.put("minDiscount", String.valueOf(object.get("minDiscount")).equals("null")?"0":object.get("minDiscount"));
						couponsGroupMap.put("minConsumption", String.valueOf(object.get("minConsumption")).equals("null")?"0":object.get("minConsumption"));
						couponsGroupMap.put("maxDiscountAmount", String.valueOf(object.get("maxDiscountAmount")).equals("null")?"0":object.get("maxDiscountAmount"));
						couponsGroupMap.put("multipleFlag", object.get("multipleFlag"));
						couponsGroupMap.put("promotionFlag", object.get("promotionFlag"));
						couponsGroupMap.put("discountType", String.valueOf(object.get("discountType")).equals("null")?"1":object.get("discountType"));
						couponsGroupMap.put("smsVerify", object.get("smsVerify"));
						couponsGroupMap.put("coupontype_state", String.valueOf(object.get("validFlag")).equals("Y")?"1":"-1");
						couponsGroupMap.put("remark", object.get("remark"));
						couponsGroupMap.put("innerPurchaseFlag", object.get("innerPurchaseFlag"));
						couponsGroupMap.put("registerSendFlag", object.get("registerSendFlag"));
						couponsGroupMap.put("relativeWechatCard", object.get("relativeWechatCard"));
						couponsGroupMap.put("markBirthday", object.get("markBirthday"));
						couponsGroupMap.put("useFlag", String.valueOf(object.get("useFlag")).equals("null")?"0":object.get("useFlag"));
						
						couponsGroupList.add(couponsGroupMap);
					}
				}
			}
			
			//获取完总数之后,更新时间戳
			planDao.updateTamp(key, String.valueOf(ts));
			returnMap.put("list", couponsGroupList);
			returnMap.put("error", errorGroupList);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		//保存原始数据
		initDao.insertData(initJson);
		return returnMap;
	}

	@Override
	public int mergeIntoX2(List<Map<String, Object>> couponsGroup_list) {
		int count = couponsGroupDao.mergeIntoD0230(couponsGroup_list);
		count = couponsGroupDao.mergeIntoD0239(couponsGroup_list);
		count = couponsGroupDao.mergeIntoD0230Extend(couponsGroup_list);
		return count;
	}

	@Override
	public int getBaseData(String sdate, String edate, int type) {
		
		String cuuid = UUID.randomUUID().toString();
		String key = "VipCouponsGroupInterface";
		//限制条件 日期区间跨度限制:30天,TS距当前时间限制:3天,分页单页条数限制:1000
		long ts = System.currentTimeMillis();
		
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		Map<String, String> detailMap;
		
		JSONObject json = new JSONObject();
		String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getCouponsGroupCountByPara";
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getPageCouponsGroupByPara";
		HttpPostUtils httpPostUtils = new HttpPostUtils();	
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String beginString ="";
		String endString="";
		
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
			
			//转换成时间戳
			json.put("beginCreateDate", beginString);
			json.put("endCreateDate", endString);
			
		}

		JSONObject c_jObject = httpPostUtils.postHttp(url, json);
		
		totalMap.put("interface_name",url );
		totalMap.put("request_data", json.toJSONString());
		totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
		totalMap.put("received_data", c_jObject.toJSONString());
		totalMap.put("uuid",cuuid );
		totalMap.put("cuuid",cuuid );
		initJson.add(totalMap);
		
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
			detailMap.put("uuid",UUID.randomUUID().toString() );
			detailMap.put("cuuid", cuuid);
			initJson.add(detailMap);
		}
		
		planDao.updateTamp(key, String.valueOf(ts));
		int count = 0;
		if( initJson!=null && initJson.size()>0 ) {
			count = initDao.insertData(initJson);
		}
		
		return count ;
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getPageCouponsGroupByPara";
		
		return initDao.getInitData("0", url1);
		
	}
	
}
