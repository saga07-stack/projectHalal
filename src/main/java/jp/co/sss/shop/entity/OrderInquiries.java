package jp.co.sss.shop.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_inquiries")
public class OrderInquiries {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inquiry_seq")
	@SequenceGenerator(name ="inquiry_seq", sequenceName ="order_inquiries_seq", allocationSize = 1)
	private Integer id;
	
	
	
	
	@Column(name = "inquiry_code")
	private String inquiryCode;
	@Column(name = "order_id")
	private Integer orderId;
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "customer_phone")
	private String customerPhone;
	@Column (name = "sent_by")
	private Integer sentBy;
	@Column(name = "customer_address")
	private String  customerAddress;
	@Column(name = "memo")
	private String memo;
	@Column(name = "sent_at")
	private Date sentAt;
	@Column(name = "expires_at")
	private Date expiresAt;
	@Column(name = "used_flag")
	private Integer usedFlag;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Date getSentAt() {
		return sentAt;
	}
	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}
	public Date getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}
	public Integer getUsedFlag() {
		return usedFlag;
	}
	public void setUsedFlag(Integer usedFlag) {
		this.usedFlag = usedFlag;
	}
	public Integer getSentBy() {
		return sentBy;
	}
	public void setSentBy(Integer sentBy) {
		this.sentBy = sentBy;
	}

	
	
	

}
