package com.mos.bsd.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mos.bsd.utils.scheduler.TokenUtil;


public class Test_HttpPostUtils {
	public static void main(String[] args) throws ClientProtocolException, IOException, ParseException {
		JSONObject jObject = new JSONObject();
		long ts = System.currentTimeMillis();
//		jObject.put("ts", ts);
//		jObject.put("openId", "oclE4xKX_4M4uho-EtwZBeKPz6Zs");
		jObject.put("storeNo", "ZD83");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		String beginString ="2016-06-01 00:00:00";  
//		String endString = "2016-06-30 00:00:00";  
		
//		jObject.put("ts", sdf.format(new Date(ts)));
		
//		System.out.println(beginString);
//		System.out.println(endString);
		
		jObject.put("saleDateStart", "2018-07-08 00:00:00");
		jObject.put("saleDateEnd", "2018-07-09 00:00:00");
//		{"storeNo":"J528","saleDateEnd":"2018-06-22 00:00:00","offset":0,"limit":10000,"saleDateStart":"2018-05-24 00:00:00"}
//		jObject.put("openId", "o8J-zjtCc8BG5VaUQdo5jZN5B7zc");
//		
		jObject.put("offset", 0);
		jObject.put("limit", 100);
//		jObject.put("ts", "2018-07-05 00:00:00");
//		String beginString = "2011-12-01 00:00:00";
//		String endString = "2011-12-30 00:00:00";
//		jObject.put("ts", "2018-06-26 00:00:00");
		
//		jObject.put("beginAllocateDate", beginString);
//		jObject.put("endAllocateDate", endString);
		
//		JSONObject result = httpPostUtils.postHttp("http://10.101.4.105:8080/bsdyun-open-api/center/stock/getChangedProductsDetailCount", jObject);
//		JSONObject result = httpPostUtils.postHttp("http://10.101.4.105:8080/bsdyun-open-api/center/user/getPageMemberDimByPara", jObject);
//		JSONObject result = httpPostUtils.postHttp("http://10.101.4.105:8080/bsdyun-open-api/center/marketing/getMemberPointCountByPara", jObject);

		JSONObject result = new JSONObject();
//		JSONObject object = new JSONObject();
//		String ts = "";
//		for(int i=0;i<100;i++) {
			result = postHttp("http://58.211.79.7:18080/bsdyun-open-api/center/order/getPageSaleOrderByPara", jObject);
			System.out.println(result);
//		}
			 
//			 JSONArray array = result.getJSONArray("data");
//			 for( Object obj:array ) {
//				 object = (JSONObject) obj;
//				 ts=object.getString("ts");
//			 }
					
			 
		System.out.println(result);	 
		
		if(!result.getBoolean("success")) {
			System.out.println("错误:"+result.getString("errorMessage"));
		}
	
		
			
//		JSONObject result = httpPostUtils.postHttp("http://58.211.79.4:14105/bsdyun-open-api/center/stock/getPageChangedProductsDetail", jObject);	
//		JSONObject result = httpPostUtils.postHttp("http://172.16.17.75:8088/bsdyun-open-api/center/marketing/getMember", jObject);
//		JSONObject result = httpPostUtils.postHttp("http://10.101.4.105:8080/bsdyun-open-api/center/marketing/getMemberPointCountByPara", jObject);
//		JSONObject result = httpPostUtils.postHttp("http://10.101.4.105:8080/bsdyun-open-api/center/marketing/getPageMemberPointByPara", jObject);
		
//		
//		if(result.containsKey("status")&&!result.getBoolean("status")) {
//			System.out.println(result.getString("errorMsg"));
//		}
		
		//外网api地址:  http://58.211.79.4:14105/bsdyun-open-api
		
		//测试访问
//		HttpPostUtils httpPostUtils1 = new HttpPostUtils();
//		JSONObject session = httpPostUtils1.login();
//		String s = session.getJSONObject("data").getString("SESSIONKEY");
//		System.out.println(s);
		
//		jObject.put("DllName", "Zhx.X3.Eral.dll");
//		jObject.put("NameSpace", "Zhx.X3.Eral");
//		jObject.put("ClasssName", "X3_Input_Clothing");
//		jObject.put("Caller", "msgid");
//		jObject.put("UserKey", "0000");
//		jObject.put("JsonObj", new JSONArray());
//		
//		JSONObject result = httpPostUtils.postToX2(jObject);
		
		System.out.println(result);
		
	}
	
	
	public static JSONObject postHttp(String url, JSONObject json) {
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
//			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
			HttpGet httpGet = new HttpGet("http://58.211.79.4:14105/bsdyun-open-api/sym/applyAccessToken?appId=shopguide&appKey=Bsd1976PassWord");
			
//			HttpGet httpGet = new HttpGet("http://58.211.79.4:14105/bsdyun-open-api/sym/applyAccessToken?appId=shopguide&appKey=Bsd1976PassWord");

			
			HttpPost httpPost = new HttpPost(url);
	//		httpPost.setConfig(requestConfig);
	//		httpGet.setConfig(requestConfig);
			JSONObject response = new JSONObject();
			
			try {
				CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String result = EntityUtils.toString(httpResponse.getEntity());//返回json格式：
					response = JSONObject.parseObject(result);
				}
				else {
					response.put("success", false);
					response.put("errorMessage", "访问token出错!"+httpResponse.getStatusLine());
					
					return response;
				}
				//获取token
				String tokenStr = response.getJSONObject("data").getString("token");
				System.out.println(tokenStr);
	//			String tokenStr = TokenUtil.getInstance().getToken();
				
				StringEntity s = new StringEntity(json.toJSONString(),"utf-8");
				s.setContentEncoding("utf-8");
				s.setContentType("application/json");//发送json数据需要设置contentType
				httpPost.setEntity(s);
				httpPost.addHeader("Authorization", "Basic "+ tokenStr);
				
				CloseableHttpResponse res = httpclient.execute(httpPost);
				
				if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String result = EntityUtils.toString(res.getEntity());//返回json格式：
					response = JSONObject.parseObject(result);
				}else {
					response.put("success", false);
					response.put("errorMessage", "访问接口出错!"+res.getStatusLine());
					return response;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				httpGet.releaseConnection();
				httpPost.releaseConnection();
			}
			return response;
		}
}
