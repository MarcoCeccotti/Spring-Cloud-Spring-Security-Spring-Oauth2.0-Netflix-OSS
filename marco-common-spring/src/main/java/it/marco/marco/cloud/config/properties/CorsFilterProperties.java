package it.marco.marco.cloud.config.properties;

import java.util.List;
import java.util.Map;

public interface CorsFilterProperties {

	public boolean isAllowCredentials();

	public void setAllowCredentials(boolean allowCredentials);

	public Map<String, String> getAllowed();

	public void setAllowed(Map<String, String> allowed);

	public List<String> getAllowedOrigins();

	public void setAllowedOrigins(List<String> allowedOrigins);

	public List<String> getAllowedHeaders();

	public void setAllowedHeaders(List<String> allowedHeaders);

	public List<String> getAllowedMethods();

	public void setAllowedMethods(List<String> allowedMethods);

	public String getPathConfiguration();

	public void setPathConfiguration(String pathConfiguration);

	public int getMaxAge();

	public void setMaxAge(int maxAge);
}
