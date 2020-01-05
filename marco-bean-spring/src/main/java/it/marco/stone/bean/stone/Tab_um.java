package it.marco.stone.bean.stone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tab_um {

	@Id
	@Column
	private String id;
	@Column
	private String title;
	@Column
	private int n_dec;

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

	public int getN_dec() {
		return n_dec;
	}

	public void setN_dec(int n_dec) {
		this.n_dec = n_dec;
	}
}
