package com.mos.bsd.service.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.scheduling.annotation.Scheduled;
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
import com.mos.bsd.biz.IBsdSaleOrderBiz;
import com.mos.bsd.biz.IBsdStoreBiz;
import com.mos.bsd.biz.IBsdVipBiz;
import com.mos.bsd.biz.IBsdVipCouponsBiz;
import com.mos.bsd.biz.IBsdVipScoreBiz;
import com.mos.bsd.service.impl.BsdClothingServiceImpl;
import com.mos.bsd.utils.thread.ClothingCallable;
import com.mos.bsd.utils.thread.CouponsGroupCallable;
import com.mos.bsd.utils.thread.SaleOrderCallable;
import com.mos.bsd.utils.thread.StockCallable;
import com.mos.bsd.utils.thread.VipCallable;
import com.mos.bsd.utils.thread.VipCouponsCallable;
import com.mos.bsd.utils.thread.VipScoreCallable;
/**
 * 定时任务服务
 * @author hao
 *
 */
@Transactional
@Service("com.mos.bsd.service.scheduler.AutoDataServiceImpl")
public class AutoDataServiceImpl {
private static final Logger logger = LoggerFactory.getLogger(BsdClothingServiceImpl.class);
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdClothingBizImpl")
	private IBsdClothingBiz clothingBiz;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipBizImpl")
	private IBsdVipBiz vip_biz;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipScoreBizImpl")
	private IBsdVipScoreBiz vipScore_biz;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipCouponsBizImpl")
	private IBsdVipCouponsBiz couponsBiz;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdCouponsGroupBizImpl")
	private IBsdCouponsGroupBiz couponsGroup_biz;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdStoreBizImpl")
	private IBsdStoreBiz store_Biz;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdSaleOrderBizImpl")
	private IBsdSaleOrderBiz saleOrderBiz;
	
	@Resource(name="com.mos.bsd.utils.thread.CouponsGroupCallable")
	private CouponsGroupCallable couponsGroupCallable;
	
	@Resource(name="com.mos.bsd.utils.thread.SaleOrderCallable")
	private SaleOrderCallable saleOrderCallable;
	
	@Resource(name="com.mos.bsd.utils.thread.StockCallable")
	private StockCallable stockCallable;
	
	@Resource(name="com.mos.bsd.utils.thread.VipCallable")
	private VipCallable vipCallable;
	
	@Resource(name="com.mos.bsd.utils.thread.VipCouponsCallable")
	private VipCouponsCallable vipCouponsCallable;
	
	@Resource(name="com.mos.bsd.utils.thread.VipScoreCallable")
	private VipScoreCallable vipScoreCallable;
	
	/**
	 * 商品
	 */
	@SuppressWarnings("unchecked")
//	@Scheduled(initialDelay = 300000,fixedDelay=14180000)
	public void getClothingData() {

		long start = System.currentTimeMillis();
		logger.info("**************开始同步商品************");
		logger.info(String.valueOf(start));
		JSONArray array = new JSONArray();
		
		//调用bsd接口获取数据
		Map<String,List<Map<String,Object>>> map = clothingBiz.getClothingData(1,"");
		
		List<Map<String,Object>> productList = map.get("productList");
		
		if( productList!=null && productList.size()>0 ) {
			
			//转换成多线程方式提交数据
			int size = 250;//每条线程处理的数据量
			int count = productList.size() / size;
			if (count * size != productList.size()) {
				count++;
			}
			int countNum = 0;  
			final CountDownLatch countDownLatch = new CountDownLatch(count);
			ExecutorService executorService = Executors.newFixedThreadPool(8);
			ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
			while( countNum < productList.size() ) {
				countNum += size;  
				ClothingCallable callable = new ClothingCallable(); 
	            callable.setList(ImmutableList.copyOf(productList.subList(countNum - size,countNum < productList.size() ? countNum : productList.size()))); 
	            
	            @SuppressWarnings("rawtypes")
				ListenableFuture listenableFuture = listeningExecutorService.submit(callable);
	            
	            Futures.addCallback(listenableFuture, new FutureCallback<JSONObject>() {  
	                @Override  
	                public void onSuccess(JSONObject jsonObject) {  
	                    countDownLatch.countDown();  
	                    if( jsonObject.containsKey("GetStatus")&&!jsonObject.getBoolean("GetStatus") ) {
	                    	array.add(jsonObject.getString("GetErrorData"));
	                    }
	                }  
	
	                @Override  
	                public void onFailure(Throwable throwable) {  
	                    countDownLatch.countDown();  
	                    logger.info("处理出错：",throwable);  
	
	                }  
	            });  
	            
			}
		}
		logger.info("**************同步完成************");
		long end = System.currentTimeMillis();
		logger.info("错误数据:"+array.toJSONString());
		logger.info("**************耗时************"+(end-start)/1000+"秒");
		logger.info("同步成功!");
	}
	
