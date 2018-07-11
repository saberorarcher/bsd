package com.mos.bsd.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.mos.bsd.domain.enums.EmBillMain;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Description X2业务单据主表实体
 * @author odin
 * @date 2016年11月21日 下午1:33:13
 */
public class BillMain implements Serializable {

	// #region private fields

	/** */
	private static final long serialVersionUID = 1L;

	/** 获取已赋过值的字段 */
	@JSONField(serialize = false)
	private List<EmBillMain> changes = new ArrayList<EmBillMain>();
	/** 单据系统号 */
	@ApiModelProperty(notes = "单据系统号", required = true, example = "18042500009994")
	private long p0670_id;
	/** 单据编号 */
	@ApiModelProperty(notes = "单据编号", required = true, example = "20180425-ZAMN-9994")
	private String bill_id;
	/** 外部单号 */
	private String bill_refid;
	/** 参照单号 */
	@ApiModelProperty(notes = "参照单号", required = true, example = "0")
	private long bill_czid;
	/** 系统数据类型 */
	private int system_type;
	/** 发货地系统编号 */
	private long set_department_id;
	/** 发货地编号 **/
	private String set_department_user_id;
	/** 收款方系统编号 */
	private long payee_department_id;
	/** 收款方编号 */
	private String payee_department_user_id;
	/** 收货地系统编号 */
	private long get_department_id;
	/** 收货地编号 **/
	private String get_department_user_id;
	/** 付款方系统编号 */
	private long payer_department_id;
	/** 付款方编号 */
	private String payer_department_user_id;
	/** 单据日期 */
	private Timestamp bill_setdate;
	/** 金额入账 */
	private int bill_sum_sure;
	/** 金额入账人 */
	private String bill_sum_surename;
	/** 金额入账日期 */
	private Timestamp bill_sum_suredate;
	/** 单据结案 */
	private int bill_over;
	/** 结案人 */
	private String bill_overname;
	/** 结案日期 */
	private Timestamp bill_overdate;
	/** 单据结账 */
	private int bill_lock;
	/** 结账人 */
	private String bill_lockname;
	/** 结账日期 */
	private Timestamp bill_lockdate;
	/** 单据备注 */
	private String bill_remark;
	/** 状态 */
	private int bill_state;
	/** 建立日期 */
	private Timestamp bill_create_date;
	/** 建立人 */
	private String bill_create_name;
	/** 单据类型识别码 */
	private String bill_idcode;
	/** 单据类型 */
	private String bill_selltype;
	/** 提醒日期 */
	private Timestamp bill_reminddate;
	/** 发货备注 */
	private String bill_sellremark;
	/** 单据数量 */
	private double bill_num;
	/** 成本金额 */
	private double bill_csum;
	/** 吊牌金额 */
	private double bill_jsum;
	/** 现价金额 */
	private double bill_xsum;
	/** 结算金额 */
	private double bill_ssum;
	/** 单据处理标记 */
	private int bill_editsign;
	/** 发货公司编号 */
	private long set_company_id;
	/** 发货地上级系统编号 */
	private long set_department_parent_id;
	/** 收货地上级系统编号 */
	private long get_department_parent_id;
	/** 单据建立机构 */
	private long cr_department_id;
	/** 数量入账 */
	private int bill_num_sure;
	/** 数量入账人 */
	private String bill_num_surename;
	/** 数量入账日期 */
	private Timestamp bill_num_suredate;
	/** 款式编号 */
	private String style_id;
	/** 收货结算店铺编号 */
	private long get_pay_department_id;
	/** 订金扣减比率 */
	private double bill_deposit_pec;
	/** 数量收货入账 */
	private int bill_num_getsure;
	/** 数量收货入账人 */
	private String bill_num_getsurename;
	/** 数量收货入账日期 */
	private Timestamp bill_num_getsuredate;
	/** 金额收货入账 */
	private int bill_sum_getsure;
	/** 金额收货入账人 */
	private String bill_sum_getsurename;
	/** 金额收货入账日期 */
	private Timestamp bill_sum_getsuredate;
	/** 借款申请单 */
	private long loan_id;
	/** 收货数量 */
	private double bill_getnum;
	/** 多收处理方式 */
	private int bill_more_deal;
	/** 少收处理方式 */
	private int bill_less_deal;
	/** 单据类型 */
	private int bill_type;
	/** 收货吊牌金额 */
	private double bill_getjsum;
	/** 收货现价金额 */
	private double bill_getxsum;
	/** 收货结算金额 */
	private double bill_getssum;
	/** 班次 */
	private long bill_shift_id;
	/** 实销金额 */
	private double bill_fsums;
	/** 冲单原单号 */
	private long original_id;
	/** 退货计算机构 */
	private long return_department_id;
	/** 收货差异 */
	private int bill_diff;
	/** 差异处理方式 */
	private int bill_diff_type;
	/** 差异处理人 */
	private String bill_diff_name;
	/** 差异处理日期 */
	private Timestamp bill_diff_date;
	/** 处理单据编号 */
	private String bill_diff_bill_id;
	/** 处理备注 */
	private String bill_diff_remark;
	/** 原数量 */
	private double bill_cnum;
	/** 货币代码 */
	private String bill_currency_code;
	/** 汇率 */
	private double bill_exchange_rate;
	/** 消费税率 */
	private double bill_consumertax_rate;
	/** 运费 */
	private double bill_freight;
	/** 消费税起征数量 */
	private double bill_consumertax_num;
	/** 佣金提成 */
	private double commission_rate;
	/** 打印次数 */
	private int bill_print_times;
	/** 收回通知标识 */
	private int bill_allot_sign;
	/** 收货备注 */
	private String bill_get_remark;
	/** 借调部门 */
	private String move_dpt;
	/** esb数据来源 */
	private String esb_source;
	/** 是否店长授权单据 */
	private int bill_issoauth;
	/** 整单发货类型 */
	private String bill_sell_code;
	/** 款式分类 */
	private String bill_style_sort;
	/** 是否需要传wms */
	private int bill_towms;
	/** 是否需要传sap */
	private int bill_tosap;
	/** 差异处理单据 */
	private int bill_isadjust;
	/** 差异来源单据 */
	private long bill_adjust_for;
	/** 供货单据类型 */
	private int bill_supply_type;
	/** 供货来源单据 */
	private long bill_supply_for;
	/** 同步标识 */
	private int bill_sync;
	/** 库存已更新 */
	private int stock_calced;
	/** sap记账日期 */
	private Timestamp bill_accountdate;
	/** 收货记账日期 */
	private Timestamp bill_get_accountdate;
	/** 收货是否需要传sap */
	private int bill_get_tosap;
	/** 单据自编号 */
	private String bill_code;
	/** 单据扩展分类1 */
	private String ex_class1;
	/** 单据扩展分类2 */
	private String ex_class2;
	/** 单据扩展分类3 */
	private String ex_class3;
	/** 单据扩展分类4 */
	private String ex_class4;
	/** 订单中心订单外部编号 */
	private String bill_order_id;

	/** 品牌集合编号 */
	private String brand_groupcode;

