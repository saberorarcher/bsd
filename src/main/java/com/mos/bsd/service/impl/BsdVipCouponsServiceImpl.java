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
import com.mos.bsd.biz.IBsdVipCouponsBiz;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.service.IBsdVipCouponsService;
import com.mos.bsd.utils.thread.VipCouponsCallable;
@Transactional
@Service("com.mos.bsd.service.impl.BsdVipCouponsServiceImpl")
public class BsdVipCouponsServiceImpl implements IBsdVipCouponsService {

	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipCouponsBizImpl")
	private IBsdVipCouponsBiz couponsBiz;
	
	@Resource(name="com.mos.bsd.utils.thread.VipCouponsCallable")
	private VipCouponsCallable callable;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdClothingBizImpl")
	private IBsdClothingBiz clothingBiz;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdVipCouponsServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public BSDResponse getData(String sdate,String edate,int type) {
		
		String uuid = UUID.randomUUID().toString();
		//查询接口数据
		Map<String, List<Map<String,Object>>> coupons_map = couponsBiz.getcouponsGroupData(sdate,edate,type,uuid);
		List<Map<String, Object>> coupons_list = coupons_map.get("list");
		List<Map<String, Object>> error_list = coupons_map.get("error");
		List<Map<String, Object>> error_list1 = new ArrayList<Map<String,Object>>();
		
		if( coupons_list!=null&&coupons_list.size()>0 ) {
			
			//改为多线程写入数据到x2
			int size = 250;//每条线程处理的数据量
			int count = coupons_list.size() / size;
			if (count * size != coupons_list.size()) {
				count++;
			}
			int countNum = 0;  
			final CountDownLatch countDownLatch = new CountDownLatch(count);
			ExecutorService executorService = Executors.newFixedThreadPool(8);
			ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
			while( countNum < coupons_list.size() ) {
				countNum += size;  
	//			VipCouponsCallable callable = new VipCouponsCallable(); 
	            callable.setList(ImmutableList.copyOf(coupons_list.subList(countNum - size,countNum < coupons_list.size() ? countNum : coupons_list.size()))); 
	            
	            @SuppressWarnings("rawtypes")
				ListenableFuture listenableFuture = listeningExecutorService.submit(callable);
	            
	            Futures.addCallback(listenableFuture, new FutureCallback<Map<String,List<Map<String, Object>>>>() {
	                @Override  
	                public void onSuccess(Map<String,List<Map<String, Object>>> map) {
	                    countDownLatch.countDown();
	                    error_list1.addAll(map.get("error"));
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
//		//写入数据到x2
//		Map<String,List<Map<String, Object>>> listMap = couponsBiz.mergeToX2(coupons_list);
		
		String errmsg = "";
		if( error_list!=null && error_list.size()>0 ) {
			errmsg = "以下数据不完整:"+error_list.toString();
		}
		if( error_list1!=null&&error_list1.size()>0 ) {
			errmsg = errmsg + ",以下数据转换不成功:"+error_list1.toString();
		}
		
		BSDResponse response = new BSDResponse();
		response.setStatus("S");
		response.setMsg("同步成功!");
		response.setErrorData(errmsg);
		response.setMsgId(uuid);
		return response;
	}

	@Override
	public BSDResponse getBaseData(int type) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if( type == 2 ) {
			//开始时间2016-03-01  结束时间2018-06-30   每次+29天
			
			Calendar c = Calendar.getInstance();
			c.set(2018, 3, 1);
			
			Calendar c1 = Calendar.getInstance();
			c1.set(2018, 3, 1);
			c1.add(Calendar.DAY_OF_MONTH, 10);
			
			long now = System.currentTimeMillis();
			while ( c.getTimeInMillis()-now<0 ) {
				couponsBiz.getBaseData(sdf.format(new Date(c.getTimeInMillis())),sdf.format(new Date(c1.getTimeInMillis())),type);
				c.add(Calendar.DAY_OF_MONTH, 10);
				c1.add(Calendar.DAY_OF_MONTH, 10);
			}
			
		}else {
			couponsBiz.getBaseData("","",type);
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
	public BSDResponse getLoadData(InitialdataEntity entity) {
		
		List<String> errorlist = new ArrayList<>();
		
		if( entity!=null ) {
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
						Map<String,List<Map<String, Object>>> listMap = couponsBiz.mergeToX2(data_list);
						List<Map<String, Object>> list2 = listMap.get("error");
						if( list2!=null && list2.size()>0 ) {
							errorlist.add(entity.getUuid());
						}
						
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
		
		List<Map<String,Object>> couponsList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> errorList = new ArrayList<Map<String,Object>>();
		Map<String, List<Map<String,Object>>> retunMap = new HashMap<String, List<Map<String,Object>>>();
		
		Map<String,Object> couponsMap ;
		Map<String,Object> errorMap ;
		JSONObject object = null;
		
		if( array!=null && array.size()>0 ) {
			for(Object obj :array) {
				object = (JSONObject) obj;
				couponsMap = new HashMap<>();
				
				//判断不能为空的字段1.coupon_code 2.coupontype_id 3,card_id
				String coupontype_id = String.valueOf(object.get("couponsGroupNo"));
				String coupon_code = String.valueOf(object.get("couponsNo"));
				String card_id = String.valueOf(object.get("memberId"));
				
				if( coupontype_id==null||coupontype_id.equals("")||coupontype_id.equals("null")) {
					errorMap = new HashMap<String,Object>();
					errorMap.put("error", object);
					errorList.add(errorMap);
					logger.error(object.toJSONString());
					continue;
				}
				
				if( coupon_code==null||coupon_code.equals("")||coupon_code.equals("null")) {
					errorMap = new HashMap<String,Object>();
					errorMap.put("error", object);
					errorList.add(errorMap);
					logger.error(object.toJSONString());
					continue;
				}
				
				if( card_id==null||card_id.equals("")||card_id.equals("null")) {
					errorMap = new HashMap<String,Object>();
					errorMap.put("error", object);
					errorList.add(errorMap);
					logger.error(object.toJSONString());
					continue;
				}
				
				couponsMap.put("coupontype_id", object.get("couponsGroupNo"));
				couponsMap.put("coupon_issue_date", String.valueOf(object.get("couponsDate")).equals("null")?"0":object.get("couponsDate"));
				
				couponsMap.put("coupon_code", object.get("couponsNo"));
				couponsMap.put("couponsSource", object.get("couponsSource"));
				couponsMap.put("scheme_id", String.valueOf(object.get("relativeNo")).equals("null")?"0":object.get("relativeNo"));
				couponsMap.put("card_id", object.get("memberId"));
				couponsMap.put("wechatCardFlag", object.get("wechatCardFlag"));
				couponsMap.put("coupon_used", String.valueOf(object.get("useState")).equals("Y")?"1":"0");
				couponsMap.put("coupon_used_date", String.valueOf(object.get("useDate")).equals("null")?"0":object.get("useDate"));
				couponsMap.put("useStore", String.valueOf(object.get("useStore")).equals("")?"0":object.get("useStore"));
				couponsMap.put("bill_id", String.valueOf(object.get("orderNo")).equals("null")?"0":object.get("orderNo"));
				couponsMap.put("coupon_state", String.valueOf(object.get("validFlag")).equals("Y")?"1":"0");
				
				couponsList.add(couponsMap);
			}
		}
		
		retunMap.put("list", couponsList);
		retunMap.put("error", errorList);
	
		return retunMap;
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		//获取要同步的数据
		List<Map<String, InitialdataEntity>> list = couponsBiz.getClothingBaseData();
		
		return list;
	}

}
