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
public class Scheduler_log {

	@Id
	@Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String scheduler_name;

	@Column
	private String action_name;

	@Column
	private String action_method;

	@Column
	private String log_json;

	@Column
	private Timestamp time_st;
	
	public Scheduler_log() {}
	
	public Scheduler_log(String action_method, String action_name, String log_json, String scheduler_name, Timestamp time_st) {
		this.time_st = time_st;
		this.scheduler_name = scheduler_name;
		this.action_name = action_name;
		this.action_method = action_method;
		this.log_json = log_json;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getScheduler_name() {
		return scheduler_name;
	}

	public void setScheduler_name(String scheduler_name) {
		this.scheduler_name = scheduler_name;
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

	public Timestamp getTime_st() {
		return time_st;
	}

	public void setTime_st(Timestamp time_st) {
		this.time_st = time_st;
	}
}
