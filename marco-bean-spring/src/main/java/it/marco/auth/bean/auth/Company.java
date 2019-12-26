package it.marco.auth.bean.auth;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.marco.marco.db.schema.DBSchema;

@Entity
@Table(schema = DBSchema.company_schema)
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "customer_id")
	@JsonProperty(value = "customer_id")
	private String customerId;

	private String dbname;

	@Column(name = "schema_name")
	@JsonProperty(value = "schema_name")
	private String schemaName;

	@Column(name = "installation_url")
	@JsonProperty(value = "installation_url")
	private String installationurl;

	private boolean active;

	@Column(name = "size_mb")
	@JsonProperty(value = "size_mb")
	private BigDecimal sizemb = new BigDecimal(0);

	@Column(name = "size_perc")
	@JsonProperty(value = "size_perc")
	private BigDecimal sizeperc = new BigDecimal(0);

	@Column(name = "db_version_string")
	@JsonProperty(value = "db_version_string")
	private String dbVersionString;

	private String theme;

	@Column(name = "template_path")
	@JsonProperty(value = "template_path")
	private String templatePath;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getInstallationurl() {
		return installationurl;
	}

	public void setInstallationurl(String installationurl) {
		this.installationurl = installationurl;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public BigDecimal getSizemb() {
		return sizemb;
	}

	public void setSizemb(BigDecimal sizemb) {
		this.sizemb = sizemb;
	}

	public BigDecimal getSizeperc() {
		return sizeperc;
	}

	public void setSizeperc(BigDecimal sizeperc) {
		this.sizeperc = sizeperc;
	}

	public String getDbVersionString() {
		return dbVersionString;
	}

	public void setDbVersionString(String dbVersionString) {
		this.dbVersionString = dbVersionString;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
}
