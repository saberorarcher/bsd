package com.mos.bsd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.mos.bsd.dao.IBsdVipDao;
import com.x3.base.core.entity.ServiceTag;
import com.x3.base.core.entity.ServiceType;
import com.x3.datahelper.X3DBSaveTemplate;

/**
 * vip档案dao
 * @author hao
 *
 */
@Repository("com.mos.bsd.dao.impl.BsdVipDaoImpl")
public class BsdVipDaoImpl extends X3DBSaveTemplate implements IBsdVipDao {

	@Override
	public int insertD0210(List<Map<String, Object>> vipList) {
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into d0210 a ");
		sb.append(" using( ");
		sb.append("       select ? card_id,? department_id,? vip_sex,? vip_birthday_year,? vip_birthday_month,? vip_birthday_day ");
		sb.append("       ,? vip_mobile,? vip_state,? vip_name,? vip_address,? vip_job,? vip_issue_date,? vip_create_name,? vip_email,? vip_academic ");
		sb.append("       ,? vip_create_department,? vip_province,? vip_town,? vip_district,? vip_likebrand,? vip_desired from dual ");
		sb.append(" )b ");
		sb.append(" on (a.card_id=b.card_id) ");
		sb.append(" when matched then ");
		sb.append(" update set a.department_id=b.department_id,a.vip_sex=b.vip_sex,a.vip_birthday_year=b.vip_birthday_year,a.vip_birthday_month=b.vip_birthday_month, ");
		sb.append("        a.vip_birthday_day=b.vip_birthday_day,a.vip_mobile=b.vip_mobile,a.vip_state=b.vip_state,a.vip_name=b.vip_name,a.vip_address=b.vip_address, ");
		sb.append("        a.vip_job=b.vip_job,a.vip_create_name=b.vip_create_name,a.vip_email=b.vip_email,a.vip_academic=b.vip_academic, ");
		sb.append("        a.vip_create_department=b.vip_create_department,a.vip_province=b.vip_province,a.vip_town=b.vip_town,a.vip_district=b.vip_district,a.vip_likebrand=b.vip_likebrand,a.vip_issue_date=b.vip_issue_date ");
		sb.append(" when not matched then ");
		sb.append(" insert(vip_id,card_id,department_id,vip_sex,vip_birthday_year,vip_birthday_month,vip_birthday_day,vip_mobile,vip_state, ");
		sb.append("      vip_name,vip_address,vip_job,vip_create_date,vip_create_name,vip_email,vip_academic,vip_create_department,vip_province, ");
		sb.append("      vip_town,vip_district,vip_likebrand,vip_desired,vip_issue_date ");
		sb.append(" ) ");
		sb.append(" values(SEQ_GLOBAL.NEXTVAL,b.card_id,b.department_id,b.vip_sex,b.vip_birthday_year,b.vip_birthday_month,b.vip_birthday_day,b.vip_mobile,b.vip_state, ");
		sb.append("      b.vip_name,b.vip_address,b.vip_job,sysdate,b.vip_create_name,b.vip_email,b.vip_academic,b.vip_create_department,b.vip_province, ");
		sb.append("      b.vip_town,b.vip_district,b.vip_likebrand,b.vip_desired,b.vip_issue_date) ");

		int [] count = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
//				String card_id = String.valueOf(vipList.get(i).get("card_id"));
//				if(card_id.equals("1681973099294404462")) {
//					System.out.println("***********************************找到记录");
//				}
				
				ps.setString(1, String.valueOf(vipList.get(i).get("card_id")));
				ps.setString(2, String.valueOf(vipList.get(i).get("department_id")).equals("null")?"125931":String.valueOf(vipList.get(i).get("department_id")));
				ps.setString(3, String.valueOf(vipList.get(i).get("vip_sex")).equals("MALE")?"1":"2");
				ps.setString(4, String.valueOf(vipList.get(i).get("vip_birthday_year")).equals("null")?"0":String.valueOf(vipList.get(i).get("vip_birthday_year")));
				ps.setString(5, String.valueOf(vipList.get(i).get("vip_birthday_month")).equals("null")?"0":String.valueOf(vipList.get(i).get("vip_birthday_month")));
				ps.setString(6, String.valueOf(vipList.get(i).get("vip_birthday_day")).equals("null")?"0":String.valueOf(vipList.get(i).get("vip_birthday_day")));
				ps.setString(7, String.valueOf(vipList.get(i).get("vip_mobile")));
				ps.setString(8, String.valueOf(vipList.get(i).get("vip_state")));
				
				if( String.valueOf(vipList.get(i).get("vip_name"))==null || String.valueOf(vipList.get(i).get("vip_name")).equals("") || String.valueOf(vipList.get(i).get("vip_name")).equals("null")) {
					ps.setString(9,"未知");
				}else {
					ps.setString(9,String.valueOf(vipList.get(i).get("vip_name")));
				}
				
				ps.setString(10, String.valueOf(vipList.get(i).get("vip_address")));
				ps.setString(11, String.valueOf(vipList.get(i).get("vip_job")));
				ps.setDate(12, new Date(Long.parseLong(String.valueOf(vipList.get(i).get("vip_issue_date")).equals("null")? String.valueOf(System.currentTimeMillis()):String.valueOf(vipList.get(i).get("vip_issue_date")))));  
				ps.setString(13, String.valueOf(vipList.get(i).get("vip_create_name")));
				ps.setString(14, String.valueOf(vipList.get(i).get("vip_email")));
				ps.setString(15, String.valueOf(vipList.get(i).get("vip_academic")));
				ps.setString(16, String.valueOf(vipList.get(i).get("vip_create_department")).equals("null")?"0":String.valueOf(vipList.get(i).get("vip_create_department")));
				ps.setString(17, String.valueOf(vipList.get(i).get("vip_province")));
				ps.setString(18, String.valueOf(vipList.get(i).get("vip_town")));
				ps.setString(19, String.valueOf(vipList.get(i).get("vip_district")));
				ps.setString(20, String.valueOf(vipList.get(i).get("createStoreNo")));
				ps.setString(21, String.valueOf(vipList.get(i).get("belongStoreNo")));
			}
			
