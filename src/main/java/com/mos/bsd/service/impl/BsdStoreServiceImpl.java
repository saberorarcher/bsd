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
import com.mos.bsd.biz.IBsdStoreBiz;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.service.IBsdStoreService;
import com.mos.bsd.utils.thread.StoreCallable;
@Transactional
@Service("com.mos.bsd.service.impl.BsdStoreServiceImpl")
public class BsdStoreServiceImpl implements IBsdStoreService {

	@Resource(name="com.mos.bsd.utils.thread.StoreCallable")
	private StoreCallable callable;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdStoreBizImpl")
	private IBsdStoreBiz store_Biz;
	
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdClothingBizImpl")
	private IBsdClothingBiz clothingBiz;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdStoreServiceImpl.class);
	
	@Override
	public BSDResponse getData(String sdate,int type) {
		
		String uuid = UUID.randomUUID().toString();
		//获取数据
		Map<String, List<Map<String,Object>>> store_map = store_Biz.getStoreData(sdate,type,uuid);
		
		//店铺
		List<Map<String,Object>> storeList = store_map.get("storeList");
		
		//客户
		List<Map<String,Object>> customerList = store_map.get("customerList");
		
		//公司
		List<Map<String,Object>> corpList = store_map.get("corpList");
		
		//片区
		List<Map<String,Object>> areaList = store_map.get("areaList");
		
		if( storeList==null||storeList.size()<=0 ) {
			BSDResponse res = new BSDResponse();
			res.setMsg("同步成功");
			res.setStatus("success");
			res.setErrorData("");
			res.setMsgId(uuid);
			return res;
		}
		
		JSONArray array = new JSONArray();	
		
		//调用接口 ,保存公司档案(每次同步1000)
//		areaList = areaList.stream().distinct().collect(Collectors.toList());
//		JSONArray j1 = this.ThreadUtils(areaList, uuid, "areaList");
		
		JSONArray j1 = store_Biz.save("areaList",areaList);
		
		//保存一级经销商(每次1000)
//		corpList = corpList.stream().distinct().collect(Collectors.toList());
//		JSONArray j2 = this.ThreadUtils(corpList, uuid, "corpList");
		
		JSONArray j2 = store_Biz.save("corpList",corpList);

		//保存二级经销商(每次1000)
//		customerList = customerList.stream().distinct().collect(Collectors.toList());
		JSONArray j3 = store_Biz.save("customerList",customerList);
//		JSONArray j3 = this.ThreadUtils(customerList, uuid, "customerList");
		
		
		//保存店铺(每次1000)
//		storeList = storeList.stream().distinct().collect(Collectors.toList());
		JSONArray j4 = store_Biz.save("storeList",storeList);
//		JSONArray j4 = this.ThreadUtils(storeList, uuid, "storeList");
		
		array.add(j1);
		array.add(j2);
		array.add(j3);
		array.add(j4);
		
		//查询出保存的店铺档案,添加到会员店铺分组
		List<Map<String, Object>> list = store_Biz.findDepartmentId(storeList);
		
		//添加进vipdp0000 用于调用保存添加店铺分组的服务
		store_Biz.insertUserKey(list);
		
		//调用rmi服务,添加进店铺分组
		store_Biz.doRmiService();
		
		BSDResponse response = new BSDResponse();
		response.setStatus("success");
		response.setMsg(array.toJSONString());
		response.setMsgId(uuid);

		return response;
	}

