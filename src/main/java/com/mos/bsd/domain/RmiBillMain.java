package com.mos.bsd.domain;

import java.io.Serializable;

/**
 * @Description 单据提交单据主单
 * @author odin
 * @date 2017年5月9日 下午10:43:57
 */
public class RmiBillMain implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;

	/** 库存中心单据系统编号 */
	private String bill_id;
	/** X2单据系统编号 */
	private String x2_bill_id;
	/** X2单据编号 */
	private String bill_code;
	/** X2参照单据编号 */
	private String bill_czid;
	/** 库存中心参照单号 */
	private String reference_id;
	/** 冲单原单号 */
	private String release_id;
	/** 价格促销单号 */
	private String price_billsid;
	/** 品牌集合编号 */
	private String brandgroup_code;
	/** 订金扣减比例 */
	private double deposit_pec;
	/** 发货仓店编号 */
	private String set_depot_id;
	/** 收货仓店编号 */
	private String get_depot_id;
	/** 发货地仓渠道编号 */
	private String set_organ_id;
	/** 收货地仓渠道编号 */
	private String get_organ_id;
	/** 单据系统类型 */
	private int system_type;
	/** 提交日期 */
	private String bill_submit_date;
	/** 单据提交人 */
	private String bill_submit_name;
	/** 记账日期 */
	private String account_date;
	/** 标识此单为结案单据 */
	private int bill_over = 0;
	/** 结案人 */
	private String over_name;
	/** 结案日期 */
	private String over_date;
	/** 销售类型编号 */
	private String selltype_id;
	/** 收货销售类型编号（用于跨区调拨） */
	private String get_selltype_id;
	/** 单据备注 */
	private String bill_remark;
	/** 建立人 */
	private String create_name;
	/** 建立日期 */
	private String create_date;
	/** 业务类型：1加盟商大宗订单，2零售订单（按销售方计收入），3调拨指定店，按此店计销售 */
	private int bill_type;
	/** VIP编号 */
	private String vip_id;
	/** VIP卡号 */
	private String vip_cardid;
	/** 天气 */
	private String bill_weather;
	/** 销售店铺所属公司 */
	private String dpt_company_id;
	/** 销售店铺所属集合 */
	private String depot_group;
	/** 销售店铺所属政策分组 */
	private String depot_brandcompany;
	/** VIP归属店铺 */
	private String vip_depot;
	/** VIP归属店铺所属分组 */
	private String vip_group;
	/** VIP归属店铺所属公司 */
	private String vip_company_id;
	/** VIP归属店铺所属政策分组 */
	private String vip_brandcompany;
	/** 积分结算系数 */
	private double score_coefficient;
	/** 积分来源 */
	private String score_source;
	/** 单据结算金额 */
	private double ssums;
	/** 提交次序，用于多单提交时，有先后顺序时使用，系统按 system_type + submit_order 值的正序提交 */
	private int submit_order = 0;
	/** 发货地上级 */
	private long set_department_parent_id = 0;
	/** 收货地上级 */
	private long get_department_parent_id = 0;

	// #region 保存D表需要的数据

	/** 单据日期 */
	private String bill_setdate;
	/** 金额入账 */
	private int bill_sum_sure;
	/** 金额入账人 */
	private String bill_sum_surename;
	/** 金额入账日期 */
	private String bill_sum_suredate;
	/** 单据结账 */
	private int bill_lock;
	/** 结账人 */
	private String bill_lockname;
	/** 结账日期 */
	private String bill_lockdate;
	/** 状态 */
	private int bill_state;
	/** 发货备注 */
	private String bill_sellremark;
	/** 订金扣减比率 */
	private int bill_deposit_pec;
	/** 参考单号 */
	private String bill_refid;
	/** 单据类型识别码 */
	private String bill_idcode;
	/** 单据类型 */
	private String bill_selltype;
	/** 数量入账 */
	private int bill_num_sure;
	/** 数量入账人 */
	private String bill_num_surename;
	/** 数量入账日期 */
	private String bill_num_suredate;
	/** 单据建立机构 */
	private long cr_department_id;
	/** 单据处理标记 */
	private int bill_editsign;
	/** 数量收货入账 */
	private int bill_num_getsure;
	/** 数量收货入账人 */
	private String bill_num_getsurename;
	/** 数量收货入账日期 */
	private String bill_num_getsuredate;
	/** 金额收货入账 */
	private int bill_sum_getsure;
	/** 金额收货入账人 */
	private String bill_sum_getsurename;
	/** 金额收货入账日期 */
	private String bill_sum_getsuredate;
	/** 收货吊牌金额 */
	private double bill_getjsum;
	/** 收货现价金额 */
	private double bill_getxsum;
	/** 收货结算金额 */
	private double bill_getssum;
	/** 班次 */
	private long bill_shift_id;
	/** 提醒日期 */
	private String bill_reminddate;
	/** 单据数量 */
	private double bill_num;
	/** 成本金额 */
	private double bill_csum;
	/** 吊牌金额 */
	private double bill_jsum;
	/** 现价金额 */
	private double bill_xsum;
	/** 款式编号 */
	private String style_id;
	/** 借款申请单 */
	private long loan_id;
	/** 收货数量 */
	private double bill_getnum;
	/** 多收处理方式 */
	private int bill_more_deal;
	/** 少收处理方式 */
	private int bill_less_deal;
	/** 实销金额 */
	private double bill_fsums;
	/** 收货差异 */
	private int bill_diff;
	/** 消费税率 */
	private double bill_consumertax_rate;
	/** 运费 */
	private double bill_freight;
	/** 佣金提成 */
	private double commission_rate;
	/** 消费税起征数量 */
	private double bill_consumertax_num;
	/** 收回通知标识 */
	private int bill_allot_sign;
	/** 差异处理方式 */
	private int bill_diff_type;
	/** 差异处理人 */
	private String bill_diff_name;
	/** 差异处理日期 */
	private String bill_diff_date;
	/** 处理单据编号 */
	private String bill_diff_bill_id;
	/** 处理备注 */
	private String bill_diff_remark;
	/** 货币代码 */
	private String bill_currency_code;
	/** 汇率 */
	private double bill_exchange_rate;
	/** 退货计算机构 */
	private long return_department_id;
	/** 原数量 */
	private double bill_cnum;
	/** 打印次数 */
	private int bill_print_times;
	/** 收货备注 */
	private String bill_get_remark;
	/** 借调部门 */
	private String move_dpt;
	/** ESB数据来源 */
	private String esb_source;
	/** 是否店长授权单据 */
	private int bill_issoauth;
	/** 收货记账日期 */
	private String bill_get_accountdate;
	/** 供货单据类型 */
	private int bill_supply_type;
	/** 供货来源单据 */
	private long bill_supply_for;
	/** 收货是否需要传SAP */
	private int bill_get_tosap;
	/** 单据自编号 */
	private String bill_selfcode;
	/** 款式分类 */
	private String bill_style_sort;
	/** 是否需要传WMS */
	private int bill_towms;
	/** 是否需要传SAP */
	private int bill_tosap;
	/** 差异处理单据 */
	private int bill_isadjust;
	/** 差异来源单据 */
	private long bill_adjust_for;
	/** 同步标识 */
	private int bill_sync;
	/** 库存已更新 */
	private int stock_calced;
	/** 订单中心订单外部编号 */
	private String bill_order_id;
	/** 单据扩展分类1 */
	private String ex_class1;
	/** 单据扩展分类2 */
	private String ex_class2;
	/** 单据扩展分类3 */
	private String ex_class3;
	/** 单据扩展分类4 */
	private String ex_class4;
	/** 加盟政策促销活动编号 */
	private long bill_promotion_id;
	/** 退货收货问题原因 */
	private String bill_return_reason;
	/** 退货收货处理结果 */
	private String bill_return_deal;
	/** 是否收货 */
	private int is_get;
	/** 冲单单据类型 0 原单 1 冲单负单 2 冲单补充单 */
	private int original_type;
	/** 冲单原单号 */
	private String original_id;
	/** 冲单状态 */
	private int original_state;
	/** 冲单原单顶级单号 */
	private long orginal_topid;
	/** 退货计算类型: 1正常退货计算逻辑，2特殊退货计算逻辑 */
	private int back_calctype;
	/** 分区编号 */
	public String partition_id;
	// #endregion

	// #region setter getter

	/**
	 * @return 获取库存中心单据系统编号
	 */
	public String getBill_id() {
		return bill_id;
	}

	/**
	 * @param 设置库存中心单据系统编号
	 */
	public void setBill_id(String bill_id) {
		this.bill_id = bill_id;
	}

	/**
	 * @return 获取X2单据系统编号
	 */
	public String getX2_bill_id() {
		return x2_bill_id;
	}

	/**
	 * @param 设置X2单据系统编号
	 */
	public void setX2_bill_id(String x2_bill_id) {
		this.x2_bill_id = x2_bill_id;
	}

	/**
	 * @return 获取X2单据编号
	 */
	public String getBill_code() {
		return bill_code;
	}

	/**
	 * @param 设置X2单据编号
	 */
	public void setBill_code(String bill_code) {
		this.bill_code = bill_code;
	}

	/**
	 * @return 获取X2参照单据编号
	 */
	public String getBill_czid() {
		return bill_czid;
	}

	/**
	 * @param 设置X2参照单据编号
	 */
	public void setBill_czid(String bill_czid) {
		this.bill_czid = bill_czid;
	}

	/**
	 * @return 获取库存中心参照单号
	 */
	public String getReference_id() {
		return reference_id;
	}

	/**
	 * @param 设置库存中心参照单号
	 */
	public void setReference_id(String reference_id) {
		this.reference_id = reference_id;
	}

	/**
	 * @return 获取冲单原单号
	 */
	public String getRelease_id() {
		return release_id;
	}

	/**
	 * @param 设置冲单原单号
	 */
	public void setRelease_id(String release_id) {
		this.release_id = release_id;
	}

	/**
	 * @return 获取价格促销单号
	 */
	public String getPrice_billsid() {
		return price_billsid;
	}

	/**
	 * @param 设置价格促销单号
	 */
	public void setPrice_billsid(String price_billsid) {
		this.price_billsid = price_billsid;
	}

	/**
	 * @return 获取品牌集合编号
	 */
	public String getBrandgroup_code() {
		return brandgroup_code;
	}

	/**
	 * @param 设置品牌集合编号
	 */
	public void setBrandgroup_code(String brandgroup_code) {
		this.brandgroup_code = brandgroup_code;
	}

	/**
	 * @return 获取订金扣减比例
	 */
	public double getDeposit_pec() {
		return deposit_pec;
	}

	/**
	 * @param 设置订金扣减比例
	 */
	public void setDeposit_pec(double deposit_pec) {
		this.deposit_pec = deposit_pec;
	}

	/**
	 * @return 获取发货仓店编号
	 */
	public String getSet_depot_id() {
		return set_depot_id;
	}

	/**
	 * @param 设置发货仓店编号
	 */
	public void setSet_depot_id(String set_depot_id) {
		this.set_depot_id = set_depot_id;
	}

	/**
	 * @return 获取收货仓店编号
	 */
	public String getGet_depot_id() {
		return get_depot_id;
	}

	/**
	 * @param 设置收货仓店编号
	 */
	public void setGet_depot_id(String get_depot_id) {
		this.get_depot_id = get_depot_id;
	}

	/**
	 * @return 获取发货地仓渠道编号
	 */
	public String getSet_organ_id() {
		return set_organ_id;
	}

	/**
	 * @param 设置发货地仓渠道编号
	 */
	public void setSet_organ_id(String set_organ_id) {
		this.set_organ_id = set_organ_id;
	}

	/**
	 * @return 获取收货地仓渠道编号
	 */
	public String getGet_organ_id() {
		return get_organ_id;
	}

	/**
	 * @param 设置收货地仓渠道编号
	 */
	public void setGet_organ_id(String get_organ_id) {
		this.get_organ_id = get_organ_id;
	}

	/**
	 * @return 获取单据系统类型
	 */
	public int getSystem_type() {
		return system_type;
	}

	/**
	 * @param 设置单据系统类型
	 */
	public void setSystem_type(int system_type) {
		this.system_type = system_type;
	}

	/**
	 * @return 获取提交日期
	 */
	public String getBill_submit_date() {
		return bill_submit_date;
	}

	/**
	 * @param 设置提交日期
	 */
	public void setBill_submit_date(String bill_submit_date) {
		this.bill_submit_date = bill_submit_date;
	}

	/**
	 * @return 获取单据提交人
	 */
	public String getBill_submit_name() {
		return bill_submit_name;
	}

	/**
	 * @param 设置单据提交人
	 */
	public void setBill_submit_name(String bill_submit_name) {
		this.bill_submit_name = bill_submit_name;
	}

	/**
	 * @return 获取记账日期
	 */
	public String getAccount_date() {
		return account_date;
	}

	/**
	 * @param 设置记账日期
	 */
	public void setAccount_date(String account_date) {
		this.account_date = account_date;
	}

	/**
	 * @return 获取标识此单为结案单据
	 */
	public int getBill_over() {
		return bill_over;
	}

	/**
	 * @param 设置标识此单为结案单据
	 */
	public void setBill_over(int bill_over) {
		this.bill_over = bill_over;
	}

	/**
	 * @return 获取结案人
	 */
	public String getOver_name() {
		return over_name;
	}

	/**
	 * @param 设置结案人
	 */
	public void setOver_name(String over_name) {
		this.over_name = over_name;
	}

	/**
	 * @return 获取结案日期
	 */
	public String getOver_date() {
		return over_date;
	}

	/**
	 * @param 设置结案日期
	 */
	public void setOver_date(String over_date) {
		this.over_date = over_date;
	}

	/**
	 * @return 获取销售类型编号
	 */
	public String getSelltype_id() {
		return selltype_id;
	}

	/**
	 * @param 设置销售类型编号
	 */
	public void setSelltype_id(String selltype_id) {
		this.selltype_id = selltype_id;
	}

	/**
	 * @return 获取收货销售类型编号（用于跨区调拨）
	 */
	public String getGet_selltype_id() {
		return get_selltype_id;
	}

	/**
	 * @param 设置收货销售类型编号（用于跨区调拨）
	 */
	public void setGet_selltype_id(String get_selltype_id) {
		this.get_selltype_id = get_selltype_id;
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
	}

	/**
	 * @return 获取建立人
	 */
	public String getCreate_name() {
		return create_name;
	}

	/**
	 * @param 设置建立人
	 */
	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}

	/**
	 * @return 获取建立日期
	 */
	public String getCreate_date() {
		return create_date;
	}

	/**
	 * @param 设置建立日期
	 */
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	/**
	 * @return 获取业务类型：1加盟商大宗订单，2零售订单（按销售方计收入），3调拨指定店，按此店计销售
	 */
	public int getBill_type() {
		return bill_type;
	}

	/**
	 * @param 设置业务类型：1加盟商大宗订单，2零售订单（按销售方计收入），3调拨指定店，按此店计销售
	 */
	public void setBill_type(int bill_type) {
		this.bill_type = bill_type;
	}

	/**
	 * @return 获取VIP编号
	 */
	public String getVip_id() {
		return vip_id;
	}

	/**
	 * @param 设置VIP编号
	 */
	public void setVip_id(String vip_id) {
		this.vip_id = vip_id;
	}

	/**
	 * @return 获取VIP卡号
	 */
	public String getVip_cardid() {
		return vip_cardid;
	}

	/**
	 * @param 设置VIP卡号
	 */
	public void setVip_cardid(String vip_cardid) {
		this.vip_cardid = vip_cardid;
	}

	/**
	 * @return 获取天气
	 */
	public String getBill_weather() {
		return bill_weather;
	}

	/**
	 * @param 设置天气
	 */
	public void setBill_weather(String bill_weather) {
		this.bill_weather = bill_weather;
	}

	/**
	 * @return 获取销售店铺所属公司
	 */
	public String getDpt_company_id() {
		return dpt_company_id;
	}

	/**
	 * @param 设置销售店铺所属公司
	 */
	public void setDpt_company_id(String dpt_company_id) {
		this.dpt_company_id = dpt_company_id;
	}

	/**
	 * @return 获取销售店铺所属集合
	 */
	public String getDepot_group() {
		return depot_group;
	}

	/**
	 * @param 设置销售店铺所属集合
	 */
	public void setDepot_group(String depot_group) {
		this.depot_group = depot_group;
	}

	/**
	 * @return 获取销售店铺所属政策分组
	 */
	public String getDepot_brandcompany() {
		return depot_brandcompany;
	}

	/**
	 * @param 设置销售店铺所属政策分组
	 */
	public void setDepot_brandcompany(String depot_brandcompany) {
		this.depot_brandcompany = depot_brandcompany;
	}

	/**
	 * @return 获取VIP归属店铺
	 */
	public String getVip_depot() {
		return vip_depot;
	}

	/**
	 * @param 设置VIP归属店铺
	 */
	public void setVip_depot(String vip_depot) {
		this.vip_depot = vip_depot;
	}

	/**
	 * @return 获取VIP归属店铺所属分组
	 */
	public String getVip_group() {
		return vip_group;
	}

	/**
	 * @param 设置VIP归属店铺所属分组
	 */
	public void setVip_group(String vip_group) {
		this.vip_group = vip_group;
	}

	/**
	 * @return 获取VIP归属店铺所属公司
	 */
	public String getVip_company_id() {
		return vip_company_id;
	}

	/**
	 * @param 设置VIP归属店铺所属公司
	 */
	public void setVip_company_id(String vip_company_id) {
		this.vip_company_id = vip_company_id;
	}

	/**
	 * @return 获取VIP归属店铺所属政策分组
	 */
	public String getVip_brandcompany() {
		return vip_brandcompany;
	}

	/**
	 * @param 设置VIP归属店铺所属政策分组
	 */
	public void setVip_brandcompany(String vip_brandcompany) {
		this.vip_brandcompany = vip_brandcompany;
	}

	/**
	 * @return 获取积分结算系数
	 */
	public double getScore_coefficient() {
		return score_coefficient;
	}

	/**
	 * @param 设置积分结算系数
	 */
	public void setScore_coefficient(double score_coefficient) {
		this.score_coefficient = score_coefficient;
	}

	/**
	 * @return 获取积分来源
	 */
	public String getScore_source() {
		return score_source;
	}

	/**
	 * @param 设置积分来源
	 */
	public void setScore_source(String score_source) {
		this.score_source = score_source;
	}

	/**
	 * @return 获取单据结算金额
	 */
	public double getSsums() {
		return ssums;
	}

	/**
	 * @param 设置单据结算金额
	 */
	public void setSsums(double ssums) {
		this.ssums = ssums;
	}

	/**
	 * @return 获取提交次序，用于多单提交时，有先后顺序时使用，系统按 system_type + submit_order 值的正序提交
	 */
	public int getSubmit_order() {
		return submit_order;
	}

	/**
	 * @param 设置提交次序，用于多单提交时，有先后顺序时使用，系统按
	 *            system_type + submit_order 值的正序提交
	 */
	public void setSubmit_order(int submit_order) {
		this.submit_order = submit_order;
	}

	/**
	 * @return 获取#region
	 */
	public String getBill_setdate() {
		return bill_setdate;
	}

	/**
	 * @param 设置#region
	 */
	public void setBill_setdate(String bill_setdate) {
		this.bill_setdate = bill_setdate;
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
	}

	/**
	 * @return 获取金额入账日期
	 */
	public String getBill_sum_suredate() {
		return bill_sum_suredate;
	}

	/**
	 * @param 设置金额入账日期
	 */
	public void setBill_sum_suredate(String bill_sum_suredate) {
		this.bill_sum_suredate = bill_sum_suredate;
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
	}

	/**
	 * @return 获取结账日期
	 */
	public String getBill_lockdate() {
		return bill_lockdate;
	}

	/**
	 * @param 设置结账日期
	 */
	public void setBill_lockdate(String bill_lockdate) {
		this.bill_lockdate = bill_lockdate;
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
	}

	/**
	 * @return 获取订金扣减比率
	 */
	public int getBill_deposit_pec() {
		return bill_deposit_pec;
	}

	/**
	 * @param 设置订金扣减比率
	 */
	public void setBill_deposit_pec(int bill_deposit_pec) {
		this.bill_deposit_pec = bill_deposit_pec;
	}

	/**
	 * @return 获取参考单号
	 */
	public String getBill_refid() {
		return bill_refid;
	}

	/**
	 * @param 设置参考单号
	 */
	public void setBill_refid(String bill_refid) {
		this.bill_refid = bill_refid;
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
	}

	/**
	 * @return 获取数量入账日期
	 */
	public String getBill_num_suredate() {
		return bill_num_suredate;
	}

	/**
	 * @param 设置数量入账日期
	 */
	public void setBill_num_suredate(String bill_num_suredate) {
		this.bill_num_suredate = bill_num_suredate;
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
	}

	/**
	 * @return 获取数量收货入账日期
	 */
	public String getBill_num_getsuredate() {
		return bill_num_getsuredate;
	}

	/**
	 * @param 设置数量收货入账日期
	 */
	public void setBill_num_getsuredate(String bill_num_getsuredate) {
		this.bill_num_getsuredate = bill_num_getsuredate;
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
	}

	/**
	 * @return 获取金额收货入账日期
	 */
	public String getBill_sum_getsuredate() {
		return bill_sum_getsuredate;
	}

	/**
	 * @param 设置金额收货入账日期
	 */
	public void setBill_sum_getsuredate(String bill_sum_getsuredate) {
		this.bill_sum_getsuredate = bill_sum_getsuredate;
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
	}

	/**
	 * @return 获取提醒日期
	 */
	public String getBill_reminddate() {
		return bill_reminddate;
	}

	/**
	 * @param 设置提醒日期
	 */
	public void setBill_reminddate(String bill_reminddate) {
		this.bill_reminddate = bill_reminddate;
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
	}

	/**
	 * @return 获取差异处理日期
	 */
	public String getBill_diff_date() {
		return bill_diff_date;
	}

	/**
	 * @param 设置差异处理日期
	 */
	public void setBill_diff_date(String bill_diff_date) {
		this.bill_diff_date = bill_diff_date;
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
	}

	/**
	 * @return 获取ESB数据来源
	 */
	public String getEsb_source() {
		return esb_source;
	}

	/**
	 * @param 设置ESB数据来源
	 */
	public void setEsb_source(String esb_source) {
		this.esb_source = esb_source;
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
	}

	/**
	 * @return 获取收货记账日期
	 */
	public String getBill_get_accountdate() {
		return bill_get_accountdate;
	}

	/**
	 * @param 设置收货记账日期
	 */
	public void setBill_get_accountdate(String bill_get_accountdate) {
		this.bill_get_accountdate = bill_get_accountdate;
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
	}

	/**
	 * @return 获取收货是否需要传SAP
	 */
	public int getBill_get_tosap() {
		return bill_get_tosap;
	}

	/**
	 * @param 设置收货是否需要传SAP
	 */
	public void setBill_get_tosap(int bill_get_tosap) {
		this.bill_get_tosap = bill_get_tosap;
	}

	/**
	 * @return 获取单据自编号
	 */
	public String getBill_selfcode() {
		return bill_selfcode;
	}

	/**
	 * @param 设置单据自编号
	 */
	public void setBill_selfcode(String bill_selfcode) {
		this.bill_selfcode = bill_selfcode;
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
	}

	/**
	 * @return 获取是否需要传WMS
	 */
	public int getBill_towms() {
		return bill_towms;
	}

	/**
	 * @param 设置是否需要传WMS
	 */
	public void setBill_towms(int bill_towms) {
		this.bill_towms = bill_towms;
	}

	/**
	 * @return 获取是否需要传SAP
	 */
	public int getBill_tosap() {
		return bill_tosap;
	}

	/**
	 * @param 设置是否需要传SAP
	 */
	public void setBill_tosap(int bill_tosap) {
		this.bill_tosap = bill_tosap;
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
	}

	/**
	 * @return 获取加盟政策促销活动编号
	 */
	public long getBill_promotion_id() {
		return bill_promotion_id;
	}

	/**
	 * @param 设置加盟政策促销活动编号
	 */
	public void setBill_promotion_id(long bill_promotion_id) {
		this.bill_promotion_id = bill_promotion_id;
	}

	/**
	 * @return 获取退货收货问题原因
	 */
	public String getBill_return_reason() {
		return bill_return_reason;
	}

	/**
	 * @param 设置退货收货问题原因
	 */
	public void setBill_return_reason(String bill_return_reason) {
		this.bill_return_reason = bill_return_reason;
	}

	/**
	 * @return 获取退货收货处理结果
	 */
	public String getBill_return_deal() {
		return bill_return_deal;
	}

	/**
	 * @param 设置退货收货处理结果
	 */
	public void setBill_return_deal(String bill_return_deal) {
		this.bill_return_deal = bill_return_deal;
	}

	/**
	 * @return 获取是否收货
	 */
	public int getIs_get() {
		return is_get;
	}

	/**
	 * @param 设置是否收货
	 */
	public void setIs_get(int is_get) {
		this.is_get = is_get;
	}

	/**
	 * @return 获取冲单单据类型 0 原单 1 冲单负单 2 冲单补充单
	 */
	public int getOriginal_type() {
		return original_type;
	}

	/**
	 * @param 设置冲单单据类型
	 *            0 原单 1 冲单负单 2 冲单补充单
	 */
	public void setOriginal_type(int original_type) {
		this.original_type = original_type;
	}

	/**
	 * @return 获取冲单原单号
	 */
	public String getOriginal_id() {
		return original_id;
	}

	/**
	 * @param 设置冲单原单号
	 */
	public void setOriginal_id(String original_id) {
		this.original_id = original_id;
	}

	/**
	 * @return 获取冲单状态
	 */
	public int getOriginal_state() {
		return original_state;
	}

	/**
	 * @param 设置冲单状态
	 */
	public void setOriginal_state(int original_state) {
		this.original_state = original_state;
	}

	/**
	 * @return 获取冲单原单顶级单号
	 */
	public long getOrginal_topid() {
		return orginal_topid;
	}

	/**
	 * @param 设置冲单原单顶级单号
	 */
	public void setOrginal_topid(long orginal_topid) {
		this.orginal_topid = orginal_topid;
	}

	/**
	 * @return 获取发货地上级
	 */
	public long getSet_department_parent_id() {
		return set_department_parent_id;
	}

	/**
	 * @param 设置发货地上级
	 */
	public void setSet_department_parent_id(long set_department_parent_id) {
		this.set_department_parent_id = set_department_parent_id;
	}

	/**
	 * @return 获取收货地上级
	 */
	public long getGet_department_parent_id() {
		return get_department_parent_id;
	}

	/**
	 * @param 设置收货地上级
	 */
	public void setGet_department_parent_id(long get_department_parent_id) {
		this.get_department_parent_id = get_department_parent_id;
	}

	/**
	 * 退货计算类型: 1正常退货计算逻辑，2特殊退货计算逻辑
	 * @return
	 */
	public int getBack_calctype() {
		return back_calctype;
	}
	
	/**
	 * 退货计算类型: 1正常退货计算逻辑，2特殊退货计算逻辑
	 * @param back_calctype
	 */
	public void setBack_calctype(int back_calctype) {
		this.back_calctype = back_calctype;
	}
	
	/**
	 * 分区编号
	 * @return
	 */
	public String getPartition_id() {
		return partition_id;
	}
	
	/**
	 * 分区编号
	 * @param partition_id
	 */
	public void setPartition_id(String partition_id) {
		this.partition_id = partition_id;
	}
	
	// #endregion
}
