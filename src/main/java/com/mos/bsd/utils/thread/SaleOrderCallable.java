package com.mos.bsd.utils.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.mos.bsd.biz.IBsdSaleOrderBiz;

@SuppressWarnings("rawtypes")
@Component("com.mos.bsd.utils.thread.SaleOrderCallable")
public class SaleOrderCallable implements Callable {  
    private List<Map<String, Object>> list;  
    private String sdate ;
    private String edate ;
    private int n;
    private String uuid;
    
	@Autowired
	@Qualifier("com.mos.bsd.biz.impl.BsdSaleOrderBizImpl")
	private IBsdSaleOrderBiz saleOrderBiz;
	
    @Override  
    public Object call() throws Exception {  
    	
//    	System.out.println("当前线程:"+Thread.currentThread().getName());
    	List<List<Map<String, Object>>> total = new ArrayList<List<Map<String, Object>>>();
    	
		for(Map<String, Object> map : list) {
			
			if(!String.valueOf(map.get("DEPARTMENT_USER_ID")).equals("A038") ) {
				continue;
			}
			
			Map<String ,List<Map<String, Object>>> couponsGroup_map = saleOrderBiz.getcouponsGroupData(String.valueOf(map.get("DEPARTMENT_USER_ID")),String.valueOf(map.get("DEPARTMENT_ID")),sdate,edate,n,uuid);
			List<Map<String, Object>> errorList = couponsGroup_map.get("errorList");
			//保存到x2临时数据
			Map<String, List<Map<String, Object>>> returnMap = saleOrderBiz.saveDataToX2(couponsGroup_map);
			
			List<Map<String, Object>> errorlist = returnMap.get("error");
			
			total.add(errorList);
			total.add(errorlist);
		}
		
		return total;
    }  
  
    public void setList(ImmutableList<Map<String, Object>> immutableList) {  
        this.list = immutableList;  
    }  
    
    public void setSdate(String sdate) {
    	this.sdate = sdate;
    }
    
    public void setEdate(String edate) {
    	this.edate = edate;
    }
    
    public void setN(int n) {
    	this.n = n;
    }

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
} 