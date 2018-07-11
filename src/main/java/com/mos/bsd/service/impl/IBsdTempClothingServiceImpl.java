package com.mos.bsd.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mos.bsd.biz.IBsdClothingBiz;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.service.IBsdTempClothingService;

@Transactional
@Service("com.mos.bsd.service.impl.IBsdTempClothingServiceImpl")
public class IBsdTempClothingServiceImpl implements IBsdTempClothingService {

	private static final Logger logger = LoggerFactory.getLogger(IBsdTempClothingServiceImpl.class);
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdClothingBizImpl")
	private IBsdClothingBiz clothingBiz;
	
	@Override
	public BSDResponse getData() {
		boolean flag = true;
		
		long start = System.currentTimeMillis();
		
		logger.info("******************开始获取数据*********************");
		logger.info(String.valueOf(start));
		//从接口获取数据
		List<Map<String, Object>> list = clothingBiz.getData();
		//存到x2临时表(状态为0,草稿状态)
		try {
			clothingBiz.insertToX2(list);
		} catch (Exception e) {
			flag = false;
			logger.error(e.getMessage());
		}
		
		long end = System.currentTimeMillis();
		logger.info("******************获取数据结束*********************");
		logger.info(String.valueOf(end));
		logger.info("******************耗时*********************");
		logger.info(String.valueOf((end-start)/1000)+"秒");
		
		BSDResponse response = new BSDResponse();
		if(!flag) {
			response.setMsgId(UUID.randomUUID().toString());
			response.setMsg("保存失败");
			response.setStatus("error");
			response.setErrorData(list.toString());
		}else {
			response.setMsgId(UUID.randomUUID().toString());
			response.setMsg("保存成功");
			response.setStatus("success");
			response.setErrorData("");
		}
		
		return response;
	}

}
