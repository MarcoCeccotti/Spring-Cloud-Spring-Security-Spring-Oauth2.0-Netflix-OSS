package it.marco.stone.bean.stone;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tab_stone_quality {

	@Id
	@Column
	private String id;
	@Column
	private String title;
	@Column
	private char stone_type;
	@Column
	private BigDecimal specific_weight;
	
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
	
	public char getStone_type() {
		return stone_type;
	}
	
	public void setStone_type(char stone_type) {
		this.stone_type = stone_type;
	}
	
	public BigDecimal getSpecific_weight() {
		return specific_weight;
	}
	
	public void setSpecific_weight(BigDecimal specific_weight) {
		this.specific_weight = specific_weight;
	}
	
}
