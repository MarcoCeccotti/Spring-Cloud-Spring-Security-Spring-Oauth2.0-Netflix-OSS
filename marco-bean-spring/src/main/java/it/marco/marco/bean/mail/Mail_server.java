package it.marco.marco.bean.mail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Mail_server {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column
	private String email_sender;
	
	@Column
	private String email_password;
	
	@Column
	private String addr_recipients;

	@Column
	private String addr_recipients_cc;

	@Column
	private String addr_recipients_bcc;

	@Column
	private String subject;

	@Column
	private String text;
	
	@Column
	private byte[] text_byte;

	@Column
	private Boolean sent;
	
	@Column
	private String error;
	
	@Column
	private Timestamp timest = new Timestamp(System.currentTimeMillis());
	
	@Column
	private String company;
	
	@JsonProperty("attachment")
	@Transient
	private List<Mail_attachment> attachments = new ArrayList<Mail_attachment>();
	
	public Mail_server() {}
	
	public Mail_server(String email_sender, String addr_recipients, String addr_recipients_cc, String addr_recipients_bcc, String email_password, String subject, String text, boolean sent, String error, Timestamp timest, String company) {
		
		this.email_sender = email_sender;
		this.addr_recipients = addr_recipients;
		this.addr_recipients_cc = addr_recipients_cc;
		this.addr_recipients_bcc = addr_recipients_bcc;
		this.email_password = email_password;
		this.subject = subject;
		this.text = text;
		this.sent = sent;
		this.error = error;
		this.timest = timest;
		this.company = company;
	}
	
	public Mail_server(String email_sender, String email_password, Mail_recipients mail_recipients, String subject, String text, boolean sent, String error, Timestamp timest, String company) {
		
		this.email_sender = email_sender;
		this.addr_recipients = mail_recipients.getMail_recipients();
		this.addr_recipients_cc = mail_recipients.getMail_recipients_cc();
		this.addr_recipients_bcc = mail_recipients.getMail_recipients_bcc();
		this.email_password = email_password;
		this.subject = subject;
		this.text = text;
		this.sent = sent;
		this.error = error;
		this.timest = timest;
		this.company = company;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail_sender() {
		return email_sender;
	}

	public void setEmail_sender(String email_sender) {
		this.email_sender = email_sender;
	}

	public String getEmail_password() {
		return email_password;
	}

	public void setEmail_password(String email_password) {
		this.email_password = email_password;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public byte[] getText_byte() {
		return text_byte;
	}

	public void setText_byte(byte[] text_byte) {
		this.text_byte = text_byte;
	}

	public Boolean getSent() {
		return sent;
	}

	public void setSent(Boolean sent) {
		this.sent = sent;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Timestamp getTimest() {
		return timest;
	}

	public void setTimest(Timestamp timest) {
		this.timest = timest;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<Mail_attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Mail_attachment> attachments) {
		this.attachments = attachments;
	}

	public void resetAttachments() {
    	this.attachments.clear();
    }

	public void addAttachment(Mail_attachment attachment) {
    	this.attachments.add(attachment);
    }

	public String getAddr_recipients() {
		return addr_recipients;
	}

	public void setAddr_recipients(String addr_recipients) {
		this.addr_recipients = addr_recipients;
	}

	public String getAddr_recipients_cc() {
		return addr_recipients_cc;
	}

	public void setAddr_recipients_cc(String addr_recipients_cc) {
		this.addr_recipients_cc = addr_recipients_cc;
	}

	public String getAddr_recipients_bcc() {
		return addr_recipients_bcc;
	}

	public void setAddr_recipients_bcc(String addr_recipients_bcc) {
		this.addr_recipients_bcc = addr_recipients_bcc;
	}
}