	/**
	 * 会员
	 */
	@SuppressWarnings("unchecked")
//	@Scheduled(initialDelay = 200000,fixedDelay=7180000)
	public void getVipData() {
		
		long start = System.currentTimeMillis();
		logger.info("**************开始同步会员************");
		logger.info(String.valueOf(start));
		
		//调用bsd接口获取数据
		Map<String,List<Map<String, Object>>> vipMap = vip_biz.getVipData("","",1,"");
		
		List<Map<String, Object>> vipList = vipMap.get("list");
		List<Map<String, Object>> errorList = vipMap.get("error");
		List<Map<String, Object>> errorVipList = new ArrayList<Map<String,Object>>();
		
		if( vipList!=null && vipList.size()>0 ) {
		
			//转换保存方式为多线程
			int size = 1000;//每条线程处理的数据量
			int count = vipList.size() / size;
			if (count * size != vipList.size()) {
				count++;
			}
			int countNum = 0;  
			final CountDownLatch countDownLatch = new CountDownLatch(count);
			ExecutorService executorService = Executors.newFixedThreadPool(8);
			ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
			while( countNum < vipList.size() ) {
				countNum += size;  
//				VipCallable callable = new VipCallable(); 
				vipCallable.setList(ImmutableList.copyOf(vipList.subList(countNum - size,countNum < vipList.size() ? countNum : vipList.size()))); 
	            
	            @SuppressWarnings("rawtypes")
				ListenableFuture listenableFuture = listeningExecutorService.submit(vipCallable);
	            
	            Futures.addCallback(listenableFuture, new FutureCallback<List<Map<String, Object>>>() {  
	                @Override  
	                public void onSuccess(List<Map<String, Object>> list) {  
	                    countDownLatch.countDown();  
	                    errorVipList.addAll(list);
	                }  
	
	                @Override  
	                public void onFailure(Throwable throwable) {  
	                    countDownLatch.countDown();  
	                    logger.info("处理出错：",throwable);  
	
	                }  
	            });  
	            
			}
		}
		
		//判断长度,每次执行1000条数据
//		int num = newVipList.size()%5000>0?(newVipList.size()/5000)+1:newVipList.size()/5000;
//		
//		for(int i=0;i<num;i++) {
//			List<Map<String,Object>> tem_list = new ArrayList<Map<String,Object>>();
//			if( i+1==num ) {
//				tem_list.addAll(newVipList.subList(i*5000, newVipList.size()));
//			}else {
//				tem_list.addAll(newVipList.subList(i*5000, (i+1)*5000));
//			}
//			
//			//写入D0210
//			vip_biz.insertD0210(tem_list);					
//			
//			//写入P0290
//			vip_biz.insertP0290(tem_list);
//		}
		
		String errmsg = "";
		if( errorList!=null && errorList.size()>0 ) {
			errmsg = "以下数据不完整:"+errorList.toString();
			logger.error(errmsg);
		}
		if( errorVipList!=null&&errorVipList.size()>0 ) {
			errmsg = errmsg + ",未找到下列会员店铺:"+errorList.toString();
			logger.error(errmsg);
		}
		
		logger.info("**************同步完成************");
		long end = System.currentTimeMillis();
		logger.info("**************耗时************"+(end-start)/1000+"秒");
		
		logger.info("同步成功!");
		logger.info("错误数据:"+errmsg);
	}
	
	/**
	 * 会员积分
	 */
	@SuppressWarnings("unchecked")
//	@Scheduled(initialDelay = 300000,fixedDelay=7180000)
	public void getVipScoreData() {
		
		long start = System.currentTimeMillis();
		logger.info("**************开始同步会员积分************");
		logger.info(String.valueOf(start));
		
		//调用接口,获取数据
		Map<String,List<Map<String, Object>>> vipScore_map= vipScore_biz.getVipScoreData("","",1,"");
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
				vipScoreCallable.setList(ImmutableList.copyOf(vipScore_list.subList(countNum - size,countNum < vipScore_list.size() ? countNum : vipScore_list.size()))); 
	            
	            @SuppressWarnings("rawtypes")
				ListenableFuture listenableFuture = listeningExecutorService.submit(vipScoreCallable);
	            
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
		}
		