	/** 经销商促销单号 */
	private long bill_promotion_id;

	/** 库存中心参照单号 */
	private String referenct_id;
	/** 收货方发货类型 */
	private String get_sell_code;

	/** 提交次序 */
	private int submit_order;
	/** 退货计算类型: 1正常退货计算逻辑，2特殊退货计算逻辑 */
	private int back_calctype;
	/** 分区编号 */
	public String partition_id;
	// #endregion

	// #region setter && getter method

	/**
	 * @return 获取单据系统号
	 */
	public long getP0670_id() {
		return p0670_id;
	}

	/**
	 * @param 设置单据系统号
	 */
	public void setP0670_id(long p0670_id) {
		this.p0670_id = p0670_id;
		if (!this.changes.contains(EmBillMain.p0670_id)) {
			this.changes.add(EmBillMain.p0670_id);
		}
	}

	/**
	 * @return 获取单据编号
	 */
	public String getBill_id() {
		return bill_id;
	}

	/**
	 * @param 设置单据编号
	 */
	public void setBill_id(String bill_id) {
		this.bill_id = bill_id;
		if (!this.changes.contains(EmBillMain.bill_id)) {
			this.changes.add(EmBillMain.bill_id);
		}
	}

	/**
	 * @return 获取参照单号
	 */
	public long getBill_czid() {
		return bill_czid;
	}

	/**
	 * @param 设置参照单号
	 */
	public void setBill_czid(long bill_czid) {
		this.bill_czid = bill_czid;
		if (!this.changes.contains(EmBillMain.bill_czid)) {
			this.changes.add(EmBillMain.bill_czid);
		}
	}

	/**
	 * @return 获取系统数据类型
	 */
	public int getSystem_type() {
		return system_type;
	}

	/**
	 * @param 设置系统数据类型
	 */
	public void setSystem_type(int system_type) {
		this.system_type = system_type;
		if (!this.changes.contains(EmBillMain.system_type)) {
			this.changes.add(EmBillMain.system_type);
		}
	}

	/**
	 * @return 获取发货地系统编号
	 */
	public long getSet_department_id() {
		return set_department_id;
	}

	/**
	 * @return 获取发货地编号
	 * @returnType String
	 */
	public String getSet_department_user_id() {
		return set_department_user_id;
	}

	/**
	 * 设置发货地编号
	 * 
	 * @param set_department_user_id
	 * @returnType void
	 */
	public void setSet_department_user_id(String set_department_user_id) {
		this.set_department_user_id = set_department_user_id;
		if (!this.changes.contains(EmBillMain.set_department_user_id)) {
			this.changes.add(EmBillMain.set_department_user_id);
		}
	}

	/**
	 * @return 获取收货地编号
	 * @returnType String
	 */
	public String getGet_department_user_id() {
		return get_department_user_id;
	}

	/**
	 * 设置收货地编号
	 * 
	 * @param get_department_user_id
	 * @returnType void
	 */
	public void setGet_department_user_id(String get_department_user_id) {
		this.get_department_user_id = get_department_user_id;
		if (!this.changes.contains(EmBillMain.get_department_user_id)) {
			this.changes.add(EmBillMain.get_department_user_id);
		}
	}

	/**
	 * @param 设置发货地系统编号
	 */
	public void setSet_department_id(long set_department_id) {
		this.set_department_id = set_department_id;
		if (!this.changes.contains(EmBillMain.set_department_id)) {
			this.changes.add(EmBillMain.set_department_id);
		}
	}

	/**
	 * @return 获取收款方编号
	 */
	public long getPayee_department_id() {
		return payee_department_id;
	}

	/**
	 * @param 设置收款方编号
	 */
	public void setPayee_department_id(long payee_department_id) {
		this.payee_department_id = payee_department_id;
		if (!this.changes.contains(EmBillMain.payee_department_id)) {
			this.changes.add(EmBillMain.payee_department_id);
		}
	}

	public String getPayee_department_user_id() {
		return payee_department_user_id;
	}

	public void setPayee_department_user_id(String payee_department_user_id) {
		this.payee_department_user_id = payee_department_user_id;
		if (!this.changes.contains(EmBillMain.payee_department_user_id)) {
			this.changes.add(EmBillMain.payee_department_user_id);
		}
	}

	/**
	 * @return 获取收货地系统编号
	 */
	public long getGet_department_id() {
		return get_department_id;
	}

	/**
	 * @param 设置收货地系统编号
	 */
	public void setGet_department_id(long get_department_id) {
		this.get_department_id = get_department_id;
		if (!this.changes.contains(EmBillMain.get_department_id)) {
			this.changes.add(EmBillMain.get_department_id);
		}
	}
	/**
	 * @return 获取付款方编号
	 */
	public String getPayer_department_user_id() {
		return payer_department_user_id;
	}
	/**
	 * @param 设置付款方编号
	 */
	public void setPayer_department_user_id(String payer_department_user_id) {
		this.payer_department_user_id = payer_department_user_id;
	}

	/**
	 * @return 获取付款方系统编号
	 */
	public long getPayer_department_id() {
		return payer_department_id;
	}

	/**
	 * @param 设置付款方系统编号
	 */
	public void setPayer_department_id(long payer_department_id) {
		this.payer_department_id = payer_department_id;
		if (!this.changes.contains(EmBillMain.payer_department_id)) {
			this.changes.add(EmBillMain.payer_department_id);
		}
	}

	/**
	 * @return 获取单据日期
	 */
	public Timestamp getBill_setdate() {
		return bill_setdate;
	}

	/**
	 * @param 设置单据日期
	 */
	public void setBill_setdate(Timestamp bill_setdate) {
		this.bill_setdate = bill_setdate;
		if (!this.changes.contains(EmBillMain.bill_setdate)) {
			this.changes.add(EmBillMain.bill_setdate);
		}
	}

	/**
	 * @return 获取金额入账
	 */
	public int getBill_sum_sure() {
		return bill_sum_sure;
	}

	/**
	 * @param 设置金额入账
	 */
	public void setBill_sum_sure(int bill_sum_sure) {
		this.bill_sum_sure = bill_sum_sure;
		if (!this.changes.contains(EmBillMain.bill_sum_sure)) {
			this.changes.add(EmBillMain.bill_sum_sure);
		}
	}

	/**
	 * @return 获取金额入账人
	 */
	public String getBill_sum_surename() {
		return bill_sum_surename;
	}

	/**
	 * @param 设置金额入账人
	 */
	public void setBill_sum_surename(String bill_sum_surename) {
		this.bill_sum_surename = bill_sum_surename;
		if (!this.changes.contains(EmBillMain.bill_sum_surename)) {
			this.changes.add(EmBillMain.bill_sum_surename);
		}
	}

	/**
	 * @return 获取金额入账日期
	 */
	public Timestamp getBill_sum_suredate() {
		return bill_sum_suredate;
	}

	/**
	 * @param 设置金额入账日期
	 */
	public void setBill_sum_suredate(Timestamp bill_sum_suredate) {
		this.bill_sum_suredate = bill_sum_suredate;
		if (!this.changes.contains(EmBillMain.bill_sum_suredate)) {
			this.changes.add(EmBillMain.bill_sum_suredate);
		}
	}