			@Override
			public int getBatchSize() {
				return vipList.size();
			}
		});
		return count.length;
	}

	@Override
	public int insertD0180(List<Map<String, Object>> vipList,int i) {
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into D0180 a ");
		sb.append(" using ( ");
		sb.append("       select ? card_id,? viptype_id,? department_id,? card_issue_date,? card_create_name,? card_state,? card_issue_department_id,? vip_id from dual ");
		sb.append(" )b ");
		sb.append(" on (a.card_id=b.card_id) ");

		sb.append(" when matched then ");
		sb.append("   update set a.viptype_id=b.viptype_id,a.department_id=b.department_id,a.card_issue_date=b.card_issue_date,a.card_create_name=b.card_create_name, ");
		sb.append("   a.card_state=b.card_state,a.card_issue_department_id=b.card_issue_department_id,a.vip_id=b.vip_id ");

		sb.append(" when not matched then ");
		sb.append("   insert (card_id,viptype_id,department_id,card_issue_date,card_create_name,card_state,card_issue_department_id,vip_id) ");
		sb.append("   values(b.card_id,b.viptype_id,b.department_id,b.card_issue_date,b.card_create_name,b.card_state,b.card_issue_department_id,b.vip_id) ");

		int [] count = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, String.valueOf(vipList.get(i).get("card_id")));
				ps.setString(2, String.valueOf(vipList.get(i).get("memberLevel")));
				ps.setString(3, String.valueOf(vipList.get(i).get("department_id")).equals("null")?"125931":String.valueOf(vipList.get(i).get("department_id")));
				ps.setDate(4, new Date(Long.parseLong(String.valueOf(vipList.get(i).get("vip_issue_date")))));
				ps.setString(5, String.valueOf(vipList.get(i).get("vip_create_name")));
				ps.setString(6, String.valueOf(vipList.get(i).get("vip_state")));
				ps.setString(7, String.valueOf(vipList.get(i).get("vip_create_department")).equals("null")?"0":String.valueOf(vipList.get(i).get("vip_create_department")));
				ps.setString(8, String.valueOf(vipList.get(i).get("vip_id")).equals("null")?"0":String.valueOf(vipList.get(i).get("vip_id")));
				
			}
			
			@Override
			public int getBatchSize() {
				return vipList.size();
			}
		});
		
		return count.length;
	}

	@Override
	public int insertP0290(List<Map<String, Object>> vipList) {
		StringBuilder sb = new StringBuilder();
		sb.append("   insert into P0290(p0290_id,vip_id,card_id,viptype_id_old,viptype_id_new,system_type,op_name) ");
		sb.append("   values(SEQ_GLOBAL.NEXTVAL,?,?,?,?,?,'system')");
		
		int count [] = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
//				String vip_id = String.valueOf(vipList.get(i).get("vip_id"));
//				String card_id = String.valueOf(vipList.get(i).get("card_id"));
//				String old_viptype_id = String.valueOf(vipList.get(i).get("old_viptype_id"));
//				String memberLevel = String.valueOf(vipList.get(i).get("memberLevel"));
//				String system_type = String.valueOf(vipList.get(i).get("system_type"));
				
				ps.setString(1, String.valueOf(vipList.get(i).get("vip_id")));
				ps.setString(2, String.valueOf(vipList.get(i).get("card_id")));
				ps.setString(3, String.valueOf(vipList.get(i).get("old_viptype_id")));//旧卡类型
				ps.setString(4, String.valueOf(vipList.get(i).get("memberLevel")));
				ps.setString(5, String.valueOf(vipList.get(i).get("system_type")));

			}
			
			@Override
			public int getBatchSize() {
				return vipList.size();
			}
		});
		return count.length;
	}

	@Override
	public void getServiceDB(ServiceTag tag) {
		tag.setAuthor("hao");
		tag.setEmail("1723923383@qq.com");
		tag.setTag("vip档案dao");
		tag.setCaption("vip档案dao");
		tag.setServiceType(ServiceType.X2Bills);
	}

	@Override
	public List<Map<String, Object>> queryChangeData(List<Map<String, Object>> vipList) {
				
		StringBuilder sb = new StringBuilder();

		int num = vipList.size()%1000>0?vipList.size()/1000+1:vipList.size()/1000;
		if( vipList.size()>1000 ) {
			for( int i=0;i<num;i++ ) {
				StringBuilder sql_card = new StringBuilder();
				for( int j = i * 1000; j < (i+1) * 1000; j++ ) {
					if(j==vipList.size()) {
						break;
					}
					if(sql_card.toString().equals("")) {
						sql_card.append("'");
						sql_card.append(String.valueOf(vipList.get(j).get("card_id")));
						sql_card.append("'");
					}else {
						sql_card.append(",'");
						sql_card.append(String.valueOf(vipList.get(j).get("card_id")));
						sql_card.append("'");
					}
				}
				if( i==0 ) {
					sb.append("   select c.card_id,b.viptype_id old_viptype_id,c.viptype_id,case when c.viptype_id-b.viptype_id>0 then 0 else 1 end system_type from D0180 b ");
					sb.append("          inner join mt_card_temp c on b.card_id=c.card_id and b.viptype_id!=c.viptype_id ");
					sb.append("   where b.card_id in("+sql_card.toString()+") ");
				}else {
					sb.append(" union all ");
					sb.append("   select c.card_id,b.viptype_id old_viptype_id,c.viptype_id,case when c.viptype_id-b.viptype_id>0 then 0 else 1 end system_type from D0180 b ");
					sb.append("          inner join mt_card_temp c on b.card_id=c.card_id and b.viptype_id!=c.viptype_id ");
					sb.append("   where b.card_id in("+sql_card.toString()+") ");
				}
			}
		} else {
			StringBuilder sql_card = new StringBuilder();
			for(Map<String, Object> map:vipList) {
				if(sql_card.toString().equals("")) {
					sql_card.append("'");
					sql_card.append(String.valueOf(map.get("card_id")));
					sql_card.append("'");
				}else {
					sql_card.append(",'");
					sql_card.append(String.valueOf(map.get("card_id")));
					sql_card.append("'");
				}
			}
			sb.append("   select c.card_id,b.viptype_id old_viptype_id,c.viptype_id,case when c.viptype_id-b.viptype_id>0 then 0 else 1 end system_type from D0180 b ");
			sb.append("          inner join mt_card_temp c on b.card_id=c.card_id and b.viptype_id!=c.viptype_id ");
			sb.append("   where b.card_id in("+sql_card.toString()+") ");
		}
		return this.getJdbcTemplate().queryForList(sb.toString());
	}

	@Override
	public List<Map<String, Object>> getVipList() {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from d0210 where rownum<=100");
		
		return this.getJdbcTemplate().queryForList(sb.toString());
	}

	@Override
	public List<Map<String, Object>> queryDepartmentData(List<Map<String, Object>> vipList,int x) {
		if(vipList==null||vipList.size()<=0) {
			return new ArrayList<Map<String,Object>>();
		}
		String word = "";
		if(x==1) {
			word = "createStoreNo";
		}else {
			word = "belongStoreNo";
		}
		
		int num = vipList.size()%1000>0?vipList.size()/1000+1:vipList.size()/1000;
		StringBuilder sb = new StringBuilder();
		if( vipList.size()>1000 ) {
			for( int i=0;i<num;i++ ) {
				StringBuilder sql_card = new StringBuilder();
				for( int j = i * 1000; j < (i+1) * 1000; j++ ) {
					if(j==vipList.size()) {
						break;
					}
					if(sql_card.toString().equals("")) {
						sql_card.append("'");
						sql_card.append(String.valueOf(vipList.get(j).get(word)));
						sql_card.append("'");
					}else {
						sql_card.append(",'");
						sql_card.append(String.valueOf(vipList.get(j).get(word)));
						sql_card.append("'");
					}
				}
				if( i==0 ) {
					sb.append("   select a.department_id,a.department_user_id from V0060 a ");
					sb.append("   where a.department_user_id in("+sql_card.toString()+") and a.language_id='zh-cn'");
				}else {
					sb.append(" union all ");
					sb.append("   select a.department_id,a.department_user_id from V0060 a ");
					sb.append("   where a.department_user_id in("+sql_card.toString()+") and a.language_id='zh-cn'");
				}
			}
		} else {
			StringBuilder sql_card = new StringBuilder();
			for(Map<String, Object> map:vipList) {
				if(sql_card.toString().equals("")) {
					sql_card.append("'");
					sql_card.append(String.valueOf(map.get(word)));
					sql_card.append("'");
				}else {
					sql_card.append(",'");
					sql_card.append(String.valueOf(map.get(word)));
					sql_card.append("'");
				}
			}
			sb.append("   select a.department_id,a.department_user_id from V0060 a ");
			sb.append("   where a.department_user_id in("+sql_card.toString()+") and a.language_id='zh-cn'");
		}
		
		return this.getJdbcTemplate().queryForList(sb.toString());
	}

	@Override
	public int cleanTempData(String uuid) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from mt_card_temp where uuid=?");
		return this.getJdbcTemplate().update(sb.toString(),new Object[] { uuid });
	}

	@Override
	public int mergeTemData(List<Map<String, Object>> vipList,String uuid) {
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into mt_card_temp a ");
		sb.append(" using( ");
		sb.append("       select ? card_id,? viptype_id,? uuid from dual ");
		sb.append(" )b ");
		sb.append(" on(a.card_id=b.card_id ) ");
		sb.append(" when matched then ");
		sb.append("   update set a.viptype_id=b.viptype_id,a.uuid=b.uuid ");
		sb.append(" when not matched then ");
		sb.append("   insert(id,card_id,viptype_id,uuid) ");
		sb.append("   values(SEQ_GLOBAL.NEXTVAL,b.card_id,b.viptype_id,b.uuid) ");
		int [] count = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, String.valueOf(vipList.get(i).get("card_id")));
				ps.setString(2, String.valueOf(vipList.get(i).get("memberLevel")));
				ps.setString(3, uuid);
			}
			
			@Override
			public int getBatchSize() {
				return vipList.size();
			}
		});
				
		return count.length;
	}

	@Override
	public List<Map<String, Object>> findWrongData() {
		StringBuilder sb = new StringBuilder();
		sb.append("select id from table_hp");
		
		return this.getJdbcTemplate().queryForList(sb.toString());
	}

}