//	private void checkSuccess(String string, JSONObject result) {
//		if( !result.getBoolean("GetStatus") ) {
//			throw new BusinessException("BsdStoreServiceImpl-01",string+result.getString("GetErrorData"));
//		}
//	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private JSONArray ThreadUtils(List<Map<String,Object>> list,String uuid,String useType) {
		
		JSONArray array = new JSONArray();
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
            callable.setUseType(useType);
            
            @SuppressWarnings("rawtypes")
			ListenableFuture listenableFuture = listeningExecutorService.submit(callable);
            
            Futures.addCallback(listenableFuture, new FutureCallback<JSONArray>() {  
                @Override  
                public void onSuccess(JSONArray arr) {  
                    countDownLatch.countDown();  
                    array.addAll(arr);
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
		return array;
	}

	@Override
	public BSDResponse getBaseData(String sdate, int type) {
		
		store_Biz.getStoreBaseData(sdate,type);
		
		//设置返回消息
		BSDResponse response = new BSDResponse();
		response.setErrorData("");
		response.setMsg("执行完成");
		response.setMsgId("");
		response.setStatus("success");
		return response;
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		//获取要同步的数据
		List<Map<String, InitialdataEntity>> list = store_Biz.getClothingBaseData();
		return list;
	}

	@Override
	public BSDResponse getLoadBaseData(InitialdataEntity entity) {
		List<String> errorlist = new ArrayList<>();
		
		if( entity==null ) {
			BSDResponse response = new BSDResponse();
			response.setErrorData("");
			response.setMsg("同步成功");
			response.setStatus("success");
			response.setMsgId(UUID.randomUUID().toString());
			return response;
		}
			
		clothingBiz.updateData(entity.getUuid(),1);
		boolean flag = true;
		String data = entity.getReceived_data();
		JSONObject obj = JSONObject.parseObject(data);
		
		//转换数据
		if( obj.containsKey("data") && obj.getJSONArray("data")!=null) {
			
			JSONArray errorArray = new JSONArray();
			JSONArray array = obj.getJSONArray("data");
			Map<String,List<Map<String,Object>>> return_map = cleanData(array);
			//店铺
			List<Map<String,Object>> storeList = return_map.get("storeList");
			
			//客户
			List<Map<String,Object>> customerList = return_map.get("customerList");
			
			//公司
			List<Map<String,Object>> corpList = return_map.get("corpList");
			
			//片区
			List<Map<String,Object>> areaList = return_map.get("areaList");
			
			if( storeList!=null && storeList.size()>0 ) {
				//处理业务逻辑
				JSONArray j1 = store_Biz.save("areaList",areaList);
				JSONArray j2 = store_Biz.save("corpList",corpList);
				JSONArray j3 = store_Biz.save("customerList",customerList);
				JSONArray j4 = store_Biz.save("storeList",storeList);
				array.add(j1);
				array.add(j2);
				array.add(j3);
				array.add(j4);
				
				//判斷有無錯誤數據
				if( j1!=null && j1.size()>0 ) {
					for( Object o1: j1 ) {
						JSONObject jo = (JSONObject)o1;
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
					}
				}
				
				if( j2!=null && j2.size()>0 ) {
					for( Object o1: j2 ) {
						JSONObject jo = (JSONObject)o1;
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
					}
				}
				
				if( j3!=null && j3.size()>0 ) {
					for( Object o1: j3 ) {
						JSONObject jo = (JSONObject)o1;
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
					}
				}
				
				if( j4!=null && j4.size()>0 ) {
					for( Object o1: j4 ) {
						JSONObject jo = (JSONObject)o1;
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
					}
				}
						
				//查询出保存的店铺档案,添加到会员店铺分组
				List<Map<String, Object>> list = store_Biz.findDepartmentId(storeList);
				
				//添加进vipdp0000 用于调用保存添加店铺分组的服务
				store_Biz.insertUserKey(list);
				
				//调用rmi服务,添加进店铺分组
				store_Biz.doRmiService();
					
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

	private Map<String, List<Map<String, Object>>> cleanData(JSONArray array) {
		
		//网点
		List<Map<String,Object>> storeList = new ArrayList<Map<String,Object>>();
		//客户
		List<Map<String,Object>> customerList = new ArrayList<Map<String,Object>>();
		//公司
		List<Map<String,Object>> corpList = new ArrayList<Map<String,Object>>();
		//片区
		List<Map<String,Object>> areaList = new ArrayList<Map<String,Object>>();
		//返回数据
		Map<String, List<Map<String,Object>>> return_map = new HashMap<String, List<Map<String,Object>>>();
		
		Map<String,Object> storeMap = new HashMap<>();
		Map<String,Object> customerMap = new HashMap<>();
		Map<String,Object> corpMap = new HashMap<>();
		Map<String,Object> areaMap = new HashMap<>();
		JSONObject object;
		
		if( array!=null && array.size()>0 ) {
			for( Object obj:array ) {
				object = (JSONObject) obj;
				
				storeMap = new HashMap<>();
				customerMap = new HashMap<>();
				corpMap = new HashMap<>();
				areaMap = new HashMap<>();
				
//				if(!String.valueOf(object.get("corpNo")).equals("978")) {
//					continue;
//				}		
				//店铺
				storeMap.put("depot_code", object.get("storeNo"));
				storeMap.put("depot_name", String.valueOf(object.get("storeName")).length()>25?String.valueOf(object.get("storeName")).substring(0, 25):object.get("storeName"));
				storeMap.put("system_type", "11");
				storeMap.put("depot_type", "1");
				storeMap.put("depot_state", -2);
				storeMap.put("manage_type", String.valueOf(object.get("storeNatureId")).equals("1")?"0":"1");
				storeMap.put("depot_parent_code", object.get("customerNo"));
				storeMap.put("depot_parent_name", "");
				storeMap.put("depot_tel", "!@#$%^&*");
				storeMap.put("depot_mobile", "!@#$%^&*");
				storeMap.put("depot_email", "");
				storeMap.put("depot_fax", "");
				storeMap.put("depot_helpid", "!@#$%^&*");
				storeMap.put("depot_bank", "");
				storeMap.put("depot_level_code", "");
				storeMap.put("depot_level", "!@#$%^&*");
				storeMap.put("depot_zone_code", "");
				storeMap.put("depot_zone", "!@#$%^&*");
				storeMap.put("depot_city_code", "");
				storeMap.put("depot_city", "!@#$%^&*");
				storeMap.put("depot_province", "!@#$%^&*");
				storeMap.put("depot_town", "!@#$%^&*");
				storeMap.put("depot_district", "!@#$%^&*");
				storeMap.put("depot_contact", "!@#$%^&*");
				storeMap.put("depot_address", "!@#$%^&*");
				storeMap.put("depot_area", "!@#$%^&*");
				storeMap.put("depot_remark", "!@#$%^&*");
				storeMap.put("depot_supply_code", "");
				storeMap.put("depot_replenish_code", "");
				storeMap.put("depot_virtual", "");
				storeMap.put("depot_switch", "");
				storeMap.put("account_id", "");
				storeMap.put("depot_opendate", "0001-01-01 00:00:00");
				storeMap.put("operate_state", 0);
				storeMap.put("depot_vmi", 0);
				storeMap.put("depot_brandcode", object.get("brandNo"));
				storeMap.put("depot_brandname", "");
				storeMap.put("esb_logid", 0.0);
				storeMap.put("depot_cablepoint", 0.0);
				storeMap.put("esb_source", "SAP");
				storeMap.put("depot_fullname", "");
				storeMap.put("depot_create_name", "");
				storeMap.put("merchantsub", new JSONArray());
				storeMap.put("currency_code", "!@#$%^&*");
				storeMap.put("depot_manager_name", "!@#$%^&*");
				storeMap.put("depot_manager_tel", "!@#$%^&*");
				storeMap.put("depot_area_manager", "!@#$%^&*");
				storeMap.put("depot_area_tel", "!@#$%^&*");
				storeMap.put("depot_steering", "!@#$%^&*");
				storeMap.put("depot_goods", "!@#$%^&*");
				storeMap.put("depot_belong_area_code", "!@#$%^&*");
				storeMap.put("depot_belong_area", "");
				storeMap.put("depot_decorate_area", -2.0);
				storeMap.put("depot_same", "!@#$%^&*");
				storeMap.put("depot_closedate", "0001-01-01 00:00:00");
				storeMap.put("depot_channel_type_code", "");
				storeMap.put("depot_channel_type", "!@#$%^&*");
				storeMap.put("depot_general_type_code", "!@#$%^&*");
				storeMap.put("depot_general_type", "");
				storeMap.put("depot_costcenter", "!@#$%^&*");
				storeMap.put("depot_payment", "!@#$%^&*");
				storeMap.put("depot_floor", "@#$%^&*");
				storeMap.put("depot_citydefinition", "!@#$%^&*");
				storeMap.put("depot_setchannel", "!@#$%^&*");
				storeMap.put("depot_financeRemark", "@#$%^&*");
				storeMap.put("depot_newid", "!@#$%^&*");
				storeMap.put("depot_clothesleven", "!@#$%^&*");
				storeMap.put("depot_seniorleven", "@#$%^&*");
				storeMap.put("depot_region", "!@#$%^&*");
				storeMap.put("depot_clothespolenum", -2.0);
				storeMap.put("depot_seniorpolenum", -2.0);
				storeMap.put("depot_epclass", "!@#$%^&*");
				storeMap.put("depot_cognate", "!@#$%^&*");
				storeMap.put("depot_EPgoods", -2.0);
				storeMap.put("depot_steering_mobile", "!@#$%^&*");
				storeMap.put("manage_type_id", "0");
				storeMap.put("depot_brand_code", object.get("brandNo"));//!@#$%^&*
				storeMap.put("depot_brand", object.get("brandNo"));
				storeMap.put("Shop_class_code", "!@#$%^&*");
				storeMap.put("Shop_class", "");
				storeMap.put("eas_code", "!@#$%^&*");
				storeMap.put("depot_ConceptShop", "!@#$%^&*");
				storeMap.put("depot_department_code", "!@#$%^&*");
				storeMap.put("depot_department", "");
				storeMap.put("depot_market", "!@#$%^&*");
				storeMap.put("am_account", "!@#$%^&*");
				storeMap.put("loc_x", "!@#$%^&*");
				storeMap.put("loc_y", "!@#$%^&*");
				storeMap.put("department_fin_type", 0);
				storeMap.put("Cooperation_mode", "!@#$%^&*");
				storeMap.put("depot_business_areas", "!@#$%^&*");
				storeMap.put("top_company_code", "");
				storeMap.put("depot_goods_mobile", "!@#$%^&*");
				storeMap.put("department_logistics_code", "!@#$%^&*");
				storeMap.put("depot_virtual_region", "");
				storeMap.put("department_ec_set", -2);
				storeMap.put("depot_update_name", "");

				storeMap.put("department_ext1", object.get("storeForm"));
				storeMap.put("department_ext2", object.get("channelType"));
				storeMap.put("department_ext3", object.get("corpIsAgent"));

				
				//经销商
				customerMap.put("merchant_code", object.get("customerNo"));
				customerMap.put("merchant_name", object.get("customerName"));
				customerMap.put("merchant_parent_code", object.get("corpNo"));
				customerMap.put("merchant_state", "1");
				customerMap.put("merchant_type", "1");
				customerMap.put("operate_state", "0");
				customerMap.put("merchant_brandcode",object.get("brandNo"));
				customerMap.put("esb_source", "SAP");
				customerMap.put("merchant_begdate", "0001-01-01 00:00:00");
				customerMap.put("merchant_enddate", "0001-01-01 00:00:00");
				customerMap.put("merchant_fin_type", "2.0");
				
				
				String merchant_parent_code =  String.valueOf(object.get("areaNo")).equals("")?"1001":String.valueOf(object.get("areaNo"));
				if("null".equals(merchant_parent_code)) {
					merchant_parent_code ="1001";
				}
				
				//经销商
				corpMap.put("merchant_code", object.get("corpNo"));
				corpMap.put("merchant_name", String.valueOf(object.get("corpName")).equals("null")?"未知":object.get("corpName"));
				corpMap.put("merchant_parent_code",merchant_parent_code);
				corpMap.put("merchant_state", "1");
				corpMap.put("merchant_type", "1");
				corpMap.put("operate_state", "0");
				corpMap.put("merchant_brandcode",object.get("brandNo"));
				corpMap.put("esb_source", "SAP");
				corpMap.put("merchant_begdate", "0001-01-01 00:00:00");
				corpMap.put("merchant_enddate", "0001-01-01 00:00:00");
				corpMap.put("merchant_fin_type", "2.0");
				
				//公司
				areaMap.put("merchant_code", object.get("areaNo"));
				areaMap.put("merchant_name", object.get("areaName"));
				areaMap.put("merchant_parent_code", "");
				areaMap.put("merchant_state", "1");
				areaMap.put("merchant_type", "3");
				areaMap.put("operate_state", "0");
				areaMap.put("merchant_brandcode",object.get("brandNo"));
				areaMap.put("esb_source", "SAP");
				areaMap.put("merchant_begdate", "0001-01-01 00:00:00");
				areaMap.put("merchant_enddate", "0001-01-01 00:00:00");
				areaMap.put("merchant_fin_type", "0.0");
				
				storeList.add(storeMap);
				customerList.add(customerMap);
				corpList.add(corpMap);
				areaList.add(areaMap);
			}
		}
		return_map.put("storeList", storeList);
		return_map.put("customerList", customerList);
		return_map.put("corpList", corpList);
		return_map.put("areaList", areaList);

		return return_map;
		
	}

	
}
