package it.marco.stone.bean.stone;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Partner_account {
	
	@Id
	private String account_id;

	private String cust_supp_account_name;

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getCust_supp_account_name() {
		return cust_supp_account_name;
	}

	public void setCust_supp_account_name(String cust_supp_account_name) {
		this.cust_supp_account_name = cust_supp_account_name;
	}
	
}
