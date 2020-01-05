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
public class Stone_log {

	@Id
	@Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private Timestamp time_st;

	@Column
	private String username;

	@Column
	private String session;

	@Column
	private String action_name;

	@Column
	private String action_method;
	
	@Column
	private String error;

	@Column
	private String log_json;
	
	public Stone_log() {}
	
	public Stone_log(Timestamp time_st, String username, String session, String action_name, String action_method, String error, String log_json) {
		this.time_st = time_st;
		this.username = username;
		this.session = session;
		this.action_name = action_name;
		this.action_method = action_method;
		this.error = error;
		this.log_json = log_json;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getTime_st() {
		return time_st;
	}

	public void setTime_st(Timestamp time_st) {
		this.time_st = time_st;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getAction_name() {
		return action_name;
	}

	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}

	public String getAction_method() {
		return action_method;
	}

	public void setAction_method(String action_method) {
		this.action_method = action_method;
	}

	public String getLog_json() {
		return log_json;
	}

	public void setLog_json(String log_json) {
		this.log_json = log_json;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
