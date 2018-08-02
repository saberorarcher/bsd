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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mos.bsd.biz.IBsdVipOpenidBiz;
import com.mos.bsd.dao.IBsdPlanDao;
import com.mos.bsd.dao.IBsdVipOpenidDao;
import com.mos.bsd.dao.IinitialdataDao;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.utils.HttpPostUtils;
import com.x3.base.core.exception.BusinessException;

@Repository("com.mos.bsd.biz.impl.BsdVipOpenidBizImpl")
public class BsdVipOpenidBizImpl implements IBsdVipOpenidBiz {

	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdVipOpenidDaoImpl")
	private IBsdVipOpenidDao openId_dao;
	
	@Autowired
	private Environment env;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdPlanDaoImpl")
	private IBsdPlanDao planDao;
	
	@Resource(name="com.mos.bsd.dao.impl.InitialdataDaoImpl")
	private IinitialdataDao initDao;
	
	@Override
	public List<Map<String, Object>> getVipOpenIdData(String sdate, String edate, int type, String uuid) {
		String key = "VipOpenIdInterface";
		//条件限制:日期区间跨度限制:30天,TS距当前时间限制:3天,分页单页条数限制:10000
		List<Map<String,Object>> couponsGroupList = new ArrayList<Map<String,Object>>();
		Map<String,Object> couponsGroupMap ;
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		Map<String, String> detailMap;
		
		try {
			long ts = System.currentTimeMillis();
			JSONObject json = new JSONObject();
			String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/user/getMemberWcpnCountByPara";
			String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/user/getPageMemberWcpnByPara";
			HttpPostUtils httpPostUtils = new HttpPostUtils();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			if( type==1 ) {
				List<Map<String, Object>> list = planDao.getTampData(key);
				if( list==null||list.size()<=0 ) {
					json.put("ts", sdf.format(new Date(ts)));
				}else {
					json.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("TIME_STAMP"))))));
				}
			}else {
				json.put("beginBindTime", sdate+" 00:00:00");
				json.put("endBindTime", edate+" 00:00:00");
			}
			
			JSONObject c_jObject = httpPostUtils.postHttp(url, json);
			JSONObject object = null;
			
			totalMap.put("interface_name",url );
			totalMap.put("request_data", json.toJSONString());
			totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
			totalMap.put("received_data", c_jObject.toJSONString());
			totalMap.put("uuid", uuid);
			initJson.add(totalMap);
			
			if( c_jObject.getBoolean("success")!=null && !c_jObject.getBoolean("success")) {
				throw new BusinessException("BsdVipOpenidBizImpl-01","查询券总数失败!错误信息:"+c_jObject.getString("errorMessage"));
			}
			
			//计算循环次数
			int number = Integer.parseInt(c_jObject.getString("data"));
			int num = number%10000>0?number/10000+1:number/10000;
			
			for( int i=0;i<num;i++ ) {
				//分页参数
				json.put("offset", i*1000);
				json.put("limit", 1000);
				
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
					throw new BusinessException("BsdVipOpenidBizImpl-02", jo.getString("errorMessage"));
				}
				//转换数据
				JSONArray array = jo.getJSONArray("data");
				if( array!=null && array.size()>0 ) {
					for(Object obj :array) {
						object = (JSONObject) obj;
						couponsGroupMap = new HashMap<>();
						couponsGroupMap.put("card_id", object.get("memberId"));
						couponsGroupMap.put("binding_app", object.get("publicNo"));
						couponsGroupMap.put("binding_val", object.get("openId"));
						couponsGroupMap.put("wechatCardFlag", object.get("wechatCardFlag"));
						couponsGroupMap.put("wechatCardCode", object.get("wechatCardCode"));
						couponsGroupMap.put("warmHomeFlag", object.get("warmHomeFlag"));
						couponsGroupMap.put("concernFlag", object.get("concernFlag"));
						couponsGroupMap.put("create_date", object.get("bindTime"));
						couponsGroupMap.put("classifyNo", object.get("classifyNo"));
						couponsGroupMap.put("remark", object.get("remark"));
						
						couponsGroupList.add(couponsGroupMap);
					}
				}
			}
			planDao.updateTamp(key, String.valueOf(ts));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//保存原始数据
		initDao.insertData(initJson);
		return couponsGroupList;
	}

	@Override
	public List<Map<String, Object>> mergeToX2(List<Map<String, Object>> vipOpenid_list) {
		
		List<Map<String, Object>> errList = new ArrayList<Map<String, Object>>();
		//获取vip_id主键 用in,每次1000条数据
		if( vipOpenid_list!=null && vipOpenid_list.size()>0 ) {
			
			List<Map<String, Object>> list = openId_dao.getVipId(vipOpenid_list);
			for( Map<String, Object> map : vipOpenid_list ) {
				for( Map<String, Object> map1 : list) {
					if(String.valueOf(map1.get("CARD_ID")).equals(String.valueOf(map.get("card_id")))) {
						map.put("vip_id", map1.get("VIP_ID"));
					}
				}
			}
			
			Iterator<Map<String, Object>> it = vipOpenid_list.iterator();
			while(it.hasNext()) {
				Map<String, Object> t_map = it.next();
				String vip_id = String.valueOf(t_map.get("vip_id"));
				if(vip_id==null||vip_id.equals("")||"".equals(vip_id)) {
					errList.add(t_map);
				}
			}
			
			//同步D0210
			openId_dao.mergeD0210OpenId(vipOpenid_list);
			//同步j_vipbinding
			openId_dao.mergeVipbinding(vipOpenid_list);
		}

		return errList;
	}

	@Override
	public int getVipBaseData(String sdate, String edate, int type) {
		
		String key = "VipOpenIdInterface";
		String cuuid = UUID.randomUUID().toString();
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		Map<String, String> detailMap;
		
		
		long ts = System.currentTimeMillis();
		JSONObject json = new JSONObject();
		String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/user/getMemberWcpnCountByPara";
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/user/getPageMemberWcpnByPara";
		HttpPostUtils httpPostUtils = new HttpPostUtils();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if( type==1 ) {
			List<Map<String, Object>> list = planDao.getTampData(key);
			if( list==null||list.size()<=0 ) {
				json.put("ts", sdf.format(new Date(ts)));
			}else {
				json.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("TIME_STAMP"))))));
			}
		}else {
			json.put("beginBindTime", sdate+" 00:00:00");
			json.put("endBindTime", edate+" 00:00:00");
		}
		
		JSONObject c_jObject = httpPostUtils.postHttp(url, json);
		
		totalMap.put("interface_name",url );
		totalMap.put("request_data", json.toJSONString());
		totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
		totalMap.put("received_data", c_jObject.toJSONString());
		totalMap.put("uuid", cuuid);
		totalMap.put("cuuid", cuuid);
		initJson.add(totalMap);
		
		//计算循环次数
		int number = Integer.parseInt(c_jObject.getString("data"));
		int num = number%10000>0?number/10000+1:number/10000;
		
		for( int i=0;i<num;i++ ) {
			//分页参数
			json.put("offset", i*1000);
			json.put("limit", 1000);
			
			JSONObject jo = httpPostUtils.postHttp(url1, json);
			
			detailMap = new HashMap<>();
			detailMap.put("interface_name",url1 );
			detailMap.put("request_data", json.toJSONString());
			detailMap.put("create_date", String.valueOf(System.currentTimeMillis()));
			detailMap.put("received_data", jo.toJSONString());
			detailMap.put("cuuid", cuuid);
			detailMap.put("uuid", UUID.randomUUID().toString());
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
		
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/user/getPageMemberWcpnByPara";
		
		return initDao.getInitData("0", url1);
	}

}