		//原处理方式
//		if( vipScore_list!=null&&vipScore_list.size()>0 ) {
//			
//			int num = vipScore_list.size()%50000>0?(vipScore_list.size()/50000)+1:vipScore_list.size()/50000;
//			for(int i=0;i<num;i++) {
//				List<Map<String,Object>> tem_list = new ArrayList<Map<String,Object>>();
//				if( i+1==num ) {
//					tem_list.addAll(vipScore_list.subList(i*50000, vipScore_list.size()));
//				}else {
//					tem_list.addAll(vipScore_list.subList(i*50000, (i+1)*50000));
//				}
//				//写入x2
//				vipScore_biz.updateVipScore(tem_list);
//				//写入bsd会员扩展信息
//				vipScore_biz.updateVipScoreExtend(tem_list);
//			}
//			
//		}
		
		logger.info("同步成功!");
		if( error_list!=null&&error_list.size()>0 ) {
			logger.error("错误数据:"+error_list.toString());
		}
		logger.info("**************同步完成************");
		long end = System.currentTimeMillis();
		logger.info("**************耗时************"+(end-start)/1000+"秒");
		
	}
	
	/**
	 * 会员券
	 */
	@SuppressWarnings("unchecked")
//	@Scheduled(initialDelay = 400000,fixedDelay=7180000)
	public void getVipCouponsData() {
		
		long start = System.currentTimeMillis();
		logger.info("**************开始同步会员券************");
		logger.info(String.valueOf(start));
		
		//查询接口数据
		Map<String, List<Map<String,Object>>> coupons_map = couponsBiz.getcouponsGroupData("","",1,"");
		List<Map<String, Object>> coupons_list = coupons_map.get("list");
		List<Map<String, Object>> error_list = coupons_map.get("error");
		List<Map<String, Object>> error_list1 = new ArrayList<Map<String,Object>>();
		
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
			vipCouponsCallable.setList(ImmutableList.copyOf(coupons_list.subList(countNum - size,countNum < coupons_list.size() ? countNum : coupons_list.size()))); 
            
            @SuppressWarnings("rawtypes")
			ListenableFuture listenableFuture = listeningExecutorService.submit(vipCouponsCallable);
            
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
		
		//写入数据到x2(原有方式)
//		Map<String,List<Map<String, Object>>> listMap = couponsBiz.mergeToX2(coupons_list);
//		List<Map<String, Object>> error_list1 = listMap.get("error");
		
		String errmsg = "";
		if( error_list!=null && error_list.size()>0 ) {
			errmsg = "以下数据不完整:"+error_list.toString();
			logger.error(errmsg);
		}
		if( error_list1!=null&&error_list1.size()>0 ) {
			errmsg = errmsg + ",以下数据转换不成功:"+error_list1.toString();
			logger.error(errmsg);
		}
		
		logger.info("**************同步完成************");
		long end = System.currentTimeMillis();
		logger.info("**************耗时************"+(end-start)/1000+"秒");
		
		logger.info("同步成功!");
		logger.info(errmsg);
		
	}
	
	/**
	 * 券类型
	 */
	@SuppressWarnings("unchecked")
//	@Scheduled(initialDelay = 100000,fixedDelay=7180000)
	public void getVipCouponsGroupData() {
		
		long start = System.currentTimeMillis();
		logger.info("**************开始同步券类型************");
		logger.info(String.valueOf(start));
		
		//调用http请求.获取数据
		Map<String,List<Map<String, Object>>> couponsGroup_map = couponsGroup_biz.getcouponsGroupData("","",1,"");
		
		List<Map<String, Object>> couponsGroup_list = couponsGroup_map.get("list");
		List<Map<String, Object>> errorGroup_list = couponsGroup_map.get("error");
		
		//修改为多线程方式
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
				couponsGroupCallable.setList(ImmutableList.copyOf(couponsGroup_list.subList(countNum - size,countNum < couponsGroup_list.size() ? countNum : couponsGroup_list.size()))); 
	            
	            @SuppressWarnings("rawtypes")
				ListenableFuture listenableFuture = listeningExecutorService.submit(couponsGroupCallable);
	            
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
			
		}
		
//		
//		if( couponsGroup_list!=null&&couponsGroup_list.size()>0 ) {
//			//插入x3 券组档案
//			couponsGroup_biz.mergeIntoX2(couponsGroup_list);
//		}
		logger.info("**************同步券类型完成************");
		long end = System.currentTimeMillis();
		logger.info("**************耗时************"+(end-start)/1000+"秒");
		logger.info("同步成功!");
		logger.info(String.valueOf(errorGroup_list));
		
	}
	
	/**
	 * 店铺,经销商
	 */
