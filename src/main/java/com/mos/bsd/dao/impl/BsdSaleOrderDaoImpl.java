package com.mos.bsd.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mos.bsd.dao.IBsdSaleOrderDao;
import com.mos.bsd.utils.DataHelper;
import com.x3.base.core.entity.ServiceTag;
import com.x3.base.core.entity.ServiceType;
import com.x3.base.core.util.DateUtil;
import com.x3.datahelper.X3DBSaveTemplate;

@Repository("com.mos.bsd.dao.impl.BsdSaleOrderDaoImpl")
public class BsdSaleOrderDaoImpl extends X3DBSaveTemplate implements IBsdSaleOrderDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void getServiceDB(ServiceTag tag) {
		tag.setAuthor("hao");
		tag.setEmail("1723923383@qq.com");
		tag.setTag("bsd零售单档案dao");
		tag.setCaption("bsd零售单档案dao");
		tag.setServiceType(ServiceType.X2Bills);
	}

	@Override
	public int mergeDretail(List<Map<String, Object>> list) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into d_retail a ");
		sb.append(" using ( ");
		sb.append("       select ? bill_diff_bill_id,0 bill_czid,19 as system_type,? set_department_id,? move_dpt ");
		sb.append("       ,sysdate bill_create_date,to_date(?,'yyyy-MM-dd hh24:mi:ss')bill_setdate,? bill_state,? bill_type,? bill_num,? bill_ssum,? bill_create_name, ");
		sb.append("       ? bill_remark,? bill_return_reason,? bill_return_deal,? ex_class1,? ex_class2,? ex_class3,? ex_class4,? bill_idcode, 0 set_department_parent_id,0 get_department_parent_id  ");
		sb.append("       ,? bill_code from dual ");
		sb.append(" )b ");
		sb.append(" on (a.bill_code=b.bill_code) ");
		sb.append(" when matched then ");
		sb.append("   update set a.bill_czid=b.bill_czid,a.set_department_id=b.set_department_id,a.move_dpt=b.move_dpt, ");
		sb.append("   a.bill_create_date=b.bill_create_date,a.bill_setdate=b.bill_setdate,a.bill_state=b.bill_state ");
		sb.append("   ,a.bill_type=b.bill_type,a.bill_num=b.bill_num,a.bill_ssum=b.bill_ssum,a.bill_create_name=b.bill_create_name,a.bill_remark=b.bill_remark, ");
		sb.append("	  a.bill_return_reason=b.bill_return_reason,a.bill_return_deal=b.bill_return_deal,a.ex_class1=b.ex_class1,a.ex_class2=b.ex_class2,a.ex_class3=b.ex_class3,");
		sb.append("   a.ex_class4=b.ex_class4,a.bill_idcode=b.bill_idcode,a.bill_diff_bill_id=b.bill_diff_bill_id ");
		sb.append(" when not matched then ");
		sb.append("   insert (p0670_id,bill_id,bill_czid,system_type,set_department_id,move_dpt,bill_create_date,bill_setdate, ");
		sb.append("   bill_state,bill_type,bill_num,bill_ssum,bill_create_name,bill_remark,bill_return_reason,bill_return_deal,ex_class1,ex_class2,ex_class3,ex_class4,bill_idcode ");
		sb.append("   ,set_department_parent_id,get_department_parent_id,bill_diff_bill_id,bill_code ) ");
		sb.append("   values(BSD_P0670_ID.NEXTVAL,BSD_BILL_ID.NEXTVAL,b.bill_czid,b.system_type,b.set_department_id,b.move_dpt,b.bill_create_date,b.bill_setdate, ");
		sb.append("   b.bill_state,b.bill_type,b.bill_num,b.bill_ssum,b.bill_create_name,b.bill_remark,b.bill_return_reason,b.bill_return_deal,b.ex_class1,b.ex_class2,b.ex_class3,b.ex_class4,b.bill_idcode,b.set_department_parent_id,b.get_department_parent_id,b.bill_diff_bill_id,b.bill_code ) ");
		
		int [] count = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
//				ps.setLong(1, getScheme_id());
//				ps.setString(2, getBill_id());
				ps.setString(1, String.valueOf(list.get(i).get("bill_diff_bill_id")));
				ps.setString(2, String.valueOf(list.get(i).get("set_department_id")));
				ps.setString(3, String.valueOf(list.get(i).get("move_dpt")));
