package com.mos.bsd.domain;

import java.io.Serializable;
import java.sql.Date;
/**
 * 零售单临时表实体类
 * @author hao
 *
 */
public class SaleOrderEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String dataId;
	private String orderNo;
	private String corpNo;
	private String customerNo;
	private String storeNo;
	private String deliveryStoreNo;
	private String billDate;
	private String saleDate;
	private String saleTime;
	private String relativeOrderNo;
	private String orderStatus;
	private String billSource;
	private String orderType;
	private String sellType;
	private String o2oType;
	private String clerkId;
	private String deliveryClerkId;
	private String posCode;
	private String discountCoupon;
	private String memberId;
	private String exchangePoint;
	private String exchangeAmount;
	private String isBirthdayConsume;
	private String isBirthdayDiscount;
	private String saleNum;
	private String saleAmount;
	private String carryDown;
	private String createUser;
	private String remark;
	private String saleOrderPaymentDTOs;
	private String saleOrderDtlDTOs;
	private String saleOrderExtDTO;
	private String validFlag;
	private String couponsNo;
	private String status;
	private Date createDate;
	private Date updateDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCorpNo() {
		return corpNo;
	}
	public void setCorpNo(String corpNo) {
		this.corpNo = corpNo;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getDeliveryStoreNo() {
		return deliveryStoreNo;
	}
	public void setDeliveryStoreNo(String deliveryStoreNo) {
		this.deliveryStoreNo = deliveryStoreNo;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public String getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}
	public String getRelativeOrderNo() {
		return relativeOrderNo;
	}
	public void setRelativeOrderNo(String relativeOrderNo) {
		this.relativeOrderNo = relativeOrderNo;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getBillSource() {
		return billSource;
	}
	public void setBillSource(String billSource) {
		this.billSource = billSource;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getSellType() {
		return sellType;
	}
	public void setSellType(String sellType) {
		this.sellType = sellType;
	}
	public String getO2oType() {
		return o2oType;
	}
	public void setO2oType(String o2oType) {
		this.o2oType = o2oType;
	}
	public String getClerkId() {
		return clerkId;
	}
	public void setClerkId(String clerkId) {
		this.clerkId = clerkId;
	}
	public String getDeliveryClerkId() {
		return deliveryClerkId;
	}
	public void setDeliveryClerkId(String deliveryClerkId) {
		this.deliveryClerkId = deliveryClerkId;
	}
	public String getPosCode() {
		return posCode;
	}
	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}
	public String getDiscountCoupon() {
		return discountCoupon;
	}
	public void setDiscountCoupon(String discountCoupon) {
		this.discountCoupon = discountCoupon;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getExchangePoint() {
		return exchangePoint;
	}
	public void setExchangePoint(String exchangePoint) {
		this.exchangePoint = exchangePoint;
	}
	public String getExchangeAmount() {
		return exchangeAmount;
	}
	public void setExchangeAmount(String exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}
	public String getIsBirthdayConsume() {
		return isBirthdayConsume;
	}
	public void setIsBirthdayConsume(String isBirthdayConsume) {
		this.isBirthdayConsume = isBirthdayConsume;
	}
	public String getIsBirthdayDiscount() {
		return isBirthdayDiscount;
	}
	public void setIsBirthdayDiscount(String isBirthdayDiscount) {
		this.isBirthdayDiscount = isBirthdayDiscount;
	}
	public String getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(String saleNum) {
		this.saleNum = saleNum;
	}
	public String getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(String saleAmount) {
		this.saleAmount = saleAmount;
	}
	public String getCarryDown() {
		return carryDown;
	}
	public void setCarryDown(String carryDown) {
		this.carryDown = carryDown;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSaleOrderPaymentDTOs() {
		return saleOrderPaymentDTOs;
	}
	public void setSaleOrderPaymentDTOs(String saleOrderPaymentDTOs) {
		this.saleOrderPaymentDTOs = saleOrderPaymentDTOs;
	}
	public String getSaleOrderDtlDTOs() {
		return saleOrderDtlDTOs;
	}
	public void setSaleOrderDtlDTOs(String saleOrderDtlDTOs) {
		this.saleOrderDtlDTOs = saleOrderDtlDTOs;
	}
	public String getSaleOrderExtDTO() {
		return saleOrderExtDTO;
	}
	public void setSaleOrderExtDTO(String saleOrderExtDTO) {
		this.saleOrderExtDTO = saleOrderExtDTO;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	public String getCouponsNo() {
		return couponsNo;
	}
	public void setCouponsNo(String couponsNo) {
		this.couponsNo = couponsNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
}
