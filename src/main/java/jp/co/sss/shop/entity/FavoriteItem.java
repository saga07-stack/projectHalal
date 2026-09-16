package jp.co.sss.shop.entity;

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
	
	
	
}
