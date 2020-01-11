package it.marco.marco.cloud.config.properties;

import java.util.Map;

public interface MailProperties {
	
	public boolean isEnable();
	
	public void setEnable(boolean enable);
	
	public Map<String, String> getSmtp();
	
	public void setSmtp(Map<String, String> smtp);
	
	public boolean isSmtp_auth();
	
	public void setSmtp_auth(boolean smtp_auth);
	
	public boolean isSmtp_starttls_enabled();

	public void setSmtp_starttls_enabled(boolean smtp_starttls_enabled);

	public String getSmtp_addr_sender();

	public void setSmtp_addr_sender(String smtp_addr_sender);

	public String getSmtp_addr_password();

	public void setSmtp_addr_password(String smtp_addr_password);

	public String getSmtp_host();

	public void setSmtp_host(String smtp_host);

	public String getSmtp_port();
	
	public void setSmtp_port(String smtp_port);
	
	public Map<String, String> getRecipients();
	
	public void setRecipients(Map<String, String> recipients);
	
	public String getRecipients_to();
	
	public void setRecipients_to(String recipients_to);
	
	public String getRecipients_cc();
	
	public void setRecipients_cc(String recipients_cc);
	
	public String getRecipients_bcc();
	
	public void setRecipients_bcc(String recipients_bcc);
}
