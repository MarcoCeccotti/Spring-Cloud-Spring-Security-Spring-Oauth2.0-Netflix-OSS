package it.marco.stone.bean.stone;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tab_stone_type {

	@Id
	@Column
	private String code;
	
	@Column
	private String title;
	
	@Column
	private BigDecimal specific_weight;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getSpecific_weight() {
		return specific_weight;
	}

	public void setSpecific_weight(BigDecimal specific_weight) {
		this.specific_weight = specific_weight;
	}
	
}
