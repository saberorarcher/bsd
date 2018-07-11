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
import com.mos.bsd.service.IBsdVipService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD会员档案读取原始数据并保存到x2", description = "BSD会员档案读取原始数据并保存到x2", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdVipLoadBaseInterface")
public class BsdVipLoadBaseInterface {
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdVipServiceImpl")
	private IBsdVipService vipService;
	
	@ApiOperation(value = "BSD会员档案读取原始数据并保存到x2", notes = "BSD会员档案读取原始数据并保存到x2")
	@RequestMapping(value = "/vipLoadBase/bsdToX2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public List<BSDResponse> sapToX2Bills() {
		List<BSDResponse> list = new ArrayList<BSDResponse>();
		
		List<Map<String, InitialdataEntity>> list1 = vipService.getClothingBaseData();
		for( Map<String, InitialdataEntity> map : list1) {
			InitialdataEntity entity = map.get("id");
			BSDResponse responses = vipService.getLoadData(entity);
			list.add(responses);
		}
		// 返回消息
		
		return list;
	}
	
}
