package com.mos.bsd.dao.impl;

import org.springframework.stereotype.Repository;

import com.mos.bsd.dao.IBsdVipGuideUpdateDao;
import com.mos.bsd.domain.VipGuideEntity;
import com.x3.base.core.entity.ServiceTag;
import com.x3.base.core.entity.ServiceType;
import com.x3.datahelper.X3DBSaveTemplate;
@Repository("com.mos.bsd.dao.impl.BsdVipGuideUpdateDaoImpl")
public class BsdVipGuideUpdateDaoImpl extends X3DBSaveTemplate implements IBsdVipGuideUpdateDao {

	@Override
	public void getServiceDB(ServiceTag tag) {
		tag.setAuthor("hao");
		tag.setEmail("1723923383@qq.com");
		tag.setTag("bsd会员专属资料dao");
		tag.setCaption("bsd会员专属资料dao");
		tag.setServiceType(ServiceType.X2Bills);
	}

	@Override
	public int updateVipGuide(VipGuideEntity vipGuide) {
		StringBuilder sb = new StringBuilder();
		sb.append(" update d0210 set vip_extend = (select USER_NAME from wf_user where USER_CODE=? and user_property=2 ) , ");
		sb.append(" vip_extend_id=(select USER_ID from wf_user where USER_CODE=? and user_property=2 ) where vip_openid=? ");
		
		return this.getJdbcTemplate().update(sb.toString(),new Object[] { vipGuide.getUserid(),vipGuide.getUserid(),vipGuide.getOpenid() });
	}

	@Override
	public int findVipByOpenId(VipGuideEntity vipGuide) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) cnt from d0210 where vip_openid=? ");

		return this.getJdbcTemplate().queryForObject(sb.toString(), new Object[] { vipGuide.getOpenid() },Integer.class);
	}

	@Override
	public int isSameDepot(VipGuideEntity vipGuide) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(*) cnt from d0210 a ");
		sb.append("        inner join wf_user b on a.department_id=b.DEPARTMENT_ID and user_property=2 and USER_CODE=? ");
		sb.append(" where a.vip_openid=? ");

		return this.getJdbcTemplate().queryForObject(sb.toString(), new Object[] { vipGuide.getUserid(),vipGuide.getOpenid() },Integer.class);
		
	}

}
