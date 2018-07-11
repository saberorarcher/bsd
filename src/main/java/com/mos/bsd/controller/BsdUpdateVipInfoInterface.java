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
import com.mos.bsd.service.IBsdUpdateVipInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD根据openid更新单个会员信息", description = "BSD根据openid更新单个会员信息", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdUpdateVipInfoInterface")
public class BsdUpdateVipInfoInterface {
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdUpdateVipInfoServiceImpl")
	private IBsdUpdateVipInfoService updateVipService;
	
	@ApiOperation(value = "BSD根据openid更新单个会员信息", notes = "BSD根据openid更新单个会员信息")
	@RequestMapping(value = "/updateVipByOpenid", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public BSDResponse sapToX2Bills(@RequestParam("openid") String openid) {
		// 返回消息
		BSDResponse responses = updateVipService.getData(openid);
		return responses;
	}
}
