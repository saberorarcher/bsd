package com.mos.bsd.domain.enums;

/**
 * @Description X2业务单据主表字段枚举
 * @author odin
 * @date 2016年11月21日 上午11:17:04
 */
public enum EmBillMain {

	/** 单据系统号 */
	p0670_id,

	/** 单据编号 */
	bill_id,

	/** 参照单号 */
	bill_czid,

	/** 系统数据类型 */
	system_type,

	/** 发货地系统编号 */
	set_department_id,
	
	/** 发货地编号 */
	set_department_user_id,

	/** 收款方系统编号 */
	payee_department_id,
	
	/** 收款方编号 */
	payee_department_user_id,

	/** 收货地系统编号 */
	get_department_id,
	
	/** 收货地编号 */
	get_department_user_id,

	/** 付款方系统 */
	payer_department_id,

	/** 单据日期 */
	bill_setdate,

	/** 金额入账 */
	bill_sum_sure,

	/** 金额入账人 */
	bill_sum_surename,

	/** 金额入账日期 */
	bill_sum_suredate,

	/** 单据结案 */
	bill_over,

	/** 结案人 */
	bill_overname,

	/** 结案日期 */
	bill_overdate,

	/** 单据结账 */
	bill_lock,

	/** 结账人 */
	bill_lockname,

	/** 结账日期 */
	bill_lockdate,

	/** 单据备注 */
	bill_remark,

	/** 状态 */
	bill_state,

	/** 建立日期 */
	bill_create_date,

	/** 建立人 */
	bill_create_name,

	/** 参考单号 */
	bill_refid,

	/** 单据类型识别码 */
	bill_idcode,

	/** 单据类型 */
	bill_selltype,

	/** 提醒日期 */
	bill_reminddate,

	/** 发货备注 */
	bill_sellremark,

	/** 单据数量 */
	bill_num,

	/** 成本金额 */
	bill_csum,

	/** 吊牌金额 */
	bill_jsum,

	/** 现价金额 */
	bill_xsum,

	/** 结算金额 */
	bill_ssum,

	/** 单据处理标记 */
	bill_editsign,

	/** 发货公司编号 */
	set_company_id,

	/** 发货地上级系统编号 */
	set_department_parent_id,

	/** 收货地上级系统编号 */
	get_department_parent_id,

	/** 单据建立机构 */
	cr_department_id,

	/** 数量入账 */
	bill_num_sure,

	/** 数量入账人 */
	bill_num_surename,

	/** 数量入账日期 */
	bill_num_suredate,

	/** 款式编号 */
	style_id,

	/** 收货结算店铺编号 */
	get_pay_department_id,

	/** 订金扣减比率 */
	bill_deposit_pec,

	/** 数量收货入账 */
	bill_num_getsure,

	/** 数量收货入账人 */
	bill_num_getsurename,

	/** 数量收货入账日期 */
	bill_num_getsuredate,

	/** 金额收货入账 */
	bill_sum_getsure,

	/** 金额收货入账人 */
	bill_sum_getsurename,

	/** 金额收货入账日期 */
	bill_sum_getsuredate,

	/** 借款申请单 */
	loan_id,

	/** 收货数量 */
	bill_getnum,

	/** 多收处理方式 */
	bill_more_deal,

	/** 少收处理方式 */
	bill_less_deal,

	/** 单据类型 */
	bill_type,

	/** 收货吊牌金额 */
	bill_getjsum,

	/** 收货现价金额 */
	bill_getxsum,

	/** 收货结算金额 */
	bill_getssum,

	/** 班次 */
	bill_shift_id,

	/** 实销金额 */
	bill_fsums,

	/** 冲单原单号 */
	original_id,

	/** 退货计算机构 */
	return_department_id,

	/** 收货差异 */
	bill_diff,

	/** 差异处理方式 */
	bill_diff_type,

	/** 差异处理人 */
	bill_diff_name,

	/** 差异处理日期 */
	bill_diff_date,

	/** 处理单据编号 */
	bill_diff_bill_id,

	/** 处理备注 */
	bill_diff_remark,

	/** 原数量 */
	bill_cnum,

	/** 货币代码 */
	bill_currency_code,

	/** 汇率 */
	bill_exchange_rate,

	/** 消费税率 */
	bill_consumertax_rate,

	/** 运费 */
	bill_freight,

	/** 消费税起征数量 */
	bill_consumertax_num,

	/** 佣金提成 */
	commission_rate,

	/** 打印次数 */
	bill_print_times,

	/** 收回通知标识 */
	bill_allot_sign,

	/** 收货备注 */
	bill_get_remark,

	/** 借调部门 */
	move_dpt,

	/** esb数据来源 */
	esb_source,

	/** 是否店长授权单据 */
	bill_issoauth,

	/** 整单发货类型 */
	bill_sell_code,

	/** 款式分类 */
	bill_style_sort,

	/** 是否需要传wms */
	bill_towms,

	/** 是否需要传sap */
	bill_tosap,

	/** 差异处理单据 */
	bill_isadjust,

	/** 差异来源单据 */
	bill_adjust_for,

	/** 供货单据类型 */
	bill_supply_type,

	/** 供货来源单据 */
	bill_supply_for,

	/** 同步标识 */
	bill_sync,

	/** 库存已更新 */
	stock_calced,

	/** sap记账日期 */
	bill_accountdate,

	/** 收货记账日期 */
	bill_get_accountdate,

	/** 收货是否需要传sap */
	bill_get_tosap,

	/** 单据自编号 */
	bill_code,

	/** 单据扩展分类1 */
	ex_class1,

	/** 单据扩展分类2 */
	ex_class2,

	/** 单据扩展分类3 */
	ex_class3,

	/** 单据扩展分类4 */
	ex_class4,

	/** 订单中心订单外部编号 */
	bill_order_id,

	// /** 发货地编号 */
	// set_department_code,
	//
	// /** 收货地编号 */
	// get_department_code,
	//
	// /** 发货地上级编号 */
	// set_department_parent_code,
	//
	// /** 收货地上级编号 */
	// get_department_parent_code,

	/** 品牌集合编号 */
	brand_groupcode,

	/** 促销单号 */
	bill_promotion_id,

	/** 退货计算类型: 1正常退货计算逻辑，2特殊退货计算逻辑 */
	back_calctype,
	/** 分区编号 */
	partition_id,
}