	/**
	 * @return 获取单据结案
	 */
	public int getBill_over() {
		return bill_over;
	}

	/**
	 * @param 设置单据结案
	 */
	public void setBill_over(int bill_over) {
		this.bill_over = bill_over;
		if (!this.changes.contains(EmBillMain.bill_over)) {
			this.changes.add(EmBillMain.bill_over);
		}
	}

	/**
	 * @return 获取结案人
	 */
	public String getBill_overname() {
		return bill_overname;
	}

	/**
	 * @param 设置结案人
	 */
	public void setBill_overname(String bill_overname) {
		this.bill_overname = bill_overname;
		if (!this.changes.contains(EmBillMain.bill_overname)) {
			this.changes.add(EmBillMain.bill_overname);
		}
	}

	/**
	 * @return 获取结案日期
	 */
	public Timestamp getBill_overdate() {
		return bill_overdate;
	}

	/**
	 * @param 设置结案日期
	 */
	public void setBill_overdate(Timestamp bill_overdate) {
		this.bill_overdate = bill_overdate;
		if (!this.changes.contains(EmBillMain.bill_overdate)) {
			this.changes.add(EmBillMain.bill_overdate);
		}
	}

	/**
	 * @return 获取单据结账
	 */
	public int getBill_lock() {
		return bill_lock;
	}

	/**
	 * @param 设置单据结账
	 */
	public void setBill_lock(int bill_lock) {
		this.bill_lock = bill_lock;
		if (!this.changes.contains(EmBillMain.bill_lock)) {
			this.changes.add(EmBillMain.bill_lock);
		}
	}

	/**
	 * @return 获取结账人
	 */
	public String getBill_lockname() {
		return bill_lockname;
	}

	/**
	 * @param 设置结账人
	 */
	public void setBill_lockname(String bill_lockname) {
		this.bill_lockname = bill_lockname;
		if (!this.changes.contains(EmBillMain.bill_lockname)) {
			this.changes.add(EmBillMain.bill_lockname);
		}
	}

	/**
	 * @return 获取结账日期
	 */
	public Timestamp getBill_lockdate() {
		return bill_lockdate;
	}

	/**
	 * @param 设置结账日期
	 */
	public void setBill_lockdate(Timestamp bill_lockdate) {
		this.bill_lockdate = bill_lockdate;
		if (!this.changes.contains(EmBillMain.bill_lockdate)) {
			this.changes.add(EmBillMain.bill_lockdate);
		}
	}

	/**
	 * @return 获取单据备注
	 */
	public String getBill_remark() {
		return bill_remark;
	}

	/**
	 * @param 设置单据备注
	 */
	public void setBill_remark(String bill_remark) {
		this.bill_remark = bill_remark;
		if (!this.changes.contains(EmBillMain.bill_remark)) {
			this.changes.add(EmBillMain.bill_remark);
		}
	}

	/**
	 * @return 获取状态
	 */
	public int getBill_state() {
		return bill_state;
	}

	/**
	 * @param 设置状态
	 */
	public void setBill_state(int bill_state) {
		this.bill_state = bill_state;
		if (!this.changes.contains(EmBillMain.bill_state)) {
			this.changes.add(EmBillMain.bill_state);
		}
	}

	/**
	 * @return 获取建立日期
	 */
	public Timestamp getBill_create_date() {
		return bill_create_date;
	}

	/**
	 * @param 设置建立日期
	 */
	public void setBill_create_date(Timestamp bill_create_date) {
		this.bill_create_date = bill_create_date;
		if (!this.changes.contains(EmBillMain.bill_create_date)) {
			this.changes.add(EmBillMain.bill_create_date);
		}
	}

	/**
	 * @return 获取建立人
	 */
	public String getBill_create_name() {
		return bill_create_name;
	}

	/**
	 * @param 设置建立人
	 */
	public void setBill_create_name(String bill_create_name) {
		this.bill_create_name = bill_create_name;
		if (!this.changes.contains(EmBillMain.bill_create_name)) {
			this.changes.add(EmBillMain.bill_create_name);
		}
	}

	/**
	 * @return 获取外部单号
	 */
	public String getBill_refid() {
		return bill_refid;
	}

	/**
	 * @param 设置外部单号
	 */
	public void setBill_refid(String bill_refid) {
		this.bill_refid = bill_refid;
		if (!this.changes.contains(EmBillMain.bill_refid)) {
			this.changes.add(EmBillMain.bill_refid);
		}
	}

	/**
	 * @return 获取单据类型识别码
	 */
	public String getBill_idcode() {
		return bill_idcode;
	}

	/**
	 * @param 设置单据类型识别码
	 */
	public void setBill_idcode(String bill_idcode) {
		this.bill_idcode = bill_idcode;
		if (!this.changes.contains(EmBillMain.bill_idcode)) {
			this.changes.add(EmBillMain.bill_idcode);
		}
	}

	/**
	 * @return 获取单据类型
	 */
	public String getBill_selltype() {
		return bill_selltype;
	}

	/**
	 * @param 设置单据类型
	 */
	public void setBill_selltype(String bill_selltype) {
		this.bill_selltype = bill_selltype;
		if (!this.changes.contains(EmBillMain.bill_selltype)) {
			this.changes.add(EmBillMain.bill_selltype);
		}
	}

	/**
	 * @return 获取提醒日期
	 */
	public Timestamp getBill_reminddate() {
		return bill_reminddate;
	}

	/**
	 * @param 设置提醒日期
	 */
	public void setBill_reminddate(Timestamp bill_reminddate) {
		this.bill_reminddate = bill_reminddate;
		if (!this.changes.contains(EmBillMain.bill_reminddate)) {
			this.changes.add(EmBillMain.bill_reminddate);
		}
	}

	/**
	 * @return 获取发货备注
	 */
	public String getBill_sellremark() {
		return bill_sellremark;
	}

	/**
	 * @param 设置发货备注
	 */
	public void setBill_sellremark(String bill_sellremark) {
		this.bill_sellremark = bill_sellremark;
		if (!this.changes.contains(EmBillMain.bill_sellremark)) {
			this.changes.add(EmBillMain.bill_sellremark);
		}
	}

	/**
	 * @return 获取单据数量
	 */
	public double getBill_num() {
		return bill_num;
	}

	/**
	 * @param 设置单据数量
	 */
	public void setBill_num(double bill_num) {
		this.bill_num = bill_num;
		if (!this.changes.contains(EmBillMain.bill_num)) {
			this.changes.add(EmBillMain.bill_num);
		}
	}

	/**
	 * @return 获取成本金额
	 */
	public double getBill_csum() {
		return bill_csum;
	}

	/**
	 * @param 设置成本金额
	 */
	public void setBill_csum(double bill_csum) {
		this.bill_csum = bill_csum;
		if (!this.changes.contains(EmBillMain.bill_csum)) {
			this.changes.add(EmBillMain.bill_csum);
		}
	}

