package com.mos.bsd.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mos.bsd.biz.IBsdClothingBiz;
import com.mos.bsd.dao.IBsdClothingDao;
import com.mos.bsd.dao.IBsdPlanDao;
import com.mos.bsd.dao.IinitialdataDao;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.utils.HttpPostUtils;
import com.x3.base.core.exception.BusinessException;
@Repository("com.mos.bsd.biz.impl.BsdClothingBizImpl")
public class BsdClothingBizImpl implements IBsdClothingBiz {

	@Autowired
	private Environment env;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdClothingDaoImpl")
	private IBsdClothingDao dao;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdPlanDaoImpl")
	private IBsdPlanDao planDao;
	
	@Resource(name="com.mos.bsd.dao.impl.InitialdataDaoImpl")
	private IinitialdataDao initDao;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdClothingBizImpl.class);
	 
	public Map<String,List<Map<String,Object>>> getClothingData(int x,String uuid) {
		
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		Map<String, String> detailMap;
		
		List<Map<String,Object>> productList = new ArrayList<Map<String,Object>>();
		Map<String, List<Map<String,Object>>> return_map = new HashMap<String, List<Map<String,Object>>>();
		String key = "ClothingInterface";
		try {
			JSONObject json = new JSONObject();
			String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/stock/getChangedProductsDetailCount";
			String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/stock/getPageChangedProductsDetail";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			//获取时间戳
			long ts = System.currentTimeMillis();
			if( x==1 ) {
				List<Map<String, Object>> list = planDao.getTampData(key);
				if( list==null||list.size()<=0 ) {
					json.put("ts", sdf.format(new Date(ts)));
				}else {
					json.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("TIME_STAMP"))))));
				}
			}else {
				json.put("ts", "2016-04-01 00:00:00");
			}
			
			HttpPostUtils httpPostUtils = new HttpPostUtils();
			
			JSONObject c_jObject = httpPostUtils.postHttp(url, json);
			
			logger.info("******************url:"+url);
			logger.info("******************json:"+json);
			
			totalMap.put("interface_name",url );
			totalMap.put("request_data", json.toJSONString());
			totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
			totalMap.put("received_data", c_jObject.toJSONString());
			totalMap.put("uuid",uuid );
			initJson.add(totalMap);
			
			JSONObject object;
			JSONObject tem_size_object;
			JSONObject tem_color_object;
			JSONArray colorDTOList;
			JSONArray sizeDTOList;
			String style_year="";
			String style_month_code="";
			String styleName;
			
			if( c_jObject.getBoolean("success")!=null && !c_jObject.getBoolean("success")) {
				throw new BusinessException("BsdClothingBizImpl-01","查询总数失败!错误信息:"+c_jObject.getString("errorMessage"));
			}

			//计算循环次数
			int number = Integer.parseInt(c_jObject.getString("data"));
			
			int num = number%200>0?number/200+1:number/200;
			
			for( int i=0;i<num;i++ ) {
				
				json.put("offset", i*200);
				json.put("limit", 200);
			
				JSONObject jo = httpPostUtils.postHttp(url1, json);		
				
				detailMap = new HashMap<>();
				detailMap.put("interface_name",url1 );
				detailMap.put("request_data", json.toJSONString());
				detailMap.put("create_date", String.valueOf(System.currentTimeMillis()));
				detailMap.put("received_data", jo.toJSONString());
				detailMap.put("uuid",uuid );
				initJson.add(detailMap);
				
				boolean success = jo.getBoolean("success");
				
				if( !success ) {
					throw new BusinessException("BsdClothingBizImpl-01",jo.getString("errorMessage"));
				}
				
				JSONArray array = jo.getJSONArray("data");
				Map<String,Object> productMap ;
				
				if( array!=null && array.size()>0 ) {
					//获取数据
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
//							logger.debug(object.toJSONString());
//							continue;
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
						productMap.put("style_saletype", "");
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
			}
			
			//获取完总数之后,更新时间戳
			planDao.updateTamp(key, String.valueOf(ts));
			return_map.put("productList", productList);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		//保存原始数据
		initDao.insertData(initJson);
		return return_map;
	}

	@Override
	public List<Map<String, Object>> getData() {
		List<Map<String,Object>> productList = new ArrayList<Map<String,Object>>();
		try {
			JSONObject json = new JSONObject();
			String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/stock/getChangedProductsDetailCount";
			String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/stock/getPageChangedProductsDetail";
			
//			long ts = System.currentTimeMillis();
//			json.put("ts", ts);
			
			json.put("ts", "2016-03-31");
			
			HttpPostUtils httpPostUtils = new HttpPostUtils();
			
			JSONObject c_jObject = httpPostUtils.postHttp(url, json);

			JSONObject object;
			JSONObject tem_size_object;
			JSONObject tem_color_object;
			JSONArray colorDTOList;
			JSONArray sizeDTOList;
			
			if( c_jObject.getBoolean("success")!=null && !c_jObject.getBoolean("success")) {
				throw new BusinessException("BsdClothingBizImpl-01","查询总数失败!错误信息:"+c_jObject.getString("errorMessage"));
			}
			//计算循环次数
			int number = Integer.parseInt(c_jObject.getString("data"));
			int num = number%200>0?number/200+1:number/200;
			
			for( int i=0;i<num;i++ ) {
				
				json.put("offset", i*200);
				json.put("limit", 200);
			
				JSONObject jo = httpPostUtils.postHttp(url1, json);		

				boolean success = jo.getBoolean("success");
				
				if( !success ) {
					throw new BusinessException("BsdClothingBizImpl-01",jo.getString("errorMessage"));
				}
				
				JSONArray array = jo.getJSONArray("data");
				Map<String,Object> productMap ;
				
				if( array!=null && array.size()>0 ) {
					//获取数据
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
						
						
						productMap.put("productNo", object.get("productNo"));
						productMap.put("productName", object.get("productName"));
						productMap.put("brandNo", object.get("brandNo"));
						productMap.put("brandName", object.get("brandName"));
						productMap.put("styleNo", object.get("styleNo"));
						productMap.put("styleName", object.get("styleName"));
						
						productMap.put("sexNo", object.get("sexNo"));
						productMap.put("sexName", object.get("sexName"));
						productMap.put("productGroupNo", object.get("productGroupNo"));
						productMap.put("productGroupName", object.get("productGroupName"));
						productMap.put("productTypeNo", object.get("productTypeNo"));
						
						productMap.put("productTypeName", object.get("productTypeName"));
						productMap.put("sizeGroupNo", object.get("sizeGroupNo"));
						productMap.put("sizeGroupName", object.get("sizeGroupName"));
						productMap.put("measureNo", object.get("measureNo"));
						productMap.put("measureName", object.get("measureName"));
						
						productMap.put("labeltypeNo", object.get("labeltypeNo"));
						productMap.put("labeltypeName", object.get("labeltypeName"));
						productMap.put("serialNo", object.get("serialNo"));
						productMap.put("tagPrice", object.get("tagPrice"));
						productMap.put("retailPrice", object.get("retailPrice"));
						
						productMap.put("ageRangeNo", object.get("ageRangeNo"));
						productMap.put("ageRangeName", object.get("ageRangeName"));
						productMap.put("productSeriesNo", object.get("productSeriesNo"));
						productMap.put("productSeriesName", object.get("productSeriesName"));
						productMap.put("productMiddleNo", object.get("productMiddleNo"));
						
						productMap.put("productMiddleName", object.get("productMiddleName"));
						productMap.put("isGifts", object.get("isGifts"));
						productMap.put("validFlag", object.get("validFlag"));
						productMap.put("ts", object.get("ts"));
						
						
						//循环  拼接尺码和颜色
						if( sizeDTOList!=null && sizeDTOList.size()>0 ) {
							for(Object sizeObj:sizeDTOList) {
								tem_size_object = (JSONObject) sizeObj;
								Map<String,Object> tem_map = new HashMap<String, Object>();
								tem_map.putAll(productMap);
								tem_map.put("sizeNo", tem_size_object.get("sizeNo"));
								tem_map.put("erpSizeNo", tem_size_object.get("erpSizeNo"));
								tem_map.put("sizeName", tem_size_object.get("sizeName"));
								tem_map.put("size_serialNo", tem_size_object.get("serialNo"));
								tem_map.put("size_name", tem_size_object.get("sizeName"));
								tem_map.put("size_validFlag", tem_size_object.get("validFlag"));
								tem_map.put("sortNo", tem_size_object.get("sortNo"));
								tem_map.put("size_ts", tem_size_object.get("ts"));
								
								if( colorDTOList!=null && colorDTOList.size()>0 ) {
									for(Object colorObj:colorDTOList) {
										tem_color_object = (JSONObject) colorObj;
										Map<String,Object> tem_map1 = new HashMap<String, Object>();
										tem_map1.putAll(tem_map);
										
										tem_map1.put("colorNo", tem_color_object.get("colorNo"));
										tem_map1.put("erpColorNo", tem_color_object.get("erpColorNo"));
										tem_map1.put("colorName", tem_color_object.get("colorName"));
										tem_map1.put("seriesNo", tem_color_object.get("seriesNo"));
										tem_map1.put("serialNo", tem_color_object.get("serialNo"));
										tem_map1.put("color_validFlag", tem_color_object.get("validFlag"));
										tem_map1.put("color_ts", tem_color_object.get("ts"));
										
										productList.add(tem_map1);
										
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return productList;
		
	}

	@Override
	public int insertToX2(List<Map<String, Object>> list) {
		return dao.insertToX2(list);
	}

	@Override
	public int getClothingBaseData(int x) {
		
		String cuuid = UUID.randomUUID().toString();
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		Map<String, String> detailMap;
		String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/stock/getChangedProductsDetailCount";
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/stock/getPageChangedProductsDetail";
		
		JSONObject json = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String key = "ClothingInterface";
		
		//获取时间戳
		long ts = System.currentTimeMillis();
		if( x==1 ) {
			List<Map<String, Object>> list = planDao.getTampData(key);
			if( list==null||list.size()<=0 ) {
				json.put("ts", sdf.format(new Date(ts)));
			}else {
				json.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("TIME_STAMP"))))));
			}
		}else {
			json.put("ts", "2016-04-01 00:00:00");
		}
		
		HttpPostUtils httpPostUtils = new HttpPostUtils();
		
		JSONObject c_jObject = httpPostUtils.postHttp(url, json);
		
		totalMap.put("interface_name",url );
		totalMap.put("request_data", json.toJSONString());
		totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
		totalMap.put("received_data", c_jObject.toJSONString());
		totalMap.put("cuuid",cuuid );
		totalMap.put("uuid",cuuid );
		initJson.add(totalMap);
		
		if( c_jObject.getBoolean("success")!=null && !c_jObject.getBoolean("success")) {
			throw new BusinessException("BsdClothingBizImpl-01","查询总数失败!错误信息:"+c_jObject.getString("errorMessage"));
		}
		
		int number = Integer.parseInt(c_jObject.getString("data"));
		
		int num = number%200>0?number/200+1:number/200;
		
		for( int i=0;i<num;i++ ) {
			
			json.put("offset", i*200);
			json.put("limit", 200);
		
			JSONObject jo = httpPostUtils.postHttp(url1, json);		
			
			boolean success = jo.getBoolean("success");
			if( !success ) {
				throw new BusinessException("BsdClothingBizImpl-01",jo.getString("errorMessage"));
			}
			
			detailMap = new HashMap<>();
			detailMap.put("interface_name",url1 );
			detailMap.put("request_data", json.toJSONString());
			detailMap.put("create_date", String.valueOf(System.currentTimeMillis()));
			detailMap.put("received_data", jo.toJSONString());
			detailMap.put("uuid",UUID.randomUUID().toString() );
			detailMap.put("cuuid",cuuid );
			initJson.add(detailMap);
			
		}
		
		planDao.updateTamp(key, String.valueOf(ts));
		int count = 0;
		if( initJson!=null && initJson.size()>0 ) {
			count = initDao.insertData(initJson);
		}

		return count;
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/stock/getPageChangedProductsDetail";
		
		return initDao.getInitData("0", url1);
	}

	@Override
	public int updateData(String uuid, int i) {
		
		return initDao.updateData(uuid,i);
	}

}
