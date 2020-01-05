package it.marco.marco.cloud.config.properties;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cors-filter")
@PropertySource("classpath:cors_filter.properties")
public class CorsFilterProperties {
	
	private boolean allowCredentials;
	
	private Map<String, String> allowed;
	
	private List<String> allowedOrigins;
	private List<String> allowedHeaders;
	private List<String> allowedMethods;
	
	private int maxAge;
	
	private String pathConfiguration;

	public boolean isAllowCredentials() {
		return allowCredentials;
	}

	public void setAllowCredentials(boolean allowCredentials) {
		this.allowCredentials = allowCredentials;
	}

	public Map<String, String> getAllowed() {
		return allowed;
	}

	public void setAllowed(Map<String, String> allowed) {
		this.allowed = allowed;

		this.setAllowedOrigins(Arrays.asList(this.allowed.get("origins")));
		this.setAllowedHeaders(Arrays.asList(this.allowed.get("headers")));
		this.setAllowedMethods(Arrays.asList(this.allowed.get("methods")));
	}

	public List<String> getAllowedOrigins() {
		return allowedOrigins;
	}

	public void setAllowedOrigins(List<String> allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}

	public List<String> getAllowedHeaders() {
		return allowedHeaders;
	}

	public void setAllowedHeaders(List<String> allowedHeaders) {
		this.allowedHeaders = allowedHeaders;
	}

	public List<String> getAllowedMethods() {
		return allowedMethods;
	}

	public void setAllowedMethods(List<String> allowedMethods) {
		this.allowedMethods = allowedMethods;
	}

	public String getPathConfiguration() {
		return pathConfiguration;
	}

	public void setPathConfiguration(String pathConfiguration) {
		this.pathConfiguration = pathConfiguration;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}
}