	/**
	 * @return 获取吊牌金额
	 */
	public double getBill_jsum() {
		return bill_jsum;
	}

	/**
	 * @param 设置吊牌金额
	 */
	public void setBill_jsum(double bill_jsum) {
		this.bill_jsum = bill_jsum;
		if (!this.changes.contains(EmBillMain.bill_jsum)) {
			this.changes.add(EmBillMain.bill_jsum);
		}
	}

	/**
	 * @return 获取现价金额
	 */
	public double getBill_xsum() {
		return bill_xsum;
	}

	/**
	 * @param 设置现价金额
	 */
	public void setBill_xsum(double bill_xsum) {
		this.bill_xsum = bill_xsum;
		if (!this.changes.contains(EmBillMain.bill_xsum)) {
			this.changes.add(EmBillMain.bill_xsum);
		}
	}

	/**
	 * @return 获取结算金额
	 */
	public double getBill_ssum() {
		return bill_ssum;
	}

	/**
	 * @param 设置结算金额
	 */
	public void setBill_ssum(double bill_ssum) {
		this.bill_ssum = bill_ssum;
		if (!this.changes.contains(EmBillMain.bill_ssum)) {
			this.changes.add(EmBillMain.bill_ssum);
		}
	}

	/**
	 * @return 获取单据处理标记
	 */
	public int getBill_editsign() {
		return bill_editsign;
	}

	/**
	 * @param 设置单据处理标记
	 */
	public void setBill_editsign(int bill_editsign) {
		this.bill_editsign = bill_editsign;
		if (!this.changes.contains(EmBillMain.bill_editsign)) {
			this.changes.add(EmBillMain.bill_editsign);
		}
	}

	/**
	 * @return 获取发货公司编号
	 */
	public long getSet_company_id() {
		return set_company_id;
	}

	/**
	 * @param 设置发货公司编号
	 */
	public void setSet_company_id(long set_company_id) {
		this.set_company_id = set_company_id;
		if (!this.changes.contains(EmBillMain.set_company_id)) {
			this.changes.add(EmBillMain.set_company_id);
		}
	}

	/**
	 * @return 获取发货地上级系统编号
	 */
	public long getSet_department_parent_id() {
		return set_department_parent_id;
	}

	/**
	 * @param 设置发货地上级系统编号
	 */
	public void setSet_department_parent_id(long set_department_parent_id) {
		this.set_department_parent_id = set_department_parent_id;
		if (!this.changes.contains(EmBillMain.set_department_parent_id)) {
			this.changes.add(EmBillMain.set_department_parent_id);
		}
	}

	/**
	 * @return 获取收货地上级系统编号
	 */
	public long getGet_department_parent_id() {
		return get_department_parent_id;
	}

	/**
	 * @param 设置收货地上级系统编号
	 */
	public void setGet_department_parent_id(long get_department_parent_id) {
		this.get_department_parent_id = get_department_parent_id;
		if (!this.changes.contains(EmBillMain.get_department_parent_id)) {
			this.changes.add(EmBillMain.get_department_parent_id);
		}
	}

	/**
	 * @return 获取单据建立机构
	 */
	public long getCr_department_id() {
		return cr_department_id;
	}

	/**
	 * @param 设置单据建立机构
	 */
	public void setCr_department_id(long cr_department_id) {
		this.cr_department_id = cr_department_id;
		if (!this.changes.contains(EmBillMain.cr_department_id)) {
			this.changes.add(EmBillMain.cr_department_id);
		}
	}

	/**
	 * @return 获取数量入账
	 */
	public int getBill_num_sure() {
		return bill_num_sure;
	}

	/**
	 * @param 设置数量入账
	 */
	public void setBill_num_sure(int bill_num_sure) {
		this.bill_num_sure = bill_num_sure;
		if (!this.changes.contains(EmBillMain.bill_num_sure)) {
			this.changes.add(EmBillMain.bill_num_sure);
		}
	}

	/**
	 * @return 获取数量入账人
	 */
	public String getBill_num_surename() {
		return bill_num_surename;
	}

	/**
	 * @param 设置数量入账人
	 */
	public void setBill_num_surename(String bill_num_surename) {
		this.bill_num_surename = bill_num_surename;
		if (!this.changes.contains(EmBillMain.bill_num_surename)) {
			this.changes.add(EmBillMain.bill_num_surename);
		}
	}

	/**
	 * @return 获取数量入账日期
	 */
	public Timestamp getBill_num_suredate() {
		return bill_num_suredate;
	}

	/**
	 * @param 设置数量入账日期
	 */
	public void setBill_num_suredate(Timestamp bill_num_suredate) {
		this.bill_num_suredate = bill_num_suredate;
		if (!this.changes.contains(EmBillMain.bill_num_suredate)) {
			this.changes.add(EmBillMain.bill_num_suredate);
		}
	}

	/**
	 * @return 获取款式编号
	 */
	public String getStyle_id() {
		return style_id;
	}

	/**
	 * @param 设置款式编号
	 */
	public void setStyle_id(String style_id) {
		this.style_id = style_id;
		if (!this.changes.contains(EmBillMain.style_id)) {
			this.changes.add(EmBillMain.style_id);
		}
	}

	/**
	 * @return 获取收货结算店铺编号
	 */
	public long getGet_pay_department_id() {
		return get_pay_department_id;
	}

	/**
	 * @param 设置收货结算店铺编号
	 */
	public void setGet_pay_department_id(long get_pay_department_id) {
		this.get_pay_department_id = get_pay_department_id;
		if (!this.changes.contains(EmBillMain.get_pay_department_id)) {
			this.changes.add(EmBillMain.get_pay_department_id);
		}
	}

	/**
	 * @return 获取订金扣减比率
	 */
	public double getBill_deposit_pec() {
		return bill_deposit_pec;
	}

	/**
	 * @param 设置订金扣减比率
	 */
	public void setBill_deposit_pec(double bill_deposit_pec) {
		this.bill_deposit_pec = bill_deposit_pec;
		if (!this.changes.contains(EmBillMain.bill_deposit_pec)) {
			this.changes.add(EmBillMain.bill_deposit_pec);
		}
	}

	/**
	 * @return 获取数量收货入账
	 */
	public int getBill_num_getsure() {
		return bill_num_getsure;
	}

	/**
	 * @param 设置数量收货入账
	 */
	public void setBill_num_getsure(int bill_num_getsure) {
		this.bill_num_getsure = bill_num_getsure;
		if (!this.changes.contains(EmBillMain.bill_num_getsure)) {
			this.changes.add(EmBillMain.bill_num_getsure);
		}
	}

	/**
	 * @return 获取数量收货入账人
	 */
	public String getBill_num_getsurename() {
		return bill_num_getsurename;
	}

	/**
	 * @param 设置数量收货入账人
	 */
	public void setBill_num_getsurename(String bill_num_getsurename) {
		this.bill_num_getsurename = bill_num_getsurename;
		if (!this.changes.contains(EmBillMain.bill_num_getsurename)) {
			this.changes.add(EmBillMain.bill_num_getsurename);
		}
	}

