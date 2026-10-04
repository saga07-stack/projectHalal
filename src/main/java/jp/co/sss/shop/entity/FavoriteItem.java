package jp.co.sss.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorites")
public class FavoriteItem {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_favorites_gen")
	@SequenceGenerator(name = "seq_favorites_gen", sequenceName = "seq_favorites", allocationSize = 1)
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "item_id")
	private Integer itemId;

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	public Integer getUserId() { return userId; }
	public void setUserId(Integer userId) { this.userId = userId; }

	public Integer getItemId() { return itemId; }
	public void setItemId(Integer itemId) { this.itemId = itemId; }
}