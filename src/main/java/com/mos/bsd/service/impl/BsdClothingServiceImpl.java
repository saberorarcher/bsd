package com.mos.bsd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.service.IBsdClothingService;
import com.mos.bsd.utils.HttpPostUtils;
import com.mos.bsd.utils.thread.ClothingCallable;

/**
 * 获取商品档案
 * @author hao
 *
 */
@Transactional
@Service("com.mos.bsd.service.impl.BsdClothingServiceImpl")
public class BsdClothingServiceImpl implements IBsdClothingService {
	
	private static final Logger logger = LoggerFactory.getLogger(BsdClothingServiceImpl.class);
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdClothingBizImpl")
	private IBsdClothingBiz clothingBiz;
	@SuppressWarnings("unchecked")
	@Override
	public BSDResponse getData(int type) {
		BSDResponse res = new BSDResponse();
		JSONArray array = new JSONArray();
		String uuid = UUID.randomUUID().toString();
		//调用bsd接口获取数据
//		List<Map<String,Object>> data_map = clothingBiz.getData();
		Map<String,List<Map<String,Object>>> map = clothingBiz.getClothingData(type,uuid);
		List<Map<String,Object>> productList = map.get("productList");
		
		if( productList!=null && productList.size()>0 ) {
			
			//转换成多线程方式提交数据
			int size = 500;//每条线程处理的数据量
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
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		
		//原先提交方式
//		JSONObject put_json = new JSONObject();
//		JSONObject response = null;
//		//判断是否有数据
//		if( productList!=null && productList.size()>0 ) {
//			//调用x2接口,保存商品档案
//			put_json.put("DllName", "Zhx.X3.Eral.dll");
//			put_json.put("NameSpace", "Zhx.X3.Eral");
//			put_json.put("ClasssName", "X3_Input_Clothing");
//			put_json.put("Caller", "msgid");
//			put_json.put("UserKey", "0000");
//			
//			HttpPostUtils httpPostUtils = new HttpPostUtils();
//			
//			int num = productList.size()%100>0?(productList.size()/100)+1:(productList.size()/100);
//			for( int i=0;i<num;i++ ) {
//				List<Map<String,Object>> tem_list = new ArrayList<Map<String,Object>>();
//				if( i+1==num ) {
//					tem_list.addAll(productList.subList(i*100, productList.size()));
//				}else {
//					tem_list.addAll(productList.subList(i*100, (i+1)*100));
//				}
//				put_json.put("JsonObj", tem_list);
//				response = httpPostUtils.postToX2(put_json);
//				logger.debug(response.toJSONString());
//			}
//			
//			if( response.containsKey("GetStatus")&&!response.getBoolean("GetStatus") ) {
//				logger.error(response.getString("GetErrorData"));
//				logger.error(response.getString("JsonObj"));
//				res.setMsgId(UUID.randomUUID().toString());
//				res.setStatus("error");
//				res.setMsg("同步失败!错误信息"+response.getString("GetErrorData"));
//				return res;
//			}
//			
//			if(response.containsKey("GetError")&&!response.getString("GetError").equals("")) {
//				logger.error(response.getString("GetErrorData"));
//				logger.error(response.getString("JsonObj"));
//				res.setMsgId(UUID.randomUUID().toString());
//				res.setStatus("error");
//				res.setMsg("同步失败!错误信息"+response.getString("GetErrorData"));
//				return res;
//			}
//			
//		}
		res.setMsgId(uuid);
		res.setStatus("success");
		res.setMsg("同步成功!");
		if( array!=null&&array.size()>0 ) {
			res.setErrorData("错误数据:"+array.toJSONString());
		}else{
			res.setErrorData("");
		}
		
		return res;
	}
	@Override
	public BSDResponse getBaseData(int type) {
		//获取原始数据,并保存
		clothingBiz.getClothingBaseData(type);
		//设置返回消息
		BSDResponse response = new BSDResponse();
		response.setErrorData("");
		response.setMsg("执行完成");
		response.setMsgId("");
		response.setStatus("success");
		return response;
	}
	@Override
	public BSDResponse getBaseData(InitialdataEntity entity) {
		
		Map<String, JSONArray> error = new HashMap<String, JSONArray>();
		JSONArray errorArray = new JSONArray();
		
		List<String> errorlist = new ArrayList<>();
		
		if( entity!=null ) {
			//修改状态为已经读取
			clothingBiz.updateData(entity.getUuid(),1);

			boolean flag = true;

			String data = entity.getReceived_data();
			JSONObject obj = JSONObject.parseObject(data);
			//转换数据
			if( obj.containsKey("data") && obj.getJSONArray("data")!=null) {
				JSONArray array = obj.getJSONArray("data");
				List<Map<String,Object>> clothing_list = cleanData(array);
				//保存到x2
				JSONObject jo = saveToX2(clothing_list);
				//获取提交失败的数据
				
				if(jo !=null && jo.containsKey("JsonObj") &&  jo.getJSONArray("JsonObj")!=null ) {
					JSONArray array2 = jo.getJSONArray("JsonObj");
					if( array2!=null && array2.size()>0 ) {
						for( Object o: array2 ) {
							JSONObject jObject = (JSONObject) o;
							if( jObject.containsKey("status")&&jObject.getBoolean("status")==false ) {
								errorArray.add(jObject);
								flag = false;
							}
						}
					}
				}
				
				if(!flag) {
					errorlist.add(entity.getUuid());
				}
				
			}
			if( errorArray!=null && errorArray.size()>0 ) {
				error.put(entity.getUuid(), errorArray);
			}
		}
		
		//将错误数据修改为2状态
		if( errorlist!=null&&errorlist.size()>0 ) {
			clothingBiz.updateData(entity.getUuid(),2);
		}
		
		BSDResponse response = new BSDResponse();
		
		if( error!=null&&error.size()>0 ) {
			response.setMsg("同步失败");
			response.setErrorData(error.toString());
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
	public List<Map<String,Object>> cleanData(JSONArray array) {
		
		JSONObject object;
		JSONObject tem_size_object;
		JSONObject tem_color_object;
		JSONArray colorDTOList;
		JSONArray sizeDTOList;
		String style_year="";
		String style_month_code="";
		String styleName;
		Map<String,Object> productMap ;
		List<Map<String,Object>> productList = new ArrayList<Map<String,Object>>();
		
		if( array!=null && array.size()>0 ) {
			for(Object obj :array) {
				object = (JSONObject) obj;
				productMap = new HashMap<>();
				String brandName = String.valueOf(object.get("brandName"));
				if( brandName!=null && !brandName.equals("波司登") ) {
					continue;
				}
				//获取尺码组
				sizeDTOList = object.getJSONArray("sizeDTOList");
				//获取颜色组
				colorDTOList =	object.getJSONArray("colorDTOList");
				
				//拆分styleName为年份+季度
				styleName = object.getString("styleName");
				if( styleName==null||styleName.equals("")||"null".equals(styleName) ) {
					styleName ="2016冬";
				}
				
				if( styleName!=null && styleName.length()>4 ) {
					style_year = styleName.substring(0, 4);
					style_month_code = styleName.substring(4, styleName.length());
				}
				
				productMap.put("brand_code", object.get("brandNo"));
				productMap.put("brand_name", object.get("brandName"));
				productMap.put("clothing_id", object.get("productNo"));
				productMap.put("style_id", object.get("productNo"));
				productMap.put("style_name", object.get("productName"));
				productMap.put("stylever_id", "0");
				productMap.put("stylever_name", "A");
				productMap.put("clothing_cost1", "0");
				productMap.put("clothing_cost2", "0");
				productMap.put("clothing_jprice", object.get("tagPrice"));
				productMap.put("clothing_img", "");
				productMap.put("style_helpid", "");
				productMap.put("clothing_helpid", object.get("productNo"));//旧条码
				productMap.put("clothing_barcode", "");
				productMap.put("clothing_gbcode", "");
				productMap.put("clothing_gbcode1", "");
				productMap.put("clothing_rfid", "");
				productMap.put("style_year", style_year);
				productMap.put("style_month", style_month_code);
				productMap.put("style_month_name", style_month_code);
				productMap.put("style_category", "0");
				productMap.put("style_category_name", "服装");
				productMap.put("style_band_code", "");
				productMap.put("style_band", "");
				productMap.put("style_class_code", object.get("productGroupNo"));
				productMap.put("style_class", object.get("productGroupName"));
				productMap.put("style_classsub_code", object.get("productTypeNo"));
				productMap.put("style_classsub", object.get("productTypeName"));
				productMap.put("style_kind_code", object.get("productMiddleNo"));
				productMap.put("style_kind", object.get("productMiddleName"));
				productMap.put("style_sex_code", object.get("sexNo"));
				productMap.put("style_sex", object.get("sexName"));
				productMap.put("style_saletype_code", object.get("isGifts"));
				productMap.put("style_saletype", object.get("isGifts"));
				productMap.put("style_unit", object.get("measureName"));
				productMap.put("style_unit_code", object.get("measureNo"));
				productMap.put("style_designer", "");
				productMap.put("style_plater", "");
				productMap.put("style_styles", "2131.01.0001");
				productMap.put("style_locate", "");
				productMap.put("style_colorsystem", "");
				productMap.put("style_theme", "");
				productMap.put("style_indenttype", "");
				productMap.put("style_priceband", "");
				productMap.put("style_composition", "");
				productMap.put("style_supplierid", "");
				productMap.put("style_supplier", "");
				productMap.put("style_remark", "");
				productMap.put("subject_id", "2131.01.0001");
				productMap.put("style_sort", "01");
				productMap.put("style_sort_name", "成衣");
				productMap.put("operate_state", "1");//0草稿  1启用
				productMap.put("style_isvmi", "0");
				productMap.put("style_classes", "0");
				productMap.put("style_spare2", object.get("labeltypeName"));
				productMap.put("style_spare3", object.get("serialNo"));
				productMap.put("style_spare5", object.get("ageRangeName"));
				productMap.put("style_spare1", object.get("labeltypeNo"));
				productMap.put("style_spare4", object.get("ageRangeNo"));
				productMap.put("style_spare6", object.get("productSeriesNo"));
				productMap.put("style_spare7", object.get("productSeriesName"));
				productMap.put("style_spare8", "");
				productMap.put("style_spare9", "");
				productMap.put("style_spare10", "");
				productMap.put("style_spare11", "");
				productMap.put("style_package", "");
				productMap.put("style_safetype", "");
				productMap.put("style_dotype", "");
				productMap.put("style_grade", "");
				productMap.put("style_brid", "");
				productMap.put("style_bridname", "");
				productMap.put("style_production", "");
				productMap.put("supp_clothingid", "");
				productMap.put("supp_styleid", "");
				productMap.put("supp_colorid", "");
				productMap.put("supp_sizeid", "");
				productMap.put("style_type", "");
				productMap.put("style_fabric", "");
				productMap.put("product_sort_code", "");
				productMap.put("product_sort", "");
				productMap.put("style_package2", "");
				productMap.put("brand_groupcode", "");
				productMap.put("style_highproject", "");
				productMap.put("esb_source", "");
				productMap.put("size_group", object.get("sizeGroupNo"));
				productMap.put("clothing_retailprice", object.get("retailPrice"));
				productMap.put("clothing_state", String.valueOf(object.get("validFlag")).equals("Y")?"1":"0");
				
				//循环  拼接尺码和颜色
				if( sizeDTOList!=null && sizeDTOList.size()>0 ) {
					for(Object sizeObj:sizeDTOList) {
						tem_size_object = (JSONObject) sizeObj;
						Map<String,Object> tem_map = new HashMap<String, Object>();
						tem_map.putAll(productMap);
						tem_map.put("size_id", tem_size_object.get("sizeNo"));
						tem_map.put("size_name", tem_size_object.get("sizeName"));
						tem_map.put("size_order", tem_size_object.get("sortNo"));
						
						if( colorDTOList!=null && colorDTOList.size()>0 ) {
							for(Object colorObj:colorDTOList) {
								tem_color_object = (JSONObject) colorObj;
								Map<String,Object> tem_map1 = new HashMap<String, Object>();
								tem_map1.putAll(tem_map);
								tem_map1.put("color_id", tem_color_object.get("colorNo"));
								tem_map1.put("color_name", tem_color_object.get("colorName"));
								tem_map1.put("color_order", 0);
								tem_map1.put("clothing_id", String.valueOf(tem_map1.get("style_id"))+String.valueOf(tem_map1.get("color_id"))+String.valueOf(tem_map1.get("size_id"))+"0");
								tem_map1.put("clothing_helpid", String.valueOf(tem_map1.get("style_id"))+String.valueOf(tem_map1.get("color_id"))+String.valueOf(tem_map1.get("size_id")));
								productList.add(tem_map1);
							}
						}
					}
				}
			}
		}
		return productList;
	}
	
	public JSONObject saveToX2(List<Map<String,Object>> list) {
		
		JSONObject put_json = new JSONObject();
		JSONObject response = new JSONObject();;
		
		if( list!=null && list.size()>0 ) {
			//调用x2接口,保存商品档案
			put_json.put("DllName", "Zhx.X3.Eral.dll");
			put_json.put("NameSpace", "Zhx.X3.Eral");
			put_json.put("ClasssName", "X3_Input_Clothing");
//			put_json.put("ClasssName", "X3_Input_Clothing_Bsd");
			put_json.put("Caller", "msgid");
			put_json.put("UserKey", "0000");
			
			HttpPostUtils httpPostUtils = new HttpPostUtils();
			
			int num = list.size()%100>0?(list.size()/100)+1:(list.size()/100);
			for( int i=0;i<num;i++ ) {
				List<Map<String,Object>> tem_list = new ArrayList<Map<String,Object>>();
				if( i+1==num ) {
					tem_list.addAll(list.subList(i*100, list.size()));
				}else {
					tem_list.addAll(list.subList(i*100, (i+1)*100));
				}
				
				//输出发送的json
				logger.info("****************输入长度："+tem_list.size());
				put_json.put("JsonObj", tem_list);
//				logger.info(put_json.toJSONString());
//				System.out.println(put_json);
				response = httpPostUtils.postToX2(put_json);
				logger.info(response.toJSONString());
				
			}
		}
		return response;
	}
	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		//获取要同步的数据
		List<Map<String, InitialdataEntity>> list = clothingBiz.getClothingBaseData();
		return list;
	}
	@Override
	public void updateData(String uuid, int i) {
		//修改状态为已经读取
		clothingBiz.updateData(uuid,i);
	}
	
	
	@SuppressWarnings("unchecked")
	public JSONArray saveDataToX2( List<Map<String,Object>>productList ) {
		
		JSONArray array = new JSONArray();
		if( productList!=null && productList.size()>0 ) {
			
			//转换成多线程方式提交数据
			int size = 200;//每条线程处理的数据量
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
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return array;
	}
	
}
