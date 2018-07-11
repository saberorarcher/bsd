package com.mos.bsd.utils.thread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.mos.bsd.biz.IBsdCouponsGroupBiz;

@SuppressWarnings("rawtypes")
@Component("com.mos.bsd.utils.thread.CouponsGroupCallable")
public class CouponsGroupCallable implements Callable {  
    private List<Map<String, Object>> list;  
  
	@Resource(name="com.mos.bsd.biz.impl.BsdCouponsGroupBizImpl")
	private IBsdCouponsGroupBiz couponsGroup_biz;
	
    @Override  
    public Object call() throws Exception {  
    	System.out.println("当前线程:"+Thread.currentThread().getName());
    	return couponsGroup_biz.mergeIntoX2(list);
    }  
  
    public void setList(ImmutableList<Map<String, Object>> immutableList) {  
        this.list = immutableList;  
    }  
} 