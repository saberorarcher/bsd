package com.mos.bsd.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.mos.bsd.dao.IBsdVipCouponsDao;
import com.x3.base.core.entity.ServiceTag;
import com.x3.base.core.entity.ServiceType;
import com.x3.datahelper.X3DBSaveTemplate;

@Repository("com.mos.bsd.dao.impl.BsdVipCouponsDaoImpl")
public class BsdVipCouponsDaoImpl extends X3DBSaveTemplate implements IBsdVipCouponsDao {

	@Override
	public void getServiceDB(ServiceTag tag) {
		tag.setAuthor("hao");
		tag.setEmail("1723923383@qq.com");
		tag.setTag("vip券档案dao");
		tag.setCaption("vip券档案dao");
		tag.setServiceType(ServiceType.X2Bills);
	}

	@Override
	public int mergeD0240(List<Map<String, Object>> coupons_list) {
		StringBuilder sb = new StringBuilder();
		sb.append("   merge into D0240 a ");
		sb.append("   using ( ");
		sb.append("         select ? coupontype_id,? coupon_issue_date , ");
		sb.append("         ? coupon_code,? coupon_customer,? vip_id,? coupon_used,? coupon_used_date ");
		sb.append("         ,? department_id,? p0670_id,? coupon_state,'system' coupon_name,'system' coupon_create_name,1 coupon_issue from dual ");
		sb.append("   )b ");
		sb.append("   on ( a.coupon_code=b.coupon_code ) ");
		sb.append("   when matched then ");
		sb.append("     update set a.coupon_issue_date=b.coupon_issue_date ");
		sb.append("     ,a.coupon_customer=b.coupon_customer,a.vip_id=b.vip_id,a.coupon_used=b.coupon_used ");
		sb.append("     ,a.coupon_used_date=b.coupon_used_date,a.department_id=b.department_id,a.p0670_id=b.p0670_id,a.coupon_state=b.coupon_state,a.coupon_issue=b.coupon_issue ");
		sb.append("     when not matched then ");
		sb.append("       insert (coupon_id,a.coupontype_id,a.coupon_issue_date, ");
		sb.append("         a.coupon_code,a.coupon_customer,a.vip_id,a.coupon_used,a.coupon_used_date ");
		sb.append("         ,a.department_id,a.p0670_id,a.coupon_state,a.coupon_name,coupon_create_name,coupon_issue) ");
		sb.append("       values(SEQ_GLOBAL.NEXTVAL,b.coupontype_id,b.coupon_issue_date, ");
		sb.append("         b.coupon_code,b.coupon_customer,b.vip_id,b.coupon_used,b.coupon_used_date ");
		sb.append("         ,b.department_id,b.p0670_id,b.coupon_state,b.coupon_name,b.coupon_create_name,b.coupon_issue) ");
		
		int count [] = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				String vip_id = String.valueOf(coupons_list.get(i).get("vip_id")).equals("null")?"0":String.valueOf(coupons_list.get(i).get("vip_id"));
				String coupontype_id = String.valueOf(coupons_list.get(i).get("coupontype_id")).trim();
				Date coupon_issue_date = new Date(Long.parseLong(String.valueOf(coupons_list.get(i).get("coupon_issue_date"))));
				String coupon_code = String.valueOf(coupons_list.get(i).get("coupon_code"));
				String scheme_id = String.valueOf(coupons_list.get(i).get("scheme_id"));
				String coupon_used = String.valueOf(coupons_list.get(i).get("coupon_used"));
				Date coupon_used_date = new Date(Long.parseLong(String.valueOf(coupons_list.get(i).get("coupon_used_date"))));
				String department_id = String.valueOf(coupons_list.get(i).get("department_id")).equals("null")?"0":String.valueOf(coupons_list.get(i).get("department_id"));
				String p0670_id = String.valueOf(coupons_list.get(i).get("p0670_id")).equals("null")?"0":String.valueOf(coupons_list.get(i).get("p0670_id"));
				String coupon_state = String.valueOf(coupons_list.get(i).get("coupon_state"));
				
				ps.setString(1, coupontype_id);
				ps.setDate(2, coupon_issue_date);
				ps.setString(3, coupon_code);
				ps.setString(4, scheme_id);
				ps.setString(5, vip_id);
				ps.setString(6, coupon_used);
				ps.setDate(7, coupon_used_date);
				ps.setString(8, department_id);
				ps.setString(9, p0670_id );
				ps.setString(10, coupon_state);
				
			}
			
