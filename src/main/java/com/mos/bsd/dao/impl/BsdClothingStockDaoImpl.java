package com.mos.bsd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.mos.bsd.dao.IBsdClothingStockDao;
import com.x3.base.core.entity.ServiceTag;
import com.x3.base.core.entity.ServiceType;
import com.x3.datahelper.X3DBSaveTemplate;
@Repository("com.mos.bsd.dao.impl.BsdClothingStockDaoImpl")
public class BsdClothingStockDaoImpl extends X3DBSaveTemplate implements IBsdClothingStockDao {

	@Override
	public void getServiceDB(ServiceTag tag) {
		tag.setAuthor("hao");
		tag.setEmail("1723923383@qq.com");
		tag.setTag("bsd商品库存接口dao");
		tag.setCaption("bsd商品库存接口dao");
		tag.setServiceType(ServiceType.X2Bills);
	}

	@Override
	public int saveDataToX2(List<Map<String, Object>> stockList) {
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into X3STOCK.x3_stockdepot a ");
		sb.append(" using ( ");
		sb.append("       select ? depot_id,? clothing_id,? ava_nums,? now_nums,? last_date,? quota_nums,? setway_nums,? getway_nums from dual ");
		sb.append(" )b ");
		sb.append(" on ( a.depot_id=b.depot_id and a.clothing_id=b.clothing_id ) ");
		sb.append(" when matched then ");
		sb.append("   update set a.ava_nums=b.ava_nums,a.now_nums=b.now_nums,a.last_date=b.last_date,a.quota_nums=b.quota_nums,a.setway_nums=b.setway_nums ");
		sb.append(" when not matched then ");
		sb.append("   insert(depot_id,clothing_id,ava_nums,now_nums,last_date,quota_nums,setway_nums,getway_nums) ");
		sb.append("   values(b.depot_id,b.clothing_id,b.ava_nums,b.now_nums,b.last_date,b.quota_nums,b.setway_nums,b.getway_nums) ");

		int [] count = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, String.valueOf(stockList.get(i).get("depot_id")));
				ps.setString(2, String.valueOf(stockList.get(i).get("clothing_id")));
				ps.setString(3, String.valueOf(stockList.get(i).get("ava_nums")).equals("")?"0":String.valueOf(stockList.get(i).get("ava_nums")));
				ps.setString(4, String.valueOf(stockList.get(i).get("now_nums")).equals("")?"0":String.valueOf(stockList.get(i).get("now_nums")));
				ps.setLong(5, System.currentTimeMillis());
				ps.setString(6, String.valueOf(stockList.get(i).get("quota_nums")).equals("")?"0":String.valueOf(stockList.get(i).get("quota_nums")));
				ps.setString(7, String.valueOf(stockList.get(i).get("setway_nums")).equals("")?"0":String.valueOf(stockList.get(i).get("setway_nums")));
				ps.setString(8, String.valueOf(stockList.get(i).get("getway_nums")).equals("")?"0":String.valueOf(stockList.get(i).get("getway_nums")));
			}
			
			@Override
			public int getBatchSize() {
				return stockList.size();
			}
		});
		
		return count.length;
	}

}
