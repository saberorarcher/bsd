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
import com.mos.bsd.biz.IBsdCouponsGroupBiz;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.service.IBsdCouponsGroupService;
import com.mos.bsd.utils.thread.CouponsGroupCallable;
/**
 * 券组档案接口
 * @author hao
 *
 */
@Transactional
@Service("com.mos.bsd.service.impl.BsdCouponsGroupServiceImpl")
public class BsdCouponsGroupServiceImpl implements IBsdCouponsGroupService {

	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdClothingBizImpl")
	private IBsdClothingBiz clothingBiz;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdCouponsGroupBizImpl")
	private IBsdCouponsGroupBiz couponsGroup_biz;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdCouponsGroupServiceImpl.class);
	
	@Resource(name="com.mos.bsd.utils.thread.CouponsGroupCallable")
	private CouponsGroupCallable callable;
	
	@SuppressWarnings("unchecked")
	@Override
	public BSDResponse getData(String begdate,String enddate,int type) {
		String uuid = UUID.randomUUID().toString();
		//调用http请求.获取数据
		Map<String,List<Map<String, Object>>> couponsGroup_map = couponsGroup_biz.getcouponsGroupData(begdate,enddate,type,uuid);
		
		List<Map<String, Object>> couponsGroup_list = couponsGroup_map.get("list");
		List<Map<String, Object>> errorGroup_list = couponsGroup_map.get("error");
		if( couponsGroup_list!=null&&couponsGroup_list.size()>0 ) {
			//修改为,使用多线程
			int size = 250;//每条线程处理的数据量
			int count = couponsGroup_list.size() / size;
			if (count * size != couponsGroup_list.size()) {
				count++;
			}
			int countNum = 0;  
			final CountDownLatch countDownLatch = new CountDownLatch(count);
			ExecutorService executorService = Executors.newFixedThreadPool(8);
			ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
			while( countNum < couponsGroup_list.size() ) {
				countNum += size;  
//				CouponsGroupCallable callable = new CouponsGroupCallable(); 
	            callable.setList(ImmutableList.copyOf(couponsGroup_list.subList(countNum - size,countNum < couponsGroup_list.size() ? countNum : couponsGroup_list.size()))); 
	            
	            @SuppressWarnings("rawtypes")
				ListenableFuture listenableFuture = listeningExecutorService.submit(callable);
	            
	            Futures.addCallback(listenableFuture, new FutureCallback<Integer>() {  
	                @Override  
	                public void onSuccess(Integer i) {  
	                    countDownLatch.countDown();  
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
		
		BSDResponse response = new BSDResponse();
		response.setStatus("success");
		response.setMsg("同步成功!");
		response.setMsgId(uuid);
		if( errorGroup_list==null || errorGroup_list.size()<=0 ) {
			response.setErrorData("");
		}else {
			response.setErrorData("错误数据:"+errorGroup_list.toString());
		}
		
		return response;
	}

	@Override
	public BSDResponse getBaseData( int type) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if( type == 2 ) {
			//开始时间2016-03-01  结束时间2018-06-30   每次+29天
			
			Calendar c = Calendar.getInstance();
			c.set(2016, 3, 1);
			
			Calendar c1 = Calendar.getInstance();
			c1.set(2016, 3, 1);
			c1.add(Calendar.DAY_OF_MONTH, 29);
			
			long now = System.currentTimeMillis();
			while ( c.getTimeInMillis()-now<0 ) {
				couponsGroup_biz.getBaseData(sdf.format(new Date(c.getTimeInMillis())),sdf.format(new Date(c1.getTimeInMillis())),type);
				c.add(Calendar.DAY_OF_MONTH, 29);
				c1.add(Calendar.DAY_OF_MONTH, 29);
			}
			
		}else {
			couponsGroup_biz.getBaseData("","",type);
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
						couponsGroup_biz.mergeIntoX2(data_list);
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

	private Map<String,List<Map<String, Object>>> cleanData(JSONArray array) {
		JSONObject object;
		Map<String,Object> couponsGroupMap;
		Map<String,Object> errorGroupMap;
		List<Map<String,Object>> errorGroupList = new ArrayList<>();
		List<Map<String,Object>> couponsGroupList = new ArrayList<>();
		Map<String,List<Map<String, Object>>> returnMap= new HashMap<String,List<Map<String, Object>>>();
		
		if( array!=null && array.size()>0 ) {
			for(Object obj :array) {
				object = (JSONObject) obj;
				couponsGroupMap = new HashMap<String,Object>();
				
				//判断不能为空的数据
				String coupontype_id = String.valueOf(object.get("couponsGroupNo"));
				if( coupontype_id==null||coupontype_id.equals("")||coupontype_id.equals("null") ) {
					errorGroupMap = new HashMap<String,Object>();
					errorGroupMap.put("errorData", object);
					errorGroupList.add(errorGroupMap);
					continue;
				}
				
				String couponsGroupName = String.valueOf(object.get("couponsGroupName"));
				if( couponsGroupName==null||couponsGroupName.equals("")||couponsGroupName.equals("null") ) {
					errorGroupMap = new HashMap<String,Object>();
					errorGroupMap.put("errorData", object);
					errorGroupList.add(errorGroupMap);
					continue;
				}
				
				couponsGroupMap.put("coupontype_id", object.get("couponsGroupNo"));
				couponsGroupMap.put("coupontype_name", object.get("couponsGroupName"));
				couponsGroupMap.put("coupontype_sort", object.get("couponsType"));
				couponsGroupMap.put("coupon_valid_begdate", object.get("beginDate"));
				couponsGroupMap.put("coupon_valid_enddate", object.get("endDate"));
				couponsGroupMap.put("coupon_value", object.get("couponsAmount"));
				couponsGroupMap.put("couponsDiscount", object.get("couponsDiscount"));
				couponsGroupMap.put("birthdayCouponsDiscount", object.get("birthdayCouponsDiscount"));
				couponsGroupMap.put("minDiscount", String.valueOf(object.get("minDiscount")).equals("null")?"0":object.get("minDiscount"));
				couponsGroupMap.put("minConsumption", String.valueOf(object.get("minConsumption")).equals("null")?"0":object.get("minConsumption"));
				couponsGroupMap.put("maxDiscountAmount", String.valueOf(object.get("maxDiscountAmount")).equals("null")?"0":object.get("maxDiscountAmount"));
				couponsGroupMap.put("multipleFlag", object.get("multipleFlag"));
				couponsGroupMap.put("promotionFlag", object.get("promotionFlag"));
				couponsGroupMap.put("discountType", String.valueOf(object.get("discountType")).equals("null")?"1":object.get("discountType"));
				couponsGroupMap.put("smsVerify", object.get("smsVerify"));
				couponsGroupMap.put("coupontype_state", String.valueOf(object.get("validFlag")).equals("Y")?"1":"-1");
				couponsGroupMap.put("remark", object.get("remark"));
				couponsGroupMap.put("innerPurchaseFlag", object.get("innerPurchaseFlag"));
				couponsGroupMap.put("registerSendFlag", object.get("registerSendFlag"));
				couponsGroupMap.put("relativeWechatCard", object.get("relativeWechatCard"));
				couponsGroupMap.put("markBirthday", object.get("markBirthday"));
				couponsGroupMap.put("useFlag", String.valueOf(object.get("useFlag")).equals("null")?"0":object.get("useFlag"));
				
				couponsGroupList.add(couponsGroupMap);
			}
			
			returnMap.put("list", couponsGroupList);
			returnMap.put("error", errorGroupList);
		}
		
		return returnMap;
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		//获取要同步的数据
		List<Map<String, InitialdataEntity>> list = couponsGroup_biz.getClothingBaseData();
		return list;
	}
	
}