			@Override
			public int getBatchSize() {
				return coupons_list.size();
			}
		});
		return count.length;
	}

	@Override
	public int mergeD0240Extend(List<Map<String, Object>> coupons_list) {
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into D0240_bsd_extend a ");
		sb.append(" using ( ");
		sb.append("       select ? coupon_code,? couponsSource,? wechatCardFlag,? useStore from dual ");
		sb.append(" )b ");
		sb.append(" on (a.coupon_code=b.coupon_code) ");
		sb.append(" when matched then ");
		sb.append("   update set a.couponsSource=b.couponsSource,a.wechatCardFlag=b.wechatCardFlag,a.useStore=b.useStore ");
		sb.append(" when not matched then ");
		sb.append("   insert(a.coupon_code,a.couponsSource,a.wechatCardFlag,a.useStore) ");
		sb.append("   values(b.coupon_code,b.couponsSource,b.wechatCardFlag,b.useStore) ");

		int [] count = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, String.valueOf(coupons_list.get(i).get("coupon_code")));
				ps.setString(2, String.valueOf(coupons_list.get(i).get("couponsSource")));
				ps.setString(3, String.valueOf(coupons_list.get(i).get("wechatCardFlag")));
				ps.setString(4, String.valueOf(coupons_list.get(i).get("useStore")));
			}
			
			@Override
			public int getBatchSize() {
				return coupons_list.size();
			}
		});
		return count.length;
	}

	@Override
	public List<Map<String, Object>> getVipList(List<Map<String, Object>> coupons_list) {
		
		int num = coupons_list.size()%1000>0?coupons_list.size()/1000+1:coupons_list.size()/1000;
		StringBuilder sb = new StringBuilder();
		if( coupons_list.size()>1000 ) {
			for( int i=0;i<num;i++ ) {
				StringBuilder sql_card = new StringBuilder();
				for( int j = i * 1000; j < (i+1) * 1000; j++ ) {
					if(j==coupons_list.size()) {
						break;
					}
					if(sql_card.toString().equals("")) {
						sql_card.append("'");
						sql_card.append(String.valueOf(coupons_list.get(j).get("card_id")));
						sql_card.append("'");
					}else {
						sql_card.append(",'");
						sql_card.append(String.valueOf(coupons_list.get(j).get("card_id")));
						sql_card.append("'");
					}
				}
				if( i==0 ) {
					sb.append("   select vip_id,card_id from d0210 a ");
					sb.append("   where a.card_id in("+sql_card.toString()+") ");
				}else {
					sb.append(" union all ");
					sb.append("   select vip_id,card_id from d0210 a ");
					sb.append("   where a.card_id in("+sql_card.toString()+") ");
				}
			}
		} else {
			StringBuilder sql_card = new StringBuilder();
			for(Map<String, Object> map:coupons_list) {
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
			sb.append("   select vip_id,card_id from d0210 a ");
			sb.append("   where a.card_id in("+sql_card.toString()+") ");
		}
		return this.getJdbcTemplate().queryForList(sb.toString());
	}

	@Override
	public List<Map<String, Object>> getDepList(List<Map<String, Object>> coupons_list) {

		int num = coupons_list.size()%1000>0?coupons_list.size()/1000+1:coupons_list.size()/1000;
		StringBuilder sb = new StringBuilder();
		if( coupons_list.size()>1000 ) {
			for( int i=0;i<num;i++ ) {
				StringBuilder sql_card = new StringBuilder();
				for( int j = i * 1000; j < (i+1) * 1000; j++ ) {
					if(j==coupons_list.size()) {
						break;
					}
					if(sql_card.toString().equals("")) {
						sql_card.append("'");
						sql_card.append(String.valueOf(coupons_list.get(j).get("useStore")));
						sql_card.append("'");
					}else {
						sql_card.append(",'");
						sql_card.append(String.valueOf(coupons_list.get(j).get("useStore")));
						sql_card.append("'");
					}
				}
				if( i==0 ) {
					sb.append("   select department_id,department_user_id from D0060 a ");
					sb.append("   where a.department_user_id in("+sql_card.toString()+") ");
				}else {
					sb.append(" union all ");
					sb.append("   select department_id,department_user_id from D0060 a ");
					sb.append("   where a.department_user_id in("+sql_card.toString()+") ");
				}
			}
		} else {
			StringBuilder sql_card = new StringBuilder();
			for(Map<String, Object> map:coupons_list) {
				if(sql_card.toString().equals("")) {
					sql_card.append("'");
					sql_card.append(String.valueOf(map.get("useStore")));
					sql_card.append("'");
				}else {
					sql_card.append(",'");
					sql_card.append(String.valueOf(map.get("useStore")));
					sql_card.append("'");
				}
			}
			sb.append("   select department_id,department_user_id from D0060 a ");
			sb.append("   where a.department_user_id in("+sql_card.toString()+") ");
		}
		return this.getJdbcTemplate().queryForList(sb.toString());
	}

}
