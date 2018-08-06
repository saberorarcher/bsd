package com.mos.bsd.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.mos.bsd.dao.IBsdCommonDao;
import com.x3.base.core.entity.ServiceTag;
import com.x3.datahelper.X3DBSaveTemplate;

@Repository("com.mos.bsd.dao.impl.BsdCommonDaoImpl")
public class BsdCommonDaoImpl extends X3DBSaveTemplate implements IBsdCommonDao {
	
	@Override
	public void getServiceDB(ServiceTag arg0) {}

	@Override
	public int saveLog(List<Map<String, String>> list) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		StringBuilder sb = new StringBuilder();
		sb.append("    insert into MT_LOG_INTERFACE(id,name,req_data,res_data,status,log_date,req_time,res_time,times) ");
		sb.append("    values(SEQ_MT_LOG_INTERFACE.NEXTVAL,?,?,?,?,sysdate,to_date(?,'yyyy-MM-dd hh24:mi:ss'),to_date(?,'yyyy-MM-dd hh24:mi:ss'),?) ");
		
		int count[] = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				String req_time = sdf.format(new Date(Long.parseLong(list.get(i).get("req_time"))));
				String res_time = sdf.format(new Date(Long.parseLong(list.get(i).get("res_time"))));
				
				ps.setString(1, list.get(i).get("name"));
				ps.setString(2, list.get(i).get("req_data"));
				ps.setString(3, list.get(i).get("res_data"));
				ps.setString(4, list.get(i).get("status"));
				ps.setString(5, req_time);
				ps.setString(6, res_time);
				ps.setString(7, list.get(i).get("times"));
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		return count.length;
	}

}
