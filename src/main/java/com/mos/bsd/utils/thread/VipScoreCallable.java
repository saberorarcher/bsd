package com.mos.bsd.utils.thread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.mos.bsd.biz.IBsdVipScoreBiz;

@SuppressWarnings("rawtypes")
@Component("com.mos.bsd.utils.thread.VipScoreCallable")
public class VipScoreCallable implements Callable {  
    private List<Map<String, Object>> list;  
  
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdVipScoreBizImpl")
	private IBsdVipScoreBiz vipScore_biz;
	
    @Override  
    public Object call() throws Exception {  
    	
    	System.out.println("当前线程:"+Thread.currentThread().getName());
    	
    	//写入x2
		int i = vipScore_biz.updateVipScore(list);
		
		i = vipScore_biz.updateVipScoreExtend(list);
		
		return i;
		
    }  
  
    public void setList(ImmutableList<Map<String, Object>> immutableList) {  
        this.list = immutableList;  
    }  
} 