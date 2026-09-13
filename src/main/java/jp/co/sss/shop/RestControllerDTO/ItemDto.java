package jp.co.sss.shop.RestControllerDTO;

import org.springframework.http.HttpStatus;

public class ItemDto {
	
	private String name;
	private String description;
	private Integer price;
	private String imagePath;
	private String categoryName;
	private Integer id;
	private Integer stock;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public ItemDto(String name, String description, Integer price, String imagePath, String categoryName, Integer id) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.imagePath = imagePath;
		this.categoryName = categoryName;
		this.id = id;
	}
	public ItemDto(String name2, String description2, Integer price2, String imagePath, Integer id2, String name3) {
		// TODO Auto-generated constructor stub
	this.name = name2;
	this.description = description2;
	this.price = price2;
	this.imagePath = imagePath;
	this.id = id2;
	this.categoryName = name3;
	
	
	
	}
	

}
