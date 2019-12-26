package it.marco.marco.bean.log;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.marco.marco.db.schema.DBSchema;

@Entity
@Table(schema = DBSchema.company_schema_log)
public class Login {

	@Id
	@Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String username;

	@Column
	private Timestamp login_time = new Timestamp(System.currentTimeMillis());

	@Column
	private String session_id;

	@Column
	private String ip;

	@Column
	private String user_agent;

	@Column
	private String hostname;
	
	@Column
	private Timestamp logout_time = new Timestamp(System.currentTimeMillis());
	
	@Column
	private String logout_type;
	
	public Login() {}
	
	/**
	 * Login constructor
	 */
	public Login(String username, String session_id, String ip, String user_agent, String hostname) {
		this.username = username;
		this.session_id = session_id;
		this.ip = ip;
		this.user_agent = user_agent;
		this.hostname = hostname;
		this.logout_time = null;
		this.logout_type = null;
	}
	
	/**
	 * Logout constructor
	 */
	public Login(String username, String session_id, String ip, String user_agent, String hostname, String logout_type) {
		this.username = username;
		this.session_id = session_id;
		this.ip = ip;
		this.user_agent = user_agent;
		this.hostname = hostname;
		this.logout_type = logout_type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getLogin_time() {
		return login_time;
	}

	public void setLogin_time(Timestamp login_time) {
		this.login_time = login_time;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Timestamp getLogout_time() {
		return logout_time;
	}

	public void setLogout_time(Timestamp logout_time) {
		this.logout_time = logout_time;
	}

	public String getLogout_type() {
		return logout_type;
	}

	public void setLogout_type(String logout_type) {
		this.logout_type = logout_type;
	}
}
