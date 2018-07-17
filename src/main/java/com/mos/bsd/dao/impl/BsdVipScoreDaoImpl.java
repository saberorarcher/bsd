package com.mos.bsd.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.mos.bsd.dao.IBsdVipScoreDao;
import com.x3.base.core.entity.ServiceTag;
import com.x3.base.core.entity.ServiceType;
import com.x3.datahelper.X3DBSaveTemplate;

@Repository("com.mos.bsd.dao.impl.BsdVipScoreDaoImpl")
public class BsdVipScoreDaoImpl extends X3DBSaveTemplate implements IBsdVipScoreDao {

	@Override
	public void getServiceDB(ServiceTag tag) {
		tag.setAuthor("hao");
		tag.setEmail("1723923383@qq.com");
		tag.setTag("vip积分接口dao");
		tag.setCaption("vip积分接口dao");
		tag.setServiceType(ServiceType.X2Bills);
	}

	@Override
	public int updateVipScore(List<Map<String, Object>> vipScore_list) {
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into d0210 a ");
		sb.append(" using ( ");
		sb.append("       select ? card_id,? vip_retail_cnt,? vip_retail_sums,? vip_retail_score,? vip_score,? vip_lastbuy_date from dual ");
		sb.append(" )b ");
		sb.append(" on (a.card_id=b.card_id) ");
		sb.append(" when matched then ");
		sb.append("   update set a.vip_retail_cnt=b.vip_retail_cnt,a.vip_retail_sums=b.vip_retail_sums ");
		sb.append("   ,a.vip_retail_score=b.vip_retail_score,a.vip_score=b.vip_score,a.vip_lastbuy_date=b.vip_lastbuy_date ");
		
		int [] count = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				String lastBuyDate = String.valueOf(vipScore_list.get(i).get("vip_lastbuy_date"));
				ps.setString(1, String.valueOf(vipScore_list.get(i).get("card_id")));
				ps.setString(2, String.valueOf(vipScore_list.get(i).get("vip_retail_cnt")));
				ps.setString(3, String.valueOf(vipScore_list.get(i).get("vip_retail_sums")));
				ps.setString(4, String.valueOf(vipScore_list.get(i).get("vip_retail_score")));
				ps.setString(5, String.valueOf(vipScore_list.get(i).get("vip_score")));
				if( lastBuyDate==null || "null".equals(lastBuyDate) || lastBuyDate.equals("0") ) {
					ps.setDate(6, null);
				}else {
					ps.setDate(6, new Date(Long.parseLong(lastBuyDate)));
				}
				
			}
			
			@Override
			public int getBatchSize() {
				return vipScore_list.size();
			}
		});
		
		return count.length;
	}

	@Override
	public int updateVipScoreExtend(List<Map<String, Object>> vipScore_list) {
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into d0210_bsd_extend a ");
		sb.append(" using ( ");
		sb.append("       select ? card_id,? totalNum,? usedPoint,? adjustPoint,? pointAbnormalTimes,? lastNum,? lastAmount from dual ");
		sb.append(" )b ");
		sb.append(" on (a.card_id=b.card_id) ");
		sb.append(" when matched then ");
		sb.append("   update set a.totalNum=b.totalNum,a.usedPoint=b.usedPoint ");
		sb.append("   ,a.adjustPoint=b.adjustPoint,a.pointAbnormalTimes=b.pointAbnormalTimes,a.lastNum=b.lastNum,a.lastAmount=b.lastAmount ");
		sb.append(" when not matched then ");
		sb.append("   insert (a.card_id,a.totalNum,a.usedPoint,a.adjustPoint,a.pointAbnormalTimes,a.lastNum,a.lastAmount) values ");
		sb.append("    (b.card_id,b.totalNum,b.usedPoint,b.adjustPoint,b.pointAbnormalTimes,b.lastNum,b.lastAmount)");
		
		int [] count = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, String.valueOf(vipScore_list.get(i).get("card_id")));
				ps.setString(2, String.valueOf(vipScore_list.get(i).get("totalNum")));
				ps.setString(3, String.valueOf(vipScore_list.get(i).get("usedPoint")));
				ps.setString(4, String.valueOf(vipScore_list.get(i).get("adjustPoint")));
				ps.setString(5, String.valueOf(vipScore_list.get(i).get("pointAbnormalTimes")));
				ps.setString(6, String.valueOf(vipScore_list.get(i).get("lastNum")));
				ps.setString(7, String.valueOf(vipScore_list.get(i).get("lastAmount")));
				
			}
			
			@Override
			public int getBatchSize() {
				return vipScore_list.size();
			}
		});
		
		return count.length;
	}

}
