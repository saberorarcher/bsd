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

import com.mos.bsd.dao.IBsdCouponsGroupDao;
import com.mos.bsd.utils.DataHelper;
import com.x3.base.core.entity.ServiceTag;
import com.x3.base.core.entity.ServiceType;
import com.x3.datahelper.X3DBSaveTemplate;

@Repository("com.mos.bsd.dao.impl.BsdCouponsGroupDaoImpl")
public class BsdCouponsGroupDaoImpl extends X3DBSaveTemplate implements IBsdCouponsGroupDao {

	@Autowired  
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public int mergeIntoD0230(List<Map<String, Object>> couponsGroup_list) {
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into d0230 a ");
		sb.append(" using( ");
		sb.append("       select ? coupontype_id,? coupontype_sort,? coupon_valid_begdate,? coupon_valid_enddate, ");
		sb.append("       ? coupon_value,? coupontype_state,'system' coupontype_create_name from dual ");
		sb.append(" ) b ");
		sb.append(" on ( a.coupontype_id = b.coupontype_id ) ");
		sb.append(" when matched then ");
		sb.append("   update set a.coupontype_sort = b.coupontype_sort,a.coupon_valid_begdate=b.coupon_valid_begdate ");
		sb.append("   ,a.coupon_valid_enddate=b.coupon_valid_enddate,a.coupon_value=b.coupon_value, ");
		sb.append("   a.coupontype_state=b.coupontype_state,a.coupontype_create_name=b.coupontype_create_name ");
		sb.append(" when not matched then ");
		sb.append("   insert (coupontype_id,coupontype_sort,coupon_valid_begdate,coupon_valid_enddate,coupon_value,coupontype_state,coupontype_create_name) ");
		sb.append("   values(b.coupontype_id,b.coupontype_sort,b.coupon_valid_begdate,b.coupon_valid_enddate, ");
		sb.append("   b.coupon_value,b.coupontype_state,b.coupontype_create_name) ");
		int count [] = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, String.valueOf(couponsGroup_list.get(i).get("coupontype_id")));
				ps.setString(2, String.valueOf(couponsGroup_list.get(i).get("coupontype_sort")));
				ps.setDate(3, new Date(Long.parseLong(String.valueOf(couponsGroup_list.get(i).get("coupon_valid_begdate")))));
				ps.setDate(4, new Date(Long.parseLong(String.valueOf(couponsGroup_list.get(i).get("coupon_valid_enddate")))));
				ps.setString(5, String.valueOf(couponsGroup_list.get(i).get("coupon_value")));
				ps.setString(6, String.valueOf(couponsGroup_list.get(i).get("coupontype_state")));
				
			}
			
			@Override
			public int getBatchSize() {
				return couponsGroup_list.size();
			}
		});
		return count.length;	
	}

	@Override
	public int mergeIntoD0239(List<Map<String, Object>> couponsGroup_list) {
		StringBuilder sb = new StringBuilder();
		sb.append("   merge into D0239 a ");
		sb.append("   using( ");
		sb.append("         select ? coupontype_name,? coupontype_id,'zh-cn' language_id from dual ");
		sb.append("   )b ");
		sb.append("   on (a.coupontype_id=b.coupontype_id) ");
		sb.append("   when matched then ");
		sb.append("     update set a.coupontype_name=b.coupontype_name ");
		sb.append("   when not matched then ");
		sb.append("     insert (a.d0239_id,a.coupontype_name,a.coupontype_id,a.language_id) ");
		sb.append("     values(SEQ_GLOBAL.NEXTVAL,b.coupontype_name,b.coupontype_id,b.language_id) ");

		int count[] = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				String coupontype_name = String.valueOf(couponsGroup_list.get(i).get("coupontype_name"));
				String coupontype_id = String.valueOf(couponsGroup_list.get(i).get("coupontype_id"));
				ps.setString(1, coupontype_name);
				ps.setString(2, coupontype_id);
			}
			
			@Override
			public int getBatchSize() {
				return couponsGroup_list.size();
			}
		});
		
		return count.length;
	}

    /**
     * 获取编号系统编号
     * @return
     */
    public long getScheme_id() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date billDate = new Date(System.currentTimeMillis());
        long dateValue = Long.parseLong(sf.format(billDate));
        String tableCode = "D0239";
        long scheme_id = Long.parseLong(DataHelper.getBillSystemId(jdbcTemplate, dateValue, tableCode));
        return scheme_id;
    }
    
	@Override
	public void getServiceDB(ServiceTag tag) {
		tag.setAuthor("hao");
		tag.setEmail("1723923383@qq.com");
		tag.setTag("bsd券组档案dao");
		tag.setCaption("bsd券组档案dao");
		tag.setServiceType(ServiceType.X2Bills);
	}

	@Override
	public int mergeIntoD0230Extend(List<Map<String, Object>> couponsGroup_list) {
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into D0230_bsd_extend a ");
		sb.append(" using ");
		sb.append(" ( ");
		sb.append("       select ? coupontype_id,? minDiscount,? minConsumption,? maxDiscountAmount,? multipleFlag,? promotionFlag,? discountType,? smsVerify,? remark,? innerPurchaseFlag,? registerSendFlag ");
		sb.append("       ,? relativeWechatCard,? markBirthday,? useFlag,? birthdayCouponsDiscount,? couponsDiscount ");
		sb.append("         from dual ");
		sb.append(" )b ");
		sb.append(" on( a.coupontype_id=b.coupontype_id ) ");
		sb.append(" when matched then ");
		sb.append("   update set a.minDiscount=b.minDiscount,a.minConsumption=b.minConsumption,a.maxDiscountAmount=b.maxDiscountAmount,a.multipleFlag=b.multipleFlag, ");
		sb.append("   a.promotionFlag=b.promotionFlag,a.discountType=b.discountType,a.smsVerify=b.smsVerify,a.remark=b.remark,a.innerPurchaseFlag=b.innerPurchaseFlag, ");
		sb.append("   a.registerSendFlag=b.registerSendFlag,a.birthdayCouponsDiscount=b.birthdayCouponsDiscount ,a.couponsDiscount=b.couponsDiscount ");
		sb.append(" when not matched then ");
		sb.append("   insert (a.coupontype_id,a.minDiscount,a.minConsumption,a.maxDiscountAmount,a.multipleFlag,a.promotionFlag,a.discountType,a.smsVerify,a.remark,a.innerPurchaseFlag,a.registerSendFlag ");
		sb.append("   ,a.relativeWechatCard,a.markBirthday,a.useFlag,a.birthdayCouponsDiscount,a.couponsDiscount) ");
		sb.append("   values(b.coupontype_id,b.minDiscount,b.minConsumption,b.maxDiscountAmount,b.multipleFlag,b.promotionFlag,b.discountType,b.smsVerify,b.remark,b.innerPurchaseFlag,b.registerSendFlag ");
		sb.append("   ,b.relativeWechatCard,b.markBirthday,b.useFlag,b.birthdayCouponsDiscount,b.couponsDiscount) ");
		
		int count [] = this.getJdbcTemplate().batchUpdate(sb.toString(), new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, String.valueOf(couponsGroup_list.get(i).get("coupontype_id")));
				ps.setString(2, String.valueOf(couponsGroup_list.get(i).get("minDiscount")));
				ps.setString(3, String.valueOf(couponsGroup_list.get(i).get("minConsumption")));
				ps.setString(4, String.valueOf(couponsGroup_list.get(i).get("maxDiscountAmount")));
				ps.setString(5, String.valueOf(couponsGroup_list.get(i).get("multipleFlag")));
				ps.setString(6, String.valueOf(couponsGroup_list.get(i).get("promotionFlag")));
				ps.setString(7, String.valueOf(couponsGroup_list.get(i).get("discountType")));
				ps.setString(8, String.valueOf(couponsGroup_list.get(i).get("smsVerify")));
				ps.setString(9, String.valueOf(couponsGroup_list.get(i).get("remark")));
				ps.setString(10, String.valueOf(couponsGroup_list.get(i).get("innerPurchaseFlag")));
				ps.setString(11, String.valueOf(couponsGroup_list.get(i).get("registerSendFlag")));
				ps.setString(12, String.valueOf(couponsGroup_list.get(i).get("relativeWechatCard")));
				ps.setString(13, String.valueOf(couponsGroup_list.get(i).get("markBirthday")));
				ps.setString(14, String.valueOf(couponsGroup_list.get(i).get("useFlag")));
				ps.setString(15, String.valueOf(couponsGroup_list.get(i).get("birthdayCouponsDiscount")));
				ps.setString(16, String.valueOf(couponsGroup_list.get(i).get("couponsDiscount")));
			}
			
			@Override
			public int getBatchSize() {
				return couponsGroup_list.size();
			}
		});
		
		return count.length;
	}

}
