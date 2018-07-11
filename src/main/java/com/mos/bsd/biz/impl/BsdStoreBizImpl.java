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
import com.mos.bsd.biz.IBsdStoreBiz;
import com.mos.bsd.dao.IBsdPlanDao;
import com.mos.bsd.dao.IBsdStoreDao;
import com.mos.bsd.dao.IinitialdataDao;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.service.IRMIController;
import com.mos.bsd.utils.HttpPostUtils;
import com.mos.bsd.utils.ResponseUtil;
import com.x3.base.core.exception.BusinessException;
import com.x3.base.core.message.RequestMsg;
import com.x3.base.core.message.ResponseMsg;
import com.x3.base.core.util.StringUtil;

@Repository("com.mos.bsd.biz.impl.BsdStoreBizImpl")
public class BsdStoreBizImpl implements IBsdStoreBiz {

	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdStoreDaoImpl")
	private IBsdStoreDao dao;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdPlanDaoImpl")
	private IBsdPlanDao planDao;
	
	@Autowired
	private IRMIController rmiController;
	
	@Autowired
	private Environment env;
	
	@Resource(name="com.mos.bsd.dao.impl.InitialdataDaoImpl")
	private IinitialdataDao initDao;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdStoreBizImpl.class);
	
	@Override
	public Map<String, List<Map<String,Object>>> getStoreData(String sdate, int x,String uuid) {
		
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
//		Map<String, String> detailMap;
		
		String key = "StoreInterface";
		//网点
		List<Map<String,Object>> storeList = new ArrayList<Map<String,Object>>();
		//客户
		List<Map<String,Object>> customerList = new ArrayList<Map<String,Object>>();
		//公司
		List<Map<String,Object>> corpList = new ArrayList<Map<String,Object>>();
		//片区
		List<Map<String,Object>> areaList = new ArrayList<Map<String,Object>>();
		//返回数据
		Map<String, List<Map<String,Object>>> return_map = new HashMap<String, List<Map<String,Object>>>();
		
		Map<String,Object> storeMap;
		Map<String,Object> customerMap;
		Map<String,Object> corpMap;
		Map<String,Object> areaMap;
		
		HttpPostUtils httpPostUtils = new HttpPostUtils();
		
		String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/store/getChangedStoreByTs";//http地址
		
		JSONObject object;
		JSONObject condition = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long ts = System.currentTimeMillis();
		//获取时间戳
		if( x==1 ) {
			List<Map<String, Object>> list = planDao.getTampData(key);
			if( list==null||list.size()<=0 ) {
				condition.put("ts", sdf.format(new Date(ts)));
			}else {
				condition.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("TIME_STAMP"))))));
			}
		}else {
			condition.put("ts", sdate+" 00:00:00");
		}
		
		JSONObject jo = httpPostUtils.postHttp(url, condition);
		
		totalMap.put("interface_name",url );
		totalMap.put("request_data", condition.toJSONString());
		totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
		totalMap.put("received_data", jo.toJSONString());
		totalMap.put("uuid", uuid);
		initJson.add(totalMap);
		
		boolean success = jo.getBoolean("success");
		if( !success ) {
			throw new BusinessException("BsdStoreBizImpl-01","获取数据失败,错误信息:"+jo.getString("errorMessage"));
		}
		
		JSONArray array = jo.getJSONArray("data");
		if( array!=null && array.size()>0 ) {
			for( Object obj:array ) {
				object = (JSONObject) obj;
				
				storeMap = new HashMap<>();
				customerMap = new HashMap<>();
				corpMap = new HashMap<>();
				areaMap = new HashMap<>();
				//店铺
				storeMap.put("depot_code", object.get("storeNo"));
				storeMap.put("depot_name", String.valueOf(object.get("storeName")).length()>25?String.valueOf(object.get("storeName")).substring(0, 25):object.get("storeName"));
				storeMap.put("system_type", "11");
				storeMap.put("depot_type", "1");
				storeMap.put("depot_state", -2);
				storeMap.put("manage_type", String.valueOf(object.get("storeNatureId")).equals("1")?"0":"1");
				storeMap.put("depot_parent_code", object.get("customerNo"));
				storeMap.put("depot_parent_name", "");
				storeMap.put("depot_tel", "!@#$%^&*");
				storeMap.put("depot_mobile", "!@#$%^&*");
				storeMap.put("depot_email", "");
				storeMap.put("depot_fax", "");
				storeMap.put("depot_helpid", "!@#$%^&*");
				storeMap.put("depot_bank", "");
				storeMap.put("depot_level_code", "");
				storeMap.put("depot_level", "!@#$%^&*");
				storeMap.put("depot_zone_code", "");
				storeMap.put("depot_zone", "!@#$%^&*");
				storeMap.put("depot_city_code", "");
				storeMap.put("depot_city", "!@#$%^&*");
				storeMap.put("depot_province", "!@#$%^&*");
				storeMap.put("depot_town", "!@#$%^&*");
				storeMap.put("depot_district", "!@#$%^&*");
				storeMap.put("depot_contact", "!@#$%^&*");
				storeMap.put("depot_address", "!@#$%^&*");
				storeMap.put("depot_area", "!@#$%^&*");
				storeMap.put("depot_remark", "!@#$%^&*");
				storeMap.put("depot_supply_code", "");
				storeMap.put("depot_replenish_code", "");
				storeMap.put("depot_virtual", "");
				storeMap.put("depot_switch", "");
				storeMap.put("account_id", "");
				storeMap.put("depot_opendate", "0001-01-01 00:00:00");
				storeMap.put("operate_state", 0);
				storeMap.put("depot_vmi", 0);
				storeMap.put("depot_brandcode", object.get("brandNo"));
				storeMap.put("depot_brandname", "");
				storeMap.put("esb_logid", 0.0);
				storeMap.put("depot_cablepoint", 0.0);
				storeMap.put("esb_source", "SAP");
				storeMap.put("depot_fullname", "");
				storeMap.put("depot_create_name", "");
				storeMap.put("merchantsub", new JSONArray());
				storeMap.put("currency_code", "!@#$%^&*");
				storeMap.put("depot_manager_name", "!@#$%^&*");
				storeMap.put("depot_manager_tel", "!@#$%^&*");
				storeMap.put("depot_area_manager", "!@#$%^&*");
				storeMap.put("depot_area_tel", "!@#$%^&*");
				storeMap.put("depot_steering", "!@#$%^&*");
				storeMap.put("depot_goods", "!@#$%^&*");
				storeMap.put("depot_belong_area_code", "!@#$%^&*");
				storeMap.put("depot_belong_area", "");
				storeMap.put("depot_decorate_area", -2.0);
				storeMap.put("depot_same", "!@#$%^&*");
				storeMap.put("depot_closedate", "0001-01-01 00:00:00");
				storeMap.put("depot_channel_type_code", "");
				storeMap.put("depot_channel_type", "!@#$%^&*");
				storeMap.put("depot_general_type_code", "!@#$%^&*");
				storeMap.put("depot_general_type", "");
				storeMap.put("depot_costcenter", "!@#$%^&*");
				storeMap.put("depot_payment", "!@#$%^&*");
				storeMap.put("depot_floor", "@#$%^&*");
				storeMap.put("depot_citydefinition", "!@#$%^&*");
				storeMap.put("depot_setchannel", "!@#$%^&*");
				storeMap.put("depot_financeRemark", "@#$%^&*");
				storeMap.put("depot_newid", "!@#$%^&*");
				storeMap.put("depot_clothesleven", "!@#$%^&*");
				storeMap.put("depot_seniorleven", "@#$%^&*");
				storeMap.put("depot_region", "!@#$%^&*");
				storeMap.put("depot_clothespolenum", -2.0);
				storeMap.put("depot_seniorpolenum", -2.0);
				storeMap.put("depot_epclass", "!@#$%^&*");
				storeMap.put("depot_cognate", "!@#$%^&*");
				storeMap.put("depot_EPgoods", -2.0);
				storeMap.put("depot_steering_mobile", "!@#$%^&*");
				storeMap.put("manage_type_id", "0");
				storeMap.put("depot_brand_code", object.get("brandNo"));//!@#$%^&*
				storeMap.put("depot_brand", object.get("brandNo"));
				storeMap.put("Shop_class_code", "!@#$%^&*");
				storeMap.put("Shop_class", "");
				storeMap.put("eas_code", "!@#$%^&*");
				storeMap.put("depot_ConceptShop", "!@#$%^&*");
				storeMap.put("depot_department_code", "!@#$%^&*");
				storeMap.put("depot_department", "");
				storeMap.put("depot_market", "!@#$%^&*");
				storeMap.put("am_account", "!@#$%^&*");
				storeMap.put("loc_x", "!@#$%^&*");
				storeMap.put("loc_y", "!@#$%^&*");
				storeMap.put("department_fin_type", 0);
				storeMap.put("Cooperation_mode", "!@#$%^&*");
				storeMap.put("depot_business_areas", "!@#$%^&*");
				storeMap.put("top_company_code", "");
				storeMap.put("depot_goods_mobile", "!@#$%^&*");
				storeMap.put("department_logistics_code", "!@#$%^&*");
				storeMap.put("depot_virtual_region", "");
				storeMap.put("department_ec_set", -2);
				storeMap.put("depot_update_name", "");

				storeMap.put("department_ext1", object.get("storeForm"));
				storeMap.put("department_ext2", object.get("channelType"));
				storeMap.put("department_ext3", object.get("corpIsAgent"));

				
				//经销商
				customerMap.put("merchant_code", object.get("customerNo"));
				customerMap.put("merchant_name", object.get("customerName"));
				customerMap.put("merchant_parent_code", object.get("corpNo"));
				customerMap.put("merchant_state", "1");
				customerMap.put("merchant_type", "1");
				customerMap.put("operate_state", "0");
				customerMap.put("merchant_brandcode",object.get("brandNo"));
				customerMap.put("esb_source", "SAP");
				customerMap.put("merchant_begdate", "0001-01-01 00:00:00");
				customerMap.put("merchant_enddate", "0001-01-01 00:00:00");
				customerMap.put("merchant_fin_type", "2.0");
				
				//经销商
				corpMap.put("merchant_code", object.get("corpNo"));
				corpMap.put("merchant_name", object.get("corpName"));
				corpMap.put("merchant_parent_code", object.get("areaNo"));
				corpMap.put("merchant_state", "1");
				corpMap.put("merchant_type", "1");
				corpMap.put("operate_state", "0");
				corpMap.put("merchant_brandcode",object.get("brandNo"));
				corpMap.put("esb_source", "SAP");
				corpMap.put("merchant_begdate", "0001-01-01 00:00:00");
				corpMap.put("merchant_enddate", "0001-01-01 00:00:00");
				corpMap.put("merchant_fin_type", "2.0");
				
				//公司
				areaMap.put("merchant_code", object.get("areaNo"));
				areaMap.put("merchant_name", object.get("areaName"));
				areaMap.put("merchant_parent_code", "");
				areaMap.put("merchant_state", "1");
				areaMap.put("merchant_type", "3");
				areaMap.put("operate_state", "0");
				areaMap.put("merchant_brandcode",object.get("brandNo"));
				areaMap.put("esb_source", "SAP");
				areaMap.put("merchant_begdate", "0001-01-01 00:00:00");
				areaMap.put("merchant_enddate", "0001-01-01 00:00:00");
				areaMap.put("merchant_fin_type", "0.0");
				
				storeList.add(storeMap);
				customerList.add(customerMap);
				corpList.add(corpMap);
				areaList.add(areaMap);
			}
		}
		return_map.put("storeList", storeList);
		return_map.put("customerList", customerList);
		return_map.put("corpList", corpList);
		return_map.put("areaList", areaList);
		
		//获取完总数之后,更新时间戳
		planDao.updateTamp(key, String.valueOf(ts));
		//保存原始数据
		initDao.insertData(initJson);
		
		return return_map;
	}

	@Override
	public JSONArray save(String name, List<Map<String, Object>> areaList) {
		
		JSONArray array = new JSONArray();
		JSONObject object = new JSONObject();
		HttpPostUtils httpPostUtils = new HttpPostUtils();
		if (name.equals("areaList") || name.equals("corpList") || name.equals("customerList")) {

			object.put("DllName", "Zhx.X3.Eral.dll");
			object.put("NameSpace", "Zhx.X3.Eral");
			object.put("ClasssName", "X3_Input_Customer");

		} else if (name.equals("storeList")) {

			object.put("DllName", "Zhx.X3.Eral.dll");
			object.put("NameSpace", "Zhx.X3.Eral");
			object.put("ClasssName", "X3_Input_Depot");

		}
		object.put("Caller", "msgid");
		object.put("UserKey", "0000");
		
		JSONObject res = new JSONObject();
		
//		Iterator<Map<String, Object>> it = areaList.iterator();
//		while (it.hasNext()) {
//			Map<String, Object> data = it.next();
//			if(!String.valueOf(data.get("merchant_code")).equals("252")) {
//				it.remove();
//			}
//		}
		
		int num = areaList.size()%100>0?(areaList.size()/100)+1:(areaList.size()/100);
		
		for( int i=0;i<num;i++ ) {
			
			List<Map<String,Object>> tem_list = new ArrayList<Map<String,Object>>();
			if( i+1==num ) {
				tem_list.addAll(areaList.subList(i*100, areaList.size()));
			}else {
				tem_list.addAll(areaList.subList(i*100, (i+1)*100));
			}
			
			object.put("JsonObj", tem_list);
			res = httpPostUtils.postToX2(object);
			array.add(res);
			logger.info(res.toJSONString());
		}
		 
		return array;
		
	}

	@Override
	public List<Map<String, Object>> findDepartmentId(List<Map<String, Object>> storeList) {
		return dao.findDepartmentId(storeList);
	}

	@Override
	public void insertUserKey(List<Map<String, Object>> list) {
		//先清空记录vipdp0000的记录
		dao.clearData();
		//插入数据
		dao.insertData(list);
	}

	@Override
	public BSDResponse doRmiService() {
		// 设置request参数
		RequestMsg requestMsg = new RequestMsg();
		requestMsg.setMethod("x3BillsArchivesService.archives.DepartmentGroupSubInsert");
		JSONObject v1 = new JSONObject();
		v1.put("area_id", "125929");//传入固定值
		requestMsg.addjson("v1", v1);
		requestMsg.setUserId("200000000000");
		requestMsg.setUserKey("0000");
		requestMsg.setUserName("锦铭泰");
		ResponseMsg responseMsg = rmiController.doService(requestMsg);
		
		String errCode = responseMsg.getErrCode();
		if (!StringUtil.isBlankOrNull(errCode) && !errCode.equalsIgnoreCase("Success")) {
			return ResponseUtil.setResponse("error", responseMsg.getErrorMsg());
		}
		
		return ResponseUtil.setResponse("S", "同步成功！");
		
	}

	@Override
	public int getStoreBaseData(String sdate, int type) {
		
		HttpPostUtils httpPostUtils = new HttpPostUtils();
		String uuid = UUID.randomUUID().toString();
		String key = "StoreInterface";
		
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/store/getChangedStoreByTs";//http地址
		
		JSONObject condition = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long ts = System.currentTimeMillis();
		//获取时间戳
		if( type==1 ) {
			List<Map<String, Object>> list = planDao.getTampData(key);
			if( list==null||list.size()<=0 ) {
				condition.put("ts", sdf.format(new Date(ts)));
			}else {
				condition.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("TIME_STAMP"))))));
			}
		}else {
			condition.put("ts", sdate+" 00:00:00");
		}
		
		JSONObject jo = httpPostUtils.postHttp(url, condition);
		
		totalMap.put("interface_name",url );
		totalMap.put("request_data", condition.toJSONString());
		totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
		totalMap.put("received_data", jo.toJSONString());
		totalMap.put("uuid", uuid);
		initJson.add(totalMap);
		
		planDao.updateTamp(key, String.valueOf(ts));
		int count = 0;
		if( initJson!=null && initJson.size()>0 ) {
			count = initDao.insertData(initJson);
		}
		
		return count;
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/store/getChangedStoreByTs";//http地址
		return initDao.getInitData("0", url);
	}

}
