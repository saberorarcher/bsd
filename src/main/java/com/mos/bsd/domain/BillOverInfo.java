package com.mos.bsd.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 单据结案信息
 * @author odin
 * @date 2017年5月12日 下午4:01:28
 */
public class BillOverInfo implements Serializable {

	/** */
	private static final long serialVersionUID = 1L;

	/** 单据编号 */
	private long bill_id;
	/** 结案备注 */
	private String remark;

	/** 主单 */
	private BillMain main;
	/** 明细 */
	private List<BillSub> sub;

	/**
	 * @return 获取单据编号
	 */
	public long getBill_id() {
		return bill_id;
	}

	/**
	 * @param 设置单据编号
	 */
	public void setBill_id(long bill_id) {
		this.bill_id = bill_id;
	}

	/**
	 * @return 获取结案备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param 设置结案备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return 获取主单
	 */
	public BillMain getMain() {
		return main;
	}

	/**
	 * @param 设置主单
	 */
	public void setMain(BillMain main) {
		this.main = main;
	}

	/**
	 * @return 获取明细
	 */
	public List<BillSub> getSub() {
		return sub;
	}

	/**
	 * @param 设置明细
	 */
	public void setSub(List<BillSub> sub) {
		this.sub = sub;
	}

}
