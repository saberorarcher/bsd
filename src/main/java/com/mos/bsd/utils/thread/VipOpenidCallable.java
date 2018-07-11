package com.mos.bsd.utils.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.mos.bsd.biz.IBsdVipOpenidBiz;

@SuppressWarnings("rawtypes")
@Component("com.mos.bsd.utils.thread.VipOpenidCallable")
public class VipOpenidCallable implements Callable {  
    private List<Map<String, Object>> list;  
  
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipOpenidBizImpl")
	private IBsdVipOpenidBiz openid_biz;
	
    @Override  
    public Object call() throws Exception {  
    	System.out.println("当前线程:"+Thread.currentThread().getName());
    	
    	List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
    	list1.addAll(list);
    	return openid_biz.mergeToX2(list1);
    }  
  
    public void setList(ImmutableList<Map<String, Object>> immutableList) {  
        this.list = immutableList;  
    }  
} 