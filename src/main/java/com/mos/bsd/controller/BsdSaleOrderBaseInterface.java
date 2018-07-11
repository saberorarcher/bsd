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

import com.mos.bsd.dao.IBsdPlanDao;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.service.IBsdSaleOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD订单原始数据保存进x2", description = "BSD订单原始数据保存进x2", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdSaleOrderBaseInterface")
public class BsdSaleOrderBaseInterface {
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdSaleOrderServiceImpl")
	private IBsdSaleOrderService saleOrderService;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdPlanDaoImpl")
	private IBsdPlanDao planDao;
	
	@ApiOperation(value = "BSD订单原始数据保存进x2", notes = "BSD订单原始数据保存进x2")
	@RequestMapping(value = "/saleOrderBase/bsdToX2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public List<BSDResponse> sapToX2Bills(@RequestParam("type") int type) {
		
		String key = "SaleOrderInterface";
		long ts = System.currentTimeMillis();
		
		List<BSDResponse> list2 = new ArrayList<BSDResponse>();
		List<Map<String, Object>> list = saleOrderService.getStoreList();
		if( list!=null && list.size()>0 ) {
			for( Map<String, Object> map: list) {
				// 返回消息
				BSDResponse responses = saleOrderService.getBaseData(type, map);
				list2.add(responses);
			}
		}
		planDao.updateTamp(key, String.valueOf(ts));
		
		return list2;
	}
}