//				ps.setDate(4, new Date(Long.parseLong(String.valueOf(list.get(i).get("bill_create_date")))));
				ps.setString(4, sdf.format(new Date(Long.parseLong(String.valueOf(list.get(i).get("bill_setdate"))))));
				ps.setString(5, String.valueOf(list.get(i).get("bill_state")));
				ps.setString(6, String.valueOf(list.get(i).get("bill_type")));
				ps.setString(7, String.valueOf(list.get(i).get("bill_num")));
				ps.setString(8, String.valueOf(list.get(i).get("bill_ssum")));
				ps.setString(9, String.valueOf(list.get(i).get("bill_create_name")));
				ps.setString(10, String.valueOf(list.get(i).get("bill_remark")));
				ps.setString(11, String.valueOf(list.get(i).get("bill_return_reason")));
				ps.setString(12, String.valueOf(list.get(i).get("bill_return_deal")));
				ps.setString(13, String.valueOf(list.get(i).get("ex_class1")));
				ps.setString(14, String.valueOf(list.get(i).get("ex_class2")));
				ps.setString(15, String.valueOf(list.get(i).get("ex_class3")));
				ps.setString(16, String.valueOf(list.get(i).get("ex_class4")));
				ps.setString(17, String.valueOf(list.get(i).get("bill_idcode")));
				ps.setString(18, String.valueOf(list.get(i).get("bill_id")).trim());
				
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		
		return count.length;
	}

	@Override
	public int mergeDretailsub(List<Map<String, Object>> list) {
		System.out.println(list);
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into d_retailsub a ");
		sb.append(" using ( ");
		sb.append("       select ? p0670_id,? bill_order,? clothing_id,? billsub_nums,? billsub_xprice,? billsub_sprice, ");
		sb.append("       ? billsub_ssum,? subcompany_id,? billsub_remark,'2131.01.0001' subject_id,? style_year_code,? style_month_code, '0' sell_class_id ");
		sb.append("       ,? location_id from dual ");
		sb.append(" )b ");
		sb.append(" on (a.p0670_id=b.p0670_id and a.location_id=b.location_id) ");
		sb.append(" when matched then ");
		sb.append("   update set a.billsub_nums=b.billsub_nums,a.billsub_xprice=b.billsub_xprice,a.billsub_sprice=b.billsub_sprice,a.clothing_id=b.clothing_id, ");
		sb.append("   a.billsub_ssum=b.billsub_ssum,a.subcompany_id=b.subcompany_id,a.billsub_remark=b.billsub_remark,a.subject_id=b.subject_id,a.style_year_code=b.style_year_code,a.style_month_code=b.style_month_code ");
		sb.append(" when not matched then ");
		sb.append("   insert (p0671_id,p0670_id,bill_order,clothing_id,billsub_nums,billsub_xprice,billsub_sprice,billsub_ssum,subcompany_id,billsub_remark,subject_id,style_year_code,style_month_code,sell_class_id,location_id) ");
		sb.append("   values (SEQ_GLOBAL.NEXTVAL,b.p0670_id,b.bill_order,b.clothing_id,b.billsub_nums,b.billsub_xprice,b.billsub_sprice,b.billsub_ssum,b.subcompany_id,b.billsub_remark,b.subject_id,b.style_year_code,b.style_month_code,b.sell_class_id,b.location_id) ");
		
		int count [] = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				ps.setString(1, String.valueOf(list.get(i).get("p0670_id")));
				ps.setInt(2, i);
				ps.setString(3, String.valueOf(list.get(i).get("clothing_id")));
				ps.setString(4, String.valueOf(list.get(i).get("billsub_nums")));
				ps.setString(5, String.valueOf(list.get(i).get("billsub_xprice")));
				ps.setString(6, String.valueOf(list.get(i).get("billsub_sprice")));
				ps.setString(7, String.valueOf(list.get(i).get("billsub_ssum")));
				ps.setString(8, String.valueOf(list.get(i).get("subcompany_id")));
				ps.setString(9, String.valueOf(list.get(i).get("billsub_remark")));
				ps.setString(10, String.valueOf(list.get(i).get("style_year_code")));
				ps.setString(11, String.valueOf(list.get(i).get("style_month_code")));
				ps.setString(12, String.valueOf(list.get(i).get("rowId")));
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		return count.length;
	}

	@Override
	public int mergeDretailexpandinfo(List<Map<String, Object>> list) {
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into d_retailexpandinfo a ");
		sb.append(" using( ");
		sb.append("       select ? p0670_id,? bill_coupon_code,? bill_vipcard_id,? bill_vip_exchange,? bill_vip_birthday,? bill_bank_sums,? bill_weather from dual ");
		sb.append(" )b ");
		sb.append(" on (a.p0670_id=b.p0670_id) ");
		sb.append(" when matched then ");
		sb.append("   update set a.bill_coupon_code=b.bill_coupon_code,a.bill_vipcard_id=b.bill_vipcard_id,a.bill_vip_exchange=b.bill_vip_exchange,a.bill_vip_birthday=b.bill_vip_birthday,a.bill_bank_sums=b.bill_bank_sums,a.bill_weather=b.bill_weather ");
		sb.append(" when not matched then ");
		sb.append("   insert (a.p0670_id,a.bill_coupon_code,a.bill_vipcard_id,a.bill_vip_exchange,a.bill_vip_birthday,a.bill_bank_sums,a.bill_weather) ");
		sb.append("   values(b.p0670_id,b.bill_coupon_code,b.bill_vipcard_id,b.bill_vip_exchange,b.bill_vip_birthday,b.bill_bank_sums,b.bill_weather) ");

		int [] count = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, String.valueOf(list.get(i).get("p0670_id")));
				ps.setString(2, String.valueOf(list.get(i).get("bill_coupon_code")));
				ps.setString(3, String.valueOf(list.get(i).get("bill_vipcard_id")));
				ps.setString(4, String.valueOf(list.get(i).get("bill_vip_exchange")));
				ps.setString(5, String.valueOf(list.get(i).get("bill_vip_birthday")));
				ps.setString(6, String.valueOf(list.get(i).get("bill_bank_sums")));
				ps.setString(7, String.valueOf(list.get(i).get("bill_weather")));
				
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		return count.length;
	}

	@Override
	public int mergeDretailpaytype(List<Map<String, Object>> list) {
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into d_retailpaytype a ");
		sb.append(" using ( ");
		sb.append("       select ? p0670_id,? pay_type,? sums from dual ");
		sb.append(" )b ");
		sb.append(" on (a.p0670_id=b.p0670_id) ");
		sb.append(" when matched then ");
		sb.append("   update set a.pay_type=b.pay_type,a.sums=b.sums ");
		sb.append(" when not matched then ");
		sb.append("   insert(a.p0670_id,a.pay_type,a.sums) ");
		sb.append("   values(b.p0670_id,b.pay_type,b.sums) ");
		
		int count [] = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, String.valueOf(list.get(i).get("p0670_id")));
				ps.setString(2, String.valueOf(list.get(i).get("pay_type")));
				ps.setString(3, String.valueOf(list.get(i).get("sums")));
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		return count.length;
	}

	@Override
	public int mergeDretailclerk(List<Map<String, Object>> list) {
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into mt_d_retailclerk a ");
		sb.append(" using( ");
		sb.append("       select ? p0670_id,? clerk_id,? deliveryClerkId from dual ");
		sb.append(" )b ");
		sb.append(" on (a.p0670_id=b.p0670_id and a.clerk_id=b.clerk_id) ");
		sb.append(" when not matched then ");
		sb.append("  insert(p0670_id,clerk_id,deliveryClerkId) ");
		sb.append("  values(b.p0670_id,b.clerk_id,b.deliveryClerkId) ");
		
		int count [] = this.getJdbcTemplate().batchUpdate(sb.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, String.valueOf(list.get(i).get("p0670_id")));
				ps.setString(2, String.valueOf(list.get(i).get("clerk_id")));
				ps.setString(3, String.valueOf(list.get(i).get("deliveryClerkId")));
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
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
        String tableCode = "D_RETAIL";
        long scheme_id = Long.parseLong(DataHelper.getBillSystemId(jdbcTemplate, dateValue, tableCode));
        return scheme_id;
    }
    
    /**
     * 获取编号系统编号
     * @return
     */
    public long getScheme_id1() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date billDate = new Date(System.currentTimeMillis());
        long dateValue = Long.parseLong(sf.format(billDate));
        String tableCode = "D_RETAILSUB";
        long scheme_id = Long.parseLong(DataHelper.getBillSystemId(jdbcTemplate, dateValue, tableCode));
        return scheme_id;
    }
    
    
    public String getBill_id() {
    	
    	int r = (int) (Math.random()*9000+1000);
    	
		long dateValue = Long.parseLong((new SimpleDateFormat("yyyyMMdd")).format(DateUtil.getSystemDate()));
		String scheme_code = DataHelper.getBillUserId(jdbcTemplate, String.valueOf(r), dateValue, "P0670");
		return scheme_code;
	}

	@Override
	public List<Map<String, Object>> getStoreList() {
		StringBuilder sb = new StringBuilder();
//		sb.append(" select department_id,department_user_id from ( ");
		sb.append("   select department_id,department_user_id from d0060 where system_type=11 and department_type=1 ");
//		sb.append("   minus ");
//		sb.append("   select d.department_id,d.department_user_id from d0060 a ");
//		sb.append("          inner join d0060 b on a.department_id=b.department_parent_id ");
//		sb.append("          inner join d0060 c on b.department_id=c.department_parent_id ");
//		sb.append("          inner join d0060 d on c.department_id=d.department_parent_id ");
//		sb.append("   where a.department_user_id in('A101','A008','A102','A901') ");
//		sb.append(" ) a ");
		
//		StringBuilder sb = new StringBuilder();
//		sb.append(" select a.department_user_id,a.department_id from d0060 a ");
//		sb.append(" where a.department_user_id='ZD83' ");

//		StringBuilder sb = new StringBuilder();
//		sb.append(" select b.department_user_id,b.department_id from d0060 a ");
//		sb.append("        inner join d0060 b on a.department_id=b.department_parent_id ");
//		sb.append(" where a.department_parent_id='109517' ");

//		StringBuilder sb = new StringBuilder();
//		sb.append("  select department_id,department_user_id from ( ");
//		sb.append("    select department_id,department_user_id from d0060 where system_type=11 and department_type=1 and department_state=1 ");
//		sb.append("    minus ");
//		sb.append("    select d.department_id,d.department_user_id from d0060 a ");
//		sb.append("           inner join d0060 b on a.department_id=b.department_parent_id ");
//		sb.append("           inner join d0060 c on b.department_id=c.department_parent_id ");
//		sb.append("           inner join d0060 d on c.department_id=d.department_parent_id ");
//		sb.append("    where a.department_user_id in('A101','A008','A102','A901') ");
//		sb.append("    minus ");
//		sb.append("    select department_id,a.department_user_id from( ");
//		sb.append("        select distinct( substr(request_data,13,4)) department_user_id from Bsd_interface_initialdata where interface_name='http://58.211.79.7:18080/bsdyun-open-api/center/order/getSaleOrderCountByPara' and id > 399388 ");
//		sb.append(" )a ");
//		sb.append(" inner join d0060 b on a.department_user_id=b.department_user_id and b.system_type=11 ");
//		sb.append("  ) a ");
		
		return this.getJdbcTemplate().queryForList(sb.toString());
	}

	@Override
	public List<Map<String, Object>> getP0670_list(List<Map<String, Object>> retailList) {
		// 判断storeList的长度
		if (retailList == null || retailList.size() == 0) {
			return new ArrayList<Map<String, Object>>();
		}
		StringBuilder sb = new StringBuilder();
		int num = retailList.size() % 1000 > 0 ? retailList.size() / 1000 + 1 : retailList.size() / 1000;
		if (retailList.size() > 1000) {
			for (int i = 0; i < num; i++) {
				StringBuilder sql_card = new StringBuilder();
				for (int j = i * 1000; j < (i + 1) * 1000; j++) {
					if (j == retailList.size()) {
						break;
					}
					if (sql_card.toString().equals("")) {
						sql_card.append("'");
						sql_card.append(String.valueOf(retailList.get(j).get("bill_id")));
						sql_card.append("'");
					} else {
						sql_card.append(",'");
						sql_card.append(String.valueOf(retailList.get(j).get("bill_id")));
						sql_card.append("'");
					}
				}
				if (i == 0) {
					sb.append(" select p0670_id,bill_code bill_id from d_retail where bill_code in("
							+ sql_card.toString() + ") ");
				} else {
					sb.append(" union all ");
					sb.append(" select p0670_id,bill_code bill_id from d_retail where bill_code in("
							+ sql_card.toString() + ") ");
				}
			}
		} else {
			StringBuilder sql_card = new StringBuilder();
			for (Map<String, Object> map : retailList) {
				if (sql_card.toString().equals("")) {
					sql_card.append("'");
					sql_card.append(String.valueOf(map.get("bill_id")));
					sql_card.append("'");
				} else {
					sql_card.append(",'");
					sql_card.append(String.valueOf(map.get("bill_id")));
					sql_card.append("'");
				}
			}
			sb.append(" select p0670_id,bill_code bill_id from d_retail where bill_code in("
					+ sql_card.toString() + ") ");
		}
		return this.getJdbcTemplate().queryForList(sb.toString());
	}

	@Override
	public List<Map<String, Object>> getYear_list(List<Map<String, Object>> retailsubList) {
		// 判断长度
		if (retailsubList == null || retailsubList.size() == 0) {
			return new ArrayList<Map<String, Object>>();
		}
		StringBuilder sb = new StringBuilder();
		int num = retailsubList.size() % 1000 > 0 ? retailsubList.size() / 1000 + 1 : retailsubList.size() / 1000;
		if (retailsubList.size() > 1000) {
			for (int i = 0; i < num; i++) {
				StringBuilder sql_card = new StringBuilder();
				for (int j = i * 1000; j < (i + 1) * 1000; j++) {
					if (j == retailsubList.size()) {
						break;
					}
					if (sql_card.toString().equals("")) {
						sql_card.append("'");
						sql_card.append(String.valueOf(retailsubList.get(j).get("clothing_id")));
						sql_card.append("'");
					} else {
						sql_card.append(",'");
						sql_card.append(String.valueOf(retailsubList.get(j).get("clothing_id")));
						sql_card.append("'");
					}
				}
				if (i == 0) {
					sb.append(" select style_year,style_month_code,clothing_id from d0050 where clothing_id in("
							+ sql_card.toString() + ") ");
				} else {
					sb.append(" union all ");
					sb.append(" select style_year,style_month_code,clothing_id from d0050 where clothing_id in("
							+ sql_card.toString() + ") ");
				}
			}
		} else {
			StringBuilder sql_card = new StringBuilder();
			for (Map<String, Object> map : retailsubList) {
				if (sql_card.toString().equals("")) {
					sql_card.append("'");
					sql_card.append(String.valueOf(map.get("clothing_id")));
					sql_card.append("'");
				} else {
					sql_card.append(",'");
					sql_card.append(String.valueOf(map.get("clothing_id")));
					sql_card.append("'");
				}
			}
			sb.append(" select style_year,style_month_code,clothing_id from d0050 where clothing_id in("
					+ sql_card.toString() + ") ");
		}
		return this.getJdbcTemplate().queryForList(sb.toString());
	}

	@Override
	public List<Map<String, Object>> getTemData() {
//		StringBuilder sb = new StringBuilder();
//		sb.append(" select ");
//		sb.append("        id,dataId,orderNo,corpNo,customerNo,storeNo,deliveryStoreNo,billDate,saleDate, ");
//		sb.append("        saleTime,relativeOrderNo,orderStatus,billSource,orderType,sellType,o2oType,clerkId,deliveryClerkId,posCode, ");
//		sb.append("        discountCoupon,memberId,exchangePoint,exchangeAmount,isBirthdayConsume,isBirthdayDiscount,saleNum,saleAmount, ");
//		sb.append("        carryDown,createUser,remark,saleOrderPaymentDTOs,saleOrderDtlDTOs,saleOrderExtDTO,validFlag,couponsNo,createDate,status,department_id ");
//		sb.append(" from TEMP_MT_ORDER_BSD where status=0 ");
		StringBuilder sb = new StringBuilder();
		sb.append("  with tem as  ( ");
		sb.append("      select  id ");
		sb.append("      from TEMP_MT_ORDER_BSD a where status=0 ");
		sb.append("      minus ");
		sb.append("        select ");
		sb.append("             id ");
		sb.append("             from TEMP_MT_ORDER_BSD a ");
		sb.append("           inner join d_retail b on a.orderNo=b.bill_code ");
		sb.append("      where status=0 ");
		sb.append(" ) ");
		sb.append(" select ");
		sb.append("         a.id,dataId,orderNo,corpNo,customerNo,storeNo,deliveryStoreNo,billDate,saleDate, ");
		sb.append("         saleTime,relativeOrderNo,orderStatus,billSource,orderType,sellType,o2oType,clerkId,deliveryClerkId,posCode, ");
		sb.append("         discountCoupon,memberId,exchangePoint,exchangeAmount,isBirthdayConsume,isBirthdayDiscount,saleNum,saleAmount, ");
		sb.append("         carryDown,createUser,remark,saleOrderPaymentDTOs,saleOrderDtlDTOs,saleOrderExtDTO,validFlag,couponsNo,createDate,status,department_id ");
		sb.append(" from TEMP_MT_ORDER_BSD a ");
		sb.append("         inner join tem b on a.id=b.id ");
		sb.append("         where a.status=0 ");

		return this.getJdbcTemplate().queryForList(sb.toString());
	}

	@Override
	public int updateErrorData(String id,int i) {
		StringBuilder sb = new StringBuilder();
		sb.append(" update TEMP_MT_ORDER_BSD set status=?,updateDate=sysdate where id=? ");

		return this.getJdbcTemplate().update(sb.toString(),new Object[] { i,id });
	}

}
