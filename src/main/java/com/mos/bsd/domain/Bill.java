package com.mos.bsd.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author hero
 *
 */
@ApiModel(value = "单据主单", description = "单据主单字段")
public class Bill extends Base {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "单据流水号(固定值:0)", required = true,example="0")
	private long p0670_id;

	@ApiModelProperty(notes = "发货地编号", required = true,example="109727")
	private long set_department_id;

	@ApiModelProperty(notes = "收货地编号", required = true,example="109729")
	private long get_department_id;

	@ApiModelProperty(notes = "款式分类([01]成衣;[02]样衣;[03]成品)", required = true,example="[01]成衣")
	private String bill_style_sort;

	@ApiModelProperty(notes = "单据类型(0正常;1特殊;2换货)", required = true,example="1")
	private int bill_type;

	// [1]买断;[2]代销;[3]代销退回;[4]换货支持;[5]退货支持;[6]订货发错;[7]质量问题
	@ApiModelProperty(notes = "发货类型([1]买断;[2]代销;[3]代销退回;[4]换货支持;[5]退货支持;[6]订货发错;[7]质量问题)", required = true,example="[1]买断")
	private String bill_selltype;

	@ApiModelProperty(notes = "单据备注", required = false,example="外部调用测试1345")
	private String bill_remark;

	/**
	 * 获取 单据流水号
	 * 
	 * @return
	 * @returnType long
	 */
	public long getP0670_id() {
		return p0670_id;
	}

	/**
	 * 设置单据流水号(固定值：0)
	 * 
	 * @param p0670_id
	 * @returnType void
	 */
	public void setP0670_id(long p0670_id) {
		this.p0670_id = p0670_id;
	}

	/**
	 * 获取 款式分类([01]成衣;[02]样衣;[03]成品)
	 * 
	 * @return
	 * @returnType String
	 */
	public String getBill_style_sort() {
		return bill_style_sort;
	}

	/**
	 * 设置 款式分类([01]成衣;[02]样衣;[03]成品)
	 * 
	 * @param bill_style_sort
	 * @returnType void
	 */
	public void setBill_style_sort(String bill_style_sort) {
		this.bill_style_sort = bill_style_sort;
	}

	/**
	 * 获取 收货地编号
	 * 
	 * @return
	 * @returnType long
	 */
	public long getGet_department_id() {
		return get_department_id;
	}

	/**
	 * 设置 收货地编号
	 * 
	 * @param get_department_id
	 * @returnType void
	 */
	public void setGet_department_id(long get_department_id) {
		this.get_department_id = get_department_id;
	}

	/**
	 * 获取 发货类型([1]买断;[2]代销;[3]代销退回;[4]换货支持;[5]退货支持;[6]订货发错;[7]质量问题)
	 * 
	 * @return
	 * @returnType String
	 */
	public String getBill_selltype() {
		return bill_selltype;
	}

	/**
	 * 设置 发货类型([1]买断;[2]代销;[3]代销退回;[4]换货支持;[5]退货支持;[6]订货发错;[7]质量问题)
	 * 
	 * @param bill_selltype
	 * @returnType void
	 */
	public void setBill_selltype(String bill_selltype) {
		this.bill_selltype = bill_selltype;
	}

	/**
	 * 获取 发货地编号
	 * 
	 * @return
	 * @returnType long
	 */
	public long getSet_department_id() {
		return set_department_id;
	}

	/**
	 * 设置 发货地编号
	 * 
	 * @param set_department_id
	 * @returnType void
	 */
	public void setSet_department_id(long set_department_id) {
		this.set_department_id = set_department_id;
	}

	/**
	 * 获取 单据类型(0|正常;1|特殊;2|换货)
	 * 
	 * @return
	 * @returnType int
	 */
	public int getBill_type() {
		return bill_type;
	}

	/**
	 * 设置 单据类型(0|正常;1|特殊;2|换货)
	 * 
	 * @param bill_type
	 * @returnType void
	 */
	public void setBill_type(int bill_type) {
		this.bill_type = bill_type;
	}

	/**
	 * 获取 单据备注
	 * 
	 * @return
	 * @returnType String
	 */
	public String getBill_remark() {
		return bill_remark;
	}

	/**
	 * 设置 单据备注
	 * 
	 * @param bill_remark
	 * @returnType void
	 */
	public void setBill_remark(String bill_remark) {
		this.bill_remark = bill_remark;
	}

	@Override
	public String toString() {
		return "Bill [bill_style_sort=" + bill_style_sort + ", get_department_id=" + get_department_id
				+ ", bill_selltype=" + bill_selltype + ", bill_setdate=" + ", p0670_id=" + p0670_id
				+ ", set_department_id=" + set_department_id + ", bill_type=" + bill_type + ", bill_remark="
				+ bill_remark + "]";
	}

}
