package com.mos.bsd.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mos.bsd.dao.IinitialdataDao;
import com.mos.bsd.domain.InitialdataEntity;
import com.x3.base.core.entity.ServiceTag;
import com.x3.datahelper.X3DBSaveTemplate;

import oracle.sql.CLOB;
@Repository("com.mos.bsd.dao.impl.InitialdataDaoImpl")
public class InitialdataDaoImpl extends X3DBSaveTemplate implements IinitialdataDao {
	
	private static final Logger logger = LoggerFactory.getLogger(InitialdataDaoImpl.class);
	
	@Override
	public int insertData(List<Map<String, String>> initJson) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append(" insert into Bsd_interface_initialdata (id,interface_name,create_date,request_data,received_data,uuid,status,count_uuid) ");
		sb.append(" values(BSD_INITDATA_ID.NEXTVAL,?,to_date(?,'yyyy-MM-dd hh24:mi:ss'),?,?,?,0,?) ");

		int count[] = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				ps.setString(1, String.valueOf(initJson.get(i).get("interface_name")));
				ps.setString(2, sdf.format(new Date(Long.parseLong(String.valueOf(initJson.get(i).get("create_date"))))));
				ps.setString(3, String.valueOf(initJson.get(i).get("request_data")));
				
				StringReader reader = new StringReader(String.valueOf(initJson.get(i).get("received_data")));
				ps.setCharacterStream(4, reader, String.valueOf(initJson.get(i).get("received_data")).length());
				ps.setString(5, String.valueOf(initJson.get(i).get("uuid")));
				ps.setString(6, String.valueOf(initJson.get(i).get("cuuid")));
//				ps.setString(7, String.valueOf(initJson.get(i).get("department_user_id")));
			}
			
			@Override
			public int getBatchSize() {
				return initJson.size();
			}
		});
		return count.length;
	}
	
	@Override
	public void getServiceDB(ServiceTag tag) {
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, InitialdataEntity>> getInitData(String status,String url) {
		StringBuilder sb = new StringBuilder();
		//and id >= 399388 and id<=414096
		sb.append(" select id,interface_name,create_date,request_data,received_data,uuid,status,count_uuid from Bsd_interface_initialdata where status = ? and interface_name=? and id > 399388 order by id ");

//		sb.append("  select id,interface_name,create_date,request_data,received_data,uuid,status,count_uuid  from Bsd_interface_initialdata a where ");
//		sb.append("  substr(request_data,13,4) ='BA33' and status=? and ");
//		sb.append("  interface_name=? ");
		
		return this.getJdbcTemplate().query(sb.toString(),new Object[] { status,url },new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map<String,InitialdataEntity> results = new HashMap<String,InitialdataEntity>();// 存放对像的hashmap
				CLOB clob = null;
				String str = "";
				try {
					InitialdataEntity bean = new InitialdataEntity();// 表对应的javaBean
					clob = (oracle.sql.CLOB) rs.getClob("received_data");// content字段属性为clob,转成clob对象
					str = clobToString(clob);// 将clob对象转为String
					bean.setId(rs.getString("id"));
					bean.setInterface_name(rs.getString("interface_name"));
					bean.setCreate_date(rs.getDate("create_date"));
					bean.setRequest_data(rs.getString("request_data"));
					bean.setReceived_data(str);
					bean.setUuid(rs.getString("uuid"));
					bean.setStatus(rs.getInt("status"));
					bean.setCuuid(rs.getString("count_uuid"));
					
					results.put("id", bean);
					
				} catch (Exception e) {
					logger.error(e.getMessage());
					results.put("content", new InitialdataEntity());
				}
				return results;

			}
			
			// 此方法将clob转为String
			@SuppressWarnings("deprecation")
			public String clobToString(CLOB clob) throws SQLException, IOException {
				String reString = "";
				if (clob == null || clob.getCharacterOutputStream() == null)
					return "";
				Reader is = clob.getCharacterStream();// 得到流
				BufferedReader br = new BufferedReader(is);
				String s = br.readLine();
				StringBuffer sb = new StringBuffer();
				while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
					sb.append(s);
					s = br.readLine();
				}
				reString = sb.toString();
				return reString;
			}
			
		});
	}

	@Override
	public int updateData(String uuid, int i) {
		StringBuilder sb = new StringBuilder();
		sb.append("update Bsd_interface_initialdata set status = ? where uuid=? ");
		return  this.getJdbcTemplate().update(sb.toString(),new Object[] { i,uuid });
	}

}
