package com.mos.bsd.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.service.IBsdUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD用户档案接口", description = "BSD用户档案接口", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdUserInterface")
public class BsdUserInterface {
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdUserServiceImpl")
	private IBsdUserService userService;
	
	@ApiOperation(value = "BSD用户档案传入X2", notes = "BSD用户档案传入X2")
	@RequestMapping(value = "/user/bsdToX2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public BSDResponse sapToX2Bills() {
		// 返回消息
		BSDResponse responses = userService.getData();
		return responses;
	}
}
