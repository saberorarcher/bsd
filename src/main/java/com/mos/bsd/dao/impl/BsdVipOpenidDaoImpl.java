package com.mos.bsd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.mos.bsd.dao.IBsdVipOpenidDao;
import com.x3.base.core.entity.ServiceTag;
import com.x3.base.core.entity.ServiceType;
import com.x3.datahelper.X3DBSaveTemplate;

@Repository("com.mos.bsd.dao.impl.BsdVipOpenidDaoImpl")
public class BsdVipOpenidDaoImpl extends X3DBSaveTemplate implements IBsdVipOpenidDao {

	@Override
	public void getServiceDB(ServiceTag tag) {
		tag.setAuthor("hao");
		tag.setEmail("1723923383@qq.com");
		tag.setTag("bsd openId接口dao");
		tag.setCaption("bsd openId接口dao");
		tag.setServiceType(ServiceType.X2Bills);
	}

	@Override
	public int mergeD0210OpenId(List<Map<String, Object>> vipOpenid_list) {
		StringBuilder sb = new StringBuilder();
		sb.append("   merge into d0210 a ");
		sb.append("   using( ");
		sb.append("         select ? card_id,? vip_openid from dual ");
		sb.append("   )b ");
		sb.append("   on (a.card_id=b.card_id) ");
		sb.append("   when matched then ");
		sb.append("     update set a.vip_openid=b.vip_openid ");

		int count [] = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, String.valueOf(vipOpenid_list.get(i).get("card_id")));
				ps.setString(2, String.valueOf(vipOpenid_list.get(i).get("binding_val")));
			}
			
			@Override
			public int getBatchSize() {
				return vipOpenid_list.size();
			}
		});
		return count.length;
	}

	@Override
	public int mergeVipbinding(List<Map<String, Object>> vipOpenid_list) {
		StringBuilder sb = new StringBuilder();
		sb.append("   merge into j_vipbinding a ");
		sb.append("   using( ");
		sb.append("         select ? vip_id,? binding_app,0 binding_type,109500 depot_group,300000008245 vip_company,? binding_val from dual ");
		sb.append("   ) b");
		sb.append("   on ( a.vip_id=b.vip_id ) ");
		sb.append("   when matched then ");
		sb.append("     update set a.binding_val=b.binding_val ");
		sb.append("   when not matched then ");
		sb.append("     insert (a.vip_id,a.binding_app,a.binding_type,a.depot_group,a.vip_company,a.binding_val) ");
		sb.append("     values (b.vip_id,b.binding_app,b.binding_type,b.depot_group,b.vip_company,b.binding_val) ");
		
		int count[] = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				String vip_id = String.valueOf(vipOpenid_list.get(i).get("vip_id")).equals("null")?"0":String.valueOf(vipOpenid_list.get(i).get("vip_id"));
				if(!vip_id.equals("0")) {
					ps.setString(1, vip_id);
					ps.setString(2, String.valueOf(vipOpenid_list.get(i).get("binding_app")));
					ps.setString(3, String.valueOf(vipOpenid_list.get(i).get("binding_val")));
				}
			}
			
			@Override
			public int getBatchSize() {
				return vipOpenid_list.size();
			}
		});
		
		return count.length;
	}

	@Override
	public List<Map<String, Object>> getVipId(List<Map<String, Object>> vipOpenid_list) {
		StringBuilder sb = new StringBuilder();

		int num = vipOpenid_list.size() % 1000 > 0 ? vipOpenid_list.size() / 1000 + 1 : vipOpenid_list.size() / 1000;
		if (vipOpenid_list.size() > 1000) {
			for (int i = 0; i < num; i++) {
				StringBuilder sql_card = new StringBuilder();
				for (int j = i * 1000; j < (i+1) * 1000; j++) {
					if(j==vipOpenid_list.size()) {
						break;
					}
					if (sql_card.toString().equals("")) {
						sql_card.append("'");
						sql_card.append(String.valueOf(vipOpenid_list.get(j).get("card_id")));
						sql_card.append("'");
					} else {
						sql_card.append(",'");
						sql_card.append(String.valueOf(vipOpenid_list.get(j).get("card_id")));
						sql_card.append("'");
					}
				}
				if (i == 0) {
					sb.append("   select a.card_id,a.vip_id from D0210 a ");
					sb.append("   where a.card_id in(" + sql_card.toString() + ") ");
				} else {
					sb.append(" union all ");
					sb.append("   select a.card_id,a.vip_id from D0210 a ");
					sb.append("   where a.card_id in(" + sql_card.toString() + ") ");
				}
			}
		} else {
			StringBuilder sql_card = new StringBuilder();
			for (Map<String, Object> map : vipOpenid_list) {
				if (sql_card.toString().equals("")) {
					sql_card.append("'");
					sql_card.append(String.valueOf(map.get("card_id")));
					sql_card.append("'");
				} else {
					sql_card.append(",'");
					sql_card.append(String.valueOf(map.get("card_id")));
					sql_card.append("'");
				}
			}
			sb.append("   select a.card_id,a.vip_id from D0210 a ");
			sb.append("   where a.card_id in(" + sql_card.toString() + ") ");
		}
		return this.getJdbcTemplate().queryForList(sb.toString());
	}

}
