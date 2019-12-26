package it.marco.marco.bean.mail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Mail_properties {
	
	@Id
	@Column(name = "addr_sender")
	private String addrSender;
	
	@Column
	private boolean mail_smtp_auth;

	@Column
	private boolean mail_smtp_starttls_enable;

	@Column
	private String mail_smtp_host;

	@Column
	private String mail_smtp_port;
	
	public Mail_properties() {}
	
	public Mail_properties(String addr_sender, boolean mail_smtp_auth, boolean mail_smtp_starttls_enable, String mail_smtp_host, String mail_smtp_port) {
		
		this.addrSender = addr_sender;
		this.mail_smtp_auth = mail_smtp_auth;
		this.mail_smtp_host = mail_smtp_host;
		this.mail_smtp_port = mail_smtp_port;
		this.mail_smtp_starttls_enable = mail_smtp_starttls_enable;
	}

	public String getAddrSender() {
		return addrSender;
	}

	public void setAddrSender(String addrSender) {
		this.addrSender = addrSender;
	}

	public boolean getMail_smtp_auth() {
		return mail_smtp_auth;
	}

	public void setMail_smtp_auth(boolean mail_smtp_auth) {
		this.mail_smtp_auth = mail_smtp_auth;
	}

	public boolean getMail_smtp_starttls_enable() {
		return mail_smtp_starttls_enable;
	}

	public void setMail_smtp_starttls_enable(boolean mail_smtp_starttls_enable) {
		this.mail_smtp_starttls_enable = mail_smtp_starttls_enable;
	}

	public String getMail_smtp_host() {
		return mail_smtp_host;
	}

	public void setMail_smtp_host(String mail_smtp_host) {
		this.mail_smtp_host = mail_smtp_host;
	}

	public String getMail_smtp_port() {
		return mail_smtp_port;
	}

	public void setMail_smtp_port(String mail_smtp_port) {
		this.mail_smtp_port = mail_smtp_port;
	}
}
