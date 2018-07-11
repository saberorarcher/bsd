package com.mos.bsd.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.mos.bsd.domain.enums.EmBillSub;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Description X2业务单据单据明细实体
 * @author odin
 * @date 2016年11月21日 下午1:41:47
 */
public class BillSubEntity implements Serializable {

	// #region private fields
	
	private static final long serialVersionUID = 1L;

	/** 获取已赋过值的字段 */
	@JSONField(serialize = false)
	private List<EmBillSub> changes = new ArrayList<EmBillSub>();

	/** 序号 */
	@ApiModelProperty(notes = "序号", required = true,example="203075329994")
	private long p0671_id;
	/** 业务主表系统号 */
	@ApiModelProperty(notes = "业务主表系统号", required = true,example="18042500009994")
	private long p0670_id;
	/** 参考单号 **/
	private String billsub_refid;
	/** 序号 */
	private String bill_order;
	/** 货号编号 */
	private String clothing_id;
	/** 数量 */
	private double billsub_nums;
	/** 成本价 */
	private double billsub_cost;
	/** 原吊牌价 */
	private double billsub_jprice;
	/** 吊牌价 */
	private double billsub_xprice;
	/** 结算价 */
	private double billsub_sprice;
	/** 折扣 */
	private double billsub_rate;
	/** 原数量 */
	private double billsub_cnums;
	/** 备注 */
	private String billsub_remark;
	/** 结案 */
	private int billsub_over;
	/** 结案人 */
	private String billsub_overname;
	/** 结案日期 */
	private Timestamp billsub_overdate;
	/** 建立日期 */
	private Timestamp billsub_create_date;
	/** 换货率 */
	private double billsub_returned_pec;
	/** 换货日期 */
	private Date billsub_returned_date;
	/** 发货类型 */
	private String sell_class_id;
	/** 成本金额 */
	private double billsub_csum;
	/** 结算金额 */
	private double billsub_ssum;
	/** 加工单价 */
	private double billsub_cmtcost;
	/** 最后修改日期 */
	private Timestamp billsub_update_date;
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
	/** 收货吊牌价 */
	private double billsub_getxprice;
	/** vip本单积分 */
	private double billsub_vip_score;
	/** 本单上季积分 */
	private double billsub_vip_score_past;
	/** 实销金额 */
	private double billsub_fsums;
	/** 原单记录标识 */
	private int billsub_original;
	/** 参加促销标识 */
	private int billsub_promotion;
	/** 促销活动编号 */
	private long p0742_id;
	/** 商场活动编号 */
	private long p0320_id;
	/** 商场扣点 */
	private double action_rate;
	/** 店铺区位编号 */
	private String location_id;
	/** 回货期 */
	private Date billsub_setdate;
	/** 折实金额 */
	private double billsub_nett;
	/** 不产生退货额金额 */
	private double billsub_noreturn_sum;
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
	/** 物流公司 */
	private String billsub_logistics;
	/** 物流单号 */
	private String billsub_logistics_no;
	/** 营业员编号 */
	private String clerk_id;
	/** 收货发货类型，用于跨区调拨 */
	private String get_sell_code;
	/** 换货折扣 */
	private double change_discount;
	/** 换货率 */
	private double change_rate;
	/** 最迟换货日期 */
	private Date change_date;

	/** 预估扣款 */
	private double billsub_pre_fee;
	/** 实际扣款 */
	private double billsub_fac_fee;
	/** 实付折扣 */
	private double billsub_payrate;
	/** 使用积分（数据库没有，计算赋值） */
	private double use_score;

	// #endregion

