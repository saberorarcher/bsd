package com.mos.bsd.biz;

import java.util.List;
import java.util.Map;

import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdSaleOrderBiz {
	/**
	 * 获取订单数据
	 * @param string 
	 * @param i 1表示增量 2表示初始化数据使用
	 * @param uuid 
	 * @return
	 */
	Map<String, List<Map<String, Object>>> getcouponsGroupData(String string,String department_id,String sdate,String edate, int i, String uuid);
	/**
	 * 同步零售单信息到x2
	 * @param couponsGroup_list
	 */
	Map<String, List<Map<String, Object>>> saveDataToX2(Map<String, List<Map<String, Object>>> couponsGroup_list);
	/**
	 * 获取需要同步的网点数据
	 * @return
	 */
	List<Map<String, Object>> getStoreList();
	/**
	 * 保存原始数据
	 * @param sdate
	 * @param edate
	 * @param type 1表示增量  2表示初始化数据
	 * @return
	 */
	int getBaseData(String department_user_id,String department_id,String sdate, String edate, int type);
	/**
	 * 查询原始数据
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();
	/**
	 * 查询未读取的数据
	 * @return
	 */
	List<Map<String, Object>> getTemData();
	/**
	 * 修改状态
	 * @param valueOf
	 */
	int updateErrorData(String valueOf);

}
