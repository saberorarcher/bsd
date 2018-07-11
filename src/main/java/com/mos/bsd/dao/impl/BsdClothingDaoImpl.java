package com.mos.bsd.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mos.bsd.dao.IBsdClothingDao;
import com.mos.bsd.utils.DataHelper;
import com.x3.base.core.entity.ServiceTag;
import com.x3.datahelper.X3DBSaveTemplate;

@Repository("com.mos.bsd.dao.impl.BsdClothingDaoImpl")
public class BsdClothingDaoImpl extends X3DBSaveTemplate implements IBsdClothingDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int insertToX2(List<Map<String, Object>> list) {
		StringBuilder sb = new StringBuilder();
		sb.append(" insert into Bsd_clothing_temp(id,productNo,productName,brandNo,brandName,styleNo,styleName,sexNo,sexName, ");
		sb.append(" productGroupNo,productGroupName,productTypeNo,productTypeName,sizeGroupNo,sizeGroupName,measureNo,measureName, ");
		sb.append(" labeltypeNo,labeltypeName,serialNo,tagPrice,retailPrice,ageRangeNo,ageRangeName,productSeriesNo,productSeriesName, ");
		sb.append(" productMiddleNo,productMiddleName,isGifts,validFlag,ts,colorNo,erpColorNo,colorName,seriesNo,color_serialNo,color_validFlag, ");
		sb.append(" sizeNo,erpSizeNo,sizeName,size_serialNo,size_validFlag,sortNo,color_ts,size_ts,status) ");
		sb.append(" values(bsd_clothing_ID.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0) ");
		
		int [] count = this.getJdbcTemplate().batchUpdate(sb.toString(), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setObject(1, list.get(i).get("productNo"));
				ps.setObject(2, list.get(i).get("productName"));
				ps.setObject(3, list.get(i).get("brandNo"));
				ps.setObject(4, list.get(i).get("brandName"));
				ps.setObject(5, list.get(i).get("styleNo"));
				ps.setObject(6, list.get(i).get("styleName"));
				ps.setObject(7, list.get(i).get("sexNo"));
				ps.setObject(8, list.get(i).get("sexName"));
				ps.setObject(9, list.get(i).get("productGroupNo"));
				ps.setObject(10, list.get(i).get("productGroupName"));
				ps.setObject(11, list.get(i).get("productTypeNo"));
				ps.setObject(12, list.get(i).get("productTypeName"));
				ps.setObject(13, list.get(i).get("sizeGroupNo"));
				ps.setObject(14, list.get(i).get("sizeGroupName"));
				ps.setObject(15, list.get(i).get("measureNo"));
				ps.setObject(16, list.get(i).get("measureName"));
				ps.setObject(17, list.get(i).get("labeltypeNo"));
				ps.setObject(18, list.get(i).get("labeltypeName"));
				ps.setObject(19, list.get(i).get("serialNo"));
				ps.setObject(20, list.get(i).get("tagPrice"));
				ps.setObject(21, list.get(i).get("retailPrice"));
				ps.setObject(22, list.get(i).get("ageRangeNo"));
				ps.setObject(23, list.get(i).get("ageRangeName"));
				ps.setObject(24, list.get(i).get("productSeriesNo"));
				ps.setObject(25, list.get(i).get("productSeriesName"));
				ps.setObject(26, list.get(i).get("productMiddleNo"));
				ps.setObject(27, list.get(i).get("productMiddleName"));
				ps.setObject(28, list.get(i).get("isGifts"));
				ps.setObject(29, list.get(i).get("validFlag"));
				ps.setObject(30, list.get(i).get("ts"));
				ps.setObject(31, list.get(i).get("colorNo"));
				ps.setObject(32, list.get(i).get("erpColorNo"));
				ps.setObject(33, list.get(i).get("colorName"));
				ps.setObject(34, list.get(i).get("seriesNo"));
				ps.setObject(35, list.get(i).get("color_serialNo"));
				ps.setObject(36, list.get(i).get("color_validFlag"));
				ps.setObject(37, list.get(i).get("sizeNo"));
				ps.setObject(38, list.get(i).get("erpSizeNo"));
				ps.setObject(39, list.get(i).get("sizeName"));
				ps.setObject(40, list.get(i).get("size_serialNo"));
				ps.setObject(41, list.get(i).get("size_validFlag"));
				ps.setObject(42, list.get(i).get("sortNo"));
				ps.setObject(43, list.get(i).get("color_ts"));
				ps.setObject(44, list.get(i).get("size_ts"));
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		return count.length;
	}
	
    public long getScheme_id() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date billDate = new Date(System.currentTimeMillis());
        long dateValue = Long.parseLong(sf.format(billDate));
        String tableCode = "BSD_CLOTHING_TEMP";
        long scheme_id = Long.parseLong(DataHelper.getBillSystemId(jdbcTemplate, dateValue, tableCode));
        return scheme_id;
    }
	
	@Override
	public void getServiceDB(ServiceTag tag) {
		
	}

}
