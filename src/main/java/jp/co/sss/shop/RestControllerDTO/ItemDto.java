package jp.co.sss.shop.RestControllerDTO;

public class ItemDto {
	
	private String name;
	private String description;
	private Integer price;
	private String imagePath;
	private String categoryName;
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
	
	public ItemDto(String name, String description, Integer price, String imagePath, String categoryName) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.imagePath = imagePath;
		this.categoryName = categoryName;
	}

}
