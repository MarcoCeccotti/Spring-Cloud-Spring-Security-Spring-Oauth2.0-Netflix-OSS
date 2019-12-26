package it.marco.stone.bean.stone;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Coan_divisioni {

	@Id
	private String id;
	
	private String title;
	
	private Boolean margine;

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

	public Boolean getMargine() {
		return margine;
	}

	public void setMargine(Boolean margine) {
		this.margine = margine;
	}
}
