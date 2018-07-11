package com.mos.bsd.utils.scheduler;

import java.sql.SQLException;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 定时获取token工具类,定时任务,两小时执行一次
 * @author hao
 *
 */
@Component
public class Scheduler {
	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
	
	@Autowired
	private Environment env;
	
	@Scheduled(fixedDelay=7180000)
	public void getAccessToken() throws SQLException{
        logger.info("==============开始获取access_token===============");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(env.getProperty("mos.bsd.url")+"/bsdyun-open-api/sym/applyAccessToken?appId=shopguide&appKey=Bsd1976PassWord");
        JSONObject response = new JSONObject();
        
        String tokenStr = "";
        
        try {  
        	CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
        	if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(httpResponse.getEntity());//返回json格式：
				response = JSONObject.parseObject(result);
			}else {
				logger.info("获取token出错!"+httpResponse.getStatusLine());
			}
        	
        	tokenStr = response.getJSONObject("data").getString("token");
        	
            logger.info("==============结束获取access_token===============");
            
            logger.info("==================================token:"+tokenStr);
        } catch (Exception e) {  
        	e.printStackTrace();  
        }
        logger.info("==============开始写入access_token===============");
        
        TokenUtil t = TokenUtil.getInstance();
        t.setToken(tokenStr);
        
        logger.info("==============写入access_token成功===============");
    }
}
	
