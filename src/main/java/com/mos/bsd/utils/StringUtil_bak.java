package com.mos.bsd.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.x3.base.core.exception.BusinessException;


/**
 * 字符串工具类
 * @author QDY
 *
 */
public class StringUtil_bak {

    /**
     * 判断STR是否为""或者null,如果为""或者null，返回	defaultValue
     * 如果不为空，返回STR
     * @param str
     * @param defaultValue
     * @return
     */
	public static String isBlankAndNull(String str,String defaultValue){
		if (isNotBlankAndNull(str))
			return str;
		else
			return defaultValue;
	}
	
	/**
	 * 判断字符串不等于""且不等于null
	 * @param str
	 * @return
	 */
	public static boolean isNotBlankAndNull(String str){
		return null != str && !"".equals(str);
	}
	
	/**
	 * 判断字段串为null或空字符串(空格也认为是空)
	 * @return
	 */
	public static boolean isBlankOrNull(String str){
		return str == null || str.trim().equals("");
	}

	/**
	 * 判断字符串是是否为""
	 * @param value
	 * @return
	 */
	public static boolean isBlank(String value) {
		return "".equals(value);
	}

	/**
	 * 判断metches字符串是否在str字符串里
	 * @param str 字符串
	 * @param metches 子字符串
	 * @return 包含返回成功
	 */
	public static boolean contains(String str, String metches) {
		return -1 != str.indexOf(metches);
	}

	/**
	 * 判断字符串等于""或者等于null.抛出异常
	 * @param str需要检查的字符串
	 * @param msg提示消息
	 */
	public static void checkNullException(final String str,final String msg){
		
		if (isNotBlankAndNull(str)==false)
			throw new BusinessException("StringUtil-01",msg);
	}
	
	
	/**
	 * 判断字符串等于""或者等于null.抛出异常
	 * @param str需要检查的字符串
	 * @param msg提示消息
	 * @param object 错误数据
	 */
	public static void checkNullException(final String str,final String msg,Object object){
		
		if (isNotBlankAndNull(str)==false)
			throw new BusinessException("StringUtil-02",msg,object);
	}


	/**
	 * 判断字符串不等于""或者不等于null.抛出异常
	 * @param str需要检查的字符串
	 * @param msg提示消息
	 * @param object 错误数据
	 */
	public static void checkIsNotNullException(final String str,final String msg,Object object){
		
		if (isNotBlankAndNull(str))
			throw new BusinessException("StringUtil-03",msg,object);
	}
	
	/**
	 * 比较str1与str2是否相等。不等抛出异常
	 * @param str1
	 * @param str2
	 * @param msg提示消息
	 */
	public static void compareException(final String str1,final String str2,final String msg){
		if (!compare(str1,str2))
			throw new BusinessException("StringUtil-04",msg);
			
	}

	/**
	 * 比较str1与str2是否相等。
	 * @param str1
	 * @param str2
	 * @param msg提示消息
	 */
	public static boolean compare(final String str1,final String str2){
		
		if (isNotBlankAndNull(str1)==false && isNotBlankAndNull(str2)==false)
			return true;
		if (isNotBlankAndNull(str1) && str1.equals(str2))
			return true;
		if (isNotBlankAndNull(str2) && str2.equals(str1))
			return true;
		
		return false;
			
	}
	public static String sqlInjection(String str){
		if (isBlankOrNull(str))
			return "";
		return str.replaceAll(".*([';]+|(--)+).*", " ");
	}
	
	public static String zip(String str)  {  
        if (null == str || str.length() <= 0) {  
            return str;  
        }  
	     try {
			return compress(str);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("StringUtil:zip", e.getMessage(), str);
		}
	    
    } 
	
	public static String unZip(String str)  {  
        if (null == str || str.length() <= 0) {  
            return str;  
        }  
	     try {
			return unCompress(str);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("StringUtil:unZip", e.getMessage(), str);
		}
	   
    } 
	
	private static String compress(String str) throws IOException {  
        if (null == str || str.length() <= 0) {  
            return str;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1");
    }  
	
	private static String unCompress(String str) throws IOException {  
        if (null == str || str.length() <= 0) {  
            return str;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str
            .getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
          out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
        return out.toString("UTF-8");
    }  
  
}
