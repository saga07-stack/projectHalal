package jp.co.sss.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItems {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cart_items")
	@SequenceGenerator(name = "seq_cart_items", sequenceName = "seq_cart_items", allocationSize = 1)
	private Integer id;
	
	
	@Column (name =  "user_id")
	private Integer userId;
	
	
	@Column( name = "item_id")
	private Integer itemId;
	
	@Column (name = "quantity")
	private Integer quantity;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
	
	
	
	
	
}
