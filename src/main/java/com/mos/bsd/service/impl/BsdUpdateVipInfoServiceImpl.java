package com.mos.bsd.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mos.bsd.biz.IBsdVipBiz;
import com.mos.bsd.biz.IBsdVipGuideUpdateBiz;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.VipGuideEntity;
import com.mos.bsd.service.IBsdUpdateVipInfoService;
import com.mos.bsd.utils.BusinessException;
@Service("com.mos.bsd.service.impl.BsdUpdateVipInfoServiceImpl")
public class BsdUpdateVipInfoServiceImpl implements IBsdUpdateVipInfoService {

	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipGuideUpdateBizImpl")
	private IBsdVipGuideUpdateBiz guideBiz;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipBizImpl")
	private IBsdVipBiz vip_biz;
	
	@Override
	public BSDResponse getData(String openid) {
		BSDResponse response = new BSDResponse();
		
		if(openid==null||"".equals(openid)) {
			throw new BusinessException("BsdVipGuideUpdateServiceImpl-01","参数openid不能为空!");
		}
		VipGuideEntity vipGuide = new VipGuideEntity();
		vipGuide.setOpenid(openid);
		
		//调用接口,查询是否有会员信息
		List <Map<String, Object>> vip_list = guideBiz.getVipData(vipGuide);
		if( vip_list==null || vip_list.size()<=0 ) {
			response.setMsgId( UUID.randomUUID().toString());
			response.setStatus("error");
			response.setMsg("绑定失败!未找到会员记录!");
			return response;
		}
		
		if( vip_list.size()>1 ) {
			response.setMsgId( UUID.randomUUID().toString());
			response.setStatus("error");
			response.setMsg("绑定失败!找到多条会员记录!");
			return response;
		}
		
		String uuid = UUID.randomUUID().toString();
		//转换department_user_id 为department_id
		Map<String,List<Map<String, Object>>> newVipMap = vip_biz.getDepartmentId(vip_list);
		
		List<Map<String, Object>> newVipList = newVipMap.get("list");
		List<Map<String, Object>> errorVipList = newVipMap.get("error");
		//写入D0210
		vip_biz.insertD0210(newVipList);
		
		//写入P0290
		vip_biz.insertP0290(newVipList,uuid);
		
		response.setMsgId( UUID.randomUUID().toString());
		response.setStatus("success");
		response.setMsg("绑定成功!");
		response.setErrorData(errorVipList.toString());
		
		return response;
	}

}
