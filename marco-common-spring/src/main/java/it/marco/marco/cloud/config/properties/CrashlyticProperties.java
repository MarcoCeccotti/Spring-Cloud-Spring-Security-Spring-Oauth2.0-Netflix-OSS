package it.marco.marco.cloud.config.properties;

import java.util.Map;

public interface CrashlyticProperties {
	
    public String getSender();
	
	public void setSender(String sender);
	
	public String getUsername();
	
	public void setUsername(String username);
	
	public String getPassword();
	
	public void setPassword(String password);
	
	public Map<String, String> getRecipients();
	
	public void setRecipients(Map<String, String> recipients);
	
	public String getRecipients_to();

	public void setRecipients_to(String recipients_to);

	public String getRecipients_cc();

	public void setRecipients_cc(String recipients_cc);

	public String getRecipients_bcc();

	public void setRecipients_bcc(String recipients_bcc);

	public Map<String, String> getSmtp();
	
	public void setSmtp(Map<String, String> smtp);

	public boolean isSmtp_auth();

	public void setSmtp_auth(boolean smtp_auth);

	public boolean isSmtp_starttls_enable();

	public void setSmtp_starttls_enable(boolean smtp_starttls_enable);

	public String getSmtp_host();

	public void setSmtp_host(String smtp_host);

	public String getSmtp_port();

	public void setSmtp_port(String smtp_port);
}
