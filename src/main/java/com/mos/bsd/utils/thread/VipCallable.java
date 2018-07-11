package com.mos.bsd.utils.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.mos.bsd.biz.IBsdVipBiz;

@SuppressWarnings("rawtypes")
@Component("com.mos.bsd.utils.thread.VipCallable")
public class VipCallable implements Callable {  
    private List<Map<String, Object>> list;  
  
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipBizImpl")
	private IBsdVipBiz vip_biz;
	
    @Override  
    public Object call() throws Exception {  
    	
    	System.out.println("当前线程:"+Thread.currentThread().getName());
    	
    	List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
    	list1.addAll(list);
    	
		//转换department_user_id 为department_id
		Map<String,List<Map<String, Object>>> newVipMap = vip_biz.getDepartmentId(list1);
		
		List<Map<String, Object>> newVipList = newVipMap.get("list");
		List<Map<String, Object>> errorVipList = newVipMap.get("error");
    	
    	vip_biz.insertD0210(newVipList);
    	
    	vip_biz.insertP0290(newVipList,"");
    	
    	return errorVipList;
    }  
  
    public void setList(ImmutableList<Map<String, Object>> immutableList) {  
        this.list = immutableList;  
    }  
} 