package com.mos.bsd.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mos.bsd.dao.IBsdPlanDao;
import com.x3.base.core.entity.ServiceTag;
import com.x3.datahelper.X3DBSaveTemplate;

@Repository("com.mos.bsd.dao.impl.BsdPlanDaoImpl")
public class BsdPlanDaoImpl extends X3DBSaveTemplate implements IBsdPlanDao {

	@Override
	public List<Map<String, Object>> getTampData(String key) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select plan_id,interface_name,time_stamp,create_date from d_bsd_interfaceplan where interface_name=? ");

		return this.getJdbcTemplate().queryForList(sb.toString(),new Object[] { key });
	}

	@Override
	public int updateTamp(String key, String tamp) {
		StringBuilder sb = new StringBuilder();
		sb.append(" merge into d_bsd_interfaceplan a ");
		sb.append(" using ( ");
		sb.append("       select ? interface_name,? time_stamp from dual ");
		sb.append(" )b ");
		sb.append(" on (a.interface_name=b.interface_name) ");
		sb.append(" when matched then ");
		sb.append("   update set a.time_stamp=b.time_stamp,a.create_date=sysdate ");
		sb.append(" when not matched then ");
		sb.append("   insert(a.plan_id,a.interface_name,a.time_stamp,a.create_date) ");
		sb.append("   values(SEQ_GLOBAL.NEXTVAL,b.interface_name,b.time_stamp,sysdate) ");
		
		return this.getJdbcTemplate().update(sb.toString(),new Object[] { key,tamp });
	}

	@Override
	public void getServiceDB(ServiceTag tag) {
		
	}
}
