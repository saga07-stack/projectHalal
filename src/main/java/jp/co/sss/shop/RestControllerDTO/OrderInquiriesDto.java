package jp.co.sss.shop.RestControllerDTO;

import java.sql.Date;

public class OrderInquiriesDto {

	private Integer id;
	private Integer orderId;
	private String inquiryCode;
	private String customerName;
	private String customerPhone;
	private String customerAddress;
	private String memo;
	private Integer sentBy;
	private Date sentAt;
	private Date arrivedAt;
	private Integer cancelStatus;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getInquiryCode() {
		return inquiryCode;
	}
	public void setInquiryCode(String inquiryCode) {
		this.inquiryCode = inquiryCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getSentBy() {
		return sentBy;
	}
	public void setSentBy(Integer sentBy) {
		this.sentBy = sentBy;
	}
	public Date getSentAt() {
		return sentAt;
	}
	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}
	public Date getArrivedAt() {
		return arrivedAt;
	}
	public void setArrivedAt(Date arrivedAt) {
		this.arrivedAt = arrivedAt;
	}
	public Integer getCancelStatus() {
		return cancelStatus;
	}
	public void setCancelStatus(Integer cancelStatus) {
		this.cancelStatus = cancelStatus;
	}
	
	
}
