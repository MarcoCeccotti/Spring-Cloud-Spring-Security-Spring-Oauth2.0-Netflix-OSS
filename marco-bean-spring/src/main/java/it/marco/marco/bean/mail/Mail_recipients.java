package it.marco.marco.bean.mail;

public class Mail_recipients {

	private String mail_recipients;
	
	private String mail_recipients_cc;
	
	private String mail_recipients_bcc;
	
	public Mail_recipients(String mail_recipients, String mail_recipients_cc, String mail_recipients_bcc) {
		
		this.mail_recipients = mail_recipients;
		this.mail_recipients_cc = mail_recipients_cc;
		this.mail_recipients_bcc = mail_recipients_bcc;
	}

	public String getMail_recipients() {
		return mail_recipients;
	}

	public void setMail_recipients(String mail_recipients) {
		this.mail_recipients = mail_recipients;
	}

	public String getMail_recipients_cc() {
		return mail_recipients_cc;
	}

	public void setMail_recipients_cc(String mail_recipients_cc) {
		this.mail_recipients_cc = mail_recipients_cc;
	}

	public String getMail_recipients_bcc() {
		return mail_recipients_bcc;
	}

	public void setMail_recipients_bcc(String mail_recipients_bcc) {
		this.mail_recipients_bcc = mail_recipients_bcc;
	}
}
