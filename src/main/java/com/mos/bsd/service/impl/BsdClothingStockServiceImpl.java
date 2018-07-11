package com.mos.bsd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.mos.bsd.biz.IBsdClothingBiz;
import com.mos.bsd.biz.IBsdClothingStockBiz;
import com.mos.bsd.biz.IBsdSaleOrderBiz;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.service.IBsdClothingStockService;
import com.mos.bsd.utils.thread.StockCallable;
@Transactional
@Service("com.mos.bsd.service.impl.BsdClothingStockServiceImpl")
public class BsdClothingStockServiceImpl implements IBsdClothingStockService {

	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdClothingStockBizImpl")
	private IBsdClothingStockBiz stockBiz;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdSaleOrderBizImpl")
	private IBsdSaleOrderBiz saleOrderBiz;
	
	@Resource(name="com.mos.bsd.utils.thread.StockCallable")
	private StockCallable callable;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdClothingBizImpl")
	private IBsdClothingBiz clothingBiz;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdClothingStockServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public BSDResponse getData(int type) {
		
		String uuid = UUID.randomUUID().toString();
		//获取需要同步库存的店铺
		List<Map<String, Object>> list = saleOrderBiz.getStoreList();
		List<List<Map<String, Object>>> total = new ArrayList<List<Map<String, Object>>>();
		
		//多线程
		int size = 250;//每条线程处理的数据量
		int count = list.size() / size;
		if (count * size != list.size()) {
			count++;
		}
		int countNum = 0;  
		final CountDownLatch countDownLatch = new CountDownLatch(count);
		ExecutorService executorService = Executors.newFixedThreadPool(8);
		ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
		while( countNum < list.size() ) {
			countNum += size;  
//          StockCallable callable = new StockCallable(); 
            callable.setList(ImmutableList.copyOf(list.subList(countNum - size,countNum < list.size() ? countNum : list.size()))); 
            callable.setUuid(uuid);
            
            @SuppressWarnings("rawtypes")
			ListenableFuture listenableFuture = listeningExecutorService.submit(callable);
            
            Futures.addCallback(listenableFuture, new FutureCallback<List<Map<String, Object>>>() {  
                @Override  
                public void onSuccess(List<Map<String, Object>> list1) {  
                    countDownLatch.countDown();  
                    total.add(list1);  
                }  

                @Override  
                public void onFailure(Throwable throwable) {  
                    countDownLatch.countDown();  
                    logger.info("处理出错：",throwable);  

                }  
            });  
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
//		//循环网点,获取数据(改为使用多线程)
//		for(Map<String, Object> map : list) {
//			
//			if(!String.valueOf(map.get("DEPARTMENT_USER_ID")).equals("A038")) {
//				continue;
//			}
//			
//			Map<String ,List<Map<String, Object>>> couponsGroup_map = stockBiz.getStockData(String.valueOf(map.get("DEPARTMENT_USER_ID")),String.valueOf(map.get("DEPARTMENT_ID")));
//			List<Map<String, Object>> errorList = couponsGroup_map.get("errorList");
//			List<Map<String, Object>> stockList = couponsGroup_map.get("list");
//			//保存到x2
//			stockBiz.saveDataToX2(stockList);
//			
//			total.add(errorList);
//		}
		
		BSDResponse response = new BSDResponse();
		response.setMsgId(uuid);
		response.setStatus("success");
		response.setMsg("同步成功!");
		if( total==null || total.size()<=0 ) {
			response.setErrorData("");
		}else {
			response.setErrorData("错误数据:"+total.toString());
		}
		return response;
		
	}

	@Override
	public BSDResponse getBaseData( int type,Map<String, Object> map ) {
		
		if( map!=null ) {
			String department_id = String.valueOf(map.get("DEPARTMENT_ID"));
			String department_user_id = String.valueOf(map.get("DEPARTMENT_USER_ID"));
			
			//判断类型是增量还是全量 1增量  2全量
			stockBiz.getBaseData(department_id,department_user_id,type);
		}
		

		
		//设置返回消息
		BSDResponse response = new BSDResponse();
		response.setErrorData("");
		response.setMsg("执行完成");
		response.setMsgId("");
		response.setStatus("success");
		return response;
	}

	@Override
	public BSDResponse getLoadBaseData(int type,InitialdataEntity entity) {

		List<String> errorlist = new ArrayList<>();
		if( entity!=null ) {
			clothingBiz.updateData(entity.getUuid(),1);

			boolean flag = true;
			String data = entity.getReceived_data();
			JSONObject obj = JSONObject.parseObject(data);
			//转换数据
			if( obj.containsKey("data") && obj.getJSONArray("data")!=null) {
				JSONArray array = obj.getJSONArray("data");
				String department_id = obj.getString("department_id");
				Map<String,List<Map<String,Object>>> return_map = cleanData(array,department_id);
				List<Map<String,Object>> error_list = return_map.get("error");
				List<Map<String,Object>> data_list = return_map.get("list");
				
				if( data_list!=null && data_list.size()>0 ) {
					try {
						
						stockBiz.saveDataToX2(data_list);
						
					} catch (Exception e) {
						errorlist.add(entity.getUuid());
						logger.error(e.getMessage());
					}
				}
				
				if( error_list!=null&&error_list.size()>0 ) {
					flag = false;
				}
				
			}
			if( !flag ) {
				errorlist.add(entity.getUuid());
			}
			
		}
		
		//将错误数据修改为2状态
		if( errorlist!=null&&errorlist.size()>0 ) {
			clothingBiz.updateData(entity.getUuid(),2);
		}
		
		BSDResponse response = new BSDResponse();
		if( errorlist!=null&&errorlist.size()>0 ) {
			response.setMsg("同步失败");
			response.setErrorData(errorlist.toString());
			response.setStatus("error");
			response.setMsgId(UUID.randomUUID().toString());
		}else {
			response.setMsg("同步成功");
			response.setErrorData("");
			response.setStatus("success");
			response.setMsgId(UUID.randomUUID().toString());
		}
		
		return response;
	}
	
	//处理数据
	private Map<String, List<Map<String, Object>>> cleanData(JSONArray array, String department_id) {
		
		Map<String, List<Map<String,Object>>> return_map = new HashMap<String, List<Map<String,Object>>>();
		List<Map<String,Object>> errorList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> stockList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> stockMap = null;
		Map<String,Object> errorMap = null;
		JSONObject object = null;
		
		if (array != null && array.size() > 0) {
			for( Object obj :array ) {
				
				object = (JSONObject) obj;
				stockMap = new HashMap<String,Object>();
				errorMap = new HashMap<String,Object>();
				//判断不能为空的参数 1.depot_id 2.clothing_id 3.ava_nums 不能为空  版型默认为0
				String  productNo = String.valueOf(object.get("productNo"));
				String  colorNo = String.valueOf(object.get("colorNo"));
				String  sizeNo = String.valueOf(object.get("sizeNo"));
				String  ava_nums = String.valueOf(object.get("availableNum"));
				
				if( productNo==null||"".equals(productNo)||"null".equals(productNo) ) {
					errorMap.put("errorData", object);
					logger.debug(object.toJSONString());
					errorList.add(errorMap);
					continue;
				}
				if (colorNo == null || "".equals(colorNo) || "null".equals(colorNo)) {
					errorMap.put("errorData", object);
					logger.debug(object.toJSONString());
					errorList.add(errorMap);
					continue;
				}
				if (sizeNo == null || "".equals(sizeNo) || "null".equals(sizeNo)) {
					errorMap.put("errorData", object);
					logger.debug(object.toJSONString());
					errorList.add(errorMap);
					continue;
				}
				if (ava_nums == null || "".equals(ava_nums) || "null".equals(ava_nums)) {
					errorMap.put("errorData", object);
					logger.debug(object.toJSONString());
					errorList.add(errorMap);
					continue;
				}
				
				String clothing_id = productNo+colorNo+sizeNo+"0";
				
				stockMap.put("depot_id", department_id);
				stockMap.put("clothing_id", clothing_id);
				
				stockMap.put("now_nums", String.valueOf(object.get("inventoryNum")).equals("null")?"0":object.get("inventoryNum"));
				stockMap.put("setway_nums", String.valueOf(object.get("deliveryOnwayNum")).equals("null")?"0":object.get("deliveryOnwayNum"));
				stockMap.put("getway_nums", String.valueOf(object.get("allocateOnwayNum")).equals("null")?"0":object.get("allocateOnwayNum"));
				stockMap.put("quota_nums", String.valueOf(object.get("lockedNum")).equals("null")?"0":object.get("lockedNum"));
				stockMap.put("ava_nums", object.get("availableNum"));
				
				stockList.add(stockMap);
			}
		}
		
		return_map.put("list", stockList);
		return_map.put("error", errorList);
		
		return return_map;
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData(int type) {
		//获取要同步的数据
		List<Map<String, InitialdataEntity>> list = stockBiz.getClothingBaseData(type);
		return list;
	}

	@Override
	public List<Map<String, Object>> getStoreList() {
		//获取需要同步的店的数据
		List<Map<String, Object>> list = saleOrderBiz.getStoreList();
		return list;
	}

}