	/**
	 * @return 获取数量收货入账日期
	 */
	public Timestamp getBill_num_getsuredate() {
		return bill_num_getsuredate;
	}

	/**
	 * @param 设置数量收货入账日期
	 */
	public void setBill_num_getsuredate(Timestamp bill_num_getsuredate) {
		this.bill_num_getsuredate = bill_num_getsuredate;
		if (!this.changes.contains(EmBillMain.bill_num_getsuredate)) {
			this.changes.add(EmBillMain.bill_num_getsuredate);
		}
	}

	/**
	 * @return 获取金额收货入账
	 */
	public int getBill_sum_getsure() {
		return bill_sum_getsure;
	}

	/**
	 * @param 设置金额收货入账
	 */
	public void setBill_sum_getsure(int bill_sum_getsure) {
		this.bill_sum_getsure = bill_sum_getsure;
		if (!this.changes.contains(EmBillMain.bill_sum_getsure)) {
			this.changes.add(EmBillMain.bill_sum_getsure);
		}
	}

	/**
	 * @return 获取金额收货入账人
	 */
	public String getBill_sum_getsurename() {
		return bill_sum_getsurename;
	}

	/**
	 * @param 设置金额收货入账人
	 */
	public void setBill_sum_getsurename(String bill_sum_getsurename) {
		this.bill_sum_getsurename = bill_sum_getsurename;
		if (!this.changes.contains(EmBillMain.bill_sum_getsurename)) {
			this.changes.add(EmBillMain.bill_sum_getsurename);
		}
	}

	/**
	 * @return 获取金额收货入账日期
	 */
	public Timestamp getBill_sum_getsuredate() {
		return bill_sum_getsuredate;
	}

	/**
	 * @param 设置金额收货入账日期
	 */
	public void setBill_sum_getsuredate(Timestamp bill_sum_getsuredate) {
		this.bill_sum_getsuredate = bill_sum_getsuredate;
		if (!this.changes.contains(EmBillMain.bill_sum_getsuredate)) {
			this.changes.add(EmBillMain.bill_sum_getsuredate);
		}
	}

	/**
	 * @return 获取借款申请单
	 */
	public long getLoan_id() {
		return loan_id;
	}

	/**
	 * @param 设置借款申请单
	 */
	public void setLoan_id(long loan_id) {
		this.loan_id = loan_id;
		if (!this.changes.contains(EmBillMain.loan_id)) {
			this.changes.add(EmBillMain.loan_id);
		}
	}

	/**
	 * @return 获取收货数量
	 */
	public double getBill_getnum() {
		return bill_getnum;
	}

	/**
	 * @param 设置收货数量
	 */
	public void setBill_getnum(double bill_getnum) {
		this.bill_getnum = bill_getnum;
		if (!this.changes.contains(EmBillMain.bill_getnum)) {
			this.changes.add(EmBillMain.bill_getnum);
		}
	}

	/**
	 * @return 获取多收处理方式
	 */
	public int getBill_more_deal() {
		return bill_more_deal;
	}

	/**
	 * @param 设置多收处理方式
	 */
	public void setBill_more_deal(int bill_more_deal) {
		this.bill_more_deal = bill_more_deal;
		if (!this.changes.contains(EmBillMain.bill_more_deal)) {
			this.changes.add(EmBillMain.bill_more_deal);
		}
	}

	/**
	 * @return 获取少收处理方式
	 */
	public int getBill_less_deal() {
		return bill_less_deal;
	}

	/**
	 * @param 设置少收处理方式
	 */
	public void setBill_less_deal(int bill_less_deal) {
		this.bill_less_deal = bill_less_deal;
		if (!this.changes.contains(EmBillMain.bill_less_deal)) {
			this.changes.add(EmBillMain.bill_less_deal);
		}
	}

	/**
	 * @return 获取单据类型
	 */
	public int getBill_type() {
		return bill_type;
	}

	/**
	 * @param 设置单据类型
	 */
	public void setBill_type(int bill_type) {
		this.bill_type = bill_type;
		if (!this.changes.contains(EmBillMain.bill_type)) {
			this.changes.add(EmBillMain.bill_type);
		}
	}

	/**
	 * @return 获取收货吊牌金额
	 */
	public double getBill_getjsum() {
		return bill_getjsum;
	}

	/**
	 * @param 设置收货吊牌金额
	 */
	public void setBill_getjsum(double bill_getjsum) {
		this.bill_getjsum = bill_getjsum;
		if (!this.changes.contains(EmBillMain.bill_getjsum)) {
			this.changes.add(EmBillMain.bill_getjsum);
		}
	}

	/**
	 * @return 获取收货现价金额
	 */
	public double getBill_getxsum() {
		return bill_getxsum;
	}

	/**
	 * @param 设置收货现价金额
	 */
	public void setBill_getxsum(double bill_getxsum) {
		this.bill_getxsum = bill_getxsum;
		if (!this.changes.contains(EmBillMain.bill_getxsum)) {
			this.changes.add(EmBillMain.bill_getxsum);
		}
	}

	/**
	 * @return 获取收货结算金额
	 */
	public double getBill_getssum() {
		return bill_getssum;
	}

	/**
	 * @param 设置收货结算金额
	 */
	public void setBill_getssum(double bill_getssum) {
		this.bill_getssum = bill_getssum;
		if (!this.changes.contains(EmBillMain.bill_getssum)) {
			this.changes.add(EmBillMain.bill_getssum);
		}
	}

	/**
	 * @return 获取班次
	 */
	public long getBill_shift_id() {
		return bill_shift_id;
	}

	/**
	 * @param 设置班次
	 */
	public void setBill_shift_id(long bill_shift_id) {
		this.bill_shift_id = bill_shift_id;
		if (!this.changes.contains(EmBillMain.bill_shift_id)) {
			this.changes.add(EmBillMain.bill_shift_id);
		}
	}

	/**
	 * @return 获取实销金额
	 */
	public double getBill_fsums() {
		return bill_fsums;
	}

	/**
	 * @param 设置实销金额
	 */
	public void setBill_fsums(double bill_fsums) {
		this.bill_fsums = bill_fsums;
		if (!this.changes.contains(EmBillMain.bill_fsums)) {
			this.changes.add(EmBillMain.bill_fsums);
		}
	}

	/**
	 * @return 获取冲单原单号
	 */
	public long getOriginal_id() {
		return original_id;
	}

	/**
	 * @param 设置冲单原单号
	 */
	public void setOriginal_id(long original_id) {
		this.original_id = original_id;
		if (!this.changes.contains(EmBillMain.original_id)) {
			this.changes.add(EmBillMain.original_id);
		}
	}

	/**
	 * @return 获取退货计算机构
	 */
	public long getReturn_department_id() {
		return return_department_id;
	}

	/**
	 * @param 设置退货计算机构
	 */
	public void setReturn_department_id(long return_department_id) {
		this.return_department_id = return_department_id;
		if (!this.changes.contains(EmBillMain.return_department_id)) {
			this.changes.add(EmBillMain.return_department_id);
		}
	}

