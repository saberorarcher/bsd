package com.mos.bsd.domain;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Description B2B价格实体类
 * @author odin
 * @date 2016年11月22日 上午9:27:08
 */
public class B2BPrice implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;

	/** 单据编号 */
	private String bill_id;

	/** 批次ID */
	private long price_id;

	/** 销售模式:0批发，1代销 */
	private int sale_mode;

	/** 货号 */
	private String clothing_id;

	/** 销售公司 */
	private String group_id;
	/** 购买方 */
	private String company_id;
	/** 销售类型 */
	private String selltype_id;

	/** 成本价 */
	private double j_cost;
	/** 吊牌原价 */
	private double j_price;
	/** 吊牌现价 */
	private double x_price;
	/** 销售折扣 */
	private double sale_rate;
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
	/** 最迟换货日期 */
	private Date change_date;

	/** 代销结算模式:设置代销结算模式：0-按吊牌价结算，1-按结算价结算 */
	private int agent_mode;
	/** 代销折扣 */
	private double agent_rate;

	/** 结算流程编号 */
	private long flow_id;
	/** 发货批次号 */
	private long b2bsell_id;

	/** 显示价格 */
	private int show_price;
	/** 控制退货 */
	private int return_ctrl;
	/** 结算层级 */
	private int level_index;
	/** 是否退货 */
	private int is_return;
	/** 超出收发货机构的层级 */
	private int is_beyound;
	/** 品牌集合 */
	private String brandgroup_code;
	/** 活动价格单号 */
	private String price_billid;

	/** 数量 */
	private double nums;
	/** 结算金额 */
	private double sums;

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
	 * @return 获取结算金额
	 */
	public double getSums() {
		return sums;
	}

	/**
	 * @param 设置结算金额
	 */
	public void setSums(double sums) {
		this.sums = sums;
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
	}

	/**
	 * @return 获取批次ID
	 */
	public long getPrice_id() {
		return price_id;
	}

	/**
	 * @param 设置批次ID
	 */
	public void setPrice_id(long price_id) {
		this.price_id = price_id;
	}

	/**
	 * @return 获取销售模式:0批发，1代销
	 */
	public int getSale_mode() {
		return sale_mode;
	}

	/**
	 * @param 设置销售模式:0批发，1代销
	 */
	public void setSale_mode(int sale_mode) {
		this.sale_mode = sale_mode;
	}

	/**
	 * @return 获取货号
	 */
	public String getClothing_id() {
		return clothing_id;
	}

	/**
	 * @param 设置货号
	 */
	public void setClothing_id(String clothing_id) {
		this.clothing_id = clothing_id;
	}

	/**
	 * @return 获取销售公司
	 */
	public String getGroup_id() {
		return group_id;
	}

	/**
	 * @param 设置销售公司
	 */
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	/**
	 * @return 获取购买方
	 */
	public String getCompany_id() {
		return company_id;
	}

	/**
	 * @param 设置购买方
	 */
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	/**
	 * @return 获取销售类型
	 */
	public String getSelltype_id() {
		return selltype_id;
	}

	/**
	 * @param 设置销售类型
	 */
	public void setSelltype_id(String selltype_id) {
		this.selltype_id = selltype_id;
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
	 * @return 获取吊牌原价
	 */
	public double getJ_price() {
		return j_price;
	}

	/**
	 * @param 设置吊牌原价
	 */
	public void setJ_price(double j_price) {
		this.j_price = j_price;
	}

	/**
	 * @return 获取吊牌现价
	 */
	public double getX_price() {
		return x_price;
	}

	/**
	 * @param 设置吊牌现价
	 */
	public void setX_price(double x_price) {
		this.x_price = x_price;
	}

	/**
	 * @return 获取销售折扣
	 */
	public double getSale_rate() {
		return sale_rate;
	}

	/**
	 * @param 设置销售折扣
	 */
	public void setSale_rate(double sale_rate) {
		this.sale_rate = sale_rate;
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
	 * @return 获取最迟换货日期
	 */
	public Date getChange_date() {
		return change_date;
	}

	/**
	 * @param 设置最迟换货日期
	 */
	public void setChange_date(Date change_date) {
		this.change_date = change_date;
	}

	/**
	 * @return 获取代销结算模式:设置代销结算模式：0-按吊牌价结算，1-按结算价结算
	 */
	public int getAgent_mode() {
		return agent_mode;
	}

	/**
	 * @param 设置代销结算模式:设置代销结算模式：0-按吊牌价结算，1-按结算价结算
	 */
	public void setAgent_mode(int agent_mode) {
		this.agent_mode = agent_mode;
	}

	/**
	 * @return 获取代销折扣
	 */
	public double getAgent_rate() {
		return agent_rate;
	}

	/**
	 * @param 设置代销折扣
	 */
	public void setAgent_rate(double agent_rate) {
		this.agent_rate = agent_rate;
	}

	/**
	 * @return 获取结算流程编号
	 */
	public long getFlow_id() {
		return flow_id;
	}

	/**
	 * @param 设置结算流程编号
	 */
	public void setFlow_id(long flow_id) {
		this.flow_id = flow_id;
	}

	/**
	 * @return 获取发货批次号
	 */
	public long getB2bsell_id() {
		return b2bsell_id;
	}

	/**
	 * @param 设置发货批次号
	 */
	public void setB2bsell_id(long b2bsell_id) {
		this.b2bsell_id = b2bsell_id;
	}

	/**
	 * @return 获取显示价格
	 */
	public int getShow_price() {
		return show_price;
	}

	/**
	 * @param 设置显示价格
	 */
	public void setShow_price(int show_price) {
		this.show_price = show_price;
	}

	/**
	 * @return 获取控制退货
	 */
	public int getReturn_ctrl() {
		return return_ctrl;
	}

	/**
	 * @param 设置控制退货
	 */
	public void setReturn_ctrl(int return_ctrl) {
		this.return_ctrl = return_ctrl;
	}

	/**
	 * @return 获取结算层级
	 */
	public int getLevel_index() {
		return level_index;
	}

	/**
	 * @param 设置结算层级
	 */
	public void setLevel_index(int level_index) {
		this.level_index = level_index;
	}

	/**
	 * @return 获取是否退货
	 */
	public int getIs_return() {
		return is_return;
	}

	/**
	 * @param 设置是否退货
	 */
	public void setIs_return(int is_return) {
		this.is_return = is_return;
	}

	/**
	 * @return 获取超出收发货机构的层级
	 */
	public int getIs_beyound() {
		return is_beyound;
	}

	/**
	 * @param 设置超出收发货机构的层级
	 */
	public void setIs_beyound(int is_beyound) {
		this.is_beyound = is_beyound;
	}

	/**
	 * @return 获取品牌集合
	 */
	public String getBrandgroup_code() {
		return brandgroup_code;
	}

	/**
	 * @param 设置品牌集合
	 */
	public void setBrandgroup_code(String brandgroup_code) {
		this.brandgroup_code = brandgroup_code;
	}

	/**
	 * @return 获取活动价格单号
	 */
	public String getPrice_billid() {
		return price_billid;
	}

	/**
	 * @param 设置活动价格单号
	 */
	public void setPrice_billid(String price_billid) {
		this.price_billid = price_billid;
	}

}
