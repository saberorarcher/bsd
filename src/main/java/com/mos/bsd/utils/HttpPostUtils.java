package com.mos.bsd.utils;


import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.mos.bsd.utils.scheduler.TokenUtil;


public class HttpPostUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpPostUtils.class);
	
	public JSONObject postHttp(String url, JSONObject json) {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
//		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
//		HttpGet httpGet = new HttpGet("http://58.211.79.4:14105/bsdyun-open-api/sym/applyAccessToken?appId=shopguide&appKey=Bsd1976PassWord");
		
		HttpPost httpPost = new HttpPost(url);
//		httpPost.setConfig(requestConfig);
//		httpGet.setConfig(requestConfig);
		JSONObject response = new JSONObject();
		
		try {
//			CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
//			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				String result = EntityUtils.toString(httpResponse.getEntity());//返回json格式：
//				response = JSONObject.parseObject(result);
//			}
//			else {
//				response.put("success", false);
//				response.put("errorMessage", "访问token出错!"+httpResponse.getStatusLine());
//				
//				return response;
//			}
//			//获取token
//			String tokenStr = response.getJSONObject("data").getString("token");
			
			String tokenStr = TokenUtil.getInstance().getToken();
			
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
			logger.error(e.getMessage());
		} finally {
//			httpGet.releaseConnection();
			httpPost.releaseConnection();
		}
		return response;
	}
	
	public JSONObject postToX2(JSONObject json) {
		
//		String url = "http://58.211.79.4:11903/BigBossQuery.ashx";
		String url = "http://47.101.143.38:702/BigBossQuery.ashx";
//		String url = "http://58.211.79.4:1901/mobile/API";
		CloseableHttpClient httpclient = HttpClients.createDefault();
//		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(200000).setConnectTimeout(200000).build();//设置请求和传输超时时间
		
		HttpPost httpPost = new HttpPost(url);
//		httpPost.setConfig(requestConfig);
		JSONObject response = new JSONObject();
		
		try {
//			String sessionKey = login().getJSONObject("data").getString("SESSIONKEY");
			StringEntity s = new StringEntity(json.toJSONString(),"utf-8");
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");// 发送json数据需要设置contentType
//			httpPost.addHeader("SessionKey", sessionKey);
			httpPost.setEntity(s);
			HttpResponse res = httpclient.execute(httpPost);

			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(res.getEntity());// 返回json格式：
				response = JSONObject.parseObject(result);
			}else {
				response.put("status", false);
				response.put("errorMsg", "访问接口出错!"+res.getStatusLine());
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.releaseConnection();
		}
		return response;
	}
	
	public JSONObject login() throws ClientProtocolException, IOException {
		
		JSONObject response = new JSONObject();
		JSONObject json = new JSONObject();
		json.put("usercode", "zhxit");
		json.put("password", "123");
		
		String login_url = "http://58.211.79.4:1901/mobile/Login";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();//设置请求和传输超时时间
		HttpPost httpPost = new HttpPost(login_url);
		httpPost.setConfig(requestConfig);
		
		StringEntity s = new StringEntity(json.toJSONString());
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json");// 发送json数据需要设置contentType
		httpPost.setEntity(s);
		
		HttpResponse res = httpclient.execute(httpPost);
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String result = EntityUtils.toString(res.getEntity());// 返回json格式：
			response = JSONObject.parseObject(result);
		}else {
			response.put("status", false);
			response.put("errorMsg", "访问接口出错!"+res.getStatusLine());
			return response;
		}
		return response;
	}
	
	public JSONObject postToLocal(String url, JSONObject json) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
//		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();//设置请求和传输超时时间
//		httpPost.setConfig(requestConfig);
		JSONObject response = new JSONObject();
		try {
			StringEntity s = new StringEntity(json.toJSONString(),"utf-8");
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");// 发送json数据需要设置contentType
			httpPost.setEntity(s);
			HttpResponse res = httpclient.execute(httpPost);
			
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(res.getEntity());// 返回json格式：
				response = JSONObject.parseObject(result);
			}else {
				response.put("status", false);
				response.put("errorMsg", "访问接口出错!"+res.getStatusLine());
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.releaseConnection();
		}
		return response;
	}
	
	
	
}