	/**
	 * @return 获取收货差异
	 */
	public int getBill_diff() {
		return bill_diff;
	}

	/**
	 * @param 设置收货差异
	 */
	public void setBill_diff(int bill_diff) {
		this.bill_diff = bill_diff;
		if (!this.changes.contains(EmBillMain.bill_diff)) {
			this.changes.add(EmBillMain.bill_diff);
		}
	}

	/**
	 * @return 获取差异处理方式
	 */
	public int getBill_diff_type() {
		return bill_diff_type;
	}

	/**
	 * @param 设置差异处理方式
	 */
	public void setBill_diff_type(int bill_diff_type) {
		this.bill_diff_type = bill_diff_type;
		if (!this.changes.contains(EmBillMain.bill_diff_type)) {
			this.changes.add(EmBillMain.bill_diff_type);
		}
	}

	/**
	 * @return 获取差异处理人
	 */
	public String getBill_diff_name() {
		return bill_diff_name;
	}

	/**
	 * @param 设置差异处理人
	 */
	public void setBill_diff_name(String bill_diff_name) {
		this.bill_diff_name = bill_diff_name;
		if (!this.changes.contains(EmBillMain.bill_diff_name)) {
			this.changes.add(EmBillMain.bill_diff_name);
		}
	}

	/**
	 * @return 获取差异处理日期
	 */
	public Timestamp getBill_diff_date() {
		return bill_diff_date;
	}

	/**
	 * @param 设置差异处理日期
	 */
	public void setBill_diff_date(Timestamp bill_diff_date) {
		this.bill_diff_date = bill_diff_date;
		if (!this.changes.contains(EmBillMain.bill_diff_date)) {
			this.changes.add(EmBillMain.bill_diff_date);
		}
	}

	/**
	 * @return 获取处理单据编号
	 */
	public String getBill_diff_bill_id() {
		return bill_diff_bill_id;
	}

	/**
	 * @param 设置处理单据编号
	 */
	public void setBill_diff_bill_id(String bill_diff_bill_id) {
		this.bill_diff_bill_id = bill_diff_bill_id;
		if (!this.changes.contains(EmBillMain.bill_diff_bill_id)) {
			this.changes.add(EmBillMain.bill_diff_bill_id);
		}
	}

	/**
	 * @return 获取处理备注
	 */
	public String getBill_diff_remark() {
		return bill_diff_remark;
	}

	/**
	 * @param 设置处理备注
	 */
	public void setBill_diff_remark(String bill_diff_remark) {
		this.bill_diff_remark = bill_diff_remark;
		if (!this.changes.contains(EmBillMain.bill_diff_remark)) {
			this.changes.add(EmBillMain.bill_diff_remark);
		}
	}

	/**
	 * @return 获取原数量
	 */
	public double getBill_cnum() {
		return bill_cnum;
	}

	/**
	 * @param 设置原数量
	 */
	public void setBill_cnum(double bill_cnum) {
		this.bill_cnum = bill_cnum;
		if (!this.changes.contains(EmBillMain.bill_cnum)) {
			this.changes.add(EmBillMain.bill_cnum);
		}
	}

	/**
	 * @return 获取货币代码
	 */
	public String getBill_currency_code() {
		return bill_currency_code;
	}

	/**
	 * @param 设置货币代码
	 */
	public void setBill_currency_code(String bill_currency_code) {
		this.bill_currency_code = bill_currency_code;
		if (!this.changes.contains(EmBillMain.bill_currency_code)) {
			this.changes.add(EmBillMain.bill_currency_code);
		}
	}

	/**
	 * @return 获取汇率
	 */
	public double getBill_exchange_rate() {
		return bill_exchange_rate;
	}

	/**
	 * @param 设置汇率
	 */
	public void setBill_exchange_rate(double bill_exchange_rate) {
		this.bill_exchange_rate = bill_exchange_rate;
		if (!this.changes.contains(EmBillMain.bill_exchange_rate)) {
			this.changes.add(EmBillMain.bill_exchange_rate);
		}
	}

	/**
	 * @return 获取消费税率
	 */
	public double getBill_consumertax_rate() {
		return bill_consumertax_rate;
	}

	/**
	 * @param 设置消费税率
	 */
	public void setBill_consumertax_rate(double bill_consumertax_rate) {
		this.bill_consumertax_rate = bill_consumertax_rate;
		if (!this.changes.contains(EmBillMain.bill_consumertax_rate)) {
			this.changes.add(EmBillMain.bill_consumertax_rate);
		}
	}

	/**
	 * @return 获取运费
	 */
	public double getBill_freight() {
		return bill_freight;
	}

	/**
	 * @param 设置运费
	 */
	public void setBill_freight(double bill_freight) {
		this.bill_freight = bill_freight;
		if (!this.changes.contains(EmBillMain.bill_freight)) {
			this.changes.add(EmBillMain.bill_freight);
		}
	}

	/**
	 * @return 获取消费税起征数量
	 */
	public double getBill_consumertax_num() {
		return bill_consumertax_num;
	}

	/**
	 * @param 设置消费税起征数量
	 */
	public void setBill_consumertax_num(double bill_consumertax_num) {
		this.bill_consumertax_num = bill_consumertax_num;
		if (!this.changes.contains(EmBillMain.bill_consumertax_num)) {
			this.changes.add(EmBillMain.bill_consumertax_num);
		}
	}

	/**
	 * @return 获取佣金提成
	 */
	public double getCommission_rate() {
		return commission_rate;
	}

	/**
	 * @param 设置佣金提成
	 */
	public void setCommission_rate(double commission_rate) {
		this.commission_rate = commission_rate;
		if (!this.changes.contains(EmBillMain.commission_rate)) {
			this.changes.add(EmBillMain.commission_rate);
		}
	}

	/**
	 * @return 获取打印次数
	 */
	public int getBill_print_times() {
		return bill_print_times;
	}

	/**
	 * @param 设置打印次数
	 */
	public void setBill_print_times(int bill_print_times) {
		this.bill_print_times = bill_print_times;
		if (!this.changes.contains(EmBillMain.bill_print_times)) {
			this.changes.add(EmBillMain.bill_print_times);
		}
	}

	/**
	 * @return 获取收回通知标识
	 */
	public int getBill_allot_sign() {
		return bill_allot_sign;
	}

	/**
	 * @param 设置收回通知标识
	 */
	public void setBill_allot_sign(int bill_allot_sign) {
		this.bill_allot_sign = bill_allot_sign;
		if (!this.changes.contains(EmBillMain.bill_allot_sign)) {
			this.changes.add(EmBillMain.bill_allot_sign);
		}
	}

	/**
	 * @return 获取收货备注
	 */
	public String getBill_get_remark() {
		return bill_get_remark;
	}

	/**
	 * @param 设置收货备注
	 */
	public void setBill_get_remark(String bill_get_remark) {
		this.bill_get_remark = bill_get_remark;
		if (!this.changes.contains(EmBillMain.bill_get_remark)) {
			this.changes.add(EmBillMain.bill_get_remark);
		}
	}

