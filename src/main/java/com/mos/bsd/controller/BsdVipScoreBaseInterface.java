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
import com.mos.bsd.service.IBsdVipScoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD会员积分原始数据写入临时表", description = "BSD会员积分原始数据写入临时表", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdVipScoreBaseInterface")
public class BsdVipScoreBaseInterface {
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdVipScoreServiceImpl")
	private IBsdVipScoreService vipScoreService;
	
	@ApiOperation(value = "BSD会员积分原始数据写入临时表", notes = "BSD会员积分原始数据写入临时表")
	@RequestMapping(value = "/vipScoreBase/bsdToX2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public BSDResponse sapToX2Bills(@RequestParam("type") int type) {
		// 返回消息
		BSDResponse responses = vipScoreService.getBaseData(type);
		
		return responses;
	}
	
}

