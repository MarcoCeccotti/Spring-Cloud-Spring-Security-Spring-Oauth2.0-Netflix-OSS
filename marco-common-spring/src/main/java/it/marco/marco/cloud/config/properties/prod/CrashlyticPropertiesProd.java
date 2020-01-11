package it.marco.marco.cloud.config.properties.prod;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.CrashlyticProperties;

@Component
@ConfigurationProperties(prefix = "crashlytic")
@Profile("prod")
@PropertySource("classpath:prod/crashlytic.properties")
public class CrashlyticPropertiesProd implements CrashlyticProperties {
	
	private String sender;
	
	private String username;
	
	private String password;
	
	private Map<String, String> recipients;

	private String recipients_to;
	private String recipients_cc;
	private String recipients_bcc;
	
	private Map<String, String> smtp;
	
    private boolean smtp_auth;
    private boolean smtp_starttls_enable;
    private String smtp_host;
    private String smtp_port;
	
    public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Map<String, String> getRecipients() {
		return recipients;
	}
	
	public void setRecipients(Map<String, String> recipients) {
		this.recipients = recipients;
		
		this.setRecipients_to(this.recipients.get("to"));
		this.setRecipients_cc(this.recipients.get("cc"));
		this.setRecipients_bcc(this.recipients.get("bcc"));
	}
	
	public String getRecipients_to() {
		return recipients_to;
	}

	public void setRecipients_to(String recipients_to) {
		this.recipients_to = recipients_to;
	}

	public String getRecipients_cc() {
		return recipients_cc;
	}

	public void setRecipients_cc(String recipients_cc) {
		this.recipients_cc = recipients_cc;
	}

	public String getRecipients_bcc() {
		return recipients_bcc;
	}

	public void setRecipients_bcc(String recipients_bcc) {
		this.recipients_bcc = recipients_bcc;
	}

	public Map<String, String> getSmtp() {
		return smtp;
	}
	
	public void setSmtp(Map<String, String> smtp) {
		this.smtp = smtp;
	}

	public boolean isSmtp_auth() {
		return smtp_auth;
	}

	public void setSmtp_auth(boolean smtp_auth) {
		this.smtp_auth = smtp_auth;
	}

	public boolean isSmtp_starttls_enable() {
		return smtp_starttls_enable;
	}

	public void setSmtp_starttls_enable(boolean smtp_starttls_enable) {
		this.smtp_starttls_enable = smtp_starttls_enable;
		
		this.setSmtp_auth(Boolean.getBoolean(this.smtp.get("auth")));
		this.setSmtp_starttls_enable(Boolean.getBoolean(this.smtp.get("starttls.enable")));
		this.setSmtp_host(this.smtp.get("host"));
		this.setSmtp_port(this.smtp.get("port"));
	}

	public String getSmtp_host() {
		return smtp_host;
	}

	public void setSmtp_host(String smtp_host) {
		this.smtp_host = smtp_host;
	}

	public String getSmtp_port() {
		return smtp_port;
	}

	public void setSmtp_port(String smtp_port) {
		this.smtp_port = smtp_port;
	}
}
