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

import com.mos.bsd.biz.IBsdTempStoreBiz;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.service.IBsdTempStoreService;
@Transactional
@Service("com.mos.bsd.service.impl.BsdTempStoreServiceImpl")
public class BsdTempStoreServiceImpl implements IBsdTempStoreService {
	
	private static final Logger logger = LoggerFactory.getLogger(BsdTempStoreServiceImpl.class);
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdTempStoreBizImpl")
	private IBsdTempStoreBiz storeBiz;
	
	@Override
	public BSDResponse getData() {
		
		boolean flag = true;
		BSDResponse response = new BSDResponse();
		long start = System.currentTimeMillis();
		
		logger.info("*****************开始获取数据*****************");
		logger.info(String.valueOf(start));
		
		//获取数据
		List<Map<String, Object>> list = storeBiz.getData();
		//存入临时表
		try {
			storeBiz.insertTemp(list);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		logger.info("*****************保存数据成功*****************");
		long end = System.currentTimeMillis();
		logger.info(String.valueOf(end));
		logger.info("*****************耗时*****************");
		logger.info(String.valueOf((end-start)/1000)+"秒");
		
		if(flag) {
			response.setStatus("success");
			response.setMsg("同步成功");
			response.setMsgId(String.valueOf(UUID.randomUUID()));
		}else {
			response.setStatus("error");
			response.setMsg("同步失败");
			response.setMsgId(String.valueOf(UUID.randomUUID()));
		}
		
		return response;
	}

}
