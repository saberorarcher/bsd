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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.mos.bsd.biz.IBsdClothingBiz;
import com.mos.bsd.biz.IBsdSaleOrderBiz;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.service.IBsdSaleOrderService;
import com.mos.bsd.utils.thread.SaleOrderCallable;
@Transactional
@Service("com.mos.bsd.service.impl.BsdSaleOrderServiceImpl")
public class BsdSaleOrderServiceImpl implements IBsdSaleOrderService {

	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdSaleOrderBizImpl")
	private IBsdSaleOrderBiz saleOrderBiz;
	
	@Resource(name="com.mos.bsd.utils.thread.SaleOrderCallable")
	private SaleOrderCallable callable;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdClothingBizImpl")
	private IBsdClothingBiz clothingBiz;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdSaleOrderServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public BSDResponse getData(String sdate,String edate,int type) {
		
		String uuid = UUID.randomUUID().toString();
		//获取需要同步的数据
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
            callable.setList(ImmutableList.copyOf(list.subList(countNum - size,countNum < list.size() ? countNum : list.size()))); 
            callable.setEdate(edate);
            callable.setSdate(sdate);
            callable.setN(type);
            callable.setUuid(uuid);
            
            @SuppressWarnings("rawtypes")
			ListenableFuture listenableFuture = listeningExecutorService.submit(callable);
            
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
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		
		//原先处理方式
		
//		//循环网点,获取数据
//		for(Map<String, Object> map : list) {
//			
//			if(!String.valueOf(map.get("DEPARTMENT_USER_ID")).equals("A038") ) {
//				continue;
//			}
//			
//			Map<String ,List<Map<String, Object>>> couponsGroup_map = saleOrderBiz.getcouponsGroupData(String.valueOf(map.get("DEPARTMENT_USER_ID")),String.valueOf(map.get("DEPARTMENT_ID")),sdate,edate,2);
//			List<Map<String, Object>> errorList = couponsGroup_map.get("errorList");
//			//保存到x2临时数据
//			Map<String, List<Map<String, Object>>> returnMap = saleOrderBiz.saveDataToX2(couponsGroup_map);
//			
//			List<Map<String, Object>> errorlist = returnMap.get("error");
//			
//			total.add(errorList);
//			total.add(errorlist);
//		}
		
		BSDResponse response = new BSDResponse();
		response.setStatus("success");
		response.setMsg("同步成功!");
		if( total==null||total.size()<=0 ) {
			response.setErrorData("");
		}else {
			response.setErrorData(total.toString());
		}
		response.setMsgId(uuid);
		
		return response;
	}

