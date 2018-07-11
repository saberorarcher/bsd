package com.mos.bsd.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.service.IBsdCouponsGroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD券组读取原始数据并保存进x2", description = "BSD券组读取原始数据并保存进x2", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdCouponsGroupLoadBaseInterface")
public class BsdCouponsGroupLoadBaseInterface {
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdCouponsGroupServiceImpl")
	private IBsdCouponsGroupService couponsGroupService;
	
	@ApiOperation(value = "BSD券组读取原始数据并保存进x2", notes = "BSD券组读取原始数据并保存进x2")
	@RequestMapping(value = "/couponsGroupLoadBase/bsdToX2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public List<BSDResponse> sapToX2Bills() {
		
		List<BSDResponse> list1 = new ArrayList<BSDResponse>();
		List<Map<String, InitialdataEntity>> list = couponsGroupService.getClothingBaseData();
		if( list!=null && list.size()>0 ) {
			for( Map<String, InitialdataEntity> map:list ) {
				// 返回消息
				BSDResponse responses = couponsGroupService.getLoadBaseData(map.get("id"));
				list1.add(responses);
			}
		}
		
		return list1;
	}
}
