package ruking.dto;

public class GlobalCatDTO {
	private Integer id;
	private String catName;
	private String productIDs;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getProductIDs() {
		return productIDs;
	}
	public void setProductIDs(String productIDs) {
		this.productIDs = productIDs;
	}

}