	// #region setter & getter methods

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
		if (!this.changes.contains(EmBillSub.p0671_id)) {
			this.changes.add(EmBillSub.p0671_id);
		}
	}

	/**
	 * @return 获取业务主表系统号
	 */
	public long getP0670_id() {
		return p0670_id;
	}

	/**
	 * @param 设置业务主表系统号
	 */
	public void setP0670_id(long p0670_id) {
		this.p0670_id = p0670_id;
		if (!this.changes.contains(EmBillSub.p0670_id)) {
			this.changes.add(EmBillSub.p0670_id);
		}
	}

	public String getBillsub_refid() {
		return billsub_refid;
	}

	public void setBillsub_refid(String billsub_refid) {
		this.billsub_refid = billsub_refid;
	}

	/**
	 * @return 获取序号
	 */
	public String getBill_order() {
		return bill_order;
	}

	/**
	 * @param 设置序号
	 */
	public void setBill_order(String bill_order) {
		this.bill_order = bill_order;
		if (!this.changes.contains(EmBillSub.bill_order)) {
			this.changes.add(EmBillSub.bill_order);
		}
	}

	/**
	 * @return 获取货号编号
	 */
	public String getClothing_id() {
		return clothing_id;
	}

	/**
	 * @param 设置货号编号
	 */
	public void setClothing_id(String clothing_id) {
		this.clothing_id = clothing_id;
		if (!this.changes.contains(EmBillSub.clothing_id)) {
			this.changes.add(EmBillSub.clothing_id);
		}
	}

	/**
	 * @return 获取数量
	 */
	public double getBillsub_nums() {
		return billsub_nums;
	}

	/**
	 * @param 设置数量
	 */
	public void setBillsub_nums(double billsub_nums) {
		this.billsub_nums = billsub_nums;
		if (!this.changes.contains(EmBillSub.billsub_nums)) {
			this.changes.add(EmBillSub.billsub_nums);
		}
	}

	/**
	 * @return 获取成本价
	 */
	public double getBillsub_cost() {
		return billsub_cost;
	}

	/**
	 * @param 设置成本价
	 */
	public void setBillsub_cost(double billsub_cost) {
		this.billsub_cost = billsub_cost;
		if (!this.changes.contains(EmBillSub.billsub_cost)) {
			this.changes.add(EmBillSub.billsub_cost);
		}
	}

	/**
	 * @return 获取原吊牌价
	 */
	public double getBillsub_jprice() {
		return billsub_jprice;
	}

	/**
	 * @param 设置原吊牌价
	 */
	public void setBillsub_jprice(double billsub_jprice) {
		this.billsub_jprice = billsub_jprice;
		if (!this.changes.contains(EmBillSub.billsub_jprice)) {
			this.changes.add(EmBillSub.billsub_jprice);
		}
	}

	/**
	 * @return 获取吊牌价
	 */
	public double getBillsub_xprice() {
		return billsub_xprice;
	}

	/**
	 * @param 设置吊牌价
	 */
	public void setBillsub_xprice(double billsub_xprice) {
		this.billsub_xprice = billsub_xprice;
		if (!this.changes.contains(EmBillSub.billsub_xprice)) {
			this.changes.add(EmBillSub.billsub_xprice);
		}
	}

	/**
	 * @return 获取结算价
	 */
	public double getBillsub_sprice() {
		return billsub_sprice;
	}

	/**
	 * @param 设置结算价
	 */
	public void setBillsub_sprice(double billsub_sprice) {
		this.billsub_sprice = billsub_sprice;
		if (!this.changes.contains(EmBillSub.billsub_sprice)) {
			this.changes.add(EmBillSub.billsub_sprice);
		}
	}

	/**
	 * @return 获取折扣
	 */
	public double getBillsub_rate() {
		return billsub_rate;
	}

	/**
	 * @param 设置折扣
	 */
	public void setBillsub_rate(double billsub_rate) {
		this.billsub_rate = billsub_rate;
		if (!this.changes.contains(EmBillSub.billsub_rate)) {
			this.changes.add(EmBillSub.billsub_rate);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_cnums)) {
			this.changes.add(EmBillSub.billsub_cnums);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_remark)) {
			this.changes.add(EmBillSub.billsub_remark);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_over)) {
			this.changes.add(EmBillSub.billsub_over);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_overname)) {
			this.changes.add(EmBillSub.billsub_overname);
		}
	}

	/**
	 * @return 获取结案日期
	 */
	public Timestamp getBillsub_overdate() {
		return billsub_overdate;
	}

	/**
	 * @param 设置结案日期
	 */
	public void setBillsub_overdate(Timestamp billsub_overdate) {
		this.billsub_overdate = billsub_overdate;
		if (!this.changes.contains(EmBillSub.billsub_overdate)) {
			this.changes.add(EmBillSub.billsub_overdate);
		}
	}

	/**
	 * @return 获取建立日期
	 */
	public Timestamp getBillsub_create_date() {
		return billsub_create_date;
	}

	/**
	 * @param 设置建立日期
	 */
	public void setBillsub_create_date(Timestamp billsub_create_date) {
		this.billsub_create_date = billsub_create_date;
		if (!this.changes.contains(EmBillSub.billsub_create_date)) {
			this.changes.add(EmBillSub.billsub_create_date);
		}
	}

	/**
	 * @return 获取换货率
	 */
	public double getBillsub_returned_pec() {
		return billsub_returned_pec;
	}

	/**
	 * @param 设置换货率
	 */
	public void setBillsub_returned_pec(double billsub_returned_pec) {
		this.billsub_returned_pec = billsub_returned_pec;
		if (!this.changes.contains(EmBillSub.billsub_returned_pec)) {
			this.changes.add(EmBillSub.billsub_returned_pec);
		}
	}

	/**
	 * @return 获取换货日期
	 */
	public Date getBillsub_returned_date() {
		return billsub_returned_date;
	}

	/**
	 * @param 设置换货日期
	 */
	public void setBillsub_returned_date(Date billsub_returned_date) {
		this.billsub_returned_date = billsub_returned_date;
		if (!this.changes.contains(EmBillSub.billsub_returned_date)) {
			this.changes.add(EmBillSub.billsub_returned_date);
		}
	}

	/**
	 * @return 获取发货类型
	 */
	public String getSell_class_id() {
		return sell_class_id;
	}

	/**
	 * @param 设置发货类型
	 */
	public void setSell_class_id(String sell_class_id) {
		this.sell_class_id = sell_class_id;
		if (!this.changes.contains(EmBillSub.sell_class_id)) {
			this.changes.add(EmBillSub.sell_class_id);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_csum)) {
			this.changes.add(EmBillSub.billsub_csum);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_ssum)) {
			this.changes.add(EmBillSub.billsub_ssum);
		}
	}

	/**
	 * @return 获取加工单价
	 */
	public double getBillsub_cmtcost() {
		return billsub_cmtcost;
	}

	/**
	 * @param 设置加工单价
	 */
	public void setBillsub_cmtcost(double billsub_cmtcost) {
		this.billsub_cmtcost = billsub_cmtcost;
		if (!this.changes.contains(EmBillSub.billsub_cmtcost)) {
			this.changes.add(EmBillSub.billsub_cmtcost);
		}
	}

	/**
	 * @return 获取最后修改日期
	 */
	public Timestamp getBillsub_update_date() {
		return billsub_update_date;
	}

	/**
	 * @param 设置最后修改日期
	 */
	public void setBillsub_update_date(Timestamp billsub_update_date) {
		this.billsub_update_date = billsub_update_date;
		if (!this.changes.contains(EmBillSub.billsub_update_date)) {
			this.changes.add(EmBillSub.billsub_update_date);
		}
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
		if (!this.changes.contains(EmBillSub.style_year_code)) {
			this.changes.add(EmBillSub.style_year_code);
		}
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
		if (!this.changes.contains(EmBillSub.style_month_code)) {
			this.changes.add(EmBillSub.style_month_code);
		}
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
		if (!this.changes.contains(EmBillSub.subject_id)) {
			this.changes.add(EmBillSub.subject_id);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_getnums)) {
			this.changes.add(EmBillSub.billsub_getnums);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_getrate)) {
			this.changes.add(EmBillSub.billsub_getrate);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_getsprice)) {
			this.changes.add(EmBillSub.billsub_getsprice);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_getssum)) {
			this.changes.add(EmBillSub.billsub_getssum);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_stock_calced)) {
			this.changes.add(EmBillSub.billsub_stock_calced);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_modified)) {
			this.changes.add(EmBillSub.billsub_modified);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_get_remark)) {
			this.changes.add(EmBillSub.billsub_get_remark);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_free)) {
			this.changes.add(EmBillSub.billsub_free);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_return)) {
			this.changes.add(EmBillSub.billsub_return);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_getxprice)) {
			this.changes.add(EmBillSub.billsub_getxprice);
		}
	}

	/**
	 * @return 获取vip本单积分
	 */
	public double getBillsub_vip_score() {
		return billsub_vip_score;
	}

	/**
	 * @param 设置vip本单积分
	 */
	public void setBillsub_vip_score(double billsub_vip_score) {
		this.billsub_vip_score = billsub_vip_score;
		if (!this.changes.contains(EmBillSub.billsub_vip_score)) {
			this.changes.add(EmBillSub.billsub_vip_score);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_vip_score_past)) {
			this.changes.add(EmBillSub.billsub_vip_score_past);
		}
	}

	/**
	 * @return 获取实销金额
	 */
	public double getBillsub_fsums() {
		return billsub_fsums;
	}

	/**
	 * @param 设置实销金额
	 */
	public void setBillsub_fsums(double billsub_fsums) {
		this.billsub_fsums = billsub_fsums;
		if (!this.changes.contains(EmBillSub.billsub_fsums)) {
			this.changes.add(EmBillSub.billsub_fsums);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_original)) {
			this.changes.add(EmBillSub.billsub_original);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_promotion)) {
			this.changes.add(EmBillSub.billsub_promotion);
		}
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
		if (!this.changes.contains(EmBillSub.p0742_id)) {
			this.changes.add(EmBillSub.p0742_id);
		}
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
		if (!this.changes.contains(EmBillSub.p0320_id)) {
			this.changes.add(EmBillSub.p0320_id);
		}
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
		if (!this.changes.contains(EmBillSub.action_rate)) {
			this.changes.add(EmBillSub.action_rate);
		}
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
		if (!this.changes.contains(EmBillSub.location_id)) {
			this.changes.add(EmBillSub.location_id);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_setdate)) {
			this.changes.add(EmBillSub.billsub_setdate);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_nett)) {
			this.changes.add(EmBillSub.billsub_nett);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_noreturn_sum)) {
			this.changes.add(EmBillSub.billsub_noreturn_sum);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_freight)) {
			this.changes.add(EmBillSub.billsub_freight);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_returnstate)) {
			this.changes.add(EmBillSub.billsub_returnstate);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_cname)) {
			this.changes.add(EmBillSub.billsub_cname);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_ctel)) {
			this.changes.add(EmBillSub.billsub_ctel);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_address)) {
			this.changes.add(EmBillSub.billsub_address);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_returnmessage)) {
			this.changes.add(EmBillSub.billsub_returnmessage);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_logistics)) {
			this.changes.add(EmBillSub.billsub_logistics);
		}
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
		if (!this.changes.contains(EmBillSub.billsub_logistics_no)) {
			this.changes.add(EmBillSub.billsub_logistics_no);
		}
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
		if (!this.changes.contains(EmBillSub.clerk_id)) {
			this.changes.add(EmBillSub.clerk_id);
		}
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
		if (!this.changes.contains(EmBillSub.get_sell_code)) {
			this.changes.add(EmBillSub.get_sell_code);
		}
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
		if (!this.changes.contains(EmBillSub.change_rate)) {
			this.changes.add(EmBillSub.change_rate);
		}
	}

	/**
	 * @return 获取换货日期
	 */
	public Date getChange_date() {
		return change_date;
	}

	/**
	 * @param 设置换货日期
	 */
	public void setChange_date(Date change_date) {
		this.change_date = change_date;
		if (!this.changes.contains(EmBillSub.change_date)) {
			this.changes.add(EmBillSub.change_date);
		}
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
		if (!this.changes.contains(EmBillSub.change_discount)) {
			this.changes.add(EmBillSub.change_discount);
		}
	}

	/**
	 * @return 获取预估扣款
	 */
	public double getBillsub_pre_fee() {
		return billsub_pre_fee;
	}

	/**
	 * @param 设置预估扣款
	 */
	public void setBillsub_pre_fee(double billsub_pre_fee) {
		this.billsub_pre_fee = billsub_pre_fee;
	}

	/**
	 * @return 获取实际扣款
	 */
	public double getBillsub_fac_fee() {
		return billsub_fac_fee;
	}

	/**
	 * @param 设置实际扣款
	 */
	public void setBillsub_fac_fee(double billsub_fac_fee) {
		this.billsub_fac_fee = billsub_fac_fee;
	}

	/**
	 * @return 获取实付折扣
	 */
	public double getBillsub_payrate() {
		return billsub_payrate;
	}

	/**
	 * @param 设置实付折扣
	 */
	public void setBillsub_payrate(double billsub_payrate) {
		this.billsub_payrate = billsub_payrate;
	}

	/**
	 * @return 获取使用积分（数据库没有，计算赋值）
	 */
	public double getUse_score() {
		return use_score;
	}

	/**
	 * @param 设置使用积分（数据库没有，计算赋值）
	 */
	public void setUse_score(double use_score) {
		this.use_score = use_score;
	}

	// #endregion

	/**
	 * @return 获取获取已赋过值的字段
	 */
	public List<EmBillSub> getChanges() {
		return changes;
	}

}
