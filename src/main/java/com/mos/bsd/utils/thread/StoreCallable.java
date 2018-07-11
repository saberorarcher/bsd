package com.mos.bsd.utils.thread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.ImmutableList;
import com.mos.bsd.biz.IBsdStoreBiz;

@SuppressWarnings("rawtypes")
@Component("com.mos.bsd.utils.thread.StoreCallable")
public class StoreCallable implements Callable {  
    private List<Map<String, Object>> list;  
  
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdStoreBizImpl")
	private IBsdStoreBiz store_Biz;
    
	private String uuid;
	private String useType;
	
    @Override  
    public Object call() throws Exception {  
    	
    	//判断useType
    	JSONArray j1 = store_Biz.save(useType,list);
    	System.out.println(uuid);
        return j1;  
    }  
  
    public void setList(ImmutableList<Map<String, Object>> immutableList) {  
        this.list = immutableList;  
    }  
    
    public void setUuid(String uuid) {
    	this.uuid = uuid;  
    }
    
    public void setUseType(String useType) {
    	this.useType = useType;
    }
} 