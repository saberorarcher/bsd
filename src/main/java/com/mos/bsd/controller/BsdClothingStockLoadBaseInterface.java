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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.service.IBsdClothingStockService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD读取商品库存并写入x2", description = "BSD读取商品库存并写入x2", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdClothingStockLoadBaseInterface")
public class BsdClothingStockLoadBaseInterface {
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdClothingStockServiceImpl")
	private IBsdClothingStockService clothingStockService;
	
	@ApiOperation(value = "BSD商品库存传入X2", notes = "BSD商品库存传入X2")
	@RequestMapping(value = "/clothingStockLoadBase/bsdToX2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public List<BSDResponse> sapToX2Bills(@RequestParam("type") int type) {
		
		List<BSDResponse> list1 = new ArrayList<BSDResponse>();
		List<Map<String, InitialdataEntity>> list = clothingStockService.getClothingBaseData(type);
		if( list!=null&& list.size()>0 ) {
			for( Map<String, InitialdataEntity> map:list ) {
				// 返回消息
				BSDResponse responses = clothingStockService.getLoadBaseData(type,map.get("id"));
				list1.add(responses);
			}
		}
		
		return list1;
	}
}