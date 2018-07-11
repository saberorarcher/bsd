package com.mos.bsd.domain.enums;

/**
 * @Description X2业务单据子表字段枚举
 * @author odin
 * @date 2016年11月21日 下午1:40:36
 */
public enum EmBillSub {

	/** 序号 */
	p0671_id,
	/** 业务主表系统号 */
	p0670_id,
	/** 序号 */
	bill_order,
	/** 货号编号 */
	clothing_id,
	/** 数量 */
	billsub_nums,
	/** 成本价 */
	billsub_cost,
	/** 原吊牌价 */
	billsub_jprice,
	/** 吊牌价 */
	billsub_xprice,
	/** 结算价 */
	billsub_sprice,
	/** 折扣 */
	billsub_rate,
	/** 原数量 */
	billsub_cnums,
	/** 备注 */
	billsub_remark,
	/** 结案 */
	billsub_over,
	/** 结案人 */
	billsub_overname,
	/** 结案日期 */
	billsub_overdate,
	/** 建立日期 */
	billsub_create_date,
	/** 换货率 */
	billsub_returned_pec,
	/** 换货日期 */
	billsub_returned_date,
	/** 发货类型 */
	sell_class_id,
	/** 成本金额 */
	billsub_csum,
	/** 结算金额 */
	billsub_ssum,
	/** 加工单价 */
	billsub_cmtcost,
	/** 最后修改日期 */
	billsub_update_date,
	/** 款式年份 */
	style_year_code,
	/** 款式季度 */
	style_month_code,
	/** 现金科目编号 */
	subject_id,
	/** 收货数量 */
	billsub_getnums,
	/** 收货折扣 */
	billsub_getrate,
	/** 收货结算价 */
	billsub_getsprice,
	/** 收货结算金额 */
	billsub_getssum,
	/** 库存已计算 */
	billsub_stock_calced,
	/** 修改过的 */
	billsub_modified,
	/** 收货备注 */
	billsub_get_remark,
	/** 赠送 */
	billsub_free,
	/** 退货 */
	billsub_return,
	/** 收货吊牌价 */
	billsub_getxprice,
	/** vip本单积分 */
	billsub_vip_score,
	/** 本单上季积分 */
	billsub_vip_score_past,
	/** 实销金额 */
	billsub_fsums,
	/** 原单记录标识 */
	billsub_original,
	/** 参加促销标识 */
	billsub_promotion,
	/** 促销活动编号 */
	p0742_id,
	/** 商场活动编号 */
	p0320_id,
	/** 商场扣点 */
	action_rate,
	/** 店铺区位编号 */
	location_id,
	/** 回货期 */
	billsub_setdate,
	/** 折实金额 */
	billsub_nett,
	/** 不产生退货额金额 */
	billsub_noreturn_sum,
	/** 运费分摊 */
	billsub_freight,
	/** 退货状态 */
	billsub_returnstate,
	/** 退货取件联系人 */
	billsub_cname,
	/** 退货取件联系电话 */
	billsub_ctel,
	/** 退货取件地址 */
	billsub_address,
	/** 退货留言 */
	billsub_returnmessage,
	/** 物流公司 */
	billsub_logistics,
	/** 物流单号 */
	billsub_logistics_no,
	/** 营业员编号 */
	clerk_id,
	/** 收货发货类型，用于跨区调拨 */
	get_sell_code,
	/** 换货折扣 */
	change_discount,
	/** 换货率 */
	change_rate,
	/** 最迟换货日期 */
	change_date
}
