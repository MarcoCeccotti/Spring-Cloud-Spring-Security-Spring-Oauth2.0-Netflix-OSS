package it.marco.products.bean.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.marco.marco.db.schema.DBSchema;

@Entity
@Table(schema = DBSchema.company_schema)
public class Shopping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String username;
	
	@Column(name = "product_id")
	private int productId;
	
	private boolean bought;
	
	public Shopping() {}
	
	public Shopping(String username, boolean bought, int productId) {
		this.username = username;
		this.bought = bought;
		this.productId = productId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public boolean isBought() {
		return bought;
	}

	public void setBought(boolean bought) {
		this.bought = bought;
	}
}
