package it.marco.auth.bean.auth;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.marco.marco.db.schema.DBSchema;

@Entity
@Table(schema = DBSchema.company_schema)
public class User_banned {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column
	private String ip;
	
	@Column
	private String username;
	
	@Column
	private String token;
	
	@Column
	private String reason;
	
	@Column
	private Timestamp time = new Timestamp(System.currentTimeMillis());
	
	public User_banned() {}
	
	public User_banned(String username, String token, String ip, String reason) {
		this.username = username;
		this.token = token;
		this.ip = ip;
		this.reason = reason;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Timestamp getInsert_time() {
		return time;
	}

	public void setInsert_time(Timestamp time) {
		this.time = time;
	}
	
}