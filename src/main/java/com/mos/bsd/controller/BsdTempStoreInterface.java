package com.mos.bsd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.service.IBsdTempStoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD店铺档案同步到临时表", description = "BSD店铺档案同步到临时表", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdTempStoreInterface")
public class BsdTempStoreInterface {
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdTempStoreServiceImpl")
	private IBsdTempStoreService storeService;
	
	@ApiOperation(value = "BSD店铺档案同步到临时表", notes = "BSD店铺档案同步到临时表")
	@RequestMapping(value = "/tempStore/bsdToX2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public BSDResponse sapToX2Bills() {
		// 返回消息
		BSDResponse responses = storeService.getData();
		return responses;
	}
}
