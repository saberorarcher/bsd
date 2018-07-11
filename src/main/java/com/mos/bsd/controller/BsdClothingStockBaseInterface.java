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
import com.mos.bsd.service.IBsdClothingStockService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD商品库存接口", description = "BSD商品库存接口", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.BsdClothingStockBaseInterface")
public class BsdClothingStockBaseInterface {
	@Autowired
	@Qualifier("com.mos.bsd.service.impl.BsdClothingStockServiceImpl")
	private IBsdClothingStockService clothingStockService;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdPlanDaoImpl")
	private IBsdPlanDao planDao;
	
	@ApiOperation(value = "BSD商品库存传入X2", notes = "BSD商品库存传入X2")
	@RequestMapping(value = "/clothingStockBase/bsdToX2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public List<BSDResponse> sapToX2Bills(@RequestParam("type") int type) {
		
		String key = "ClothingStockInterface";
		long ts = System.currentTimeMillis();
		
		List<BSDResponse> list2 = new ArrayList<BSDResponse>();
		//获取需要同步的店的数据
		List<Map<String, Object>> list = clothingStockService.getStoreList();
		if( list!=null&& list.size()>0 ) {
			for( Map<String, Object> map:list ) {
//				if(!String.valueOf(map.get("department_user_id")).equals("ZD83")) {
//					continue;
//				}
				// 返回消息
				BSDResponse responses = clothingStockService.getBaseData(type,map);
				list2.add(responses);
			}
		}
		
		planDao.updateTamp(key, String.valueOf(ts));
		return list2;
	}
}
