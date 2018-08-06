package com.mos.bsd;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.config.ScheduledTasksBeanDefinitionParser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mos.bsd.utils.DataHelper;

public class Test_Calendar {
//	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String date = "2017-04-29";
//		Date d = sdf.parse(date);
//		Calendar c = Calendar.getInstance();
//		c.setTime(d);
//		System.out.println(d);
//		System.out.println(c);
//		System.out.println(c.get(Calendar.YEAR));
//		System.out.println(c.get(Calendar.MONTH)+1);
//		System.out.println(c.get(Calendar.DATE));
		
//		List<Map<String,Object>> vipList = new ArrayList<Map<String,Object>>();
//		
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("type", "app");
//		map.put("a", "a");
//		Map<String,Object> map1 = new HashMap<String,Object>();
//		map1.put("type", "and");
//		map1.put("a", "b");
//		vipList.add(map);
//		vipList.add(map1);
//		
//		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
//		
//		Map<String,Object> map2 = new HashMap<String,Object>();
//		map2.put("type", "app");
//		map2.put("order", "1");
//		Map<String,Object> map3 = new HashMap<String,Object>();
//		map3.put("type", "and");
//		map3.put("order", "2");
//		newList.add(map2);
//		newList.add(map3);
//		
//		
//		System.out.println(vipList);
//		
//		for( Map<String, Object> change_map:newList ) {
//			for(Map<String, Object> main_map:vipList) {
//				if(String.valueOf(main_map.get("type")).equals(String.valueOf(change_map.get("type")))) {
//					main_map.put("order", change_map.get("order"));
//				}
//			}
//		}
			
//		Map<String,Object> vipMap ;
//		JSONArray array = new JSONArray();
//		JSONObject object = new JSONObject();
//		object.put("type", 1);
//		
//		JSONObject object1 = new JSONObject();
//		object1.put("type", 2);
//		
//		JSONObject object2 = new JSONObject();
//		object2.put("type", 3);
//		
//		JSONObject object3 = new JSONObject();
//		object3.put("type", 4);
//		
//		array.add(object);
//		array.add(object1);
//		array.add(object2);
//		array.add(object3);
//		for(Object obj :array) {
//			object = (JSONObject) obj;
//			vipMap = new HashMap<>();
//			//vip_id 系统自动生成
//			vipMap.put("card_id", object.get("type"));
//			vipList.add(vipMap);
//		}

//		System.out.println(vipList);
		
//		Date d = new Date(0);
//		
//		System.out.println(d);
//		JSONArray cloArray = new JSONArray();
//		JSONObject cloObject1 = new JSONObject();
//		
//		JSONArray colorArray = new JSONArray();
//		JSONObject coloroObject1 = new JSONObject();
//		coloroObject1.put("colorNo", "w");
//		JSONObject coloroObject2 = new JSONObject();
//		coloroObject2.put("colorNo", "b");
//		colorArray.add(coloroObject1);
//		colorArray.add(coloroObject2);
//		
//		JSONArray sizeArray = new JSONArray();
//		JSONObject sizeObject1 = new JSONObject();
//		sizeObject1.put("size", "35");
//		JSONObject sizeObject2 = new JSONObject();
//		sizeObject2.put("size", "36");
//		sizeArray.add(sizeObject1);
//		sizeArray.add(sizeObject2);
//		
//		cloObject1.put("clothing1", "bsd");
//		cloObject1.put("sizelist", sizeArray);
//		cloObject1.put("colorlist", colorArray);
//		cloArray.add(cloObject1);
//		
//		Map<String,Object> productMap ;
//		JSONObject object;
//		JSONArray sizelist;
//		JSONArray colorlist;
//		JSONObject tem_size_object;
//		JSONObject tem_color_object;
//		List<Map<String,Object>> productList = new ArrayList<Map<String,Object>>();
//		for(Object obj :cloArray) {
//			object = (JSONObject) obj;
//			productMap = new HashMap<>();
//			
//			productMap.put("clothing", object.get("clothing1"));
//			
//			//获取尺码组
//			sizelist = object.getJSONArray("sizelist");
//			//获取颜色组
//			colorlist =	object.getJSONArray("colorlist");
//			for(Object sizeObj:sizelist) {
//				tem_size_object = (JSONObject) sizeObj;
//				Map<String,Object> tem_map = new HashMap<String, Object>();
//				tem_map.putAll(productMap);
//				tem_map.put("size", tem_size_object.get("size"));
//				
//				
//				for(Object colorObj:colorlist) {
//					tem_color_object = (JSONObject) colorObj;
//					Map<String,Object> tem_map1 = new HashMap<String, Object>();
//					tem_map1.putAll(tem_map);
//					tem_map1.put("color", tem_color_object.get("colorNo"));
//					
//					productList.add(tem_map1);
//				}
//			}
//		}
//		System.out.println(productList);
		
//	}
	
//	public static void main(String[] args) {
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("status", "true");
//		List<String>list = new ArrayList<String>();
//		list.add("a");
//		list.add("b");
//		list.add("c");
//		for(int i=0;i<list.size();i++) {
//			jsonObject.put("num", list.get(i));
//			System.out.println(jsonObject.toJSONString());
//		}
//		
//	}
	
//	public static void main(String[] args) {
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("status", "true");
//		List<String>list = new ArrayList<String>();
//		for(int i=0;i<19;i++) {
//			if(i>=10) {
//				list.add("b");
//			}else {
//				list.add("a");
//			}
//			
//		}
//		
//		int num = list.size()/10>0?(list.size()/10)+1:list.size()/10;
//		StringBuilder sql_card = new StringBuilder();
//		for(int i=0;i<num;i++) {
//			for(int j = i * 10; j < (i+1) * 10; j++) {
//				if( j==list.size() ) {
//					break;
//				}
//				if (sql_card.toString().equals("")) {
//					sql_card.append("'");
//					sql_card.append(list.get(j));
//					sql_card.append("'");
//				} else {
//					sql_card.append(",'");
//					sql_card.append(list.get(j));
//					sql_card.append("'");
//				}
//			}
//		}
//		System.out.println(sql_card.toString());
//	}
//	public static void main(String[] args) {
//		List<Map<String, Object>> list = new ArrayList<>();
//		System.out.println(list.toString());
//	}
	
//	public static void main(String[] args) {
//		
//		StringBuffer sb = new StringBuffer();
//		for(int i=0;i<2;i++) {
//			StringBuffer sBuffer = new StringBuffer();
//			for(int j=0;j<10;j++) {
//				if(i==0) {
//					sBuffer.append("a"+i);
//				}
//				if(i==1) {
//					sBuffer.append("b"+i);
//				}
//				sb.append(sBuffer.toString());
//			}
//		}
//		System.out.println(sb.toString());
//		
//	}
	