	/**
	 * @return 获取借调部门
	 */
	public String getMove_dpt() {
		return move_dpt;
	}

	/**
	 * @param 设置借调部门
	 */
	public void setMove_dpt(String move_dpt) {
		this.move_dpt = move_dpt;
		if (!this.changes.contains(EmBillMain.move_dpt)) {
			this.changes.add(EmBillMain.move_dpt);
		}
	}

	/**
	 * @return 获取esb数据来源
	 */
	public String getEsb_source() {
		return esb_source;
	}

	/**
	 * @param 设置esb数据来源
	 */
	public void setEsb_source(String esb_source) {
		this.esb_source = esb_source;
		if (!this.changes.contains(EmBillMain.esb_source)) {
			this.changes.add(EmBillMain.esb_source);
		}
	}

	/**
	 * @return 获取是否店长授权单据
	 */
	public int getBill_issoauth() {
		return bill_issoauth;
	}

	/**
	 * @param 设置是否店长授权单据
	 */
	public void setBill_issoauth(int bill_issoauth) {
		this.bill_issoauth = bill_issoauth;
		if (!this.changes.contains(EmBillMain.bill_issoauth)) {
			this.changes.add(EmBillMain.bill_issoauth);
		}
	}

	/**
	 * @return 获取整单发货类型
	 */
	public String getBill_sell_code() {
		return bill_sell_code;
	}

	/**
	 * @param 设置整单发货类型
	 */
	public void setBill_sell_code(String bill_sell_code) {
		this.bill_sell_code = bill_sell_code;
		if (!this.changes.contains(EmBillMain.bill_sell_code)) {
			this.changes.add(EmBillMain.bill_sell_code);
		}
	}

	/**
	 * @return 获取款式分类
	 */
	public String getBill_style_sort() {
		return bill_style_sort;
	}

	/**
	 * @param 设置款式分类
	 */
	public void setBill_style_sort(String bill_style_sort) {
		this.bill_style_sort = bill_style_sort;
		if (!this.changes.contains(EmBillMain.bill_style_sort)) {
			this.changes.add(EmBillMain.bill_style_sort);
		}
	}

	/**
	 * @return 获取是否需要传wms
	 */
	public int getBill_towms() {
		return bill_towms;
	}

	/**
	 * @param 设置是否需要传wms
	 */
	public void setBill_towms(int bill_towms) {
		this.bill_towms = bill_towms;
		if (!this.changes.contains(EmBillMain.bill_towms)) {
			this.changes.add(EmBillMain.bill_towms);
		}
	}

	/**
	 * @return 获取是否需要传sap
	 */
	public int getBill_tosap() {
		return bill_tosap;
	}

	/**
	 * @param 设置是否需要传sap
	 */
	public void setBill_tosap(int bill_tosap) {
		this.bill_tosap = bill_tosap;
		if (!this.changes.contains(EmBillMain.bill_tosap)) {
			this.changes.add(EmBillMain.bill_tosap);
		}
	}

	/**
	 * @return 获取差异处理单据
	 */
	public int getBill_isadjust() {
		return bill_isadjust;
	}

	/**
	 * @param 设置差异处理单据
	 */
	public void setBill_isadjust(int bill_isadjust) {
		this.bill_isadjust = bill_isadjust;
		if (!this.changes.contains(EmBillMain.bill_isadjust)) {
			this.changes.add(EmBillMain.bill_isadjust);
		}
	}

	/**
	 * @return 获取差异来源单据
	 */
	public long getBill_adjust_for() {
		return bill_adjust_for;
	}

	/**
	 * @param 设置差异来源单据
	 */
	public void setBill_adjust_for(long bill_adjust_for) {
		this.bill_adjust_for = bill_adjust_for;
		if (!this.changes.contains(EmBillMain.bill_adjust_for)) {
			this.changes.add(EmBillMain.bill_adjust_for);
		}
	}

	/**
	 * @return 获取供货单据类型
	 */
	public int getBill_supply_type() {
		return bill_supply_type;
	}

	/**
	 * @param 设置供货单据类型
	 */
	public void setBill_supply_type(int bill_supply_type) {
		this.bill_supply_type = bill_supply_type;
		if (!this.changes.contains(EmBillMain.bill_supply_type)) {
			this.changes.add(EmBillMain.bill_supply_type);
		}
	}

	/**
	 * @return 获取供货来源单据
	 */
	public long getBill_supply_for() {
		return bill_supply_for;
	}

	/**
	 * @param 设置供货来源单据
	 */
	public void setBill_supply_for(long bill_supply_for) {
		this.bill_supply_for = bill_supply_for;
		if (!this.changes.contains(EmBillMain.bill_supply_for)) {
			this.changes.add(EmBillMain.bill_supply_for);
		}
	}

	/**
	 * @return 获取同步标识
	 */
	public int getBill_sync() {
		return bill_sync;
	}

	/**
	 * @param 设置同步标识
	 */
	public void setBill_sync(int bill_sync) {
		this.bill_sync = bill_sync;
		if (!this.changes.contains(EmBillMain.bill_sync)) {
			this.changes.add(EmBillMain.bill_sync);
		}
	}

	/**
	 * @return 获取库存已更新
	 */
	public int getStock_calced() {
		return stock_calced;
	}

	/**
	 * @param 设置库存已更新
	 */
	public void setStock_calced(int stock_calced) {
		this.stock_calced = stock_calced;
		if (!this.changes.contains(EmBillMain.stock_calced)) {
			this.changes.add(EmBillMain.stock_calced);
		}
	}

	/**
	 * @return 获取sap记账日期
	 */
	public Timestamp getBill_accountdate() {
		return bill_accountdate;
	}

	/**
	 * @param 设置sap记账日期
	 */
	public void setBill_accountdate(Timestamp bill_accountdate) {
		this.bill_accountdate = bill_accountdate;
		if (!this.changes.contains(EmBillMain.bill_accountdate)) {
			this.changes.add(EmBillMain.bill_accountdate);
		}
	}

	/**
	 * @return 获取收货记账日期
	 */
	public Timestamp getBill_get_accountdate() {
		return bill_get_accountdate;
	}

	/**
	 * @param 设置收货记账日期
	 */
	public void setBill_get_accountdate(Timestamp bill_get_accountdate) {
		this.bill_get_accountdate = bill_get_accountdate;
		if (!this.changes.contains(EmBillMain.bill_get_accountdate)) {
			this.changes.add(EmBillMain.bill_get_accountdate);
		}
	}

	/**
	 * @return 获取收货是否需要传sap
	 */
	public int getBill_get_tosap() {
		return bill_get_tosap;
	}

	/**
	 * @param 设置收货是否需要传sap
	 */
	public void setBill_get_tosap(int bill_get_tosap) {
		this.bill_get_tosap = bill_get_tosap;
		if (!this.changes.contains(EmBillMain.bill_get_tosap)) {
			this.changes.add(EmBillMain.bill_get_tosap);
		}
	}

	/**
	 * @return 获取单据自编号
	 */
	public String getBill_code() {
		return bill_code;
	}

