package com.mos.bsd.utils.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.mos.bsd.utils.HttpPostUtils;

@SuppressWarnings("rawtypes")
public class ClothingCallable implements Callable {  
	
    private List<Map<String, Object>> list;  

	private static final Logger logger = LoggerFactory.getLogger(ClothingCallable.class);
	
    @Override  
    public Object call() throws Exception {  
    	
    	System.out.println("当前线程:"+Thread.currentThread().getName());
    	//处理业务逻辑
		JSONObject put_json = new JSONObject();
		JSONObject response = null;
		//判断是否有数据
		if( list!=null && list.size()>0 ) {
			//调用x2接口,保存商品档案
			put_json.put("DllName", "Zhx.X3.Eral.dll");
			put_json.put("NameSpace", "Zhx.X3.Eral");
			put_json.put("ClasssName", "X3_Input_Clothing");
			put_json.put("Caller", "msgid");
			put_json.put("UserKey", "0000");
			
			HttpPostUtils httpPostUtils = new HttpPostUtils();
			
			int num = list.size()%200>0?(list.size()/200)+1:(list.size()/200);
			for( int i=0;i<num;i++ ) {
				List<Map<String,Object>> tem_list = new ArrayList<Map<String,Object>>();
				if( i+1==num ) {
					tem_list.addAll(list.subList(i*200, list.size()));
				}else {
					tem_list.addAll(list.subList(i*200, (i+1)*200));
				}
				put_json.put("JsonObj", tem_list);
				response = httpPostUtils.postToX2(put_json);
				logger.info(response.toJSONString());
				
			}
		}
		return response;
    }  
  
    public void setList(ImmutableList<Map<String, Object>> immutableList) {  
        this.list = immutableList;  
    }  
} 