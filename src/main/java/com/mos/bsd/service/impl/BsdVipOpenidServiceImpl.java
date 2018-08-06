package com.mos.bsd.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.mos.bsd.biz.IBsdVipOpenidBiz;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.service.IBsdVipOpenidService;
import com.mos.bsd.utils.thread.VipOpenidCallable;
@Transactional
@Service("com.mos.bsd.service.impl.BsdVipOpenidServiceImpl")
public class BsdVipOpenidServiceImpl implements IBsdVipOpenidService {

	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipOpenidBizImpl")
	private IBsdVipOpenidBiz openid_biz;
	
	@Resource(name="com.mos.bsd.utils.thread.VipOpenidCallable")
	private VipOpenidCallable callable;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdClothingBizImpl")
	private IBsdClothingBiz clothingBiz;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdVipOpenidServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public BSDResponse getData(String sdate, String edate, int type) {
		
		String uuid = UUID.randomUUID().toString();
		List<Map<String, Object>> vipOpenid_list = openid_biz.getVipOpenIdData(sdate,edate,type,uuid);
		List<Map<String, Object>> errorlist = new ArrayList<Map<String,Object>>();
		//转换成多线程方式
		if( vipOpenid_list!=null && vipOpenid_list.size()>0 ) {
			//转换保存方式为多线程
			int size = 1000;//每条线程处理的数据量
			int count = vipOpenid_list.size() / size;
			if (count * size != vipOpenid_list.size()) {
				count++;
			}
			int countNum = 0;  
			final CountDownLatch countDownLatch = new CountDownLatch(count);
			ExecutorService executorService = Executors.newFixedThreadPool(8);
			ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
			while( countNum < vipOpenid_list.size() ) {
				countNum += size;  
//				VipCallable callable = new VipCallable(); 
	            callable.setList(ImmutableList.copyOf(vipOpenid_list.subList(countNum - size,countNum < vipOpenid_list.size() ? countNum : vipOpenid_list.size()))); 
	            
	            @SuppressWarnings("rawtypes")
				ListenableFuture listenableFuture = listeningExecutorService.submit(callable);
	            
	            Futures.addCallback(listenableFuture, new FutureCallback<List<Map<String, Object>>>() {  
	                @Override  
	                public void onSuccess(List<Map<String, Object>> list) {  
	                    countDownLatch.countDown();  
	                    errorlist.addAll(list);
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
		}
		
		
		//写入到X2
//		openid_biz.mergeToX2(vipOpenid_list);
		
		BSDResponse response = new BSDResponse();
		if( errorlist!=null&&errorlist.size()>0 ) {
			response.setErrorData(response.toString());
		}else{
			response.setErrorData("");
		}
		response.setStatus("success");
		response.setMsg("同步成功!");
		response.setMsgId(uuid);
		return response;
		
	}

	@Override
	public BSDResponse getBaseData(int type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if( type == 2 ) {
			//开始时间2009-01-01  结束时间2018-06-30   每次+29天
			
			Calendar c = Calendar.getInstance();
			c.set(2016, 3, 1);
			
			Calendar c1 = Calendar.getInstance();
			c1.set(2016, 3, 1);
			c1.add(Calendar.DAY_OF_MONTH, 29);
			
			long now = System.currentTimeMillis();
			while ( c.getTimeInMillis()-now<0 ) {
				
				openid_biz.getVipBaseData(sdf.format(new Date(c.getTimeInMillis())),sdf.format(new Date(c1.getTimeInMillis())),type);
				
				c.add(Calendar.DAY_OF_MONTH, 29);
				c1.add(Calendar.DAY_OF_MONTH, 29);
				
			}
			
		}else {
			openid_biz.getVipBaseData("","",type);
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
	public BSDResponse getLoadBaseData(InitialdataEntity entity) {
		
		List<String> errorlist = new ArrayList<>();
		
		if( entity !=null ) {
			clothingBiz.updateData(entity.getUuid(),1);

			boolean flag = true;
			String data = entity.getReceived_data();
			JSONObject obj = JSONObject.parseObject(data);
			
			//转换数据
			if( obj.containsKey("data") && obj.getJSONArray("data")!=null) {
				JSONArray array = obj.getJSONArray("data");
				Map<String,List<Map<String,Object>>> return_map = cleanData(array);
				List<Map<String,Object>> error_list = return_map.get("error");
				List<Map<String,Object>> data_list = return_map.get("list");
				
				if( data_list!=null && data_list.size()>0 ) {
					try {
						//处理业务逻辑
						openid_biz.mergeToX2(data_list);
						
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
	private Map<String, List<Map<String, Object>>> cleanData(JSONArray array) {
		Map<String, List<Map<String,Object>>> rturnMap = new HashMap<>();
		List<Map<String,Object>> couponsGroupList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> errorList = new ArrayList<Map<String,Object>>();
		Map<String,Object> couponsGroupMap ;
		Map<String,Object> errorMap ;
		JSONObject object = null;
		
		if( array!=null && array.size()>0 ) {
			for(Object obj :array) {
				object = (JSONObject) obj;
				couponsGroupMap = new HashMap<>();
				
				//判断不能为空的参数 card_id,binding_val
				String card_id = String.valueOf(object.get("memberId"));
				String binding_val = String.valueOf(object.get("openId"));
				
				if(card_id==null||"".equals(card_id)||"null".equals(card_id)) {
					errorMap = new HashMap<String,Object>();
					errorMap.put("errorData", object);
					errorList.add(errorMap);
					logger.error(object.toJSONString());
					continue;
				}
				
				if(binding_val==null||"".equals(binding_val)||"null".equals(binding_val)) {
					errorMap = new HashMap<String,Object>();
					errorMap.put("errorData", object);
					errorList.add(errorMap);
					logger.error(object.toJSONString());
					continue;
				}
				
				couponsGroupMap.put("card_id", object.get("memberId"));
				couponsGroupMap.put("binding_app", object.get("publicNo"));
				couponsGroupMap.put("binding_val", object.get("openId"));
				couponsGroupMap.put("wechatCardFlag", object.get("wechatCardFlag"));
				couponsGroupMap.put("wechatCardCode", object.get("wechatCardCode"));
				couponsGroupMap.put("warmHomeFlag", object.get("warmHomeFlag"));
				couponsGroupMap.put("concernFlag", object.get("concernFlag"));
				couponsGroupMap.put("create_date", object.get("bindTime"));
				couponsGroupMap.put("classifyNo", object.get("classifyNo"));
				couponsGroupMap.put("remark", object.get("remark"));
				
				couponsGroupList.add(couponsGroupMap);
			}
		}
		rturnMap.put("list", couponsGroupList);
		rturnMap.put("error", errorList);
		return rturnMap;
		
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		//获取要同步的数据
		List<Map<String, InitialdataEntity>> list = openid_biz.getClothingBaseData();
		return list;
	}
		
}
