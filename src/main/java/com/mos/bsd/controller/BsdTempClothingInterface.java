package com.mos.bsd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.service.IBsdTempClothingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD商品档案存入临时表", description = "BSD商品档案存入临时表", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdTempClothingInterface")
public class BsdTempClothingInterface {
	
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.IBsdTempClothingServiceImpl")
	private IBsdTempClothingService tempClothingService;
	
	@ApiOperation(value = "BSD商品档案传入X2", notes = "BSD商品档案传入X2")
	@RequestMapping(value = "/tempClothing/bsdToX2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public BSDResponse sapToX2Bills() {
		// 返回消息
		BSDResponse responses = tempClothingService.getData();
		return responses;
	}
	
}
