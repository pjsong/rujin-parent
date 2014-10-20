package ruking.dto;

public class AttributeDTO {
	private String id;
	private String productId = "";
	private String attrName = "";;
	private String attrValue = "";
	private String displayOrder;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public String getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	};


}
