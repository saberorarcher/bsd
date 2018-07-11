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
import com.mos.bsd.service.IBsdVipGuideUpdateService;
@Service("com.mos.bsd.service.impl.BsdVipGuideUpdateServiceImpl")
public class BsdVipGuideUpdateServiceImpl implements IBsdVipGuideUpdateService {

	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipGuideUpdateBizImpl")
	private IBsdVipGuideUpdateBiz guideBiz;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipBizImpl")
	private IBsdVipBiz vip_biz;
	
	@Override
	public BSDResponse updateData(VipGuideEntity vipGuide) {
		BSDResponse response = new BSDResponse();
		
		//判断参数是否为空
		if(vipGuide.getUserid()==null||"".equals(vipGuide.getUserid())) {
			response.setMsgId( UUID.randomUUID().toString());
			response.setStatus("error");
			response.setMsg("绑定失败!参数用户编号不能为空!");
			return response;
//			throw new BusinessException("BsdVipGuideUpdateServiceImpl-01","参数用户编号不能为空!");
		}
		
		if(vipGuide.getOpenid()==null||"".equals(vipGuide.getOpenid())) {
			response.setMsgId( UUID.randomUUID().toString());
			response.setStatus("error");
			response.setMsg("绑定失败!参数openid不能为空!");
			return response;
			
//			throw new BusinessException("BsdVipGuideUpdateServiceImpl-01","参数openid不能为空!");
		}
		
		//查询本地是否有openid的会员
//		int count = guideBiz.findVipByOpenId(vipGuide);
//		
//		if( count>0 ) {
//			//判断会员所属店铺和
//			
//			//直接更新
//			count = guideBiz.updateVipGuide(vipGuide);
//			
//		}else {
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
		
		//同步会员信息
		String uuid = UUID.randomUUID().toString();
		
		//转换department_user_id 为department_id
		Map<String,List<Map<String, Object>>> newVipMap = vip_biz.getDepartmentId(vip_list);
		
		List<Map<String, Object>> newVipList = newVipMap.get("list");
		
		//写入D0210
		vip_biz.insertD0210(newVipList);
		
		//写入P0290
		vip_biz.insertP0290(newVipList,uuid);
		
		//判断导购和会员所属店铺是否是同一个
		
		int n = guideBiz.isSameDepot(vipGuide);
		
		if( n<=0 ) {
			response.setMsgId( UUID.randomUUID().toString());
			response.setStatus("error");
			response.setMsg("绑定失败!会员与导购不属于同一店铺!");
		}else {
			
			//修改专属导购
			int count = guideBiz.updateVipGuide(vipGuide);
			//返回成功信息
			if( count>0 ) {
				response.setMsgId( UUID.randomUUID().toString());
				response.setStatus("success");
				response.setMsg("绑定成功!");
			}else {
				response.setMsgId( UUID.randomUUID().toString());
				response.setStatus("error");
				response.setMsg("绑定不成功,请重试!");
			}
		}
			
//		}
		return response;
	}

}
