package com.mos.bsd.biz.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.mos.bsd.biz.IBsdVipGuideUpdateBiz;
import com.mos.bsd.dao.IBsdVipGuideUpdateDao;
import com.mos.bsd.domain.VipGuideEntity;
import com.mos.bsd.utils.BusinessException;
import com.mos.bsd.utils.HttpPostUtils;
@Repository("com.mos.bsd.biz.impl.BsdVipGuideUpdateBizImpl")
public class BsdVipGuideUpdateBizImpl implements IBsdVipGuideUpdateBiz {

	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdVipGuideUpdateDaoImpl")
	private IBsdVipGuideUpdateDao dao;
	
	@Autowired
	private Environment env;
	
	@Override
	public int updateVipGuide(VipGuideEntity vipGuide) {
		return dao.updateVipGuide(vipGuide);
	}

	@Override
	public int findVipByOpenId(VipGuideEntity vipGuide) {
		return dao.findVipByOpenId(vipGuide);
	}

	@Override
	public List<Map<String, Object>> getVipData(VipGuideEntity vipGuide) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/marketing/getMember";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("openId", vipGuide.getOpenid());
		HttpPostUtils httpPostUtils = new HttpPostUtils();
		JSONObject c_jObject = httpPostUtils.postHttp(url, jsonObject);
		if(c_jObject==null) {
			return list;
		}
		
		if(c_jObject.containsKey("success")&&!c_jObject.getBoolean("success")) {
			throw new BusinessException("BsdVipGuideUpdateBizImpl-01","查询会员信息失败:错误信息"+c_jObject.getString("errorMessage"));
		}
		
		Map<String, Object> vipMap = new HashMap<String,Object>();
		
		JSONObject main = c_jObject.getJSONObject("data");
		JSONObject object = main.getJSONObject("memberBaseInfoDTO");
		vipMap.put("card_id", object.get("memberId"));
		vipMap.put("userNo", object.get("userNo"));
		vipMap.put("vip_name", object.get("memberName"));
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
		vipMap.put("vip_province", object.get("provinceName"));
		vipMap.put("vip_town", object.get("cityName"));
		vipMap.put("vip_district", object.get("address"));
		vipMap.put("vip_address", object.get("town"));
		
		vipMap.put("createStoreNo", object.get("createStoreNo"));//department_user_id
		vipMap.put("belongStoreNo", object.get("belongCustNo"));//department_user_id
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
		
		list.add(vipMap);
		
		return list;
	}

	@Override
	public int isSameDepot(VipGuideEntity vipGuide) {

		return dao.isSameDepot(vipGuide);
	}

}
