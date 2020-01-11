package it.marco.marco.cloud.config.properties;

import java.util.Map;

public interface StoneProperties {
	
	public String getServerUrl();
	
	public void setServerUrl(String serverUrl);
	
	public Map<String, String> getFile();
	
	public void setFile(Map<String, String> file);
	
	public String getFile_path_base();
	
	public void setFile_path_base(String file_path_base);
	
	public String getFile_path_documents();
	
	public void setFile_path_documents(String file_path_documents);
	
	public String getFile_path_images();
	
	public void setFile_path_images(String file_path_images);
}
