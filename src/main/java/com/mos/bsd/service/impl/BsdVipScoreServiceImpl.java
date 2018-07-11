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
import com.mos.bsd.biz.IBsdVipScoreBiz;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.service.IBsdVipScoreService;
import com.mos.bsd.utils.thread.VipScoreCallable;
/**
 * bsd 会员积分service
 * @author hao
 *
 */
@Transactional
@Service("com.mos.bsd.service.impl.BsdVipScoreServiceImpl")
public class BsdVipScoreServiceImpl implements IBsdVipScoreService {

	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipScoreBizImpl")
	private IBsdVipScoreBiz vipScore_biz;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdClothingBizImpl")
	private IBsdClothingBiz clothingBiz;
	
	@Resource(name="com.mos.bsd.utils.thread.VipScoreCallable")
	private VipScoreCallable callable;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdVipScoreServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public BSDResponse getData(String sdate,String edate,int type) {
		String uuid = UUID.randomUUID().toString();
		//调用接口,获取数据
		Map<String,List<Map<String, Object>>> vipScore_map= vipScore_biz.getVipScoreData(sdate,edate,type,uuid);
		List<Map<String, Object>> vipScore_list = vipScore_map.get("list");
		List<Map<String, Object>> error_list = vipScore_map.get("error");
		
		if( vipScore_list!=null&&vipScore_list.size()>0 ) {
			//转换数据保存方式为多线程
			int size = 1000;//每条线程处理的数据量
			int count = vipScore_list.size() / size;
			if (count * size != vipScore_list.size()) {
				count++;
			}
			int countNum = 0;  
			final CountDownLatch countDownLatch = new CountDownLatch(count);
			ExecutorService executorService = Executors.newFixedThreadPool(8);
			ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
			while( countNum < vipScore_list.size() ) {
				countNum += size;  
//				VipScoreCallable callable = new VipScoreCallable();
	            callable.setList(ImmutableList.copyOf(vipScore_list.subList(countNum - size,countNum < vipScore_list.size() ? countNum : vipScore_list.size()))); 
	            
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
		//旧的处理方式
//		if( vipScore_list!=null&&vipScore_list.size()>0 ) {
//			
//			int num = vipScore_list.size()%50000>0?(vipScore_list.size()/50000)+1:vipScore_list.size()/50000;
//			
//			for(int i=0;i<num;i++) {
//				List<Map<String,Object>> tem_list = new ArrayList<Map<String,Object>>();
//				if( i+1==num ) {
//					tem_list.addAll(vipScore_list.subList(i*50000, vipScore_list.size()));
//				}else {
//					tem_list.addAll(vipScore_list.subList(i*50000, (i+1)*50000));
//				}
//			
//				//写入x2
//				vipScore_biz.updateVipScore(tem_list);
//				
//				//写入bsd会员扩展信息
//				vipScore_biz.updateVipScoreExtend(tem_list);
//			}
//		}
		
		BSDResponse response = new BSDResponse();
		response.setStatus("S");
		response.setMsg("同步成功!");
		response.setMsgId(uuid);
		if( error_list!=null&&error_list.size()>0 ) {
			response.setErrorData(error_list.toString());
		}else{
			response.setErrorData("");
		}
		
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
			c1.add(Calendar.DAY_OF_MONTH, 10);
			
			long now = System.currentTimeMillis();
			while ( c.getTimeInMillis()-now<0 ) {
				
				vipScore_biz.getVipBaseData(sdf.format(new Date(c.getTimeInMillis())),sdf.format(new Date(c1.getTimeInMillis())),type);
				c.add(Calendar.DAY_OF_MONTH, 10);
				c1.add(Calendar.DAY_OF_MONTH, 10);
				
			}
			
		}else {
			vipScore_biz.getVipBaseData("","",type);
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
					
					//写入x2
					vipScore_biz.updateVipScore(data_list);
					
					//写入bsd会员扩展信息
					vipScore_biz.updateVipScoreExtend(data_list);
					
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
	
	//转换数据
	private Map<String, List<Map<String, Object>>> cleanData(JSONArray array) {
		
		Map<String, List<Map<String,Object>>> return_map = new HashMap<String, List<Map<String,Object>>>();
		List<Map<String,Object>> vipScoreList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> errorList = new ArrayList<Map<String,Object>>();
		Map<String,Object> vipScoreMap ;
		Map<String,Object> errorMap ;
		JSONObject object = null;
		
		if( array!=null && array.size()>0 ) {
			for(Object obj :array) {
				object = (JSONObject) obj;
				vipScoreMap = new HashMap<>();
				errorMap = new HashMap<>();
				
				//判断非空的参数
				String card_id = String.valueOf(object.get("memberId"));
				if(card_id==null || "".equals(card_id)||"null".equals(card_id)) {
					errorMap.put("errorData", object);
					errorList.add(errorMap);
					logger.debug(object.toJSONString());
					continue;
				}
				
				vipScoreMap.put("card_id", object.get("memberId"));
				vipScoreMap.put("department_id", object.get("storeNo"));
				vipScoreMap.put("vip_retail_cnt", String.valueOf(object.get("totalTimes")).equals("null")?"0":object.get("totalTimes"));
				vipScoreMap.put("totalNum", String.valueOf(object.get("totalNum")).equals("null")?"0":object.get("totalNum"));
				vipScoreMap.put("vip_retail_sums", String.valueOf(object.get("totalAmount")).equals("null")?"0":object.get("totalAmount"));
				vipScoreMap.put("vip_retail_score", String.valueOf(object.get("totalPoint")).equals("null")?"0":object.get("totalPoint"));
				vipScoreMap.put("usedPoint", String.valueOf(object.get("usedPoint")).equals("null")?"0":object.get("usedPoint"));
				vipScoreMap.put("adjustPoint", String.valueOf(object.get("adjustPoint")).equals("null")?"0":object.get("adjustPoint"));
				vipScoreMap.put("vip_score", String.valueOf(object.get("canusePoint")).equals("null")?"0":object.get("canusePoint"));
				vipScoreMap.put("pointAbnormalTimes", String.valueOf(object.get("pointAbnormalTimes")).equals("null")?"0":object.get("pointAbnormalTimes"));
				vipScoreMap.put("vip_lastbuy_date", String.valueOf(object.get("lastDate")).equals("null")?"0":object.get("lastDate"));
				vipScoreMap.put("lastNum", String.valueOf(object.get("lastNum")).equals("null")?"0":object.get("lastNum"));
				vipScoreMap.put("lastAmount", String.valueOf(object.get("lastAmount")).equals("null")?"0":object.get("lastAmount"));
				
				vipScoreList.add(vipScoreMap);
			}
		}
		
		return_map.put("list", vipScoreList);
		return_map.put("error", errorList);
		return return_map;
		
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		//获取要同步的数据
		List<Map<String, InitialdataEntity>> list = vipScore_biz.getClothingBaseData();
		return list;
	}

}
