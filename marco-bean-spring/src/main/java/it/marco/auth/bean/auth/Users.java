package it.marco.auth.bean.auth;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.marco.marco.db.schema.DBSchema;

@Entity
@Table(schema = DBSchema.company_schema)
public class Users {

	@Column
	private String username;
	@Column
	private String passwd;
	@Column
	private String dbname;
	@Column
	private String schema_name;
	@Column
	private int partner;
	@Column
	private int id;
	@Column
	private String company_id;
	@Column
	private int com_partner_id;
	@Column
	private String pswhash;
	@Column
	private String email;
	@Column
	private Boolean newsletter;
	@Column
	private Boolean exclude_log;
	@Column
	private String roles_text = "USR";
	@Id
	@Column(name = "user_tag")
	@JsonProperty(value = "user_tag")
	private String userTag;
	@Column
	private Integer company_branch_id;
	@Column
	private Integer company_branch_desk_id;
	@Column
	private String customer_id;
	@Column
	private String supplier_id;
	@Column
	private Boolean debug;
	@Column
	private String facebook_id;
	@Column
	private String theme;
	@Column
	private String template_path;
	@Column
	private Boolean disabled = false;
	@Column
	private Boolean batchonly;
	@Column
	private Date ps_date_expiry;
	@Column
	private Boolean property_data_rights;
	@Column
	private Boolean privacy_authorization;
	@Column(name = "pwd_spring")
	private String password;
	
	public String getUserTag() {
		return userTag;
	}
	public void setUserTag(String userTag) {
		this.userTag = userTag;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	public String getSchema_name() {
		return schema_name;
	}
	public void setSchema_name(String schema_name) {
		this.schema_name = schema_name;
	}
	public int getPartner() {
		return partner;
	}
	public void setPartner(int partner) {
		this.partner = partner;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public int getCom_partner_id() {
		return com_partner_id;
	}
	public void setCom_partner_id(int com_partner_id) {
		this.com_partner_id = com_partner_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String pswhash) {
		this.password = pswhash;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean isNewsletter() {
		return newsletter;
	}
	public void setNewsletter(Boolean newsletter) {
		this.newsletter = newsletter;
	}
	public Boolean isExclude_log() {
		return exclude_log;
	}
	public void setExclude_log(Boolean exclude_log) {
		this.exclude_log = exclude_log;
	}
	public String getRoles_text() {
		return roles_text;
	}
	public void setRoles_text(String roles_text) {
		this.roles_text = roles_text;
	}
	public Integer getCompany_branch_id() {
		return company_branch_id;
	}
	public Integer getCompany_branch_desk_id() {
		return company_branch_desk_id;
	}
	public void setCompany_branch_desk_id(Integer company_branch_desk_id) {
		this.company_branch_desk_id = company_branch_desk_id;
	}
	public void setCompany_branch_id(Integer company_branch_id) {
		this.company_branch_id = company_branch_id;
	}
	public void setCompany_branch_desk_id(int company_branch_desk_id) {
		this.company_branch_desk_id = company_branch_desk_id;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}
	public Boolean isDebug() {
		return debug;
	}
	public void setDebug(Boolean debug) {
		this.debug = debug;
	}
	public String getFacebook_id() {
		return facebook_id;
	}
	public void setFacebook_id(String facebook_id) {
		this.facebook_id = facebook_id;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getTemplate_path() {
		return template_path;
	}
	public void setTemplate_path(String template_path) {
		this.template_path = template_path;
	}
	public Boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	public Boolean isBatchonly() {
		return batchonly;
	}
	public void setBatchonly(Boolean batchonly) {
		this.batchonly = batchonly;
	}
	public Date getPs_date_expiry() {
		return ps_date_expiry;
	}
	public void setPs_date_expiry(Date ps_date_expiry) {
		this.ps_date_expiry = ps_date_expiry;
	}
	public Boolean getProperty_data_rights() {
		return property_data_rights;
	}
	public void setProperty_data_rights(Boolean property_data_rights) {
		this.property_data_rights = property_data_rights;
	}
	public Boolean getPrivacy_authorization() {
		return privacy_authorization;
	}
	public void setPrivacy_authorization(Boolean privacy_authorization) {
		this.privacy_authorization = privacy_authorization;
	}
	
}