//	@Scheduled(initialDelay = 500000,fixedDelay=7180000)
	public void getStoreData() {
		
		long start = System.currentTimeMillis();
		logger.info("**************开始同步店铺,经销商************");
		logger.info(String.valueOf(start));
		
		//获取数据
		Map<String, List<Map<String,Object>>> store_map = store_Biz.getStoreData("",1,"");
		
		//店铺
		List<Map<String,Object>> storeList = store_map.get("storeList");
		
		//客户
		List<Map<String,Object>> customerList = store_map.get("customerList");
		
		//公司
		List<Map<String,Object>> corpList = store_map.get("corpList");
		
		//片区
		List<Map<String,Object>> areaList = store_map.get("areaList");
		
		if( storeList==null||storeList.size()<=0 ) {
			logger.info("同步成功");
			logger.info("success");
			logger.info(String.valueOf(UUID.randomUUID()));
		}
		
		//调用接口 ,保存公司档案(每次同步1000)
		areaList = areaList.stream().distinct().collect(Collectors.toList());
		store_Biz.save("areaList",areaList);
		
		//保存一级经销商(每次1000)
		corpList = corpList.stream().distinct().collect(Collectors.toList());
		store_Biz.save("corpList",corpList);

		//保存二级经销商(每次1000)
		customerList = customerList.stream().distinct().collect(Collectors.toList());
		store_Biz.save("customerList",customerList);
		
		//保存店铺(每次1000)
		storeList = storeList.stream().distinct().collect(Collectors.toList());
		store_Biz.save("storeList",storeList);
		
		//查询出保存的店铺档案,添加到会员店铺分组
		List<Map<String, Object>> list = store_Biz.findDepartmentId(storeList);
		
		//添加进vipdp0000 用于调用保存添加店铺分组的服务
		store_Biz.insertUserKey(list);
		
		//调用rmi服务,添加进店铺分组
		store_Biz.doRmiService();
		
		logger.info("**************同步店铺,经销商************");
		long end = System.currentTimeMillis();
		logger.info("**************耗时************"+(end-start)/1000+"秒");
		
	}
	
	/**
	 * 库存(暂无增量接口)
	 */
//	@Scheduled(initialDelay = 500000,fixedDelay=7180000)
//	public void getClothingStockData() {
//		
//	}
	
	/**
	 * 零售单
	 */
	@SuppressWarnings("unchecked")
//	@Scheduled(initialDelay = 50000,fixedDelay=14180000)
	public void getSaleOrderData() {
		//获取需要同步的网点数据
		List<Map<String, Object>> list = saleOrderBiz.getStoreList();
		List<List<Map<String, Object>>> total = new ArrayList<List<Map<String, Object>>>();
		
		//多线程获取方式
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
//			SaleOrderCallable callable = new SaleOrderCallable(); 
			saleOrderCallable.setList(ImmutableList.copyOf(list.subList(countNum - size,countNum < list.size() ? countNum : list.size()))); 
			saleOrderCallable.setEdate("");
			saleOrderCallable.setSdate("");
			saleOrderCallable.setN(1);
            
            @SuppressWarnings("rawtypes")
			ListenableFuture listenableFuture = listeningExecutorService.submit(saleOrderCallable);
            
            Futures.addCallback(listenableFuture, new FutureCallback<List<List<Map<String, Object>>>>() {  
                @Override  
                public void onSuccess(List<List<Map<String, Object>>> list) {  
                    countDownLatch.countDown();  
                    total.addAll(list);
                }  

                @Override  
                public void onFailure(Throwable throwable) {  
                    countDownLatch.countDown();  
                    logger.info("处理出错：",throwable);  

                }  
            });  
            
		}
		
		
		//原有方式
//		//循环网点,获取数据
//		for(Map<String, Object> map : list) {
//			
//			if(!String.valueOf(map.get("DEPARTMENT_USER_ID")).equals("A038") ) {
//				continue;
//			}
//			
//			Map<String ,List<Map<String, Object>>> couponsGroup_map = saleOrderBiz.getcouponsGroupData(String.valueOf(map.get("DEPARTMENT_USER_ID")),String.valueOf(map.get("DEPARTMENT_ID")),"","",1);
//			List<Map<String, Object>> errorList = couponsGroup_map.get("errorList");
//			//保存到x2临时数据
//			Map<String, List<Map<String, Object>>> returnMap = saleOrderBiz.saveDataToX2(couponsGroup_map);
//			
//			List<Map<String, Object>> errorlist = returnMap.get("error");
//			
//			total.add(errorList);
//			total.add(errorlist);
//		}
		
		logger.info("同步成功!");
		logger.info("错误数据:"+total.toString());
		
	}
	
	
}
