package com.mos.bsd.domain;

import java.io.Serializable;

/**
 * @Description 零售单扩展信息
 * @author odin
 * @date 2017年5月10日 上午11:25:27
 */
public class BillRetailExpandInfo implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;

	/** 零售单号 */
	public long bills_id;
	/** 付款金额 */
	public double bill_pay;
	/** 找零 */
	public double bill_change;
	/** VIP编号 */
	public String bill_vip_id;
	/** VIP卡号 */
	public String bill_vipcard_id;
	/** 是否已计算积分 */
	public int bill_vip_calc;
	/** 兑现扣减积分 */
	public double bill_vip_exchange;
	/** VIP参数 */
	public String bill_vip_param;
	/** 生日优惠 */
	public int bill_vip_birthday;
	/** 实销已计算 */
	public int bill_fsums_calc;
	/** 券号 */
	public String bill_coupon_code;
	/** 天气 */
	public String bill_weather;
	/** 四舍五入金额 */
	public double bill_round;
	/** 商场卡号 */
	public String bill_market_card_id;
	/** 自动供货状态 */
	public int bill_autosupply;
	/** 自动供货店铺 */
	public int department_autosupply_id;
	/** 微信用户编号 */
	public String bill_openid;
	/** 物流公司编号 */
	public String bill_logistics_code;
	/** 物流单号 */
	public String bill_logistics_billid;
	/** 重量 */
	public double bill_weight;
	/** 是否开票 */
	public int bill_isinvoice;
	/** 物流方式 */
	public String bill_logistics_type;
	/** 本单之前有效积分 */
	public double bill_old_score;
	/** 储值券号 */
	public String bill_coupon_code1;
	/** 支付状态 */
	public int pay_status;
	/** 支付方式 */
	public int pay_type;
	/** 支付交易号 */
	public String pay_transaction_id;
	/** 支付APP_ID */
	public String pay_appid;
	/** 商户号（微信） */
	public String wechat_account;
	/** 会员专属导购编号 */
	public long bill_vip_extend_id;
	/** 物流服务导购编号 */
	public long bill_send_clerk_id;
	/** 零售单同单号 */
	public long bill_same_id;
	/** 本单VIP不打折 */
	public int bill_vipnorate;
	/** 本单VIP不积分 */
	public int bill_vipnocentum;
	/** 老VIP编号 */
	public long bill_old_vipid;
	/** 打折退货原因 */
	public String bill_back_reason;
	/** 打折退货原因详情 */
	public String bill_back_reasonsub;
	/** 刷卡卡号 */
	public String bill_bank_cardid;
	/** 刷卡金额 */
	public double bill_bank_sums;
	/** 刷卡检索号 */
	public String bill_bank_sn;
	/** 商城单号 */
	public String bill_platform_id;
	/** 商城申请单号 */
	public String bill_source_id;
	/** 库存中心单号 */
	public String stock_bills_id;
	/** 支付宝类型 */
	public int bill_aliapy_type;
	/** 是否补录 */
	public int bill_makeup;
	/** 储值卡号 */
	public String bill_iccard_id;

	/**
	 * @return 获取零售单号
	 */
	public long getBills_id() {
		return bills_id;
	}

	/**
	 * @param 设置零售单号
	 */
	public void setBills_id(long bills_id) {
		this.bills_id = bills_id;
	}

	/**
	 * @return 获取付款金额
	 */
	public double getBill_pay() {
		return bill_pay;
	}

	/**
	 * @param 设置付款金额
	 */
	public void setBill_pay(double bill_pay) {
		this.bill_pay = bill_pay;
	}

	/**
	 * @return 获取找零
	 */
	public double getBill_change() {
		return bill_change;
	}

	/**
	 * @param 设置找零
	 */
	public void setBill_change(double bill_change) {
		this.bill_change = bill_change;
	}

	/**
	 * @return 获取VIP编号
	 */
	public String getBill_vip_id() {
		return bill_vip_id;
	}

	/**
	 * @param 设置VIP编号
	 */
	public void setBill_vip_id(String bill_vip_id) {
		this.bill_vip_id = bill_vip_id;
	}

	/**
	 * @return 获取VIP卡号
	 */
	public String getBill_vipcard_id() {
		return bill_vipcard_id;
	}

	/**
	 * @param 设置VIP卡号
	 */
	public void setBill_vipcard_id(String bill_vipcard_id) {
		this.bill_vipcard_id = bill_vipcard_id;
	}

	/**
	 * @return 获取是否已计算积分
	 */
	public int getBill_vip_calc() {
		return bill_vip_calc;
	}

	/**
	 * @param 设置是否已计算积分
	 */
	public void setBill_vip_calc(int bill_vip_calc) {
		this.bill_vip_calc = bill_vip_calc;
	}

	/**
	 * @return 获取兑现扣减积分
	 */
	public double getBill_vip_exchange() {
		return bill_vip_exchange;
	}

	/**
	 * @param 设置兑现扣减积分
	 */
	public void setBill_vip_exchange(double bill_vip_exchange) {
		this.bill_vip_exchange = bill_vip_exchange;
	}

	/**
	 * @return 获取VIP参数
	 */
	public String getBill_vip_param() {
		return bill_vip_param;
	}

	/**
	 * @param 设置VIP参数
	 */
	public void setBill_vip_param(String bill_vip_param) {
		this.bill_vip_param = bill_vip_param;
	}

	/**
	 * @return 获取生日优惠
	 */
	public int getBill_vip_birthday() {
		return bill_vip_birthday;
	}

	/**
	 * @param 设置生日优惠
	 */
	public void setBill_vip_birthday(int bill_vip_birthday) {
		this.bill_vip_birthday = bill_vip_birthday;
	}

	/**
	 * @return 获取实销已计算
	 */
	public int getBill_fsums_calc() {
		return bill_fsums_calc;
	}

	/**
	 * @param 设置实销已计算
	 */
	public void setBill_fsums_calc(int bill_fsums_calc) {
		this.bill_fsums_calc = bill_fsums_calc;
	}

	/**
	 * @return 获取券号
	 */
	public String getBill_coupon_code() {
		return bill_coupon_code;
	}

	/**
	 * @param 设置券号
	 */
	public void setBill_coupon_code(String bill_coupon_code) {
		this.bill_coupon_code = bill_coupon_code;
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
	 * @return 获取四舍五入金额
	 */
	public double getBill_round() {
		return bill_round;
	}

	/**
	 * @param 设置四舍五入金额
	 */
	public void setBill_round(double bill_round) {
		this.bill_round = bill_round;
	}

	/**
	 * @return 获取商场卡号
	 */
	public String getBill_market_card_id() {
		return bill_market_card_id;
	}

	/**
	 * @param 设置商场卡号
	 */
	public void setBill_market_card_id(String bill_market_card_id) {
		this.bill_market_card_id = bill_market_card_id;
	}

	/**
	 * @return 获取自动供货状态
	 */
	public int getBill_autosupply() {
		return bill_autosupply;
	}

	/**
	 * @param 设置自动供货状态
	 */
	public void setBill_autosupply(int bill_autosupply) {
		this.bill_autosupply = bill_autosupply;
	}

	/**
	 * @return 获取自动供货店铺
	 */
	public int getDepartment_autosupply_id() {
		return department_autosupply_id;
	}

	/**
	 * @param 设置自动供货店铺
	 */
	public void setDepartment_autosupply_id(int department_autosupply_id) {
		this.department_autosupply_id = department_autosupply_id;
	}

	/**
	 * @return 获取微信用户编号
	 */
	public String getBill_openid() {
		return bill_openid;
	}

	/**
	 * @param 设置微信用户编号
	 */
	public void setBill_openid(String bill_openid) {
		this.bill_openid = bill_openid;
	}

	/**
	 * @return 获取物流公司编号
	 */
	public String getBill_logistics_code() {
		return bill_logistics_code;
	}

	/**
	 * @param 设置物流公司编号
	 */
	public void setBill_logistics_code(String bill_logistics_code) {
		this.bill_logistics_code = bill_logistics_code;
	}

	/**
	 * @return 获取物流单号
	 */
	public String getBill_logistics_billid() {
		return bill_logistics_billid;
	}

	/**
	 * @param 设置物流单号
	 */
	public void setBill_logistics_billid(String bill_logistics_billid) {
		this.bill_logistics_billid = bill_logistics_billid;
	}

	/**
	 * @return 获取重量
	 */
	public double getBill_weight() {
		return bill_weight;
	}

	/**
	 * @param 设置重量
	 */
	public void setBill_weight(double bill_weight) {
		this.bill_weight = bill_weight;
	}

	/**
	 * @return 获取是否开票
	 */
	public int getBill_isinvoice() {
		return bill_isinvoice;
	}

	/**
	 * @param 设置是否开票
	 */
	public void setBill_isinvoice(int bill_isinvoice) {
		this.bill_isinvoice = bill_isinvoice;
	}

	/**
	 * @return 获取物流方式
	 */
	public String getBill_logistics_type() {
		return bill_logistics_type;
	}

	/**
	 * @param 设置物流方式
	 */
	public void setBill_logistics_type(String bill_logistics_type) {
		this.bill_logistics_type = bill_logistics_type;
	}

	/**
	 * @return 获取本单之前有效积分
	 */
	public double getBill_old_score() {
		return bill_old_score;
	}

	/**
	 * @param 设置本单之前有效积分
	 */
	public void setBill_old_score(double bill_old_score) {
		this.bill_old_score = bill_old_score;
	}

	/**
	 * @return 获取储值券号
	 */
	public String getBill_coupon_code1() {
		return bill_coupon_code1;
	}

	/**
	 * @param 设置储值券号
	 */
	public void setBill_coupon_code1(String bill_coupon_code1) {
		this.bill_coupon_code1 = bill_coupon_code1;
	}

	/**
	 * @return 获取支付状态
	 */
	public int getPay_status() {
		return pay_status;
	}

	/**
	 * @param 设置支付状态
	 */
	public void setPay_status(int pay_status) {
		this.pay_status = pay_status;
	}

	/**
	 * @return 获取支付方式
	 */
	public int getPay_type() {
		return pay_type;
	}

	/**
	 * @param 设置支付方式
	 */
	public void setPay_type(int pay_type) {
		this.pay_type = pay_type;
	}

	/**
	 * @return 获取支付交易号
	 */
	public String getPay_transaction_id() {
		return pay_transaction_id;
	}

	/**
	 * @param 设置支付交易号
	 */
	public void setPay_transaction_id(String pay_transaction_id) {
		this.pay_transaction_id = pay_transaction_id;
	}

	/**
	 * @return 获取支付APP_ID
	 */
	public String getPay_appid() {
		return pay_appid;
	}

	/**
	 * @param 设置支付APP_ID
	 */
	public void setPay_appid(String pay_appid) {
		this.pay_appid = pay_appid;
	}

	/**
	 * @return 获取商户号（微信）
	 */
	public String getWechat_account() {
		return wechat_account;
	}

	/**
	 * @param 设置商户号（微信）
	 */
	public void setWechat_account(String wechat_account) {
		this.wechat_account = wechat_account;
	}

	/**
	 * @return 获取会员专属导购编号
	 */
	public long getBill_vip_extend_id() {
		return bill_vip_extend_id;
	}

	/**
	 * @param 设置会员专属导购编号
	 */
	public void setBill_vip_extend_id(long bill_vip_extend_id) {
		this.bill_vip_extend_id = bill_vip_extend_id;
	}

	/**
	 * @return 获取物流服务导购编号
	 */
	public long getBill_send_clerk_id() {
		return bill_send_clerk_id;
	}

	/**
	 * @param 设置物流服务导购编号
	 */
	public void setBill_send_clerk_id(long bill_send_clerk_id) {
		this.bill_send_clerk_id = bill_send_clerk_id;
	}

	/**
	 * @return 获取零售单同单号
	 */
	public long getBill_same_id() {
		return bill_same_id;
	}

	/**
	 * @param 设置零售单同单号
	 */
	public void setBill_same_id(long bill_same_id) {
		this.bill_same_id = bill_same_id;
	}

	/**
	 * @return 获取本单VIP不打折
	 */
	public int getBill_vipnorate() {
		return bill_vipnorate;
	}

	/**
	 * @param 设置本单VIP不打折
	 */
	public void setBill_vipnorate(int bill_vipnorate) {
		this.bill_vipnorate = bill_vipnorate;
	}

	/**
	 * @return 获取本单VIP不积分
	 */
	public int getBill_vipnocentum() {
		return bill_vipnocentum;
	}

	/**
	 * @param 设置本单VIP不积分
	 */
	public void setBill_vipnocentum(int bill_vipnocentum) {
		this.bill_vipnocentum = bill_vipnocentum;
	}

	/**
	 * @return 获取老VIP编号
	 */
	public long getBill_old_vipid() {
		return bill_old_vipid;
	}

	/**
	 * @param 设置老VIP编号
	 */
	public void setBill_old_vipid(long bill_old_vipid) {
		this.bill_old_vipid = bill_old_vipid;
	}

	/**
	 * @return 获取打折退货原因
	 */
	public String getBill_back_reason() {
		return bill_back_reason;
	}

	/**
	 * @param 设置打折退货原因
	 */
	public void setBill_back_reason(String bill_back_reason) {
		this.bill_back_reason = bill_back_reason;
	}

	/**
	 * @return 获取打折退货原因详情
	 */
	public String getBill_back_reasonsub() {
		return bill_back_reasonsub;
	}

	/**
	 * @param 设置打折退货原因详情
	 */
	public void setBill_back_reasonsub(String bill_back_reasonsub) {
		this.bill_back_reasonsub = bill_back_reasonsub;
	}

	/**
	 * @return 获取刷卡卡号
	 */
	public String getBill_bank_cardid() {
		return bill_bank_cardid;
	}

	/**
	 * @param 设置刷卡卡号
	 */
	public void setBill_bank_cardid(String bill_bank_cardid) {
		this.bill_bank_cardid = bill_bank_cardid;
	}

	/**
	 * @return 获取刷卡金额
	 */
	public double getBill_bank_sums() {
		return bill_bank_sums;
	}

	/**
	 * @param 设置刷卡金额
	 */
	public void setBill_bank_sums(double bill_bank_sums) {
		this.bill_bank_sums = bill_bank_sums;
	}

	/**
	 * @return 获取刷卡检索号
	 */
	public String getBill_bank_sn() {
		return bill_bank_sn;
	}

	/**
	 * @param 设置刷卡检索号
	 */
	public void setBill_bank_sn(String bill_bank_sn) {
		this.bill_bank_sn = bill_bank_sn;
	}

	/**
	 * @return 获取商城单号
	 */
	public String getBill_platform_id() {
		return bill_platform_id;
	}

	/**
	 * @param 设置商城单号
	 */
	public void setBill_platform_id(String bill_platform_id) {
		this.bill_platform_id = bill_platform_id;
	}

	/**
	 * @return 获取商城申请单号
	 */
	public String getBill_source_id() {
		return bill_source_id;
	}

	/**
	 * @param 设置商城申请单号
	 */
	public void setBill_source_id(String bill_source_id) {
		this.bill_source_id = bill_source_id;
	}

	/**
	 * @return 获取库存中心单号
	 */
	public String getStock_bills_id() {
		return stock_bills_id;
	}

	/**
	 * @param 设置库存中心单号
	 */
	public void setStock_bills_id(String stock_bills_id) {
		this.stock_bills_id = stock_bills_id;
	}

	/**
	 * @return 获取支付宝类型
	 */
	public int getBill_aliapy_type() {
		return bill_aliapy_type;
	}

	/**
	 * @param 设置支付宝类型
	 */
	public void setBill_aliapy_type(int bill_aliapy_type) {
		this.bill_aliapy_type = bill_aliapy_type;
	}

	/**
	 * @return 获取是否补录
	 */
	public int getBill_makeup() {
		return bill_makeup;
	}

	/**
	 * @param 设置是否补录
	 */
	public void setBill_makeup(int bill_makeup) {
		this.bill_makeup = bill_makeup;
	}

	/**
	 * @return 获取储值卡号
	 */
	public String getBill_iccard_id() {
		return bill_iccard_id;
	}

	/**
	 * @param 设置储值卡号
	 */
	public void setBill_iccard_id(String bill_iccard_id) {
		this.bill_iccard_id = bill_iccard_id;
	}

}
