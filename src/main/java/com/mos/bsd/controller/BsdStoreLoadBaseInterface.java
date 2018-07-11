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
import com.mos.bsd.service.IBsdStoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD店铺档案讀取原始數據并提交到x2", description = "BSD店铺档案讀取原始數據并提交到x2", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdStoreLoadBaseInterface")
public class BsdStoreLoadBaseInterface {
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdStoreServiceImpl")
	private IBsdStoreService storeService;
	
	@ApiOperation(value = "BSD店铺档案讀取原始數據并提交到x2", notes = "BSD店铺档案讀取原始數據并提交到x2")
	@RequestMapping(value = "/storeLoadBase/bsdToX2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public List<BSDResponse> sapToX2Bills() {
		
		List<BSDResponse> list2 = new ArrayList<BSDResponse>();
		List<Map<String, InitialdataEntity>> list = storeService.getClothingBaseData();
		
		System.out.println("*************************查询完毕");
		if( list!=null && list.size()>0 ) {
			for( Map<String, InitialdataEntity> map: list ) {
				// 返回消息
				BSDResponse responses = storeService.getLoadBaseData(map.get("id"));
				list2.add(responses);
			}
			
		}
		
		return list2;
	}
}