package com.mos.bsd.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.service.IBsdVipCouponsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD会员券档案接口", description = "BSD会员券档案接口", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdVipCouponsInterface")
public class BsdVipCouponsInterface {
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdVipCouponsServiceImpl")
	private IBsdVipCouponsService vipCouponsService;
	
	@ApiOperation(value = "BSD会员券档案传入X2", notes = "BSD会员券档案传入X2")
	@RequestMapping(value = "/coupons/bsdToX2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public BSDResponse sapToX2Bills(@RequestParam("sdate") String sdate,@RequestParam("edate") String edate,@RequestParam("type") int type) {
		// 返回消息
		BSDResponse responses = vipCouponsService.getData(sdate,edate,type);
		
		return responses;
	}
}
