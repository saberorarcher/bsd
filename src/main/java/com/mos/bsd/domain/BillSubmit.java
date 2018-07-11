package com.mos.bsd.domain;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description:SAP传X2单据提交参数包装类
 * @author:陈真
 * @date:2018年4月24日 下午1:58:27
 * @version:1.0
 */
@ApiModel(value = "SAP传X2单据提交参数包装类", description = "SAP传X2单据提交参数包装类")
public class BillSubmit extends Base implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * X2业务单据主单
	 */
	@ApiModelProperty(notes = "X2业务单据主单", required = true)
	private BillMain billMain;

	/**
	 * X2业务单据明细
	 */
	@ApiModelProperty(notes = "X2业务单据明细", required = true)
	private List<BillSub> billSubs;

	// /**
	// * B2B价格实体
	// */
	// @ApiModelProperty(notes = "B2B价格实体", required = true)
	// private List<B2BPrice> billPrices;

	/**
	 * 创建一个新的实例 BillSubmit.
	 * 
	 */
	public BillSubmit() {
		super();
	}

	/**
	 * 创建一个新的实例 BillSubmit.
	 * 
	 * @param billMains
	 * @param billSubs
	 */
	public BillSubmit(BillMain billMain, List<BillSub> billSubs) {
		super();
		this.billMain = billMain;
		this.billSubs = billSubs;
	}

	public BillMain getBillMain() {
		return billMain;
	}

	public void setBillMain(BillMain billMain) {
		this.billMain = billMain;
	}

	public List<BillSub> getBillSubs() {
		return billSubs;
	}

	public void setBillSubs(List<BillSub> billSubs) {
		this.billSubs = billSubs;
	}

}
