package com.mos.bsd.dao;

import java.util.List;
import java.util.Map;

public interface IBsdSaleOrderDao {
	/**
	 * 保存零售单主单
	 * @param list
	 * @return
	 */
	int mergeDretail(List<Map<String, Object>> list);
	/**
	 * 保存零售单明细
	 * @param list
	 * @return
	 */
	int mergeDretailsub(List<Map<String, Object>> list);
	/**
	 * 保存零售单扩展信息
	 * @param list
	 * @return
	 */
	int mergeDretailexpandinfo(List<Map<String, Object>> list);
	/**
	 * 保存零售单收款方式
	 * @param list
	 * @return
	 */
	int mergeDretailpaytype(List<Map<String, Object>> list);
	/**
	 * 保存零售单营业员
	 * @param list
	 * @return
	 */
	int mergeDretailclerk(List<Map<String, Object>> list);
	/**
	 * 获取需要更新的网点
	 * @return
	 */
	List<Map<String, Object>> getStoreList();
	/**
	 * 根据bill_id查询p0670_id
	 * @param retailList
	 * @return
	 */
	List<Map<String, Object>> getP0670_list(List<Map<String, Object>> retailList);
	/**
	 * 获取款式年份和季度
	 * @param retailsubList
	 * @return
	 */
	List<Map<String, Object>> getYear_list(List<Map<String, Object>> retailsubList);
	/**
	 * 查询未读取的数据
	 * @return
	 */
	List<Map<String, Object>> getTemData();
	/**
	 * 修改错误数据
	 * @param id
	 * @return
	 */
	int updateErrorData(String id);

}
