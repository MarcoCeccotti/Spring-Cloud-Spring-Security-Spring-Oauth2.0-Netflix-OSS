package it.marco.stone.bean.stone;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tab_exchange {

	@Id
	@Column
	private String id;
	@Column
	private String title;
	@Column
	private BigDecimal exchange_last_value;
	@Column
	private Date exchange_last_data;
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
	
	public BigDecimal getExchange_last_value() {
		return exchange_last_value;
	}
	
	public void setExchange_last_value(BigDecimal exchange_last_value) {
		this.exchange_last_value = exchange_last_value;
	}
	
	public Date getExchange_last_data() {
		return exchange_last_data;
	}
	
	public void setExchange_last_data(Date exchange_last_data) {
		this.exchange_last_data = exchange_last_data;
	}
	
	public int getN_dec() {
		return n_dec;
	}
	
	public void setN_dec(int n_dec) {
		this.n_dec = n_dec;
	}
	
}