	/**
	 * @param 设置单据自编号
	 */
	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
		if (!this.changes.contains(EmBillMain.bill_code)) {
			this.changes.add(EmBillMain.bill_code);
		}
	}

	/**
	 * @return 获取单据扩展分类1
	 */
	public String getEx_class1() {
		return ex_class1;
	}

	/**
	 * @param 设置单据扩展分类1
	 */
	public void setEx_class1(String ex_class1) {
		this.ex_class1 = ex_class1;
		if (!this.changes.contains(EmBillMain.ex_class1)) {
			this.changes.add(EmBillMain.ex_class1);
		}
	}

	/**
	 * @return 获取单据扩展分类2
	 */
	public String getEx_class2() {
		return ex_class2;
	}

	/**
	 * @param 设置单据扩展分类2
	 */
	public void setEx_class2(String ex_class2) {
		this.ex_class2 = ex_class2;
		if (!this.changes.contains(EmBillMain.ex_class2)) {
			this.changes.add(EmBillMain.ex_class2);
		}
	}

	/**
	 * @return 获取单据扩展分类3
	 */
	public String getEx_class3() {
		return ex_class3;
	}

	/**
	 * @param 设置单据扩展分类3
	 */
	public void setEx_class3(String ex_class3) {
		this.ex_class3 = ex_class3;
		if (!this.changes.contains(EmBillMain.ex_class3)) {
			this.changes.add(EmBillMain.ex_class3);
		}
	}

	/**
	 * @return 获取单据扩展分类4
	 */
	public String getEx_class4() {
		return ex_class4;
	}

	/**
	 * @param 设置单据扩展分类4
	 */
	public void setEx_class4(String ex_class4) {
		this.ex_class4 = ex_class4;
		if (!this.changes.contains(EmBillMain.ex_class4)) {
			this.changes.add(EmBillMain.ex_class4);
		}
	}

	/**
	 * @return 获取订单中心订单外部编号
	 */
	public String getBill_order_id() {
		return bill_order_id;
	}

	/**
	 * @param 设置订单中心订单外部编号
	 */
	public void setBill_order_id(String bill_order_id) {
		this.bill_order_id = bill_order_id;
		if (!this.changes.contains(EmBillMain.bill_order_id)) {
			this.changes.add(EmBillMain.bill_order_id);
		}
	}

	// /**
	// * @return 获取发货地编号
	// */
	// public String getSet_department_code() {
	// return set_department_code;
	// }
	//
	// /**
	// * @param 设置发货地编号
	// */
	// public void setSet_department_code(String set_department_code) {
	// this.set_department_code = set_department_code;
	// if (!this.changes.contains(P0670Enum.set_department_code)) {
	// this.changes.add(P0670Enum.set_department_code);
	// }
	// }
	//
	// /**
	// * @return 获取收货地编号
	// */
	// public String getGet_department_code() {
	// return get_department_code;
	// }
	//
	// /**
	// * @param 设置收货地编号
	// */
	// public void setGet_department_code(String get_department_code) {
	// this.get_department_code = get_department_code;
	// if (!this.changes.contains(P0670Enum.get_department_code)) {
	// this.changes.add(P0670Enum.get_department_code);
	// }
	// }
	//
	// /**
	// * @return 获取发货地上级编号
	// */
	// public String getSet_department_parent_code() {
	// return set_department_parent_code;
	// }
	//
	// /**
	// * @param 设置发货地上级编号
	// */
	// public void setSet_department_parent_code(String
	// set_department_parent_code) {
	// this.set_department_parent_code = set_department_parent_code;
	// if (!this.changes.contains(P0670Enum.set_department_parent_code)) {
	// this.changes.add(P0670Enum.set_department_parent_code);
	// }
	// }
	//
	// /**
	// * @return 获取收货地上级编号
	// */
	// public String getGet_department_parent_code() {
	// return get_department_parent_code;
	// }
	//
	// /**
	// * @param 设置收货地上级编号
	// */
	// public void setGet_department_parent_code(String
	// get_department_parent_code) {
	// this.get_department_parent_code = get_department_parent_code;
	// if (!this.changes.contains(P0670Enum.get_department_parent_code)) {
	// this.changes.add(P0670Enum.get_department_parent_code);
	// }
	// }

	/**
	 * @return 获取发货地编号
	 */
	public String getBrand_groupcode() {
		return brand_groupcode;
	}

	/**
	 * @param 设置发货地编号
	 */
	public void setBrand_groupcode(String brand_groupcode) {
		this.brand_groupcode = brand_groupcode;
		if (!this.changes.contains(EmBillMain.brand_groupcode)) {
			this.changes.add(EmBillMain.brand_groupcode);
		}
	}

	/**
	 * @return 获取经销商促销单号
	 */
	public long getBill_promotion_id() {
		return bill_promotion_id;
	}

	/**
	 * @param 设置经销商促销单号
	 */
	public void setBill_promotion_id(long bill_promotion_id) {
		this.bill_promotion_id = bill_promotion_id;
		if (!this.changes.contains(EmBillMain.bill_promotion_id)) {
			this.changes.add(EmBillMain.bill_promotion_id);
		}
	}

	/**
	 * @param 设置库存中心参照单号
	 */
	public void setReferenct_id(String referenct_id) {
		this.referenct_id = referenct_id;
	}

	/**
	 * @return 获取库存中心参照单号
	 */
	public String getReferenct_id() {
		return referenct_id;
	}

	/**
	 * @return 获取收货方发货类型
	 */
	public String getGet_sell_code() {
		return get_sell_code;
	}

	/**
	 * @param 设置收货方发货类型
	 */
	public void setGet_sell_code(String get_sell_code) {
		this.get_sell_code = get_sell_code;
	}

	/**
	 * @return 获取提交次序
	 */
	public int getSubmit_order() {
		return submit_order;
	}

	/**
	 * @param 设置提交次序
	 */
	public void setSubmit_order(int submit_order) {
		this.submit_order = submit_order;
	}

	// #endregion

	/**
	 * @return 获取获取已赋过值的字段
	 */
	public List<EmBillMain> getChanges() {
		return changes;
	}

	/**
	 * 退货计算类型: 1正常退货计算逻辑，2特殊退货计算逻辑
	 * 
	 * @return
	 */
	public int getBack_calctype() {
		return back_calctype;
	}

	/**
	 * 退货计算类型: 1正常退货计算逻辑，2特殊退货计算逻辑
	 * 
	 * @param back_calctype
	 */
	public void setBack_calctype(int back_calctype) {
		this.back_calctype = back_calctype;
		if (!this.changes.contains(EmBillMain.back_calctype)) {
			this.changes.add(EmBillMain.back_calctype);
		}
	}

	/**
	 * 分区编号
	 * 
	 * @return
	 */
	public String getPartition_id() {
		return partition_id;
	}

	/**
	 * 分区编号
	 * 
	 * @param partition_id
	 */
	public void setPartition_id(String partition_id) {
		this.partition_id = partition_id;
		if (!this.changes.contains(EmBillMain.partition_id)) {
			this.changes.add(EmBillMain.partition_id);
		}
	}
}
