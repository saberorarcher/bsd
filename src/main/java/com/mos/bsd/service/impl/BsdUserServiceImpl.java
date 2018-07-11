package com.mos.bsd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mos.bsd.biz.IBsdUserBiz;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.service.IBsdUserService;
@Service("com.mos.bsd.service.impl.BsdUserServiceImpl")
public class BsdUserServiceImpl implements IBsdUserService {
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdUserBizImpl")
	private IBsdUserBiz user_biz;
	
	@Override
	public BSDResponse getData() {
		//调用接口,添加得到用户档案
		List<Map<String, Object>> list = user_biz.getData();
		//调用user档案接口,同步用户
		
		System.out.println(list);
		
		BSDResponse response = new BSDResponse();
		response.setStatus("S");
		response.setMsg("同步成功!");
		return response;
	}
	
}
