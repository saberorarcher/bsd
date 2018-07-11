package com.mos.bsd.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mos.bsd.domain.B2BPrice;
import com.mos.bsd.domain.BillMain;
import com.mos.bsd.domain.BillOverInfo;
import com.mos.bsd.domain.BillPrice;
import com.mos.bsd.domain.BillRetailExpandInfo;
import com.mos.bsd.domain.BillSub;
import com.mos.bsd.domain.RmiBillMain;
import com.mos.bsd.domain.RmiBillSub;
import com.x3.base.core.message.RequestMsg;
import com.x3.base.core.util.DateUtil;
import com.x3.base.core.util.LogUtil;


/**
 * @Description 单据转换工具
 * @author odin
 * @date 2017年5月10日 上午11:23:27
 */
public class BillConvertUtil {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static String dateToStr(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		} else {
			return dateFormat.format(timestamp);
		}
	}

	/**
	 * 将单据实体主单转换为RMI单据提交实体主单
	 * 
	 * @param main
	 * @param is_receive
	 * @param retailInfo
	 * @return
	 */
	public static RmiBillMain billMainToRmiBillMain(BillMain main, boolean is_receive,
			BillRetailExpandInfo retailInfo) {

		LogUtil.logger.debug("RMI实体主单转换开始..." + main.getP0670_id());

		String p0670_id = String.valueOf(main.getP0670_id());

		RmiBillMain en = new RmiBillMain();

		en.setBill_id(p0670_id);
		en.setX2_bill_id(p0670_id);
		en.setBill_code(main.getBill_id());

		// 参照单号：X2中参照关系
		en.setBill_czid(main.getBill_czid() == 0 ? "" : String.valueOf(main.getBill_czid()));
		// 引用单号(用于处理库存中心参照关系）
		en.setReference_id(StringUtil_bak.compare(main.getReferenct_id(), "0") ? "" : main.getReferenct_id());

		en.setSelltype_id(main.getBill_sell_code());
		en.setGet_selltype_id(main.getGet_sell_code());

		en.setSystem_type(main.getSystem_type());
		en.setBill_type(main.getBill_type());

		en.setDeposit_pec(main.getBill_deposit_pec());

		en.setPrice_billsid(main.getBill_promotion_id() == 0 ? "" : String.valueOf(main.getBill_promotion_id()));
		en.setBrandgroup_code(main.getBrand_groupcode());

		en.setSet_depot_id(main.getSet_department_id() == 0 ? "" : String.valueOf(main.getSet_department_id()));
		en.setGet_depot_id(main.getGet_department_id() == 0 ? "" : String.valueOf(main.getGet_department_id()));

		en.setCreate_date(dateToStr(main.getBill_create_date()));
		en.setCreate_name(main.getBill_create_name());

		en.setBill_submit_date(
				is_receive ? dateToStr(main.getBill_num_getsuredate()) : dateToStr(main.getBill_num_suredate()));
		en.setBill_submit_name(is_receive ? main.getBill_num_getsurename() : main.getBill_num_surename());

		en.setOver_date(dateToStr(main.getBill_overdate()));
		en.setOver_name(main.getBill_overname());

		en.setAccount_date(dateToStr(main.getBill_accountdate()));
		en.setBill_remark(main.getBill_remark());

		en.setBill_weather(retailInfo != null ? retailInfo.getBill_weather() : "");
		en.setVip_id(retailInfo != null
				? (StringUtil_bak.compare(retailInfo.getBill_vip_id(), "0") ? "" : retailInfo.getBill_vip_id())
				: "");
		en.setVip_cardid(retailInfo != null ? retailInfo.getBill_vipcard_id() : "");

		en.setSsums(main.getBill_ssum());

		en.setSubmit_order(main.getSubmit_order());
		en.setBack_calctype(main.getBack_calctype());
		en.setPartition_id(main.getPartition_id());
		// TODO 单据提交：以下字段没有赋值
		// en.setSet_organ_id("");
		// en.setGet_organ_id("");
		// en.dpt_company_id( main.getDPT_COMPANY_ID);
		// en.depot_group = main.DEPOT_GROUP;
		// en.depot_brandcompany = main.DEPOT_BRANDCOMPANY;
		// en.vip_depot = main.VIP_DEPOT;
		// en.vip_group = main.VIP_GROUP;
		// en.vip_company_id = main.VIP_COMPANY_ID;
		// en.vip_brandcompany = main.VIP_BRANDCOMPANY;
		// en.score_coefficient = main.SCORE_COEFFICIENT;
		// en.score_source = main.SCORE_SOURCE;

		// 退货收货问题原因
		// en.setbill_return_reason = main.BILL_RETURN_REASON;
		// 退货收货处理结果
		// en.bill_return_deal = main.BILL_RETURN_DEAL;

		// // 冲单原单号
		// en.setoriginal_id = Convert.ToInt64(main.ORIGINAL_ID).ToString();
		// // 冲单单据类型
		// en.original_type = Convert.ToInt16(main.ORIGINAL_TYPE);
		// // 冲单状态
		// en.original_state = Convert.ToInt16(main.ORIGINAL_STATE);
		// // 冲单原单顶级单号
		// en.orginal_topid = Convert.ToInt64(main.ORGINAL_TOPID);

		// 只有冲单负单才赋值冲单单号,否则冲单补充单也会按冲单处理
		// if (en.original_type == 1) {
		// en.release_id = main.ORIGINAL_ID == 0 ? "" :
		// Convert.ToInt64(main.ORIGINAL_ID).ToString();
		// } else {
		// en.release_id = "";
		// }
		//
		// if (en.account_date < en.bill_submit_date.AddDays(-365)) {
		// en.account_date = en.bill_submit_date;
		// }
		// #region 保存D表需要的数据

		// 发货地父级编号
		en.setSet_department_parent_id(main.getSet_department_parent_id());
		// 收货地父级编号
		en.setGet_department_parent_id(main.getGet_department_parent_id());

		// 单据日期
		en.setBill_setdate(dateToStr(main.getBill_setdate()));
		// 金额入账
		en.setBill_sum_sure(main.getBill_sum_sure());
		// 金额入账人
		en.setBill_sum_surename(main.getBill_sum_surename());
		// 金额入账日期
		en.setBill_sum_suredate(dateToStr(main.getBill_sum_suredate()));
		// 单据结账
		en.setBill_lock(main.getBill_lock());
		// 结账人
		en.setBill_lockname(main.getBill_lockname());
		// 结账日期
		en.setBill_lockdate(dateToStr(main.getBill_lockdate()));
		// 状态
		en.setBill_state(main.getBill_state());
		// 发货备注
		en.setBill_sellremark(main.getBill_sellremark());
		// 参考单号
		en.setBill_refid(main.getBill_refid());
		// 单据类型识别码
		en.setBill_idcode(main.getBill_idcode());
		// 单据类型
		en.setBill_selltype(main.getBill_selltype());
		// 数量入账
		en.setBill_num_sure(main.getBill_num_sure());
		// 数量入账人
		en.setBill_num_surename(main.getBill_num_surename());
		// 数量入账日期
		en.setBill_num_suredate(dateToStr(main.getBill_num_suredate()));
		// 单据建立机构
		en.setCr_department_id(main.getCr_department_id());
		// 单据处理标记
		en.setBill_editsign(main.getBill_editsign());
		// 数量收货入账
		en.setBill_num_getsure(main.getBill_num_getsure());
		// 数量收货入账人
		en.setBill_num_getsurename(main.getBill_num_getsurename());
		// 数量收货入账日期
		en.setBill_num_getsuredate(dateToStr(main.getBill_num_getsuredate()));
		// 金额收货入账
		en.setBill_sum_getsure(main.getBill_sum_getsure());
		// 金额收货入账人
		en.setBill_sum_getsurename(main.getBill_sum_getsurename());
		// 金额收货入账日期
		en.setBill_sum_getsuredate(dateToStr(main.getBill_sum_getsuredate()));
		// 收货吊牌金额
		en.setBill_getjsum(main.getBill_getjsum());
		// 收货现价金额
		en.setBill_getxsum(main.getBill_getxsum());
		// 收货结算金额
		en.setBill_getssum(main.getBill_getssum());
		// 班次
		en.setBill_shift_id(main.getBill_shift_id());
		// 提醒日期
		en.setBill_reminddate(dateToStr(main.getBill_reminddate()));
		// 单据数量
		en.setBill_num(main.getBill_num());
		// 成本金额
		en.setBill_csum(main.getBill_csum());
		// 吊牌金额
		en.setBill_jsum(main.getBill_jsum());
		// 现价金额
		en.setBill_xsum(main.getBill_xsum());
		// 款式编号
		en.setStyle_id(main.getStyle_id());
		// 借款申请单
		en.setLoan_id(main.getLoan_id());
		// 收货数量
		en.setBill_getnum(main.getBill_getnum());
		// 多收处理方式
		en.setBill_more_deal(main.getBill_more_deal());
		// 少收处理方式
		en.setBill_less_deal(main.getBill_less_deal());
		// 实销金额
		en.setBill_fsums(main.getBill_fsums());
		// 收货差异
		en.setBill_diff(main.getBill_diff());
		// 消费税率
		en.setBill_consumertax_rate(main.getBill_consumertax_rate());
		// 运费
		en.setBill_freight(main.getBill_freight());
		// 佣金提成
		en.setCommission_rate(main.getCommission_rate());
		// 消费税起征数量
		en.setBill_consumertax_num(main.getBill_consumertax_num());
		// 收回通知标识
		en.setBill_allot_sign(main.getBill_allot_sign());
		// 差异处理方式
		en.setBill_diff_type(main.getBill_diff_type());
		// 差异处理人
		en.setBill_diff_name(main.getBill_diff_name());
		// 差异处理日期
		en.setBill_diff_date(dateToStr(main.getBill_diff_date()));
		// 处理单据编号
		en.setBill_diff_bill_id(main.getBill_diff_bill_id());
		// 处理备注
		en.setBill_diff_remark(main.getBill_diff_remark());
		// 货币代码
		en.setBill_currency_code(main.getBill_currency_code());
		// 汇率
		en.setBill_exchange_rate(main.getBill_exchange_rate());
		// 退货计算机构
		en.setReturn_department_id(main.getReturn_department_id());
		// 原数量
		en.setBill_cnum(main.getBill_cnum());
		// 打印次数
		en.setBill_print_times(main.getBill_print_times());
		// 收货备注
		en.setBill_get_remark(main.getBill_get_remark());
		// 借调部门
		en.setMove_dpt(main.getMove_dpt());
		// ESB数据来源
		en.setEsb_source(main.getEsb_source());
		// 是否店长授权单据
		en.setBill_issoauth(main.getBill_issoauth());
		// 收货记账日期
		en.setBill_get_accountdate(dateToStr(main.getBill_get_accountdate()));
		// 供货单据类型
		en.setBill_supply_type(main.getBill_supply_type());
		// 供货来源单据
		en.setBill_supply_for(main.getBill_supply_for());
		// 收货是否需要传SAP
		en.setBill_get_tosap(main.getBill_get_tosap());
		// 单据自编号
		en.setBill_selfcode(main.getBill_code());
		// 款式分类
		en.setBill_style_sort(main.getBill_style_sort());
		// 是否需要传WMS
		en.setBill_towms(main.getBill_towms());
		// 是否需要传SAP
		en.setBill_tosap(main.getBill_tosap());
		// 差异处理单据
		en.setBill_isadjust(main.getBill_isadjust());
		// 差异来源单据
		en.setBill_adjust_for(main.getBill_adjust_for());
		// 同步标识
		en.setBill_sync(main.getBill_sync());
		// 库存已更新
		en.setStock_calced(main.getStock_calced());
		// 订单中心订单外部编号
		en.setBill_order_id(main.getBill_order_id());
		// 单据扩展分类1
		en.setEx_class1(main.getEx_class1());
		// 单据扩展分类2
		en.setEx_class2(main.getEx_class2());
		// 单据扩展分类3
		en.setEx_class3(main.getEx_class3());
		// 单据扩展分类4
		en.setEx_class4(main.getEx_class4());

		en.setIs_get(is_receive ? 1 : 0);

		// #endregion

		if (is_receive) {
			en.setBill_id("G".concat(en.getBill_id()));
		}

		if (en.getSystem_type() == 19) {
			en.setBill_id("P".concat(en.getBill_id()));
		} else if (en.getSystem_type() == 36) {
			en.setBill_id("S".concat(en.getBill_id()));
		}
		en.setAccount_date(en.getBill_submit_date());

		LogUtil.logger.debug("RMI实体主单转换完成..." + main.getP0670_id());

		return en;
	}

	/**
	 * 将单据实体主单转换为RMI单据提交实体主单
	 * 
	 * @param mains
	 * @param is_receive
	 * @param retailInfo
	 * @return
	 */
	public static List<RmiBillMain> billMainToRmiBillMain(List<BillMain> mains, boolean is_receive,
			BillRetailExpandInfo retailInfo) {

		List<RmiBillMain> list = new ArrayList<>();
		if (mains == null || mains.isEmpty()) {
			return list;
		}

		for (BillMain main : mains) {
			list.add(billMainToRmiBillMain(main, is_receive, retailInfo));
		}
		return list;
	}

	/**
	 * 将单据实体明细转换为RMI单据提交实体明细
	 * 
	 * @param sub
	 * @param retail
	 * @param is_receive
	 * @return
	 */
	public static RmiBillSub billSubToRmiBilSub(BillSub sub, boolean retail, boolean is_receive) {

		LogUtil.logger.debug("RMI实体明细转换开始..." + sub.getP0670_id());

		RmiBillSub en = new RmiBillSub();

		String p0670_id = String.valueOf(sub.getP0670_id());
		en.setBill_id(p0670_id);
		en.setX2_bill_id(p0670_id);

		en.setClothing_id(sub.getClothing_id());
		en.setSelltype_id(sub.getSell_class_id());
		en.setGet_selltype_id(sub.getGet_sell_code());

		en.setNums(sub.getBillsub_nums());
		en.setJ_cost(sub.getBillsub_cost());
		en.setJ_price(sub.getBillsub_jprice());

		en.setX_price(sub.getBillsub_xprice());
		en.setSell_rate(sub.getBillsub_rate());
		en.setS_price(sub.getBillsub_sprice());

		en.setBack_date(sub.getBillsub_returned_date());
		en.setBack_rate(sub.getBillsub_returned_pec());

		en.setChange_rate(sub.getChange_rate());

		en.setChange_date(sub.getChange_date());

		en.setFsums(retail ? sub.getBillsub_fsums() : sub.getBillsub_ssum());
		en.setF_price(retail ? (sub.getBillsub_nums() == 0 ? sub.getBillsub_fsums()
				: BigDecimal.valueOf(sub.getBillsub_fsums() / sub.getBillsub_nums()).setScale(2, RoundingMode.HALF_EVEN)
						.doubleValue())
				: sub.getBillsub_sprice());

		en.setVip_score(sub.getBillsub_vip_score());

		en.setPre_fee(sub.getBillsub_pre_fee());
		en.setFac_fee(sub.getBillsub_fac_fee());
		en.setPay_rate(sub.getBillsub_payrate());

		en.setVip_points(sub.getBillsub_vip_score());
		en.setUse_points(sub.getUse_score());
		en.setVip_purse(0);

		// #region 保存D表需要的数据

		// 序号
		en.setBill_order(sub.getBill_order());
		// 原数量
		en.setBillsub_cnums(sub.getBillsub_cnums());
		// 备注
		en.setBillsub_remark(sub.getBillsub_remark());
		// 结案
		en.setBillsub_over(sub.getBillsub_over());
		// 结案人
		en.setBillsub_overname(sub.getBillsub_overname());
		// 结案日期
		en.setBillsub_overdate(dateToStr(sub.getBillsub_overdate()));
		// 建立日期
		en.setBillsub_create_date(dateToStr(sub.getBillsub_create_date()));
		// 成本金额
		en.setBillsub_csum(sub.getBillsub_csum());
		// 结算金额
		en.setBillsub_ssum(sub.getBillsub_ssum());
		// 最后修改日期
		en.setBillsub_update_date(dateToStr(sub.getBillsub_update_date()));
		// 款式年份
		en.setStyle_year_code(sub.getStyle_year_code());
		// 款式季度
		en.setStyle_month_code(sub.getStyle_month_code());
		// 现金科目编号
		en.setSubject_id(sub.getSubject_id());
		// 收货数量
		en.setBillsub_getnums(sub.getBillsub_getnums());
		// 收货吊牌价
		en.setBillsub_getxprice(sub.getBillsub_getxprice());
		// 收货折扣
		en.setBillsub_getrate(sub.getBillsub_getrate());
		// 收货结算价
		en.setBillsub_getsprice(sub.getBillsub_getsprice());
		// 收货结算金额
		en.setBillsub_getssum(sub.getBillsub_getssum());
		// 加工单价
		en.setBillsub_CMTcost(sub.getBillsub_cmtcost());
		// 序号
		en.setP0671_id(sub.getP0671_id());
		// 库存已计算
		en.setBillsub_stock_calced(sub.getBillsub_stock_calced());
		// 修改过的
		en.setBillsub_modified(sub.getBillsub_modified());
		// 收货备注
		en.setBillsub_get_remark(sub.getBillsub_get_remark());
		// 赠送
		en.setBillsub_free(sub.getBillsub_free());
		// 退货
		en.setBillsub_return(sub.getBillsub_return());
		// 本单上季积分
		en.setBillsub_vip_score_past(sub.getBillsub_vip_score_past());
		// 原单记录标识
		en.setBillsub_original(sub.getBillsub_original());
		// 商场活动编号
		en.setP0320_id(sub.getP0320_id());
		// 商场扣点
		en.setAction_rate(sub.getAction_rate());
		// 不产生退货额金额
		en.setBillsub_noreturn_sum(sub.getBillsub_noreturn_sum());
		// 店铺区位编号
		en.setLocation_id(sub.getLocation_id());
		// 促销活动编号
		en.setP0742_id(sub.getP0742_id());
		// 回货期
		en.setBillsub_setdate(sub.getBillsub_setdate());
		// 参加促销标识
		en.setBillsub_promotion(sub.getBillsub_promotion());
		// 折实金额
		en.setBillsub_nett(sub.getBillsub_nett());
		// 运费分摊
		en.setBillsub_freight(sub.getBillsub_freight());
		// 退货状态
		en.setBillsub_returnstate(sub.getBillsub_returnstate());
		// 退货取件联系人
		en.setBillsub_cname(sub.getBillsub_cname());
		// 退货取件联系电话
		en.setBillsub_ctel(sub.getBillsub_ctel());
		// 退货取件地址
		en.setBillsub_address(sub.getBillsub_address());
		// 退货留言
		en.setBillsub_returnmessage(sub.getBillsub_returnmessage());
		// 物流单号
		en.setBillsub_logistics_no(sub.getBillsub_logistics_no());
		// 物流公司
		en.setBillsub_logistics(sub.getBillsub_logistics());
		// 营业员编号
		en.setClerk_id(sub.getClerk_id());
		// 收货发货类型
		en.setGet_sell_code(sub.getGet_sell_code());
		// 使用积分
		en.setUse_score(sub.getUse_score());

		// #endregion

		// 处理收货单号
		if (is_receive) {
			en.setBill_id("G" + en.getBill_id());
		}

		LogUtil.logger.debug("RMI实体明细转换完成..." + sub.getP0670_id());

		return en;
	}

	/**
	 * 将单据实体明细转换为RMI单据提交实体明细
	 * 
	 * @param subs
	 * @param retail
	 * @param is_receive
	 * @return
	 */
	public static List<RmiBillSub> billSubToRmiBilSub(List<BillSub> subs, boolean retail, boolean is_receive) {

		List<RmiBillSub> list = new ArrayList<>();
		if (subs == null || subs.isEmpty()) {
			return list;
		}

		for (BillSub sub : subs) {
			list.add(billSubToRmiBilSub(sub, retail, is_receive));
		}
		return list;
	}

	/**
	 * 结案信息转RMI单据主单实体
	 * 
	 * @param info
	 * @param req
	 * @param system_type
	 * @return
	 */
	public static RmiBillMain billOverInfoToRmiBillMain(BillOverInfo info, RequestMsg req, int system_type) {

		RmiBillMain en = new RmiBillMain();

		en.setBill_id(String.valueOf(info.getBill_id()));
		en.setX2_bill_id(String.valueOf(info.getBill_id()));
		en.setBill_remark(info.getRemark());
		en.setBill_over(1);
		en.setOver_date(dateToStr(DateUtil.getSystemTimestamp()));
		en.setOver_name(req.getUserName());
		en.setRelease_id(info.getMain() != null ? info.getMain().getReferenct_id() : "");
		en.setSystem_type(system_type);
		en.setCreate_date(dateToStr(DateUtil.getSystemTimestamp()));
		en.setCreate_name(req.getUserName());

		return en;
	}

	public static BillPrice billPriceToRmiBilPrice(B2BPrice sub, boolean is_receive) {

		LogUtil.logger.debug("RMI实体价格明细转换开始..." + sub.getBill_id());

		BillPrice en = new BillPrice();

		en.setBill_id(sub.getBill_id());
		en.setPrice_id(sub.getPrice_id());
		en.setSale_mode(sub.getSale_mode());
		en.setClothing_id(sub.getClothing_id());
		en.setGroup_id(sub.getGroup_id());
		en.setCompany_id(sub.getCompany_id());
		en.setSelltype_id(sub.getSelltype_id());
		en.setJ_cost(sub.getJ_cost());
		en.setJ_price(sub.getJ_price());
		en.setX_price(sub.getX_price());
		en.setSale_rate(sub.getSale_rate());
		en.setS_price(sub.getS_price());
		en.setBack_rate(sub.getBack_rate());
		en.setBack_date(sub.getBack_date());
		en.setChange_discount(sub.getChange_discount());
		en.setChange_rate(sub.getChange_rate());
		en.setChange_date(sub.getChange_date());
		en.setAgent_mode(sub.getAgent_mode());
		en.setAgent_rate(sub.getAgent_rate());
		en.setFlow_id(sub.getFlow_id());
		en.setB2bsell_id(sub.getB2bsell_id());
		en.setShow_price(sub.getShow_price());
		en.setReturn_ctrl(sub.getReturn_ctrl());
		en.setLevel_index(sub.getLevel_index());
		en.setIs_return(sub.getIs_return());
		en.setIs_beyound(sub.getIs_beyound());
		en.setBrandgroup_code(sub.getBrandgroup_code());
		en.setPrice_billid(sub.getPrice_billid());

		// C#提交库存中心未赋值
		// en.setNums(sub.getNums());
		// en.setSums(sub.getSums());

		// 处理收货单号
		if (is_receive) {
			en.setBill_id("G" + en.getBill_id());
		}

		return en;
	}

	public static List<BillPrice> billPriceToRmiBilPrice(List<B2BPrice> subs, boolean is_receive) {

		List<BillPrice> list = new ArrayList<>();
		if (subs == null || subs.isEmpty()) {
			return list;
		}

		for (B2BPrice main : subs) {
			list.add(billPriceToRmiBilPrice(main, is_receive));
		}
		return list;
	}

	/**
	 * 将单据实体明细转换为RMI单据提交实体明细
	 * 
	 * @param sub
	 * @param retail
	 * @param is_receive
	 * @return
	 */
	public static RmiBillSub billSubToRmiBilSub(BillSub sub, boolean retail, int system_type, boolean is_receive) {

		LogUtil.logger.debug("RMI实体明细转换开始..." + sub.getP0670_id());

		RmiBillSub en = new RmiBillSub();

		String p0670_id = String.valueOf(sub.getP0670_id());
		en.setBill_id(p0670_id);
		en.setX2_bill_id(p0670_id);

		en.setClothing_id(sub.getClothing_id());
		en.setSelltype_id(sub.getSell_class_id());
		en.setGet_selltype_id(sub.getGet_sell_code());

		en.setNums(sub.getBillsub_nums());
		en.setJ_cost(sub.getBillsub_cost());
		en.setJ_price(sub.getBillsub_jprice());

		en.setX_price(sub.getBillsub_xprice());
		en.setSell_rate(sub.getBillsub_rate());
		en.setS_price(sub.getBillsub_sprice());

		en.setBack_date(sub.getBillsub_returned_date());
		en.setBack_rate(sub.getBillsub_returned_pec());

		en.setChange_rate(sub.getChange_rate());

		en.setChange_date(sub.getChange_date());

		en.setFsums(retail ? sub.getBillsub_fsums() : sub.getBillsub_ssum());
		en.setF_price(retail ? (sub.getBillsub_nums() == 0 ? sub.getBillsub_fsums()
				: BigDecimal.valueOf(sub.getBillsub_fsums() / sub.getBillsub_nums()).setScale(2, RoundingMode.HALF_EVEN)
						.doubleValue())
				: sub.getBillsub_sprice());

		en.setVip_score(sub.getBillsub_vip_score());

		en.setPre_fee(sub.getBillsub_pre_fee());
		en.setFac_fee(sub.getBillsub_fac_fee());
		en.setPay_rate(sub.getBillsub_payrate());

		en.setVip_points(sub.getBillsub_vip_score());
		en.setUse_points(sub.getUse_score());
		en.setVip_purse(0);

		// #region 保存D表需要的数据

		// 序号
		en.setBill_order(sub.getBill_order());
		// 原数量
		en.setBillsub_cnums(sub.getBillsub_cnums());
		// 备注
		en.setBillsub_remark(sub.getBillsub_remark());
		// 结案
		en.setBillsub_over(sub.getBillsub_over());
		// 结案人
		en.setBillsub_overname(sub.getBillsub_overname());
		// 结案日期
		en.setBillsub_overdate(dateToStr(sub.getBillsub_overdate()));
		// 建立日期
		en.setBillsub_create_date(dateToStr(sub.getBillsub_create_date()));
		// 成本金额
		en.setBillsub_csum(sub.getBillsub_csum());
		// 结算金额
		en.setBillsub_ssum(sub.getBillsub_ssum());
		// 最后修改日期
		en.setBillsub_update_date(dateToStr(sub.getBillsub_update_date()));
		// 款式年份
		en.setStyle_year_code(sub.getStyle_year_code());
		// 款式季度
		en.setStyle_month_code(sub.getStyle_month_code());
		// 现金科目编号
		en.setSubject_id(sub.getSubject_id());
		// 收货数量
		en.setBillsub_getnums(sub.getBillsub_getnums());
		// 收货吊牌价
		en.setBillsub_getxprice(sub.getBillsub_getxprice());
		// 收货折扣
		en.setBillsub_getrate(sub.getBillsub_getrate());
		// 收货结算价
		en.setBillsub_getsprice(sub.getBillsub_getsprice());
		// 收货结算金额
		en.setBillsub_getssum(sub.getBillsub_getssum());
		// 加工单价
		en.setBillsub_CMTcost(sub.getBillsub_cmtcost());
		// 序号
		en.setP0671_id(sub.getP0671_id());
		// 库存已计算
		en.setBillsub_stock_calced(sub.getBillsub_stock_calced());
		// 修改过的
		en.setBillsub_modified(sub.getBillsub_modified());
		// 收货备注
		en.setBillsub_get_remark(sub.getBillsub_get_remark());
		// 赠送
		en.setBillsub_free(sub.getBillsub_free());
		// 退货
		en.setBillsub_return(sub.getBillsub_return());
		// 本单上季积分
		en.setBillsub_vip_score_past(sub.getBillsub_vip_score_past());
		// 原单记录标识
		en.setBillsub_original(sub.getBillsub_original());
		// 商场活动编号
		en.setP0320_id(sub.getP0320_id());
		// 商场扣点
		en.setAction_rate(sub.getAction_rate());
		// 不产生退货额金额
		en.setBillsub_noreturn_sum(sub.getBillsub_noreturn_sum());
		// 店铺区位编号
		en.setLocation_id(sub.getLocation_id());
		// 促销活动编号
		en.setP0742_id(sub.getP0742_id());
		// 回货期
		en.setBillsub_setdate(sub.getBillsub_setdate());
		// 参加促销标识
		en.setBillsub_promotion(sub.getBillsub_promotion());
		// 折实金额
		en.setBillsub_nett(sub.getBillsub_nett());
		// 运费分摊
		en.setBillsub_freight(sub.getBillsub_freight());
		// 退货状态
		en.setBillsub_returnstate(sub.getBillsub_returnstate());
		// 退货取件联系人
		en.setBillsub_cname(sub.getBillsub_cname());
		// 退货取件联系电话
		en.setBillsub_ctel(sub.getBillsub_ctel());
		// 退货取件地址
		en.setBillsub_address(sub.getBillsub_address());
		// 退货留言
		en.setBillsub_returnmessage(sub.getBillsub_returnmessage());
		// 物流单号
		en.setBillsub_logistics_no(sub.getBillsub_logistics_no());
		// 物流公司
		en.setBillsub_logistics(sub.getBillsub_logistics());
		// 营业员编号
		en.setClerk_id(sub.getClerk_id());
		// 收货发货类型
		en.setGet_sell_code(sub.getGet_sell_code());
		// 使用积分
		en.setUse_score(sub.getUse_score());

		// #endregion

		// 处理收货单号
		if (is_receive) {
			en.setBill_id("G" + en.getBill_id());
		}

		if (system_type == 19) {
			en.setBill_id("P" + en.getBill_id());
		} else if (system_type == 36) {
			en.setBill_id("S" + en.getBill_id());
		}

		LogUtil.logger.debug("RMI实体明细转换完成..." + sub.getP0670_id());

		return en;
	}

	/**
	 * 将单据实体明细转换为RMI单据提交实体明细
	 * 
	 * @param subs
	 * @param retail
	 * @param is_receive
	 * @return
	 */
	public static List<RmiBillSub> billSubToRmiBilSub(List<BillSub> subs, boolean retail, int system_type,
			boolean is_receive) {

		List<RmiBillSub> list = new ArrayList<>();
		if (subs == null || subs.isEmpty()) {
			return list;
		}

		for (BillSub sub : subs) {
			list.add(billSubToRmiBilSub(sub, retail, system_type, is_receive));
		}
		return list;
	}
}
