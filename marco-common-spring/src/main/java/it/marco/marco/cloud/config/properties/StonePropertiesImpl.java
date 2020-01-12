package it.marco.marco.cloud.config.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "stone")
@PropertySource("classpath:stone.properties")
public class StonePropertiesImpl implements StoneProperties {
	
	private String serverUrl;
	
	private Map<String, String> file;
	
	private String file_path_base;
	private String file_path_documents;
	private String file_path_images;
	
	public String getServerUrl() {
		return serverUrl;
	}
	
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	public Map<String, String> getFile() {
		return file;
	}
	
	public void setFile(Map<String, String> file) {
		this.file = file;
		
		this.setFile_path_base(this.file.get("path.base"));
		this.setFile_path_documents(this.file.get("path.documents"));
		this.setFile_path_images(this.file.get("path.images"));
	}
	
	public String getFile_path_base() {
		return file_path_base;
	}
	
	public void setFile_path_base(String file_path_base) {
		this.file_path_base = file_path_base;
	}
	
	public String getFile_path_documents() {
		return file_path_documents;
	}
	
	public void setFile_path_documents(String file_path_documents) {
		this.file_path_documents = file_path_documents;
	}
	
	public String getFile_path_images() {
		return file_path_images;
	}
	
	public void setFile_path_images(String file_path_images) {
		this.file_path_images = file_path_images;
	}
}
