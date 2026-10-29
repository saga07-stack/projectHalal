package jp.co.sss.shop.RestControllerDTO;

public class ItemTrackingDetailsDto {
	
	private Number order_id;
	private String inquiry_code;
	private String customer_name;
	private String customer_address;
	private String customer_phone;
	public Number getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Number order_id) {
		this.order_id = order_id;
	}
	public String getInquiry_code() {
		return inquiry_code;
	}
	public void setInquiry_code(String inquiry_code) {
		this.inquiry_code = inquiry_code;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCustomer_address() {
		return customer_address;
	}
	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}
	public String getCustomer_phone() {
		return customer_phone;
	}
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
	
	
	public ItemTrackingDetailsDto(Number order_id, String inquiry_code, String customer_name, String customer_address,
			String customer_phone) {
		super();
		this.order_id = order_id;
		this.inquiry_code = inquiry_code;
		this.customer_name = customer_name;
		this.customer_address = customer_address;
		this.customer_phone = customer_phone;
	}
	
	
	

	

}
