package com.mos.bsd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.mos.bsd.dao.IBsdStoreDao;
import com.x3.base.core.entity.ServiceTag;
import com.x3.base.core.entity.ServiceType;
import com.x3.datahelper.X3DBSaveTemplate;
@Repository("com.mos.bsd.dao.impl.BsdStoreDaoImpl")
public class BsdStoreDaoImpl extends X3DBSaveTemplate implements IBsdStoreDao {

	@Override
	public void getServiceDB(ServiceTag tag) {
		tag.setAuthor("hao");
		tag.setEmail("1723923383@qq.com");
		tag.setTag("bsd店铺档案dao");
		tag.setCaption("bsd店铺档案dao");
		tag.setServiceType(ServiceType.X2Bills);
	}

	@Override
	public List<Map<String, Object>> findDepartmentId(List<Map<String, Object>> storeList) {
		// 判断storeList的长度
		if (storeList == null || storeList.size() == 0) {
			return new ArrayList<Map<String, Object>>();
		}
		StringBuilder sb = new StringBuilder();
		int num = storeList.size() % 1000 > 0 ? storeList.size() / 1000 + 1 : storeList.size() / 1000;
		if (storeList.size() > 1000) {
			for (int i = 0; i < num; i++) {
				StringBuilder sql_card = new StringBuilder();
				for (int j = i * 1000; j < (i+1) * 1000; j++) {
					if( j==storeList.size() ) {
						break;
					}
					if (sql_card.toString().equals("")) {
						sql_card.append("'");
						sql_card.append(String.valueOf(storeList.get(j).get("depot_code")));
						sql_card.append("'");
					} else {
						sql_card.append(",'");
						sql_card.append(String.valueOf(storeList.get(j).get("depot_code")));
						sql_card.append("'");
					}
				}
				if (i == 0) {
					sb.append(" select department_id,department_user_id from d0060 where department_user_id in("
							+ sql_card.toString() + ") and system_type=11 ");
				} else {
					sb.append(" union all ");
					sb.append(" select department_id,department_user_id from d0060 where department_user_id in("
							+ sql_card.toString() + ") and system_type=11 ");
				}
			}
		} else {
			StringBuilder sql_card = new StringBuilder();
			for(Map<String, Object> map:storeList) {
				if(sql_card.toString().equals("")) {
					sql_card.append("'");
					sql_card.append(String.valueOf(map.get("depot_code")));
					sql_card.append("'");
				}else {
					sql_card.append(",'");
					sql_card.append(String.valueOf(map.get("depot_code")));
					sql_card.append("'");
				}
			}
			sb.append(" select department_id,department_user_id from d0060 where department_user_id in("
					+ sql_card.toString() + ") and system_type=11 ");
		}
		return this.getJdbcTemplate().queryForList(sb.toString());
	}

	@Override
	public int clearData() {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from vipdp0000");
		return this.getJdbcTemplate().update(sb.toString());
	}

	@Override
	public int insertData(List<Map<String, Object>> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into vipdp0000(department_id)values(?)");
		int count [] = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, String.valueOf(list.get(i).get("DEPARTMENT_ID")));
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		return count.length;
	}

}
