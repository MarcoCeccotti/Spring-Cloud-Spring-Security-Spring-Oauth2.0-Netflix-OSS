package it.marco.marco.cloud.config.properties.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.ApiProperties;

@Component
@ConfigurationProperties(prefix = "api")
@Profile("test")
@PropertySource("classpath:test/api.properties")
public class ApiPropertiesTest implements ApiProperties {
	
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
