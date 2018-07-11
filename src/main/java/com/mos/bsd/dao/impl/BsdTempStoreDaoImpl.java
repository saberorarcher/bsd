package com.mos.bsd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.mos.bsd.dao.IBsdTempStoreDao;
import com.x3.base.core.entity.ServiceTag;
import com.x3.datahelper.X3DBSaveTemplate;

@Repository("com.mos.bsd.dao.impl.BsdTempStoreDaoImpl")
public class BsdTempStoreDaoImpl extends X3DBSaveTemplate implements IBsdTempStoreDao {

	@Override
	public void getServiceDB(ServiceTag tag) {
		
	}

	@Override
	public int insertTemp(List<Map<String, Object>> list) {
		StringBuilder sb = new StringBuilder();
		sb.append(" insert into Bsd_department_temp(id,storeNo,storeName,customerNo,customerName,corpNo,corpName,areaNo, ");
		sb.append(" areaName,storeNatureId,storeForm,channelType,corpIsAgent,brandNo,status ");
		sb.append(" )values(BSD_DEPOT_ID.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,'0') ");

		int count [] = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setObject(1, list.get(i).get("storeNo"));
				ps.setObject(2, list.get(i).get("storeName"));
				ps.setObject(3, list.get(i).get("customerNo"));
				ps.setObject(4, list.get(i).get("customerName"));
				ps.setObject(5, list.get(i).get("corpNo"));
				ps.setObject(6, list.get(i).get("corpName"));
				ps.setObject(7, list.get(i).get("areaNo"));
				ps.setObject(8, list.get(i).get("areaName"));
				ps.setObject(9, list.get(i).get("storeNatureId"));
				ps.setObject(10, list.get(i).get("storeForm"));
				ps.setObject(11, list.get(i).get("channelType"));
				ps.setObject(12, list.get(i).get("corpIsAgent"));
				ps.setObject(13, list.get(i).get("brandNo"));
				
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		
		return count.length;
	}

}
