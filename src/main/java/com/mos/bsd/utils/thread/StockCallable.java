package com.mos.bsd.utils.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.mos.bsd.biz.IBsdClothingStockBiz;

@SuppressWarnings("rawtypes")
@Component("com.mos.bsd.utils.thread.StockCallable")
public class StockCallable implements Callable {  
    private List<Map<String, Object>> list;  
  
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdClothingStockBizImpl")
	private IBsdClothingStockBiz stockBiz;
    
	private String uuid;
	
    @Override  
    public Object call() throws Exception {  
    	//处理业务逻辑
    	List<List<Map<String, Object>>> total = new ArrayList<List<Map<String, Object>>>();
    	for(Map<String, Object> map : list) {
    		
//    		System.out.println("当前线程:"+Thread.currentThread().getName());
			if (!String.valueOf(map.get("DEPARTMENT_USER_ID")).equals("A001")) {
				continue;
			}
    		
	    	Map<String ,List<Map<String, Object>>> couponsGroup_map = stockBiz.getStockData(String.valueOf(map.get("DEPARTMENT_USER_ID")),String.valueOf(map.get("DEPARTMENT_ID")),uuid);
	    	
			List<Map<String, Object>> errorList = couponsGroup_map.get("errorList");
			List<Map<String, Object>> stockList = couponsGroup_map.get("list");
			//保存到x2
			stockBiz.saveDataToX2(stockList);
			
			total.add(errorList);
    	}
        return total;  
    }  
  
    public void setList(ImmutableList<Map<String, Object>> immutableList) {  
        this.list = immutableList;  
    }  
    
    public void setUuid(String uuid) {
    	this.uuid = uuid;  
    }
} 