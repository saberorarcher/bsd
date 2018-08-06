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
import com.mos.bsd.service.IBsdSaleOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD读取订单原始数据并保存进x2", description = "BSD读取订单原始数据并保存进x2", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdSaleOrderLoadBaseInterface")
public class BsdSaleOrderLoadBaseInterface {
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdSaleOrderServiceImpl")
	private IBsdSaleOrderService saleOrderService;
	
	@ApiOperation(value = "BSD读取订单原始数据并保存进x2", notes = "BSD读取订单原始数据并保存进x2")
	@RequestMapping(value = "/saleOrderLoadBase/bsdToX2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public List<BSDResponse> sapToX2Bills() {
		
		List<BSDResponse> list1 = new ArrayList<BSDResponse>();
		//原始读取数据方式
//		List<Map<String, InitialdataEntity>> list = saleOrderService.getClothingBaseData();
		
		//新的读取方式
		List<Map<String, Object>> list = saleOrderService.getTemData();
		if( list!=null && list.size()>0 ) {
			for(Map<String, Object> map:list) {
				// 返回消息
				BSDResponse responses = saleOrderService.getLoadTemData(map);
				list1.add(responses);
			}
		}
		
		return list1;
	}
}
