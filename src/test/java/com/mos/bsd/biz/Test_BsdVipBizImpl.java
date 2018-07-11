package com.mos.bsd.biz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mos.bsd.utils.HttpPostUtils;
import com.x3.base.core.exception.BusinessException;

public class Test_BsdVipBizImpl {
	public static void main(String[] args) {
		List<Map<String,Object>> vipList = new ArrayList<Map<String,Object>>();
		Map<String,Object> vipMap = new HashMap<>();
		try {
			JSONObject json = new JSONObject();
			//查询时间戳
			String url = "http://172.16.17.75:8088/bsdyun-open-api/center/user/getMemberDimCountByPara";
			String url1 = "http://172.16.17.75:8088/bsdyun-open-api/center/user/getPageMemberDimByPara";
			HttpPostUtils httpPostUtils = new HttpPostUtils();
			
			//接口允许条数限制10000 日期区间跨度限制:30天 TS距当前时间限制:3天
			long ts = System.currentTimeMillis();
			json.put("ts", ts);
			
			JSONObject object = null;
			//查询出总数
			JSONObject c_jObject = httpPostUtils.postHttp(url, json);
			
			if( c_jObject.getBoolean("success")!=null && !c_jObject.getBoolean("success")) {
				throw new BusinessException("BsdVipBizImpl-01","查询会员总数失败!错误信息:"+c_jObject.getString("errorMessage"));
			}
			
			int number = Integer.parseInt(c_jObject.getString("data"));
			int num = number%10000>0?number/10000+1:number/10000;
			
			for( int i=0;i<num;i++ ) {
//				String result = service.getPageMemberDimByPara(condition, (num-1)*10000, num*10000 );
				json.put("offset", (num-1)*10000);
				json.put("limit", num*10000);
				
				JSONObject jo = httpPostUtils.postHttp(url1, json);
				boolean success = jo.getBoolean("success");

				if (!success) {
					throw new BusinessException("BsdClothingBizImpl-01", jo.getString("errorMessage"));
				}
				//转换数据
				JSONArray array = jo.getJSONArray("data");
				if( array!=null && array.size()>0 ) {
					//获取数据
					for(Object obj :array) {
						object = (JSONObject) obj;
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
						vipMap.put("vip_create_department", object.get("createStoreNo"));//department_user_id
						vipMap.put("department_id", object.get("belongStoreNo"));//department_user_id
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
						vipMap.put("ts", object.get("ts"));
						
						vipList.add(vipMap);
					}
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		
		System.out.println(vipList);
	}
}
