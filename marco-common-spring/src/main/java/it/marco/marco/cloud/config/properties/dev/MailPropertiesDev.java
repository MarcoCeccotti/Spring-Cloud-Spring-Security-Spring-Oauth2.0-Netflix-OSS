package it.marco.marco.cloud.config.properties.dev;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.MailProperties;

@Component
@ConfigurationProperties(prefix = "mail")
@Profile("dev")
@PropertySource("classpath:dev/mail.properties")
public class MailPropertiesDev implements MailProperties {
	
	private boolean enable;
	
	private Map<String, String> smtp;
	
	private boolean smtp_auth;
	private boolean smtp_starttls_enabled;
	private String smtp_addr_sender;
	private String smtp_addr_password;
	private String smtp_host;
	private String smtp_port;
	
	private Map<String, String> recipients;
	
	private String recipients_to;
	private String recipients_cc;
	private String recipients_bcc;
	
	public boolean isEnable() {
		return enable;
	}
	
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public Map<String, String> getSmtp() {
		return smtp;
	}
	
	public void setSmtp(Map<String, String> smtp) {
		this.smtp = smtp;
		
		this.setSmtp_auth(Boolean.getBoolean(this.smtp.get("auth")));
		this.setSmtp_starttls_enabled(Boolean.getBoolean(this.smtp.get("starttls.enabled")));
		this.setSmtp_addr_sender(this.smtp.get("addr.sender"));
		this.setSmtp_addr_password(this.smtp.get("addr.password"));
		this.setSmtp_host(this.smtp.get("host"));
		this.setSmtp_port(this.smtp.get("port"));
	}
	
	public boolean isSmtp_auth() {
		return smtp_auth;
	}
	
	public void setSmtp_auth(boolean smtp_auth) {
		this.smtp_auth = smtp_auth;
	}
	
	public boolean isSmtp_starttls_enabled() {
		return smtp_starttls_enabled;
	}

	public void setSmtp_starttls_enabled(boolean smtp_starttls_enabled) {
		this.smtp_starttls_enabled = smtp_starttls_enabled;
	}

	public String getSmtp_addr_sender() {
		return smtp_addr_sender;
	}

	public void setSmtp_addr_sender(String smtp_addr_sender) {
		this.smtp_addr_sender = smtp_addr_sender;
	}

	public String getSmtp_addr_password() {
		return smtp_addr_password;
	}

	public void setSmtp_addr_password(String smtp_addr_password) {
		this.smtp_addr_password = smtp_addr_password;
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
}
