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
import com.mos.bsd.service.IBsdStoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD店铺档案原始數據传入X2", description = "BSD店铺档案原始數據传入X2", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdStoreBaseInterface")
public class BsdStoreBaseInterface {
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdStoreServiceImpl")
	private IBsdStoreService storeService;
	
	@ApiOperation(value = "BSD店铺档案原始數據传入X2", notes = "BSD店铺档案原始數據传入X2")
	@RequestMapping(value = "/storeBase/bsdToX2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public BSDResponse sapToX2Bills(@RequestParam("sdate") String sdate,@RequestParam("type") int type) {
		// 返回消息
		BSDResponse responses = storeService.getBaseData(sdate,type);
		return responses;
	}
}

