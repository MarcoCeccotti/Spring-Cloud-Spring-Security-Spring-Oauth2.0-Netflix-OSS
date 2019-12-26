package it.marco.stone.bean.stone;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tab_stone_selection {
	
	@Id
	private String id;
	
	private String title;
	
	private String stone_type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStone_type() {
		return stone_type;
	}

	public void setStone_type(String stone_type) {
		this.stone_type = stone_type;
	}
}