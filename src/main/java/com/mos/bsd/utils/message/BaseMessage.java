package com.mos.bsd.utils.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.mos.bsd.utils.StringUtil_bak;

/**
 * 消息基本模块
 * @author qdy
 *
 */
public class BaseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSONObject dataJson;
	private String data;

	/**
	 * 得到解压数据
	 * @return
	 */
	@JSONField(serialize=false)
	public String getDataZip() {
		return StringUtil_bak.unZip(data);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
    
	/**
	 * 赋值传递数据，并且会被压缩
	 * @param data
	 */
	public void setDataZip(String data){
		this.data=StringUtil_bak.zip(data);
	}


	public void setDataJson(JSONObject dataJson) {
		this.dataJson = dataJson;
	}


	public JSONObject getDataJson() {
		return dataJson;
	}


	public Map<String, Object> getMapParam() {
		return mapParam;
	}

	public void setMapParam(Map<String, Object> mapParam) {
		this.mapParam = mapParam;
	}

	private Map<String, Object> mapParam;

	public BaseMessage() {
		this.mapParam = new HashMap<String, Object>();
		dataJson= new JSONObject(true);
	}

	/**
	 * 添加参数
	 * @param paramname 参数名称，相互约定的名称
	 * @param object 参数对象
	 */
	public void addParam(String paramname,Object object) {
		this.mapParam.put(paramname, object);
	}
	
	/**
	 * 得到参数对象
	 * @param paramname 参数名称
	 * @return
	 */
	public Object getParam(String paramname){
		return this.mapParam.get(paramname);
	}
	
	/**
	 * 往datajson中添加JSONObject对象
	 * @param key
	 * @param value
	 */
	public void addjson(final String key,Object value){
		 this.dataJson.put(key, value);  
	}
	/**
	 * 插入字符型数据
	 * @param key
	 * @param value
	 */
	public void addjson(final String key,String value){
		
		 this.dataJson.put(key, value);  
	}
	/**
	 * 插入int数据参数
	 * @param key
	 * @param value
	 */
	public void addjson(final String key,int value){
		
		 this.dataJson.put(key, value);  
	}

	/**
	 * 把多个JSON字符串合并成JSONArray字符串
	 * @param args
	 */
	public void addjsonArray(final String key,JSONArray value){
		 this.dataJson.put(key, value);   
	}

}
