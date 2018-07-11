package com.mos.bsd.biz;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.mos.bsd.domain.BSDResponse;
import com.mos.bsd.domain.InitialdataEntity;

public interface IBsdStoreBiz {
	/**
	 * 获取网点
	 * @param i 1代表增量 ,2表示初始化数据
	 * @param string 
	 * @param uuid 
	 * @return
	 */
	Map<String, List<Map<String,Object>>> getStoreData(String string, int i, String uuid);
	/**
	 * http请求保存服务
	 * @param string
	 * @param areaList
	 * @return
	 */
	JSONArray save(String string, List<Map<String, Object>> areaList);
	/**
	 * 查询出要添加到店铺分组的department_id
	 * @param storeList
	 * @return
	 */
	List<Map<String, Object>> findDepartmentId(List<Map<String, Object>> storeList);
	/**
	 * 保存数据进临时表
	 * @param list
	 */
	void insertUserKey(List<Map<String, Object>> list);
	/**
	 * 调用rmi服务
	 */
	BSDResponse doRmiService();
	/**
	 * 读取原始数据，并保存到x2表
	 * @param sdate
	 * @param type
	 * @return
	 */
	int getStoreBaseData(String sdate, int type);
	/**
	 * 讀取數據
	 * @return
	 */
	List<Map<String, InitialdataEntity>> getClothingBaseData();

}
