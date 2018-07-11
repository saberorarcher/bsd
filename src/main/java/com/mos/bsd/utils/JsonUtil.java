package com.mos.bsd.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * JSON处理工具
 * @author QDY
 *
 */
public class JsonUtil {

	private static SerializeConfig mapping = new SerializeConfig();
	private static String dateFormat;
	static {
		dateFormat = "yyyy-MM-dd HH:mm:ss";
		mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
		mapping.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
	}

	/**
	 * 把对象转为JSON
	 * 时间格式为"yyyy-MM-dd HH:mm:ss"
	 * @param jsonText
	 * @return
	 */
	public static String toJSONString(Object jsonText) {
		
		return JSON.toJSONString(jsonText
				, mapping,SerializerFeature.PrettyFormat
				,SerializerFeature.WriteMapNullValue
				,SerializerFeature.WriteNullNumberAsZero
				 ,SerializerFeature.WriteNullBooleanAsFalse
				 ,SerializerFeature.WriteNullStringAsEmpty);
	}
	
	/**
	 * 把对象转为json
	 * @param jsonText 对象
	 * @param prettyFormat 是否格式化json
	 * @return
	 */
	public static String toJSONString(Object jsonText,boolean prettyFormat) {
		if (prettyFormat)
			return JSON.toJSONString(jsonText
					, mapping,SerializerFeature.PrettyFormat
					,SerializerFeature.WriteMapNullValue
					,SerializerFeature.WriteNullNumberAsZero
					 ,SerializerFeature.WriteNullBooleanAsFalse
					 ,SerializerFeature.WriteNullStringAsEmpty);

		else
			return JSON.toJSONString(jsonText, mapping
					,SerializerFeature.WriteMapNullValue
					,SerializerFeature.WriteNullNumberAsZero
					,SerializerFeature.WriteNullBooleanAsFalse
					,SerializerFeature.WriteNullStringAsEmpty);

	}

	/**
	 * 自定义时间格式
	 * 
	 * @param jsonText
	 * @return
	 */
	public static String toJSONString(String dateFormat, String jsonText) {
		mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
		return JSON.toJSONString(jsonText, mapping
				,SerializerFeature.WriteMapNullValue
				,SerializerFeature.WriteNullNumberAsZero
				,SerializerFeature.WriteNullBooleanAsFalse
				,SerializerFeature.WriteNullStringAsEmpty);
	}
	
	/**
	 * 判断是JSON对象
	 * @param json
	 * @return
	 */
	public static boolean checkJsonObject(final String json) {
		if (StringUtil_bak.isNotBlankAndNull(json)==false)
			return false;
		String jsonstr=json.replace(" ", "");
		return (jsonstr.trim().startsWith("{") && jsonstr.trim().endsWith("}"));
	}
	/**
	 * 判断是否是数组对象
	 * @param json
	 * @return
	 */
	public static boolean checkJsonArray(final String json) {
		if (StringUtil_bak.isNotBlankAndNull(json)==false)
			return false;
		String jsonstr=json.replace(" ", "");
		return (jsonstr.trim().startsWith("[{") && jsonstr.trim().endsWith("}]"));

	}
	
	/**
	 * 把字符串转换为JSON数组。如果不是数组的JSON字符串也转成数组
	 * 
	 * @param json
	 *            只要是合法的JSON字符串。都统一按转成数组
	 * @return 返回数组
	 */
	public static JSONArray jsonToJSONArray(String json) {

		if (json == null || json.isEmpty())
			throw new BusinessException("JsonUtil-01", "json不能为空！");
		if (JsonUtil.checkJsonArray(json) == false) {
			if (JsonUtil.checkJsonObject(json) == false) {
				throw new BusinessException("JsonUtil-02", "不是合法的JSON字符串" + json);
			} else
				json = "[" + json + "]";
		}
		return JSON.parseArray(json);
	}
	
	/**
	 * 检查json包里是否包含指定字段
	 * @param jsonObject JSON对象
	 * @param fieldName 字段名称
	 * @return 存在返回true
	 */
	public static boolean checkFieldIsExist(JSONObject jsonObject, final String fieldName){
		
		return (jsonObject.containsKey(fieldName));
			
	}
	/**
	 * 检查字段列表里的值是否为空，或者不存在。
	 * 为空或者不存在抛异常
	 * @param jsonObject
	 * @param fields Map<字段名称，提示信息>
	 */
	public static void checkValueIsNullException(JSONObject jsonObject,Map<String,String> fields){
		if (fields==null || fields.size()==0)
			return;
		for(Entry<String,String> s:fields.entrySet()){
			//检查对象是否存在
			checkFieldIsNotExistException(jsonObject,s.getKey(),s.getValue(),s.getValue());
			//检查值
			checkValueIsNullException(jsonObject,s.getKey(),s.getValue());
		}
		
	}
	
	/**
	 * 替换JSONObject制定字段值。如果替换失败，抛出异常
	 * @param jsonObject 
	 * @param key 字段名称
	 * @param value 值
	 * @param msg 抛出异常提示信息
	 */
	public static void replaceValueException(JSONObject jsonObject,final String key,Object value,final String msg){
		if (!replaceValue(jsonObject,key,value))
			throw new BusinessException("JsonUtil-03",msg);
	}

	/**
	 * 替换JSONObject制定字段值
	 * @param jsonObject 
	 * @param key 字段名称
	 * @param value 值
	 * @return 成功返回真
	 */
	public static boolean replaceValue(JSONObject jsonObject,final String key,Object value){
		Object obj=jsonObject.replace(key, value);
		return (obj!=null);
	}
	/**
	 * 检查json包里是否包含指定字段
	 * @param jsonObject JSON对象
	 * @param fieldName 字段名称
	 * @param msg 抛出异常提示信息
	 */
	public static void checkFieldIsNotExistException(JSONObject jsonObject, final String fieldName
			,final String msg,final String errdata){
		if (!checkFieldIsExist(jsonObject,fieldName))
			throw new BusinessException("JsonUtil-04",msg,errdata);
	}
	
	/**
	 * 检查值是否为空，为空抛出异常
	 * @param jsonObject
	 * @param fieldName 字段名称
	 * @param msg 抛异常提示消息
	 */
	public static void checkValueIsNullException(JSONObject jsonObject, final String fieldName,final String msg){
		Object value=jsonObject.get(fieldName);
		CheckIsNullException(value, msg);
	}

	/**
	 * 判断值是否为空
	 * @param value
	 * @return
	 */
	public static boolean CheckIsNull(Object value){
		return (value==null);
	}
	
	/**
	 * 检查值是否为空，如果为空，抛出异常
	 * @param value 需要检查的值
	 * @param msg 提示信息
	 */
	public static void CheckIsNullException(Object value,String msg){
		if (CheckIsNull(value))
			throw new BusinessException("JsonUtil-05",msg);
	}
}
