package it.marco.marco.service.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.MessagingException;

import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;

import it.marco.marco.bean.mail.Mail_recipients;
import it.marco.marco.bean.mail.Mail_server;

public interface MailService {
	
	/**
	 * Checks if, given the address of the sender, exists its email properties on db. If so, encapsulates the data in a Properties object.
	 * @param addr_sender the address of the sender used to fetch its email properties
	 * @return a Properties object if the data is present on db; null otherwise.
	 */
	public Properties fetchEmailProperties(String addr_sender);
	
	/**
	 * Gets the email properties. These properties regards only about smtp host, it's port, and it's username and password.
	 * @return the Properties object.
	 */
	public Properties getEmailProperties();
	
	/**
	 * Gets the email properties for crashlytics. Crashlytics is a reporter of application crashes, which sends an email in presence of an exception.<br/> These properties regards only about smtp host, it's port, and it's username and password.
	 * @return the Properties object.
	 */
	public Properties getEmailCrashlyticsProperties();
	
	/**
	 * Creates a new Properties object for email sending based on the values present in the hashmap passed as parameter.
	 * @param props_values the hashmap containing the mapping of the properties with the correspective values
	 * @return the Properties object.
	 */
	public Properties createOwnEmailProperties(HashMap<String, String> props_values);
	
	/**
	 * Sends an email with its attachments in a dynamic way.<br>The dynamism lies in the Properties object, where you can define the smtp server and other properties.
	 * @param props the Properties to send email, for example smtp, port and so on. They can be fetched by getEmailProperties method of this @Service or you can create new ones
	 * @param mail_server the Mail_server object, containing a possible list of Mail_attachment attachment objects
	 * @param attach_base_path the base path to fetch the attaches. It CANT be null, only empty String is permitted
	 * @throws MailAuthenticationException
	 * @throws MailSendException
	 * @throws MailException
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void sendDynamicEmail(Properties props, Mail_server mail_server, String attach_base_path)
			throws MailAuthenticationException, MailSendException, MailException, MessagingException, IOException;
	
	/**
	 * Sends an email with its attachments.<br>The difference with dynamic method is that the server mail informations are defined in application.properties file, that are "static".
	 * @param mail_server the Mail_server object, containing a list of attachments Mail_attachment
	 * @param attach_base_path the base path to fetch the attaches. It CANT be null, only empty String is permitted
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void sendEmail(Mail_server mail_server, String attach_base_path) throws MessagingException, IOException;
	
	/**
	 * Saves the email with its attachments on database. The email should be saved on db before sending it, to prevent the possible lost of the data. 
	 * @param mail_server the Mail_server object to save. The attachments must already be present in the object before calling this method
	 * @return the Mail_server object saved on db. It contains the attachments, also saved on db.
	 */
	public Mail_server saveEmail(Mail_server mail_server);
	
	/**
	 * Handles the saving and sending of the email.
	 * <br>It's execution consists of 3 steps:
	 * <br>1) Saving of the email in preliminar form, to prevent possibile lost of datas;
	 * <br>2) Sending of the email with standard email sending service;
	 * <br>3) New saving (updating indeed) of the mail based on the response of step 2).
	 * @param mail_server the mail to be handled
	 * @param attach_base_path the base path to fetch the attaches. It CANT be null, only empty String is permitted
	 * @param username the username of the sender (for the logs)
	 * @return false if something goes wrong; true otherwise.
	 */
	public boolean saveAndSendEmail(Mail_server mail_server, String attach_base_path, String username);
	
	/**
	 * Obtains the recipients of the mail.
	 * @return a Mail_properties object filled with the recipients of app mail.
	 */
	public Mail_recipients getEmailRecipients();
	
	/**
	 * Obtains the recipients of the mail.
	 * @return a Mail_properties object filled with the recipients of crashlytic mail.
	 */
	public Mail_recipients getEmailCrashlyticsRecipients();
	
	/**
	 * Gets the email address of the sender. This value by default is based on relative property in email.properties file.
	 * @return the email address String.
	 */
	public String getMail_smtp_addr_sender();

	/**
	 * Gets the email password of the sender. This value by default is based on relaive property in email.properties file.
	 * @return the email password String.
	 */
	public String getMail_smtp_addr_password();
	
	/**
	 * Adds a new mail recipient
	 * @param mail_recipient the new mail recipient to be added.
	 */
	public void addMailRecipient(String mail_recipient);
	
	/**
	 * Adds a new mail recipient in carbon copy (Cc)
	 * @param mail_recipient_cc the new mail recipient to be added in carbon copy.
	 */
	public void addMailRecipientCc(String mail_recipient_cc);
	
	/**
	 * Adds a new mail recipient in blind carbon copy (Bcc)
	 * @param mail_recipient_bcc the new mail recipient to be added in blind carbon copy.
	 */
	public void addMailRecipientBcc(String mail_recipient_bcc);
}
