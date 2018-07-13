package com.mos.bsd.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.mos.bsd.biz.IBsdVipBiz;
import com.mos.bsd.dao.IBsdPlanDao;
import com.mos.bsd.dao.IBsdVipDao;
import com.mos.bsd.dao.IBsdVipOpenidDao;
import com.mos.bsd.dao.IinitialdataDao;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.utils.HttpPostUtils;
import com.x3.base.core.exception.BusinessException;
/**
 * bsd vip档案接口
 * @author hao
 *
 */
@Repository("com.mos.bsd.biz.impl.BsdVipBizImpl")
public class BsdVipBizImpl implements IBsdVipBiz {

	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdVipDaoImpl")
	private IBsdVipDao vip_dao;
	
	@Autowired
	private Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdVipBizImpl.class);
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdVipOpenidDaoImpl")
	private IBsdVipOpenidDao openidDao;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdPlanDaoImpl")
	private IBsdPlanDao planDao;
	
	@Resource(name="com.mos.bsd.dao.impl.InitialdataDaoImpl")
	private IinitialdataDao initDao;
	
	@Override
	public Map<String,List<Map<String, Object>>> getVipData(String sdate,String edate,int x,String uuid) {
		String key = "VipInterface";
		Map<String,List<Map<String, Object>>> returnMap = new HashMap<String,List<Map<String, Object>>>();
		List<Map<String,Object>> vipList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> errorList = new ArrayList<Map<String,Object>>();
		Map<String,Object> vipMap ;
		Map<String,Object> errorMap ;
		
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		Map<String, String> detailMap;
		
		JSONObject json = new JSONObject();
		//查询时间戳
		String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/user/getMemberDimCountByPara";
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/user/getPageMemberDimByPara";
		HttpPostUtils httpPostUtils = new HttpPostUtils();
		
		long ts = System.currentTimeMillis();
		
		//接口允许条数限制10000 日期区间跨度限制:30天 TS距当前时间限制:3天
		
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

			beginString = sdate+" 00:00:00";
			endString = edate+" 00:00:00";  
		
			json.put("beginAllocateDate", beginString);
			json.put("endAllocateDate", endString);
		}
		
		JSONObject object = null;
		//查询出总数
		JSONObject c_jObject = httpPostUtils.postHttp(url, json);
		
		totalMap.put("interface_name",url );
		totalMap.put("request_data", json.toJSONString());
		totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
		totalMap.put("received_data", c_jObject.toJSONString());
		totalMap.put("uuid", uuid);
		initJson.add(totalMap);
		
		if(c_jObject.containsKey("status")&&!c_jObject.getBoolean("status")) {
			logger.debug(c_jObject.getString("errorMsg"));
			throw new BusinessException("BsdVipBizImpl-01","查询会员总数失败!错误信息:"+c_jObject.getString("errorMessage"));
		}
		
		if( c_jObject.getBoolean("success")!=null && !c_jObject.getBoolean("success")) {
			logger.debug(c_jObject.getString("errorMsg"));
			throw new BusinessException("BsdVipBizImpl-01","查询会员总数失败!错误信息:"+c_jObject.getString("errorMessage"));
		}
		
		int number = Integer.parseInt(c_jObject.getString("data"));
		int num = number%10000>0?number/10000+1:number/10000;
		
		for( int i=0;i<num;i++ ) {
			
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
			
			if(jo.containsKey("status")&&!jo.getBoolean("status")) {
				throw new BusinessException("BsdVipBizImpl-01","查询会员失败!错误信息:"+c_jObject.getString("errorMsg"));
			}
			
			boolean success = jo.getBoolean("success");

			if (!success) {
				logger.debug(c_jObject.getString("errorMsg"));
				throw new BusinessException("BsdClothingBizImpl-01", jo.getString("errorMessage"));
			}
			//转换数据
			JSONArray array = jo.getJSONArray("data");
			if( array!=null && array.size()>0 ) {
				//获取数据
				for(Object obj :array) {
					object = (JSONObject) obj;
					vipMap = new HashMap<String,Object>();
					
					//检查数据  1.card_id  2.department_id  3.birthday  4.vip_name  5.vip_create_name
					String card_id = String.valueOf(object.get("memberId"));
					String department_id = String.valueOf(object.get("createStoreNo"));
					String date = String.valueOf(object.get("birthday"));
					
					if(card_id==null||card_id.equals("")||"null".equals(card_id)) {
						errorMap = new HashMap<String,Object>();
						errorMap.put("error", object);
						errorList.add(errorMap);
						logger.debug(object.toJSONString());
						continue;
					}
					
					if(department_id==null||department_id.equals("")||"null".equals(department_id)) {
						errorMap = new HashMap<String,Object>();
						errorMap.put("error", object);
						errorList.add(errorMap);
						logger.debug(object.toJSONString());
						continue;
					}
					
					if(date==null||date.equals("")||"null".equals(date)) {
						errorMap = new HashMap<String,Object>();
						errorMap.put("error", object);
						errorList.add(errorMap);
						logger.debug(object.toJSONString());
						continue;
					}
					
					//vip_id 系统自动生成
					vipMap.put("card_id", object.get("memberId"));
					vipMap.put("userNo", object.get("userNo"));
					vipMap.put("vip_name", object.get("userName"));
					vipMap.put("vip_sex", object.get("gender"));
					//拆分日期
					Date birthday = object.getDate("birthday");
					Calendar c = Calendar.getInstance();
					c.setTime(birthday);
					
					vipMap.put("birthday", object.get("birthday"));//年+月+日
					vipMap.put("vip_birthday_year", c.get(Calendar.YEAR));
					vipMap.put("vip_birthday_month", c.get(Calendar.MONTH)+1);
					vipMap.put("vip_birthday_day", c.get(Calendar.DATE));
					
					vipMap.put("vip_mobile", object.get("mobileNo"));
					vipMap.put("vip_email", object.get("emailNo"));
					vipMap.put("vip_province", object.get("province"));
					vipMap.put("vip_town", object.get("city"));
					vipMap.put("vip_district", object.get("county"));
					vipMap.put("vip_address", object.get("town"));					
					
					vipMap.put("createStoreNo", object.get("createStoreNo"));//department_user_id
					vipMap.put("belongStoreNo", object.get("belongStoreNo"));//department_user_id
					vipMap.put("vip_issue_date", object.get("createDate"));
					vipMap.put("vip_create_name", object.get("createUser"));
					vipMap.put("memberLevel", object.get("memberLevel"));//卡等级   写入表D0180
					vipMap.put("vip_state", object.get("memberStatus"));
					vipMap.put("firstClassify", object.get("firstClassify"));
					vipMap.put("secondClassify", object.get("secondClassify"));
					vipMap.put("thirdClassify", object.get("thirdClassify"));
					vipMap.put("annualIncome", object.get("annualIncome"));//家庭年收入
					vipMap.put("vip_job", object.get("occupation"));
					vipMap.put("vip_academic", object.get("education"));
					vipMap.put("pointAbnormalTimes", object.get("pointAbnormalTimes"));//积分异常次数
					
					vipList.add(vipMap);
				}
			}
		}
		
		//获取完总数之后,更新时间戳
		planDao.updateTamp(key, String.valueOf(ts));
		//保存原始数据
		initDao.insertData(initJson);
		
		returnMap.put("list", vipList);
		returnMap.put("error", errorList);
		return returnMap;
	}

	@Override
	public int insertD0210(List<Map<String, Object>> vipList) {
		
		return vip_dao.insertD0210(vipList);
	}

	@Override
	public int insertD0180(List<Map<String, Object>> vipList) {
		
		return vip_dao.insertD0180(vipList,1);
	}

	@Override
	public int insertP0290(List<Map<String, Object>> vipList,String uuid) {
		
		List <Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		
		//先插入到临时表,card_temp
		vip_dao.mergeTemData(vipList,uuid);
		
		List <Map<String, Object>> changeList = vip_dao.queryChangeData(vipList);
		//循环,取出记录
		for( Map<String, Object> change_map:changeList ) {
			for(Map<String, Object> main_map:vipList) {
				if(String.valueOf(main_map.get("card_id")).equals(String.valueOf(change_map.get("CARD_ID")))) {
					main_map.put("old_viptype_id", change_map.get("OLD_VIPTYPE_ID"));
					main_map.put("system_type", change_map.get("SYSTEM_TYPE"));
					newList.add(main_map);
				}
			}
		}
		
		//根据card_id查询出vip_id
		List <Map<String, Object>> vipidList = openidDao.getVipId(vipList);
		//循环,取出记录
		for( Map<String, Object> change_map:vipidList ) {
			for(Map<String, Object> main_map:vipList) {
				if(String.valueOf(main_map.get("card_id")).equals(String.valueOf(change_map.get("CARD_ID")))) {
					main_map.put("vip_id", change_map.get("VIP_ID"));
				}
			}
		}
		
		for( Map<String, Object> change_map:vipidList ) {
			for(Map<String, Object> main_map:newList) {
				if(String.valueOf(main_map.get("card_id")).equals(String.valueOf(change_map.get("CARD_ID")))) {
					main_map.put("vip_id", change_map.get("VIP_ID"));
				}
			}
		}
		
		//插入到卡等级变更表
		int count = vip_dao.insertP0290(newList);
		//更新D0210
		vip_dao.insertD0180(vipList,2);
		//删除card_temp
		vip_dao.cleanTempData(uuid);
		
		return count;
	}

	@Override
	public List<Map<String, Object>> getVipList() {
		return vip_dao.getVipList();
	}

	@Override
	public Map<String,List<Map<String, Object>>> getDepartmentId(List<Map<String, Object>> vipList) {
		
		Map<String,List<Map<String, Object>>> returnMap = new HashMap<String,List<Map<String, Object>>>();
		
		List <Map<String, Object>> errorDataList = new ArrayList<Map<String, Object>>();
		//查询出depertment_id列表
		List <Map<String, Object>> changeList = vip_dao.queryDepartmentData(vipList,1);

		//循环匹配
		for( Map<String, Object> change_map:changeList ) {
			for(Map<String, Object> main_map:vipList) {
				if(String.valueOf(main_map.get("createStoreNo")).equals(String.valueOf(change_map.get("DEPARTMENT_USER_ID")))) {
					main_map.put("vip_create_department", change_map.get("DEPARTMENT_ID"));
				}
			}
		}
		
//		Iterator<Map<String, Object>> it = vipList.iterator();
//		while ( it.hasNext() ) {
//			Map<String, Object> data = it.next();
//			if( !data.containsKey("vip_create_department") ) {
//				errorDataList.add(data);
//				it.remove();
//			}
//		}
		
		//删除没有vip_create_department的数据
		
		//归属店铺
		List <Map<String, Object>> changeList1 = vip_dao.queryDepartmentData(vipList,2);
		//查询出depertment_id列表
		for( Map<String, Object> change_map:changeList1 ) {
			for(Map<String, Object> main_map:vipList) {
				if(String.valueOf(main_map.get("belongStoreNo")).equals(String.valueOf(change_map.get("DEPARTMENT_USER_ID")))) {
					main_map.put("department_id", change_map.get("DEPARTMENT_ID"));
				}
			}
		}
		
		//删除没有department_id的数据
		Iterator<Map<String, Object>> it1 = vipList.iterator();
		while ( it1.hasNext() ) {
			Map<String, Object> data = it1.next();
			if( !data.containsKey("department_id") ) {
				errorDataList.add(data);
//				it1.remove();
			}
		}
		
		returnMap.put("list", vipList);
		returnMap.put("error", errorDataList);
		return returnMap;
	}

	@Override
	public int getVipBaseData(String sdate, String edate, int type) {
		
		String key = "VipInterface";
		String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/user/getMemberDimCountByPara";
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/user/getPageMemberDimByPara";
		String cuuid = UUID.randomUUID().toString();
		
		Map<String, String> totalMap = new HashMap<String, String>();
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> detailMap;
		
		long ts = System.currentTimeMillis();
		
		//接口允许条数限制10000 日期区间跨度限制:30天 TS距当前时间限制:3天
		HttpPostUtils httpPostUtils = new HttpPostUtils();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String beginString ="";
		String endString="";
		JSONObject json = new JSONObject();
		
		if( type == 1 ) {
			List<Map<String, Object>> list = planDao.getTampData(key);
			if( list==null||list.size()<=0 ) {
				json.put("ts", sdf.format(new Date(ts)));
			}else {
				json.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("TIME_STAMP"))))));
			}
		}else {
			beginString = sdate+" 00:00:00";
			endString = edate+" 00:00:00";  
		
			json.put("beginAllocateDate", beginString);
			json.put("endAllocateDate", endString);
		}
		
		//查询出总数
		JSONObject c_jObject = httpPostUtils.postHttp(url, json);
		
		totalMap.put("interface_name",url );
		totalMap.put("request_data", json.toJSONString());
		totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
		totalMap.put("received_data", c_jObject.toJSONString());
		totalMap.put("uuid", cuuid);
		totalMap.put("cuuid", cuuid);
		initJson.add(totalMap);
		
		
		if(c_jObject.containsKey("status")&&!c_jObject.getBoolean("status")) {
			logger.debug(c_jObject.getString("errorMsg"));
			throw new BusinessException("BsdVipBizImpl-01","查询会员总数失败!错误信息:"+c_jObject.getString("errorMessage"));
		}
		
		if( c_jObject.getBoolean("success")!=null && !c_jObject.getBoolean("success")) {
			logger.debug(c_jObject.getString("errorMsg"));
			throw new BusinessException("BsdVipBizImpl-01","查询会员总数失败!错误信息:"+c_jObject.getString("errorMessage"));
		}
		
		int number = Integer.parseInt(c_jObject.getString("data"));
		int num = number%10000>0?number/10000+1:number/10000;
		
		for (int i = 0; i < num; i++) {

			json.put("offset", i * 10000);
			json.put("limit", 10000);

			JSONObject jo = httpPostUtils.postHttp(url1, json);

			detailMap = new HashMap<>();
			detailMap.put("interface_name", url1);
			detailMap.put("request_data", json.toJSONString());
			detailMap.put("create_date", String.valueOf(System.currentTimeMillis()));
			detailMap.put("received_data", jo.toJSONString());
			detailMap.put("uuid", UUID.randomUUID().toString());
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
	public void findClobData() {
		List<Map<String, InitialdataEntity>> intList = initDao.getInitData("0", "cs2");
		for(Map<String, InitialdataEntity> map:intList) {
			InitialdataEntity entity= map.get("id");
			
			System.out.println(entity.getId());
			System.out.println(entity.getRequest_data());
			System.out.println(entity.getReceived_data());
			System.out.println(entity.getCuuid());
			System.out.println(entity.getUuid());
			System.out.println(entity.getStatus());
		}
		
		System.out.println(intList);
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/user/getPageMemberDimByPara";
		
		return initDao.getInitData("0", url1);
	}

	@Override
	public List<Map<String, Object>> getWrongData() {
		return vip_dao.findWrongData();
	}

}