	public static void main(String[] args) throws ParseException {
		
//		String ts = String.valueOf(System.currentTimeMillis());
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		Calendar c = Calendar.getInstance();
//		c.set(2009, 0, 1);
//		
//		Calendar c1 = Calendar.getInstance();
//		c1.set(2009, 0, 1);
//		c1.add(Calendar.DAY_OF_MONTH, 29);
//		
//		long now = System.currentTimeMillis();
//		while ( c1.getTimeInMillis()-now<0 ) {
//			
//			c.add(Calendar.DAY_OF_MONTH, 29);
//			c1.add(Calendar.DAY_OF_MONTH, 29);
//			
//			System.out.println(sdf.format(c.getTime()));
//			System.out.println(sdf.format(c1.getTime()));
//			
//		}
		
//		List<String> list = new ArrayList<>();
//		List<String> tem_list = new ArrayList<>();
//		list.add("1");
//		tem_list.addAll(list.subList(0, list.size()));
//		
//		System.out.println(tem_list);
//		
//		Calendar start = Calendar.getInstance();
//		Calendar end = Calendar.getInstance();
//		
//		start.set(2016, 04, 01);
//		end.set(nowDate.getYear(), nowDate.getMonth(), nowDate.getDay());
//		
//		//获取当前时间至2016-03-31之间月份的差
//		int result = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
//		int result1 = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
//		System.out.println(result);
//		System.out.println(result1);
//		System.out.println(nowDate.getYear()+1900);
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println( System.currentTimeMillis());
//		
//		Long long1 = new Long("1530800000000");
//		
//		Date date = new Date(long1);
//		
//		System.out.println(sdf.format(date));
//		
//		String valueString = "d";
//		String valueString1 = "abcd";
//		
//		System.out.println(valueString.indexOf(valueString1));
		
//		System.out.println(System.currentTimeMillis());
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(new Date(Long.parseLong("1533103110000"))));
		
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("success", true);
//		jsonObject.put("resultCode", "0008");
//		jsonObject.put("errorMessage", "数据不存在！");
//		
//		if( jsonObject.containsKey("success")&&!jsonObject.getBoolean("success") ) {
//			System.out.println("111111111");
//		}else {
//			System.out.println("2222222222");
//		}
		
//		{"department_id":"111209","success":false,"resultCode":"0008","errorMessage":"数据不存在！"}
		
//		List <Map<String, String>> initJson = new ArrayList<Map<String, String>>();
//		Map<String, String> totalMap = new HashMap<String, String>();
		
//		totalMap.put("status","0" );
//		initJson.add(totalMap);
//		
//		for( Map<String, String> map:initJson ) {
//			if(map.containsKey("status")) {
//				System.out.println("true");
//			}else {
//				System.out.println("false");
//			}
//		}
		
		System.out.println(new Date(System.currentTimeMillis()));
		
		
		
		
	}
	
}
