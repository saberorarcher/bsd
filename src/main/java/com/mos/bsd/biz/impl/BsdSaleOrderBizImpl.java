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
import com.mos.bsd.biz.IBsdSaleOrderBiz;
import com.mos.bsd.dao.IBsdPlanDao;
import com.mos.bsd.dao.IBsdSaleOrderDao;
import com.mos.bsd.dao.IinitialdataDao;
import com.mos.bsd.domain.InitialdataEntity;
import com.mos.bsd.utils.HttpPostUtils;
import com.x3.base.core.exception.BusinessException;

@Repository("com.mos.bsd.biz.impl.BsdSaleOrderBizImpl")
public class BsdSaleOrderBizImpl implements IBsdSaleOrderBiz {

	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdSaleOrderDaoImpl")
	private IBsdSaleOrderDao sale_dao;
	
	@Autowired
	@Qualifier("com.mos.bsd.dao.impl.BsdPlanDaoImpl")
	private IBsdPlanDao planDao;
	
	@Autowired
	private Environment env;
	
	@Resource(name="com.mos.bsd.dao.impl.InitialdataDaoImpl")
	private IinitialdataDao initDao;
	
	private static final Logger logger = LoggerFactory.getLogger(BsdSaleOrderBizImpl.class);
	
	@Override
	public Map<String, List<Map<String, Object>>> getcouponsGroupData(String department_user_id,String department_id,String sdate,String edate,int x,String uuid) {
		
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		Map<String, String> detailMap;
		
		String key = "SaleOrderInterface";
		// 查询条件限制 日期区间跨度限制:30天,TS距当前时间限制:3天,分页单页条数限制:10000
		List<Map<String,Object>> retailList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> retailexpandinfoList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> retailsubList = new ArrayList<Map<String,Object>>(); 
		List<Map<String,Object>> retailclothingpaytypeList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> retailclerkList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> errorList = new ArrayList<Map<String,Object>>();
		Map<String, List<Map<String,Object>>> return_map = new HashMap<String, List<Map<String,Object>>>();

		try {
			JSONObject json = new JSONObject();
			String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/order/getSaleOrderCountByPara";
			String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/order/getPageSaleOrderByPara";
			HttpPostUtils httpPostUtils = new HttpPostUtils();

//			long ts = System.currentTimeMillis();
//			json.put("ts", ts);
			
			String beginString ="";
			String endString="";
			long ts = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			//获取时间戳
			if( x==1 ) {
				List<Map<String, Object>> list = planDao.getTampData(key);
				if( list==null||list.size()<=0 ) {
					json.put("ts", sdf.format(new Date(ts)));
				}else {
					json.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("TIME_STAMP"))))));
				}
			}else {
				
				beginString = sdate+" 00:00:00";
				endString = edate+" 00:00:00";
				
				json.put("saleDateStart", beginString);
				json.put("saleDateEnd", endString);
				
			}

			json.put("storeNo", department_user_id);
			
			JSONObject c_jObject = httpPostUtils.postHttp(url, json);
			
			totalMap.put("interface_name",url );
			totalMap.put("request_data", json.toJSONString());
			totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
			totalMap.put("received_data", c_jObject.toJSONString());
			totalMap.put("uuid",uuid );
			initJson.add(totalMap);
			
			
			JSONObject object = null;
			JSONArray saleOrderDtl;
			JSONArray saleOrderPayment;
			JSONObject tem_payment;
			JSONObject tem_dtl;
			
			Map<String,Object> retailMap ;
			Map<String,Object> retailexpandinfoMap ;
			Map<String,Object> retailsubMap ;
			Map<String,Object> retailclothingpaytypeMap ;
			Map<String,Object> retailclerkMap ;
			Map<String,Object> errorMap ;
			
			if (c_jObject.getBoolean("success") != null && !c_jObject.getBoolean("success")) {
				throw new BusinessException("BsdSaleOrderBizImpl-01",
						"查询订单总数失败!错误信息:" + c_jObject.getString("errorMessage"));
			}
			// 计算循环次数
			int number = Integer.parseInt(c_jObject.getString("data"));
			int num = number % 10000 > 0 ? number / 10000 + 1 : number / 10000;

			for (int i = 0; i < num; i++) {
				
				json.put("offset", i*10000);
				json.put("limit",10000);
				JSONObject jo = httpPostUtils.postHttp(url1, json);
				
				detailMap = new HashMap<>();
				detailMap.put("interface_name",url1 );
				detailMap.put("request_data", json.toJSONString());
				detailMap.put("create_date", String.valueOf(System.currentTimeMillis()));
				detailMap.put("received_data", jo.toJSONString());
				detailMap.put("uuid",uuid );
				initJson.add(detailMap);
				
				boolean success = jo.getBoolean("success");
				
				if (!success) {
					logger.error(jo.getString("errorMessage"));
					continue;
				}
				// 转换数据
				JSONArray array = jo.getJSONArray("data");
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
						String set_department_id = String.valueOf(object.get("storeNo"));
						if( bill_id==null||bill_id.equals("null")||"".equals(bill_id) || bill_create_name==null||bill_create_name.equals("null")||"".equals(bill_create_name) || set_department_id==null||set_department_id.equals("null")||"".equals(set_department_id) ) {
							errorMap.put("errorData", object);
							errorList.add(errorMap);
							logger.debug(object.toJSONString());
							continue;
						}
						
						//获取付款方式
						saleOrderPayment = object.getJSONArray("saleOrderPaymentDTOs");
						//获取订单明细
						saleOrderDtl =	object.getJSONArray("saleOrderDtlDTOs");
						
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
								
								retailsubList.add(retailsubMap);
							}
						}
						
						retailMap.put("bill_return_reason", object.get("corpNo"));
						retailMap.put("bill_return_deal", object.get("customerNo"));
						retailMap.put("set_department_id", department_id);//店铺编号
						retailMap.put("move_dpt", object.get("deliveryStoreNo"));//发货店铺编号
						retailMap.put("bill_create_date", object.get("billDate"));
						retailMap.put("bill_setdate", object.get("saleDate"));
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
				//获取完总数之后,更新时间戳
				planDao.updateTamp(key, String.valueOf(ts));
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		//保存原始数据
		initDao.insertData(initJson);
		return return_map;
	}

	@Override
	public Map<String, List<Map<String, Object>>> saveDataToX2(Map<String, List<Map<String, Object>>> couponsGroup_map) {
		
		Map<String, List<Map<String, Object>>> returnMap = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> retailList = couponsGroup_map.get("retailList");
		List<Map<String, Object>> retailsubList = couponsGroup_map.get("retailsubList");
		List<Map<String, Object>> retailexpandinfoList = couponsGroup_map.get("retailexpandinfoList");
		List<Map<String, Object>> retailclothingpaytypeList = couponsGroup_map.get("retailclothingpaytypeList");
		List<Map<String, Object>> retailclerkList = couponsGroup_map.get("retailclerkList");
		
		if( retailList==null|| retailList.size()<=0 ) {
			returnMap.put("error", new ArrayList<>());
			return returnMap;
		}
		
		//零售单主单，每次提交500条记录
		int x = 500;
		int num = retailList.size()%x>0?(retailList.size()/x)+1:(retailList.size()/x);
		for( int i=0;i<num;i++ ) {
			List<Map<String,Object>> tem_list = new ArrayList<Map<String,Object>>();
			if( i+1==num ) {
				tem_list.addAll(retailList.subList(i*x, retailList.size()));
			}else {
				tem_list.addAll(retailList.subList(i*x, (i+1)*x));
			}
			sale_dao.mergeDretail( tem_list );
		}
		
		//查询生成的p0670_id
		System.out.println("******************************开始查询");
		List<Map<String, Object>> p0670_list = sale_dao.getP0670_list(retailList);
		System.out.println("******************************查询完毕");
		//获取款式年份和季度
//		List<Map<String, Object>> year_list = sale_dao.getYear_list(retailsubList);
		
		//拼接p0670_id
		addP0670Id(retailsubList,p0670_list);
		//拼接款式年份,季度
		returnMap = addStyleYear(retailsubList);

		//保存零售单明细
		sale_dao.mergeDretailsub( retailsubList );
		
		//拼接
		addP0670Id(retailexpandinfoList,p0670_list);
		//零售单扩展信息
		sale_dao.mergeDretailexpandinfo( retailexpandinfoList );
		
		//拼接
		addP0670Id(retailclothingpaytypeList,p0670_list);
		//零售单商品收款方式
		sale_dao.mergeDretailpaytype( retailclothingpaytypeList );
		
		addP0670Id(retailclerkList,p0670_list);
		//零售单营业员明细
		sale_dao.mergeDretailclerk( retailclerkList );
		
		return returnMap;
	}

	@Override
	public List<Map<String, Object>> getStoreList() {
		
		return sale_dao.getStoreList();
	}
	
	/**
	 * 拼接p0670_id
	 * @param p0670_list 
	 * @param retailsubList 
	 */
	private void addP0670Id(List<Map<String, Object>> retailsubList, List<Map<String, Object>> p0670_list){
		for(Map<String, Object> main_map:retailsubList) {
			for(Map<String, Object> sub_map:p0670_list) {
				if(String.valueOf(main_map.get("bill_id")).trim().equals(String.valueOf(sub_map.get("BILL_ID")).trim())) {
					main_map.put("p0670_id", sub_map.get("P0670_ID"));
				}
			}
		}
	}
	/**
	 * 拼接款式年份,季节
	 * @param retailsubList
	 * @param year_list
	 */
	private Map<String, List<Map<String, Object>>> addStyleYear(List<Map<String, Object>> retailsubList) {
		Map<String, List<Map<String, Object>>> errorMap = new HashMap<String, List<Map<String, Object>>>();
		
		List<Map<String, Object>> errorDataList = new ArrayList<Map<String, Object>>();
		
		for(Map<String, Object> main_map:retailsubList) {
//			for(Map<String, Object> sub_map:year_list) {
//				if(String.valueOf(main_map.get("clothing_id")).equals(String.valueOf(sub_map.get("CLOTHING_ID")))) {
//					main_map.put("style_year_code", sub_map.get("STYLE_YEAR"));
//					main_map.put("style_month_code", sub_map.get("STYLE_MONTH_CODE"));
//				}
				main_map.put("style_year_code", "1999");
				main_map.put("style_month_code", "冬");
//			}
		}
		
//		Iterator<Map<String, Object>> it = retailsubList.iterator();
//		while (it.hasNext()) {
//			Map<String, Object> data = it.next();
//			if(!data.containsKey("style_year_code")||!data.containsKey("style_month_code")) {
//				logger.debug(data.toString());
//				errorDataList.add(data);
//				it.remove();
//			}
//			
//		}
		
		errorMap.put("error", errorDataList);
		
		return errorMap;
	}

	@Override
	public int getBaseData(String department_user_id,String department_id,String sdate, String edate, int type) {
		
		String cuuid = UUID.randomUUID().toString();
		//用来存储访问和返回数据
		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		Map<String, String> detailMap;
		
		String key = "SaleOrderInterface";
		
		JSONObject json = new JSONObject();
		String url = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/order/getSaleOrderCountByPara";
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/order/getPageSaleOrderByPara";
		HttpPostUtils httpPostUtils = new HttpPostUtils();
		
		String beginString ="";
		String endString="";
		long ts = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//获取时间戳
		if( type==1 ) {
			List<Map<String, Object>> list = planDao.getTampData(key);
			if( list==null||list.size()<=0 ) {
				json.put("ts", sdf.format(new Date(ts)));
			}else {
				json.put("ts", sdf.format(new Date(Long.parseLong(String.valueOf(list.get(0).get("TIME_STAMP"))))));
			}
		}else {
			
			beginString = sdate+" 00:00:00";
			endString = edate+" 00:00:00";
			
			json.put("saleDateStart", beginString);
			json.put("saleDateEnd", endString);
			
		}

		json.put("storeNo", department_user_id);
		
		JSONObject c_jObject = httpPostUtils.postHttp(url, json);
		
		if (c_jObject.getBoolean("success") != null && !c_jObject.getBoolean("success")) {

			logger.error(c_jObject.getString("errorMessage"));
			return 0;
		}
		
		totalMap.put("interface_name",url );
		totalMap.put("request_data", json.toJSONString());
		totalMap.put("create_date", String.valueOf(System.currentTimeMillis()));
		totalMap.put("received_data", c_jObject.toJSONString());
		totalMap.put("uuid",cuuid );
		totalMap.put("cuuid",cuuid );
		initJson.add(totalMap);
		
		if( c_jObject==null || !c_jObject.containsKey("data")||c_jObject.getString("data")==null ||c_jObject.getString("data").equals("null") ) {
			logger.error(c_jObject.getString("errorMessage"));
			return 0;
		}
		
		int number = Integer.parseInt(c_jObject.getString("data"));
		int num = number % 10000 > 0 ? number / 10000 + 1 : number / 10000;

		for (int i = 0; i < num; i++) {
			
			json.put("offset", i*10000);
			json.put("limit",10000);
			JSONObject jo = httpPostUtils.postHttp(url1, json);
			
			boolean success = jo.getBoolean("success");
			
			if (!success) {
				logger.error(jo.getString("errorMessage"));
				continue;
			}
			
			jo.put("department_id", department_id);
			
			detailMap = new HashMap<>();
			detailMap.put("interface_name",url1 );
			detailMap.put("request_data", json.toJSONString());
			detailMap.put("create_date", String.valueOf(System.currentTimeMillis()));
			detailMap.put("received_data", jo.toJSONString());
			detailMap.put("uuid",UUID.randomUUID().toString());
			detailMap.put("cuuid",cuuid);
			detailMap.put("department_user_id",department_user_id);
			initJson.add(detailMap);
		}
		
//		planDao.updateTamp(key, String.valueOf(ts));
		int count = 0;
		if( initJson!=null && initJson.size()>0 ) {
			count = initDao.insertData(initJson);
		}
		
		return count ;
	}

	@Override
	public List<Map<String, InitialdataEntity>> getClothingBaseData() {
		
		String url1 = env.getProperty("mos.bsd.url")+"/bsdyun-open-api/center/order/getPageSaleOrderByPara";
		
		return initDao.getInitData("0", url1);
		
	}


}
