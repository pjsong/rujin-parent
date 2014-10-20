package ruking.dto;

import java.io.Serializable;

public class ProductDTO  implements Serializable{
	private String id;
	private String title = "";
	private String description = "";;
	private String catID="";
	
	
	public String getCatID() {
		return catID;
	}
	public void setCatID(String catID) {
		this.catID = catID;
	}
	public String getId() {
		return id;
	}
	public void setId(String string) {
		this.id = string;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
