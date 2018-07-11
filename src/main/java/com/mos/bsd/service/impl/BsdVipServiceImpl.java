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
import com.mos.bsd.biz.IBsdVipBiz;
import com.mos.bsd.dao.IBsdVipDao;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.service.IBsdVipService;
import com.mos.bsd.utils.thread.VipCallable;
/**
 * bsd会员档案传入x2
 * @author hao
 *
 */
@Transactional
@Service("com.mos.bsd.service.impl.BsdVipServiceImpl")
public class BsdVipServiceImpl implements IBsdVipService {

	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipBizImpl")
	private IBsdVipBiz vip_biz;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdClothingBizImpl")
	private IBsdClothingBiz clothingBiz;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdVipServiceImpl.class);
	
	@Resource(name="com.mos.bsd.utils.thread.VipCallable")
	private VipCallable callable;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdVipDaoImpl")
	private IBsdVipDao vip_dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public BSDResponse getData(String sdate,String edate,int type) {
		
		String uuid = UUID.randomUUID().toString();
		//清空临时表
		vip_dao.cleanTempData(uuid);
		
		//调用bsd接口获取数据
		Map<String,List<Map<String, Object>>> vipMap = vip_biz.getVipData(sdate,edate,type,uuid);
		
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
	            callable.setList(ImmutableList.copyOf(vipList.subList(countNum - size,countNum < vipList.size() ? countNum : vipList.size()))); 
	            
	            @SuppressWarnings("rawtypes")
				ListenableFuture listenableFuture = listeningExecutorService.submit(callable);
	            
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
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		
		//原方式
//		//判断长度,每次执行1000条数据
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
////			//写入D0180
////			vip_biz.insertD0180(tem_list);
//			
//			//写入P0290
//			vip_biz.insertP0290(tem_list);
//		}
		
//		List<Map<String, Object>> vipList = vip_biz.getVipList();
//		System.out.println(vipList);
		
		String errmsg = "";
		if( errorList!=null && errorList.size()>0 ) {
			errmsg = "以下数据不完整:"+errorList.toString();
		}
		if( errorVipList!=null&&errorVipList.size()>0 ) {
			errmsg = errmsg + ",未找到下列会员店铺:"+errorList.toString();
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
			//开始时间2009-01-01  结束时间2018-06-30   每次+29天
			
			Calendar c = Calendar.getInstance();
			c.set(2009, 0, 1);
			
			Calendar c1 = Calendar.getInstance();
			c1.set(2009, 0, 1);
			c1.add(Calendar.DAY_OF_MONTH, 29);
			
			long now = System.currentTimeMillis();
			while ( c.getTimeInMillis()-now<0 ) {
				
				vip_biz.getVipBaseData(sdf.format(new Date(c.getTimeInMillis())),sdf.format(new Date(c1.getTimeInMillis())),type);
				c.add(Calendar.DAY_OF_MONTH, 29);
				c1.add(Calendar.DAY_OF_MONTH, 29);
				
			}
			
		}else {
			vip_biz.getVipBaseData("","",type);
		}
		
		//设置返回消息
		BSDResponse response = new BSDResponse();
		response.setErrorData("");
		response.setMsg("执行完成");
		response.setMsgId("");
		response.setStatus("success");
		return response;
	}
	
	public void name() {
		System.out.println("111111111");
		vip_biz.findClobData();
	}

	@Override
	public BSDResponse getLoadData(InitialdataEntity entity) {
		
		String uuid = UUID.randomUUID().toString();
		
		//获取要同步的数据
//		List<Map<String, InitialdataEntity>> list = vip_biz.getClothingBaseData();
		List<String> errorlist = new ArrayList<>();
		List<Map<String, Object>> error = new ArrayList<>();
		
		clothingBiz.updateData(entity.getUuid(),1);
		boolean flag = true;

		String data = entity.getReceived_data();
		JSONObject obj = JSONObject.parseObject(data);
		
		//转换数据
		if( obj.containsKey("data") && obj.getJSONArray("data")!=null) {
			JSONArray array = obj.getJSONArray("data");
			Map<String,List<Map<String,Object>>> return_map = cleanData(array);
			List<Map<String,Object>> error_list = null;
			List<Map<String,Object>> data_list =null;
			if( return_map!=null ) {
				error_list = return_map.get("error");
				data_list = return_map.get("list");
			}
			
			if( data_list!=null && data_list.size()>0 ) {
				try {
					//处理业务逻辑
					
					//转换department_user_id 为department_id
					Map<String,List<Map<String, Object>>> newVipMap = vip_biz.getDepartmentId(data_list);
					
					List<Map<String, Object>> error1 = newVipMap.get("error");
//					if( error1!=null&& error1.size()>0 ) {
//						error.addAll(error1);
//					}
					
					vip_biz.insertD0210(newVipMap.get("list"));
					
					vip_biz.insertP0290(newVipMap.get("list"),uuid);
					
				} catch (Exception e) {
					errorlist.add(entity.getUuid());
					logger.error(e.getMessage());
				}
			}
			
			if( error_list!=null&&error_list.size()>0 ) {
//				flag = false;
			}
			
		}
		if( !flag ) {
			errorlist.add(entity.getUuid());
		}
		
		//清空临时表
		vip_dao.cleanTempData(uuid);
		
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
	
	/**
	 * 转换数据
	 * @param array
	 * @return
	 */
	private Map<String, List<Map<String, Object>>> cleanData(JSONArray array) {
		Map<String,List<Map<String, Object>>> returnMap = new HashMap<String,List<Map<String, Object>>>();
		List<Map<String,Object>> vipList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> errorList = new ArrayList<Map<String,Object>>();
		Map<String,Object> vipMap ;
		Map<String,Object> errorMap ;
		JSONObject object = null;
		
		if( array!=null && array.size()>0 ) {
			//获取数据
			for(Object obj :array) {
				object = (JSONObject) obj;
				vipMap = new HashMap<String,Object>();
				
				//检查数据  1.card_id  2.department_id  3.birthday  4.vip_name  5.vip_create_name
				String card_id = String.valueOf(object.get("memberId"));
				
				
//				String department_id = String.valueOf(object.get("createStoreNo"));
				String date = String.valueOf(object.get("birthday"));
				if( date==null||"null".equals(date)||"".equals(date) ) {
					date = String.valueOf(System.currentTimeMillis());
//					errorMap = new HashMap<String,Object>();
//					errorMap.put("error", object);
//					errorList.add(errorMap);
//					logger.debug(object.toJSONString());
				}else {
					continue;
				}
				
				if(card_id==null||card_id.equals("")||"null".equals(card_id)) {
					errorMap = new HashMap<String,Object>();
					errorMap.put("error", object);
					errorList.add(errorMap);
					logger.debug(object.toJSONString());
					continue;
				}
				
//				if(department_id==null||department_id.equals("")||"null".equals(department_id)) {
//					errorMap = new HashMap<String,Object>();
//					errorMap.put("error", object);
//					errorList.add(errorMap);
//					logger.debug(object.toJSONString());
//					continue;
//				}
				
//				if(date==null||date.equals("")||"null".equals(date)) {
//					date = String.valueOf(System.currentTimeMillis());
//					errorMap = new HashMap<String,Object>();
//					errorMap.put("error", object);
//					errorList.add(errorMap);
//					logger.debug(object.toJSONString());
//					continue;
//				}
				
				//vip_id 系统自动生成
				vipMap.put("card_id", object.get("memberId"));
				vipMap.put("userNo", object.get("userNo"));
				vipMap.put("vip_name", object.get("userName"));
				vipMap.put("vip_sex", object.get("gender"));
				//拆分日期
				Date birthday = new Date(Long.parseLong(date));
				Calendar c = Calendar.getInstance();
				c.setTime(birthday);
				
				vipMap.put("birthday", object.get("birthday"));//年+月+日
				vipMap.put("vip_birthday_year", c.get(Calendar.YEAR));
				vipMap.put("vip_birthday_month", c.get(Calendar.MONTH)+1);
				vipMap.put("vip_birthday_day", c.get(Calendar.DATE));
				
				vipMap.put("vip_mobile", object.get("mobileNo"));
				vipMap.put("vip_email", object.get("emailNo"));
				vipMap.put("vip_province", object.get("province"));
				vipMap.put("vip_town", object.get("city"));
				vipMap.put("vip_district", object.get("county"));
				vipMap.put("vip_address", object.get("town"));					
				
				vipMap.put("createStoreNo", object.get("createStoreNo"));//department_user_id
				vipMap.put("belongStoreNo", object.get("belongStoreNo"));//department_user_id
				vipMap.put("vip_issue_date", object.get("createDate"));
				vipMap.put("vip_create_name", object.get("createUser"));
				vipMap.put("memberLevel", String.valueOf(object.get("memberLevel")).equals("null")?"1":object.get("memberLevel"));//卡等级   写入表D0180
				vipMap.put("vip_state", String.valueOf(object.get("memberStatus")).equals("1")?"1":"2");
				vipMap.put("firstClassify", object.get("firstClassify"));
				vipMap.put("secondClassify", object.get("secondClassify"));
				vipMap.put("thirdClassify", object.get("thirdClassify"));
				vipMap.put("annualIncome", object.get("annualIncome"));//家庭年收入
				vipMap.put("vip_job", object.get("occupation"));
				vipMap.put("vip_academic", object.get("education"));
				vipMap.put("pointAbnormalTimes", object.get("pointAbnormalTimes"));//积分异常次数
				
				vipList.add(vipMap);
			}
		}
		
		returnMap.put("list", vipList);
		returnMap.put("error", errorList);
		return returnMap;
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		List<Map<String, InitialdataEntity>> list = vip_biz.getClothingBaseData();
		return list;
	}

	@Override
	public BSDResponse getWrongData(InitialdataEntity entity) {
		List<Map<String, Object>> list = vip_biz.getWrongData();
		
		if( list!=null && list.size()>0 ) {
			for( Map<String, Object> map: list ) {				
				
				if(entity.getReceived_data().indexOf(String.valueOf(map.get("id")))!=-1) {
					clothingBiz.updateData( entity.getUuid(),2 );
				}
			}
		}
		
		return null;
	}
	
}
