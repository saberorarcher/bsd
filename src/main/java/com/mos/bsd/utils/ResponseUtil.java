package com.mos.bsd.utils;

import java.util.ArrayList;
import java.util.List;

import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.BillSubmit;


/**
 * @Description 返回消息工具类
 * @author 陈真
 * @date 2018年5月11日 上午10:20:44
 * @version 1.0
 */
public class ResponseUtil {
	/**
	 * 设置返回消息
	 * 
	 * @param msgId
	 *            参数id
	 * @param refId
	 *            外部记录id
	 * @param dataKey
	 *            内部记录id
	 * @param status
	 *            状态："E"失败，"S"成功
	 * @param msg
	 *            消息内容
	 * @return
	 * @returnType GatewayResponse
	 */
	public static BSDResponse setResponse(String msgId, String refId, String dataKey, String status, String msg) {
		BSDResponse response = new BSDResponse();
		response.setMsgId(msgId);
		response.setStatus(status);
		response.setMsg(msg);
		return response;
	}

	/**
	 * @param valiExcep
	 *            数据验证异常
	 * @return
	 * @returnType GatewayResponse
	 */
	public static BSDResponse setResponse(ValidateException valiExcep) {
		BSDResponse response = new BSDResponse();
		response.setMsgId(valiExcep.getMsgId());
		response.setStatus(valiExcep.getStatus());
		response.setMsg(valiExcep.getMsg());
		return response;
	}

	/**
	 * 返回消息列表
	 * 
	 * @param msgId
	 *            参数id
	 * @param refId
	 *            外部记录id
	 * @param dataKey
	 *            内部记录id
	 * @param status
	 *            状态："E"失败，"S"成功
	 * @param msg
	 *            消息内容
	 * @return
	 * @returnType List<GatewayResponse>
	 */
	public static List<BSDResponse> setResponseList(String msgId, String refId, String dataKey, String status,
			String msg) {
		BSDResponse response = new BSDResponse();
		response.setMsgId(msgId);
		response.setStatus(status);
		response.setMsg(msg);
		List<BSDResponse> responseList = new ArrayList<>();
		responseList.add(response);
		return responseList;
	}

	/**
	 * @param valiExcep
	 *            数据验证异常
	 * @return
	 * @returnType List<GatewayResponse>
	 */
	public static List<BSDResponse> setResponseList(ValidateException valiExcep) {
		BSDResponse response = new BSDResponse();
		response.setMsgId(valiExcep.getMsgId());
		response.setStatus(valiExcep.getStatus());
		response.setMsg(valiExcep.getMsg());
		List<BSDResponse> responseList = new ArrayList<>();
		responseList.add(response);
		return responseList;
	}

	public static BSDResponse setResponse(String status, String msg) {
		BSDResponse response = new BSDResponse();
		response.setStatus(status);
		response.setMsg(msg);
		return response;
	}

	public static List<BSDResponse> setResponseList(BillSubmit billSubmit, String status, String msg) {
		BSDResponse response = new BSDResponse();
		response.setMsgId(billSubmit.getMsg_id());
		response.setStatus(status);
		response.setMsg(msg);
		List<BSDResponse> responseList = new ArrayList<>();
		responseList.add(response);
		return responseList;
	}
}
