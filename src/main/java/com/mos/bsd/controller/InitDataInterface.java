package com.mos.bsd.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.utils.HttpPostUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Configuration
@Api(value = "BSD初始化数据接口", description = "BSD初始化数据接口", position = 1)
@RequestMapping("/api")
@Controller("com.mos.bsd.controller.InitDataInterface")
public class InitDataInterface {
	
	@SuppressWarnings("deprecation")
	@ApiOperation(value = "BSD初始化数据接口", notes = "BSD初始化数据接口")
	@RequestMapping(value = "/initData/bsdToX2", method = RequestMethod.POST, produces = "application/json")
	public BSDResponse toX2Bills(HttpServletResponse response,@RequestParam("url") String url) {
		
		String address = "";
		JSONObject resultData = new JSONObject();
		//日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//定义日历  从2016-03-31 开始  至当前时间
		Date nowDate = new Date(System.currentTimeMillis());
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		start.set(2016, 02, 01);
		end.set(nowDate.getYear()+1900, nowDate.getMonth(), nowDate.getDay());
		
		//获取当前时间至2016-03-31之间月份的差
		int result = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
		int result1 = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
		
		result = result1*12+result;
				
		System.out.println("*******************result"+result);
		//循环日期,每次月份+1,取月份的第一天和最后一天
		for ( int i=0;i<result;i++ ) {
			
			Calendar begin = Calendar.getInstance();
			begin.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), 1);
			
			Calendar end1 = Calendar.getInstance();
			end1.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			//转化成字符串
			String bedString = sdf.format(begin.getTime());
			String endString = sdf.format(end1.getTime());
			
			//使用http请求
			HttpPostUtils utils = new HttpPostUtils();
			//拼装请求地址
//			url="http://localhost:8080/api/vipScore/bsdToX2?sdate=2016-03-01&edate=2016-03-31";
			address = "http://localhost:8080/api/"+url+"/bsdToX2?sdate="+bedString+"&edate="+endString+"";
			//设置请求参数
			JSONObject object = new JSONObject();
			object.put("sdate", bedString);
			object.put("edate", endString);
			resultData = utils.postToLocal(address, object);
			
			start.add(Calendar.MONTH, 1);
			System.out.println("*****************i"+i);
		}
		
		
		System.out.println(resultData);
		BSDResponse responses = new BSDResponse();
		
		return responses;
	}
	
}
