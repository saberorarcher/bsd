package com.mos.bsd.domain;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Description 单据提交单据明细
 * @author odin
 * @date 2017年5月9日 下午10:43:26
 */
public class RmiBillSub implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;

	/** 库存中心单据系统编号 */
	private String bill_id;
	/** X2单据系统编号 */
	private String x2_bill_id;
	/** sku编号 */
	private String clothing_id;
	/** 发货销售类型编号 */
	private String selltype_id;
	/** 收货销售类型编号（用于跨区调拨） */
	private String get_selltype_id;
	/** 数量 */
	private double nums;
	/** 成本价 */
	private double j_cost;
	/** 原吊牌价 */
	private double j_price;
	/** 现吊牌价 */
	private double x_price;
	/** 销售折扣 */
	private double sell_rate;
	/** 结算价 */
	private double s_price;
	/** 退货率 */
	private double back_rate;
	/** 最迟退货日期 */
	private Date back_date;
	/** 换货折扣 */
	private double change_discount;
	/** 换货率 */
	private double change_rate;
	/** 最迟退货日期 */
	private Date change_date;
	/** 实销金额 */
	private double fsums;
	/** 实销价 */
	private double f_price;
	/** 会员积分 */
	private double vip_score;
	/** 实付折扣 */
	private double pay_rate;
	/** 预估扣费 */
	private double pre_fee;
	/** 实际扣费 */
	private double fac_fee;
	/** 会员积分 */
	private double vip_points;
	/** 使用积分 */
	private double use_points;
	/** 会员钱包 */
	private double vip_purse;

	// #region 保存D表需要的数据

	/** 序号 */
	private String bill_order;
	/** 原数量 */
	private double billsub_cnums;
	/** 备注 */
	private String billsub_remark;
	/** 结案 */
	private int billsub_over;
	/** 结案人 */
	private String billsub_overname;
	/** 结案日期 */
	private String billsub_overdate;
	/** 建立日期 */
	private String billsub_create_date;
	/** 建立人 */
	private String billsub_create_name;
	/** 成本金额 */
	private double billsub_csum;
	/** 结算金额 */
	private double billsub_ssum;
	/** 最后修改日期 */
	private String billsub_update_date;
	/** 款式年份 */
	private String style_year_code;
	/** 款式季度 */
	private String style_month_code;
	/** 现金科目编号 */
	private String subject_id;
	/** 收货数量 */
	private double billsub_getnums;
	/** 收货折扣 */
	private double billsub_getrate;
	/** 收货结算价 */
	private double billsub_getsprice;
	/** 收货结算金额 */
	private double billsub_getssum;
	/** 加工单价 */
	private double billsub_CMTcost;
	/** 序号 */
	private long p0671_id;
	/** 库存已计算 */
	private int billsub_stock_calced;
	/** 修改过的 */
	private int billsub_modified;
	/** 收货备注 */
	private String billsub_get_remark;
	/** 赠送 */
	private int billsub_free;
	/** 退货 */
	private int billsub_return;
	/** 本单上季积分 */
	private double billsub_vip_score_past;
	/** 收货吊牌价 */
	private double billsub_getxprice;
	/** 原单记录标识 */
	private int billsub_original;
	/** 商场活动编号 */
	private long p0320_id;
	/** 商场扣点 */
	private double action_rate;
	/** 不产生退货额金额 */
	private double billsub_noreturn_sum;
	/** 店铺区位编号 */
	private String location_id;
	/** 促销活动编号 */
	private long p0742_id;
	/** 回货期 */
	private Date billsub_setdate;
	/** 参加促销标识 */
	private int billsub_promotion;
	/** 折实金额 */
	private double billsub_nett;
	/** 运费分摊 */
	private double billsub_freight;
	/** 退货状态 */
	private long billsub_returnstate;
	/** 退货取件联系人 */
	private String billsub_cname;
	/** 退货取件联系电话 */
	private String billsub_ctel;
	/** 退货取件地址 */
	private String billsub_address;
	/** 退货留言 */
	private String billsub_returnmessage;
	/** 物流单号 */
	private String billsub_logistics_no;
	/** 物流公司 */
	private String billsub_logistics;
	/** 营业员编号 */
	private String clerk_id;
	/** 收货发货类型 */
	private String get_sell_code;
	/** 使用积分 */
	private double use_score;

	// #endregion

	// #region setters getters

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
	 * @return 获取sku编号
	 */
	public String getClothing_id() {
		return clothing_id;
	}

	/**
	 * @param 设置sku编号
	 */
	public void setClothing_id(String clothing_id) {
		this.clothing_id = clothing_id;
	}

	/**
	 * @return 获取发货销售类型编号
	 */
	public String getSelltype_id() {
		return selltype_id;
	}

	/**
	 * @param 设置发货销售类型编号
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
	 * @return 获取数量
	 */
	public double getNums() {
		return nums;
	}

	/**
	 * @param 设置数量
	 */
	public void setNums(double nums) {
		this.nums = nums;
	}

	/**
	 * @return 获取成本价
	 */
	public double getJ_cost() {
		return j_cost;
	}

	/**
	 * @param 设置成本价
	 */
	public void setJ_cost(double j_cost) {
		this.j_cost = j_cost;
	}

	/**
	 * @return 获取原吊牌价
	 */
	public double getJ_price() {
		return j_price;
	}

	/**
	 * @param 设置原吊牌价
	 */
	public void setJ_price(double j_price) {
		this.j_price = j_price;
	}

	/**
	 * @return 获取现吊牌价
	 */
	public double getX_price() {
		return x_price;
	}

	/**
	 * @param 设置现吊牌价
	 */
	public void setX_price(double x_price) {
		this.x_price = x_price;
	}

	/**
	 * @return 获取销售折扣
	 */
	public double getSell_rate() {
		return sell_rate;
	}

	/**
	 * @param 设置销售折扣
	 */
	public void setSell_rate(double sell_rate) {
		this.sell_rate = sell_rate;
	}

	/**
	 * @return 获取结算价
	 */
	public double getS_price() {
		return s_price;
	}

	/**
	 * @param 设置结算价
	 */
	public void setS_price(double s_price) {
		this.s_price = s_price;
	}

	/**
	 * @return 获取退货率
	 */
	public double getBack_rate() {
		return back_rate;
	}

	/**
	 * @param 设置退货率
	 */
	public void setBack_rate(double back_rate) {
		this.back_rate = back_rate;
	}

	/**
	 * @return 获取最迟退货日期
	 */
	public Date getBack_date() {
		return back_date;
	}

	/**
	 * @param 设置最迟退货日期
	 */
	public void setBack_date(Date back_date) {
		this.back_date = back_date;
	}

	/**
	 * @return 获取换货折扣
	 */
	public double getChange_discount() {
		return change_discount;
	}

	/**
	 * @param 设置换货折扣
	 */
	public void setChange_discount(double change_discount) {
		this.change_discount = change_discount;
	}

	/**
	 * @return 获取换货率
	 */
	public double getChange_rate() {
		return change_rate;
	}

	/**
	 * @param 设置换货率
	 */
	public void setChange_rate(double change_rate) {
		this.change_rate = change_rate;
	}

	/**
	 * @return 获取最迟退货日期
	 */
	public Date getChange_date() {
		return change_date;
	}

	/**
	 * @param 设置最迟退货日期
	 */
	public void setChange_date(Date change_date) {
		this.change_date = change_date;
	}

	/**
	 * @return 获取实销金额
	 */
	public double getFsums() {
		return fsums;
	}

	/**
	 * @param 设置实销金额
	 */
	public void setFsums(double fsums) {
		this.fsums = fsums;
	}

	/**
	 * @return 获取实销价
	 */
	public double getF_price() {
		return f_price;
	}

	/**
	 * @param 设置实销价
	 */
	public void setF_price(double f_price) {
		this.f_price = f_price;
	}

	/**
	 * @return 获取会员积分
	 */
	public double getVip_score() {
		return vip_score;
	}

	/**
	 * @param 设置会员积分
	 */
	public void setVip_score(double vip_score) {
		this.vip_score = vip_score;
	}

	/**
	 * @return 获取实付折扣
	 */
	public double getPay_rate() {
		return pay_rate;
	}

	/**
	 * @param 设置实付折扣
	 */
	public void setPay_rate(double pay_rate) {
		this.pay_rate = pay_rate;
	}

	/**
	 * @return 获取预估扣费
	 */
	public double getPre_fee() {
		return pre_fee;
	}

	/**
	 * @param 设置预估扣费
	 */
	public void setPre_fee(double pre_fee) {
		this.pre_fee = pre_fee;
	}

	/**
	 * @return 获取实际扣费
	 */
	public double getFac_fee() {
		return fac_fee;
	}

	/**
	 * @param 设置实际扣费
	 */
	public void setFac_fee(double fac_fee) {
		this.fac_fee = fac_fee;
	}

	/**
	 * @return 获取会员积分
	 */
	public double getVip_points() {
		return vip_points;
	}

	/**
	 * @param 设置会员积分
	 */
	public void setVip_points(double vip_points) {
		this.vip_points = vip_points;
	}

	/**
	 * @return 获取使用积分
	 */
	public double getUse_points() {
		return use_points;
	}

	/**
	 * @param 设置使用积分
	 */
	public void setUse_points(double use_points) {
		this.use_points = use_points;
	}

	/**
	 * @return 获取会员钱包
	 */
	public double getVip_purse() {
		return vip_purse;
	}

	/**
	 * @param 设置会员钱包
	 */
	public void setVip_purse(double vip_purse) {
		this.vip_purse = vip_purse;
	}

	/**
	 * @return 获取#regio
	 */
	public String getBill_order() {
		return bill_order;
	}

	/**
	 * @param 设置#regio
	 */
	public void setBill_order(String bill_order) {
		this.bill_order = bill_order;
	}

	/**
	 * @return 获取原数量
	 */
	public double getBillsub_cnums() {
		return billsub_cnums;
	}

	/**
	 * @param 设置原数量
	 */
	public void setBillsub_cnums(double billsub_cnums) {
		this.billsub_cnums = billsub_cnums;
	}

	/**
	 * @return 获取备注
	 */
	public String getBillsub_remark() {
		return billsub_remark;
	}

	/**
	 * @param 设置备注
	 */
	public void setBillsub_remark(String billsub_remark) {
		this.billsub_remark = billsub_remark;
	}

	/**
	 * @return 获取结案
	 */
	public int getBillsub_over() {
		return billsub_over;
	}

	/**
	 * @param 设置结案
	 */
	public void setBillsub_over(int billsub_over) {
		this.billsub_over = billsub_over;
	}

	/**
	 * @return 获取结案人
	 */
	public String getBillsub_overname() {
		return billsub_overname;
	}

	/**
	 * @param 设置结案人
	 */
	public void setBillsub_overname(String billsub_overname) {
		this.billsub_overname = billsub_overname;
	}

	/**
	 * @return 获取结案日期
	 */
	public String getBillsub_overdate() {
		return billsub_overdate;
	}

	/**
	 * @param 设置结案日期
	 */
	public void setBillsub_overdate(String billsub_overdate) {
		this.billsub_overdate = billsub_overdate;
	}

	/**
	 * @return 获取建立日期
	 */
	public String getBillsub_create_date() {
		return billsub_create_date;
	}

	/**
	 * @param 设置建立日期
	 */
	public void setBillsub_create_date(String billsub_create_date) {
		this.billsub_create_date = billsub_create_date;
	}

	/**
	 * @return 获取建立人
	 */
	public String getBillsub_create_name() {
		return billsub_create_name;
	}

	/**
	 * @param 设置建立人
	 */
	public void setBillsub_create_name(String billsub_create_name) {
		this.billsub_create_name = billsub_create_name;
	}

	/**
	 * @return 获取成本金额
	 */
	public double getBillsub_csum() {
		return billsub_csum;
	}

	/**
	 * @param 设置成本金额
	 */
	public void setBillsub_csum(double billsub_csum) {
		this.billsub_csum = billsub_csum;
	}

	/**
	 * @return 获取结算金额
	 */
	public double getBillsub_ssum() {
		return billsub_ssum;
	}

	/**
	 * @param 设置结算金额
	 */
	public void setBillsub_ssum(double billsub_ssum) {
		this.billsub_ssum = billsub_ssum;
	}

	/**
	 * @return 获取最后修改日期
	 */
	public String getBillsub_update_date() {
		return billsub_update_date;
	}

	/**
	 * @param 设置最后修改日期
	 */
	public void setBillsub_update_date(String billsub_update_date) {
		this.billsub_update_date = billsub_update_date;
	}

	/**
	 * @return 获取款式年份
	 */
	public String getStyle_year_code() {
		return style_year_code;
	}

	/**
	 * @param 设置款式年份
	 */
	public void setStyle_year_code(String style_year_code) {
		this.style_year_code = style_year_code;
	}

	/**
	 * @return 获取款式季度
	 */
	public String getStyle_month_code() {
		return style_month_code;
	}

	/**
	 * @param 设置款式季度
	 */
	public void setStyle_month_code(String style_month_code) {
		this.style_month_code = style_month_code;
	}

	/**
	 * @return 获取现金科目编号
	 */
	public String getSubject_id() {
		return subject_id;
	}

	/**
	 * @param 设置现金科目编号
	 */
	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	/**
	 * @return 获取收货数量
	 */
	public double getBillsub_getnums() {
		return billsub_getnums;
	}

	/**
	 * @param 设置收货数量
	 */
	public void setBillsub_getnums(double billsub_getnums) {
		this.billsub_getnums = billsub_getnums;
	}

	/**
	 * @return 获取收货折扣
	 */
	public double getBillsub_getrate() {
		return billsub_getrate;
	}

	/**
	 * @param 设置收货折扣
	 */
	public void setBillsub_getrate(double billsub_getrate) {
		this.billsub_getrate = billsub_getrate;
	}

	/**
	 * @return 获取收货结算价
	 */
	public double getBillsub_getsprice() {
		return billsub_getsprice;
	}

	/**
	 * @param 设置收货结算价
	 */
	public void setBillsub_getsprice(double billsub_getsprice) {
		this.billsub_getsprice = billsub_getsprice;
	}

	/**
	 * @return 获取收货结算金额
	 */
	public double getBillsub_getssum() {
		return billsub_getssum;
	}

	/**
	 * @param 设置收货结算金额
	 */
	public void setBillsub_getssum(double billsub_getssum) {
		this.billsub_getssum = billsub_getssum;
	}

	/**
	 * @return 获取加工单价
	 */
	public double getBillsub_CMTcost() {
		return billsub_CMTcost;
	}

	/**
	 * @param 设置加工单价
	 */
	public void setBillsub_CMTcost(double billsub_CMTcost) {
		this.billsub_CMTcost = billsub_CMTcost;
	}

	/**
	 * @return 获取序号
	 */
	public long getP0671_id() {
		return p0671_id;
	}

	/**
	 * @param 设置序号
	 */
	public void setP0671_id(long p0671_id) {
		this.p0671_id = p0671_id;
	}

	/**
	 * @return 获取库存已计算
	 */
	public int getBillsub_stock_calced() {
		return billsub_stock_calced;
	}

	/**
	 * @param 设置库存已计算
	 */
	public void setBillsub_stock_calced(int billsub_stock_calced) {
		this.billsub_stock_calced = billsub_stock_calced;
	}

	/**
	 * @return 获取修改过的
	 */
	public int getBillsub_modified() {
		return billsub_modified;
	}

	/**
	 * @param 设置修改过的
	 */
	public void setBillsub_modified(int billsub_modified) {
		this.billsub_modified = billsub_modified;
	}

	/**
	 * @return 获取收货备注
	 */
	public String getBillsub_get_remark() {
		return billsub_get_remark;
	}

	/**
	 * @param 设置收货备注
	 */
	public void setBillsub_get_remark(String billsub_get_remark) {
		this.billsub_get_remark = billsub_get_remark;
	}

	/**
	 * @return 获取赠送
	 */
	public int getBillsub_free() {
		return billsub_free;
	}

	/**
	 * @param 设置赠送
	 */
	public void setBillsub_free(int billsub_free) {
		this.billsub_free = billsub_free;
	}

	/**
	 * @return 获取退货
	 */
	public int getBillsub_return() {
		return billsub_return;
	}

	/**
	 * @param 设置退货
	 */
	public void setBillsub_return(int billsub_return) {
		this.billsub_return = billsub_return;
	}

	/**
	 * @return 获取本单上季积分
	 */
	public double getBillsub_vip_score_past() {
		return billsub_vip_score_past;
	}

	/**
	 * @param 设置本单上季积分
	 */
	public void setBillsub_vip_score_past(double billsub_vip_score_past) {
		this.billsub_vip_score_past = billsub_vip_score_past;
	}

	/**
	 * @return 获取收货吊牌价
	 */
	public double getBillsub_getxprice() {
		return billsub_getxprice;
	}

	/**
	 * @param 设置收货吊牌价
	 */
	public void setBillsub_getxprice(double billsub_getxprice) {
		this.billsub_getxprice = billsub_getxprice;
	}

	/**
	 * @return 获取原单记录标识
	 */
	public int getBillsub_original() {
		return billsub_original;
	}

	/**
	 * @param 设置原单记录标识
	 */
	public void setBillsub_original(int billsub_original) {
		this.billsub_original = billsub_original;
	}

	/**
	 * @return 获取商场活动编号
	 */
	public long getP0320_id() {
		return p0320_id;
	}

	/**
	 * @param 设置商场活动编号
	 */
	public void setP0320_id(long p0320_id) {
		this.p0320_id = p0320_id;
	}

	/**
	 * @return 获取商场扣点
	 */
	public double getAction_rate() {
		return action_rate;
	}

	/**
	 * @param 设置商场扣点
	 */
	public void setAction_rate(double action_rate) {
		this.action_rate = action_rate;
	}

	/**
	 * @return 获取不产生退货额金额
	 */
	public double getBillsub_noreturn_sum() {
		return billsub_noreturn_sum;
	}

	/**
	 * @param 设置不产生退货额金额
	 */
	public void setBillsub_noreturn_sum(double billsub_noreturn_sum) {
		this.billsub_noreturn_sum = billsub_noreturn_sum;
	}

	/**
	 * @return 获取店铺区位编号
	 */
	public String getLocation_id() {
		return location_id;
	}

	/**
	 * @param 设置店铺区位编号
	 */
	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}

	/**
	 * @return 获取促销活动编号
	 */
	public long getP0742_id() {
		return p0742_id;
	}

	/**
	 * @param 设置促销活动编号
	 */
	public void setP0742_id(long p0742_id) {
		this.p0742_id = p0742_id;
	}

	/**
	 * @return 获取回货期
	 */
	public Date getBillsub_setdate() {
		return billsub_setdate;
	}

	/**
	 * @param 设置回货期
	 */
	public void setBillsub_setdate(Date billsub_setdate) {
		this.billsub_setdate = billsub_setdate;
	}

	/**
	 * @return 获取参加促销标识
	 */
	public int getBillsub_promotion() {
		return billsub_promotion;
	}

	/**
	 * @param 设置参加促销标识
	 */
	public void setBillsub_promotion(int billsub_promotion) {
		this.billsub_promotion = billsub_promotion;
	}

	/**
	 * @return 获取折实金额
	 */
	public double getBillsub_nett() {
		return billsub_nett;
	}

	/**
	 * @param 设置折实金额
	 */
	public void setBillsub_nett(double billsub_nett) {
		this.billsub_nett = billsub_nett;
	}

	/**
	 * @return 获取运费分摊
	 */
	public double getBillsub_freight() {
		return billsub_freight;
	}

	/**
	 * @param 设置运费分摊
	 */
	public void setBillsub_freight(double billsub_freight) {
		this.billsub_freight = billsub_freight;
	}

	/**
	 * @return 获取退货状态
	 */
	public long getBillsub_returnstate() {
		return billsub_returnstate;
	}

	/**
	 * @param 设置退货状态
	 */
	public void setBillsub_returnstate(long billsub_returnstate) {
		this.billsub_returnstate = billsub_returnstate;
	}

	/**
	 * @return 获取退货取件联系人
	 */
	public String getBillsub_cname() {
		return billsub_cname;
	}

	/**
	 * @param 设置退货取件联系人
	 */
	public void setBillsub_cname(String billsub_cname) {
		this.billsub_cname = billsub_cname;
	}

	/**
	 * @return 获取退货取件联系电话
	 */
	public String getBillsub_ctel() {
		return billsub_ctel;
	}

	/**
	 * @param 设置退货取件联系电话
	 */
	public void setBillsub_ctel(String billsub_ctel) {
		this.billsub_ctel = billsub_ctel;
	}

	/**
	 * @return 获取退货取件地址
	 */
	public String getBillsub_address() {
		return billsub_address;
	}

	/**
	 * @param 设置退货取件地址
	 */
	public void setBillsub_address(String billsub_address) {
		this.billsub_address = billsub_address;
	}

	/**
	 * @return 获取退货留言
	 */
	public String getBillsub_returnmessage() {
		return billsub_returnmessage;
	}

	/**
	 * @param 设置退货留言
	 */
	public void setBillsub_returnmessage(String billsub_returnmessage) {
		this.billsub_returnmessage = billsub_returnmessage;
	}

	/**
	 * @return 获取物流单号
	 */
	public String getBillsub_logistics_no() {
		return billsub_logistics_no;
	}

	/**
	 * @param 设置物流单号
	 */
	public void setBillsub_logistics_no(String billsub_logistics_no) {
		this.billsub_logistics_no = billsub_logistics_no;
	}

	/**
	 * @return 获取物流公司
	 */
	public String getBillsub_logistics() {
		return billsub_logistics;
	}

	/**
	 * @param 设置物流公司
	 */
	public void setBillsub_logistics(String billsub_logistics) {
		this.billsub_logistics = billsub_logistics;
	}

	/**
	 * @return 获取营业员编号
	 */
	public String getClerk_id() {
		return clerk_id;
	}

	/**
	 * @param 设置营业员编号
	 */
	public void setClerk_id(String clerk_id) {
		this.clerk_id = clerk_id;
	}

	/**
	 * @return 获取收货发货类型
	 */
	public String getGet_sell_code() {
		return get_sell_code;
	}

	/**
	 * @param 设置收货发货类型
	 */
	public void setGet_sell_code(String get_sell_code) {
		this.get_sell_code = get_sell_code;
	}

	/**
	 * @return 获取使用积分
	 */
	public double getUse_score() {
		return use_score;
	}

	/**
	 * @param 设置使用积分
	 */
	public void setUse_score(double use_score) {
		this.use_score = use_score;
	}

	// #endregion

}