	@Override
	public BSDResponse getBaseData( int type ,Map<String, Object> map ) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if( map!=null ) {
			if( type == 2 ) {
				
				//开始时间2016-04-01  结束时间2018-06-30   每次+29天
				Calendar c = Calendar.getInstance();
//				c.set(2016, 3, 1);
				c.set(2018, 6, 1);
				Calendar c1 = Calendar.getInstance();
				c1.set(2018, 6, 1);
//				c1.set(2016, 3, 1);
				c1.add(Calendar.DAY_OF_MONTH, 29);
				
				long now = System.currentTimeMillis();
				while ( c.getTimeInMillis()-now<0 ) {
					
					saleOrderBiz.getBaseData(String.valueOf(map.get("DEPARTMENT_USER_ID")),String.valueOf(map.get("DEPARTMENT_ID")),sdf.format(new Date(c.getTimeInMillis())),sdf.format(new Date(c1.getTimeInMillis())),type);
					c.add(Calendar.DAY_OF_MONTH, 29);
					c1.add(Calendar.DAY_OF_MONTH, 29);
				}
				
			}else {
				saleOrderBiz.getBaseData(String.valueOf(map.get("DEPARTMENT_USER_ID")),String.valueOf(map.get("DEPARTMENT_ID")),"","",type);
			}
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
				String department_id = obj.getString("department_id");
				
				Map<String,List<Map<String,Object>>> return_map = cleanData(array,department_id);
				List<Map<String,Object>> error_list = return_map.get("error");
				List<Map<String,Object>> data_list = return_map.get("retailList");
				
				if( data_list!=null && data_list.size()>0 ) {
					try {
						
						Map<String, List<Map<String, Object>>> returnMap = saleOrderBiz.saveDataToX2(return_map);
						List<Map<String, Object>> list2 = returnMap.get("returnMap");
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

	private Map<String, List<Map<String, Object>>> cleanData(JSONArray array, String department_id) {
		
		List<Map<String,Object>> retailList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> retailexpandinfoList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> retailsubList = new ArrayList<Map<String,Object>>(); 
		List<Map<String,Object>> retailclothingpaytypeList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> retailclerkList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> errorList = new ArrayList<Map<String,Object>>();
		
		Map<String, List<Map<String,Object>>> return_map = new HashMap<String, List<Map<String,Object>>>();
		Map<String,Object> retailMap ;
		Map<String,Object> retailexpandinfoMap ;
		Map<String,Object> retailsubMap ;
		Map<String,Object> retailclothingpaytypeMap ;
		Map<String,Object> retailclerkMap ;
		Map<String,Object> errorMap ;
		
		JSONObject object = null;
		JSONArray saleOrderDtl;
		JSONArray saleOrderPayment;
		JSONObject tem_payment;
		JSONObject tem_dtl;
		
		if (array != null && array.size() > 0) {
			for (Object obj : array) {
				
				object = (JSONObject) obj;
				retailMap = new HashMap<>();
				retailexpandinfoMap = new HashMap<>();
				retailclerkMap = new HashMap<>();
				errorMap = new HashMap<>();
				
				//判断不能为空的参数[1.bill_id,2.set_department_id,3.bill_create_name,4.set_department_parent_id 默认0	,5.get_department_parent_id 默认0 ]
				String bill_id = String.valueOf(object.get("orderNo"));
				String bill_create_name = String.valueOf(object.get("createUser"));
				if(bill_create_name==null||"null".equals(bill_create_name)) {
					bill_create_name="未知";
				}
//				String set_department_id = String.valueOf(object.get("storeNo"));
				if( bill_id==null||bill_id.equals("null")||"".equals(bill_id) || bill_create_name==null||bill_create_name.equals("null")||"".equals(bill_create_name) ) {
					errorMap.put("errorData", object);
					errorList.add(errorMap);
//					logger.debug(object.toJSONString());
					continue;
				}
				
				//获取付款方式
				saleOrderPayment = JSON.parseArray(object.getString("saleOrderPaymentDTOs"));
				//获取订单明细
				saleOrderDtl = JSON.parseArray(object.getString("saleOrderDtlDTOs"));
//				saleOrderPayment = object.getJSONArray("saleOrderPaymentDTOs");
				
//				saleOrderDtl =	object.getJSONArray("saleOrderDtlDTOs");
				
				if( saleOrderPayment!=null && saleOrderPayment.size()>0 ) {
					for(Object paymentObj:saleOrderPayment) {
						tem_payment = (JSONObject) paymentObj;
						retailclothingpaytypeMap = new HashMap<>();
						retailclothingpaytypeMap.put("bill_id", tem_payment.get("orderNo"));
						retailclothingpaytypeMap.put("pay_type", tem_payment.get("paymentId"));
						retailclothingpaytypeMap.put("sums", tem_payment.get("paymentAmount"));
						retailclothingpaytypeList.add(retailclothingpaytypeMap);
					}
				}
				
				if( saleOrderDtl!=null && saleOrderDtl.size()>0 ) {
					for(Object dtlObj:saleOrderDtl) {
						tem_dtl = (JSONObject) dtlObj;
						retailsubMap = new HashMap<>();
						retailsubMap.put("bill_id", tem_dtl.get("orderNo"));
						retailsubMap.put("style_id", tem_dtl.get("productNo"));
						retailsubMap.put("color_id", tem_dtl.get("colorNo"));
						
						retailsubMap.put("size_id", tem_dtl.get("sizeNo"));
						retailsubMap.put("billsub_nums", String.valueOf(tem_dtl.get("saleNum")).equals("null")?"0":tem_dtl.get("saleNum"));
						retailsubMap.put("billsub_xprice", String.valueOf(tem_dtl.get("tagPrice")).equals("null")?"0":tem_dtl.get("tagPrice"));
						retailsubMap.put("billsub_sprice", String.valueOf(tem_dtl.get("retailPrice")).equals("null")?"0":tem_dtl.get("retailPrice"));
						retailsubMap.put("billsub_cost", String.valueOf(tem_dtl.get("exchangeAmount")).equals("null")?"0":tem_dtl.get("exchangeAmount"));
						retailsubMap.put("billsub_logistics", tem_dtl.get("discountCoupon"));
						retailsubMap.put("billsub_rate", String.valueOf(tem_dtl.get("rowDiscount")).equals("null")?"0":tem_dtl.get("rowDiscount"));
						
						retailsubMap.put("billsub_ssum", String.valueOf(tem_dtl.get("saleAmount")).equals("null")?"0":tem_dtl.get("saleAmount"));
						retailsubMap.put("subcompany_id", String.valueOf(tem_dtl.get("promotionId")).equals("null")?"0":tem_dtl.get("promotionId"));
						retailsubMap.put("billsub_address", tem_dtl.get("promotionName"));
						retailsubMap.put("billsub_remark", tem_dtl.get("remark"));
						retailsubMap.put("billsub_returnmessage", tem_dtl.get("couponsNo"));
						retailsubMap.put("clothing_id", String.valueOf(tem_dtl.get("productNo"))+String.valueOf(tem_dtl.get("colorNo"))+String.valueOf(tem_dtl.get("sizeNo"))+"0");
						retailsubMap.put("rowId", tem_dtl.get("rowId"));
						retailsubList.add(retailsubMap);
					}
				}
				
				retailMap.put("bill_return_reason", object.get("corpNo"));
				retailMap.put("bill_return_deal", object.get("customerNo"));
				retailMap.put("set_department_id", department_id);//店铺编号
				retailMap.put("move_dpt", object.get("deliveryStoreNo"));//发货店铺编号
				retailMap.put("bill_create_date", object.get("billDate"));
				retailMap.put("bill_setdate", object.get("saleTime"));
				retailMap.put("saleTime", object.get("saleTime"));
				retailMap.put("bill_id", object.get("orderNo"));
				retailMap.put("bill_diff_bill_id", object.get("relativeOrderNo"));
				retailMap.put("bill_state", String.valueOf(object.get("orderStatus")).equals("null")?"0":object.get("orderStatus"));
				retailMap.put("ex_class1", object.get("billSource"));
				retailMap.put("bill_type", String.valueOf(object.get("orderType")).equals("null")?"0":object.get("orderType"));
				retailMap.put("ex_class2", object.get("sellType"));
				retailMap.put("ex_class3", object.get("o2oType"));
				retailMap.put("ex_class4", object.get("posCode"));
				
				//营业员信息
				retailclerkMap.put("bill_id", object.get("orderNo"));
				retailclerkMap.put("clerk_id", object.get("clerkId"));
				retailclerkMap.put("deliveryClerkId", object.get("deliveryClerkId"));
				
				//零售单扩展信息
				retailexpandinfoMap.put("bill_id", object.get("orderNo"));
				retailexpandinfoMap.put("bill_coupon_code", object.get("discountCoupon"));
				retailexpandinfoMap.put("bill_vipcard_id", object.get("memberId"));
				retailexpandinfoMap.put("bill_vip_exchange", String.valueOf(object.get("exchangePoint")).equals("null")?"0":object.get("exchangePoint"));
				retailexpandinfoMap.put("bill_bank_sums", String.valueOf(object.get("exchangeAmount")).equals("null")?"0":object.get("exchangeAmount"));
				retailexpandinfoMap.put("bill_vip_birthday", String.valueOf(object.get("isBirthdayConsume")).equals("Y")?"1":"0");
				retailexpandinfoMap.put("bill_weather", object.get("isBirthdayDiscount"));
				
				retailMap.put("bill_num", String.valueOf(object.get("saleNum")).equals("null")?"0":object.get("saleNum"));
				retailMap.put("bill_ssum", String.valueOf(object.get("saleAmount")).equals("null")?"0":object.get("saleAmount"));
				retailMap.put("bill_idcode", object.get("carryDown"));
				retailMap.put("bill_create_name", object.get("createUser"));
				retailMap.put("bill_remark", object.get("remark"));
				retailMap.put("original_state", String.valueOf(object.get("validFlag")).equals("N")?"0":"1");
				retailMap.put("bill_sellremark", object.get("couponsNo"));
				
				retailList.add(retailMap);
				retailclerkList.add(retailclerkMap);
				retailexpandinfoList.add(retailexpandinfoMap);
			}
		}
		
		return_map.put("retailList", retailList);
		return_map.put("retailexpandinfoList", retailexpandinfoList);
		return_map.put("retailsubList", retailsubList);
		return_map.put("retailclothingpaytypeList", retailclothingpaytypeList);
		return_map.put("retailclerkList", retailclerkList);
		return_map.put("errorList", errorList);
		
		return return_map;
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		//获取要同步的数据
		List<Map<String, InitialdataEntity>> list = saleOrderBiz.getClothingBaseData();
		return list;
	}

	@Override
	public List<Map<String, Object>> getStoreList() {
		List<Map<String, Object>> list = saleOrderBiz.getStoreList();
		return list;
	}

	@Override
	public List<Map<String, Object>> getTemData() {
		return saleOrderBiz.getTemData();
	}

	@Override
	public BSDResponse getLoadTemData(Map<String, Object> map) {
		
		//将数据更新为1
		saleOrderBiz.updateErrorData(String.valueOf(map.get("id")),1);
		
		//转换为jsonObject
		JSONObject jObject = mapToJson(map);
		
		//转换数据
		JSONArray array = new JSONArray();
		array.add(jObject);
		
		String department_id = String.valueOf(map.get("department_id"));
		boolean flag = true;
		List<String> errorlist = new ArrayList<>();
		Map<String,List<Map<String,Object>>> return_map = cleanData(array,department_id);
		List<Map<String,Object>> error_list = return_map.get("error");
		List<Map<String,Object>> data_list = return_map.get("retailList");
		if( data_list!=null && data_list.size()>0 ) {
			try {
				
				Map<String, List<Map<String, Object>>> returnMap = saleOrderBiz.saveDataToX2(return_map);
				List<Map<String, Object>> list2 = returnMap.get("returnMap");
				if( list2!=null && list2.size()>0 ) {
					errorlist.add(String.valueOf(map.get("id")));
				}
				
			} catch (Exception e) {
				errorlist.add(String.valueOf(map.get("id")));
				logger.error(e.getMessage());
			}
		}
		
		if( error_list!=null&&error_list.size()>0 ) {
			flag = false;
		}
		
		if( !flag ) {
			errorlist.add(String.valueOf(map.get("id")));
		}
		
		//将错误数据修改为2状态
		if( errorlist!=null&&errorlist.size()>0 ) {
			saleOrderBiz.updateErrorData(String.valueOf(map.get("id")),2);
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

	private JSONObject mapToJson(Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("orderNo", map.get("orderNo"));
		jsonObject.put("corpNo", map.get("corpNo"));
		jsonObject.put("customerNo", map.get("customerNo"));
		jsonObject.put("storeNo", map.get("storeNo"));
		jsonObject.put("deliveryStoreNo", map.get("deliveryStoreNo"));
		jsonObject.put("billDate", map.get("billDate"));
		jsonObject.put("saleDate", map.get("saleDate"));
		jsonObject.put("saleTime", map.get("saleTime"));
		jsonObject.put("relativeOrderNo", map.get("relativeOrderNo"));
		jsonObject.put("orderStatus", map.get("orderStatus"));
		jsonObject.put("billSource", map.get("billSource"));
		jsonObject.put("orderType", map.get("orderType"));
		jsonObject.put("sellType", map.get("sellType"));
		jsonObject.put("o2oType", map.get("o2oType"));
		jsonObject.put("clerkId", map.get("clerkId"));
		jsonObject.put("deliveryClerkId", map.get("deliveryClerkId"));
		jsonObject.put("posCode", map.get("posCode"));
		jsonObject.put("discountCoupon", map.get("discountCoupon"));
		jsonObject.put("memberId", map.get("memberId"));
		jsonObject.put("exchangePoint", map.get("exchangePoint"));
		jsonObject.put("exchangeAmount", map.get("exchangeAmount"));
		jsonObject.put("isBirthdayConsume", map.get("isBirthdayConsume"));
		jsonObject.put("isBirthdayDiscount", map.get("isBirthdayDiscount"));
		jsonObject.put("saleNum", map.get("saleNum"));
		jsonObject.put("saleAmount", map.get("saleAmount"));
		jsonObject.put("carryDown", map.get("carryDown"));
		jsonObject.put("createUser", map.get("createUser"));
		jsonObject.put("remark", map.get("remark"));
		jsonObject.put("saleOrderPaymentDTOs", map.get("saleOrderPaymentDTOs"));
		jsonObject.put("saleOrderDtlDTOs", map.get("saleOrderDtlDTOs"));
		jsonObject.put("saleOrderExtDTO", map.get("saleOrderExtDTO"));
		jsonObject.put("validFlag", map.get("validFlag"));
		jsonObject.put("couponsNo", map.get("couponsNo"));
		
		return jsonObject;
	}

}
