package com.mos.bsd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.VipGuideEntity;
import com.mos.bsd.service.IBsdVipGuideUpdateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD修改会员专属导购接口", description = "BSD修改会员专属导购接口", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdVipGuideUpdateInterface")
public class BsdVipGuideUpdateInterface {
	
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdVipGuideUpdateServiceImpl")
	private IBsdVipGuideUpdateService vipGuideService;
	
	@ApiOperation(value = "BSD修改会员专属导购", notes = "BSD修改会员专属导购")
	@RequestMapping(value = "/UpdateVipGuide", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public BSDResponse sapToX2Bills(@RequestBody VipGuideEntity vipGuide) {
		// 返回消息
		BSDResponse responses = vipGuideService.updateData(vipGuide);
		
		return responses;
	}
}
