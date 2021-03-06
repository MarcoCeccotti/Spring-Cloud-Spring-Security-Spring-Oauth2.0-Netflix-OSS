package it.marco.marco.cloud.config.properties;

public class ApiProperties {
	
	private String basePackage;
	
	private String title;
	
	private String description;
	
	private String version;
	
	private String antPathAuthenticated;

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAntPathAuthenticated() {
		return antPathAuthenticated;
	}

	public void setAntPathAuthenticated(String antPathAuthenticated) {
		this.antPathAuthenticated = antPathAuthenticated;
	}
}
