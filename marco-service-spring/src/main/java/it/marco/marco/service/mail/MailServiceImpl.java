package it.marco.marco.service.mail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import it.marco.marco.bean.mail.Mail_attachment;
import it.marco.marco.bean.mail.Mail_properties;
import it.marco.marco.bean.mail.Mail_recipients;
import it.marco.marco.bean.mail.Mail_server;
import it.marco.marco.cloud.configuration.properties.CrashlyticProperties;
import it.marco.marco.cloud.configuration.properties.MailProperties;
import it.marco.marco.dao.mail.Mail_attachment_DAO;
import it.marco.marco.dao.mail.Mail_properties_DAO;
import it.marco.marco.dao.mail.Mail_server_DAO;
import it.marco.marco.service.log.LogService;
import it.marco.marco.utils.utilities.LogUtils;

@Service
public class MailServiceImpl implements MailService {
	
	@Inject
	private Mail_server_DAO mail_server_DAO;
	@Inject
	private Mail_attachment_DAO mail_attachment_DAO;
	@Inject
	private Mail_properties_DAO mail_properties_DAO;
	
	@Inject
	private LogService logService;
	
    @Inject
    private JavaMailSender sender;
    
    @Inject
    private MailProperties mailProps;
    @Inject
    private CrashlyticProperties crashProps;
    
    @PostConstruct
    public void init() {
    	
    	// controllo sia per l'invio mail standard che per crashlytic che i dati prelevati dal file email.properties esistono o meno nel db
    	// in caso negativo i dati delle properties vengono salvati nel db
    	
    	if(!mail_properties_DAO.findById(mailProps.getSmtp_addr_sender()).isPresent()) {
    		mail_properties_DAO.save(new Mail_properties(mailProps.getSmtp_addr_sender(), mailProps.isSmtp_auth(), mailProps.isSmtp_starttls_enabled(), mailProps.getSmtp_host(), mailProps.getSmtp_port()));
    	}
    	
    	if(!mail_properties_DAO.findById(crashProps.getSender()).isPresent()) {
    		mail_properties_DAO.save(new Mail_properties(crashProps.getSender(), crashProps.isSmtp_auth(), crashProps.isSmtp_starttls_enable(), crashProps.getSmtp_host(), crashProps.getSmtp_port()));
    	}
    }
	
    @Override
    public Properties fetchEmailProperties(String addr_sender) {
    	
    	Properties props = new Properties();
    	
    	Optional<Mail_properties> opt_mail_properties = mail_properties_DAO.findById(addr_sender);
    	
    	if (!opt_mail_properties.isPresent()) {
    		return null;
    	}
    	
    	Mail_properties mail_properties = opt_mail_properties.get();
    	
    	props.put("mail.smtp.auth", mail_properties.getMail_smtp_auth());
    	props.put("mail.smtp.starttls.enable", mail_properties.getMail_smtp_starttls_enable());
    	props.put("mail.smtp.host", mail_properties.getMail_smtp_host());
    	props.put("mail.smtp.port", mail_properties.getMail_smtp_port());
    	
    	return props;
    }
    
	@Override
	public Properties getEmailProperties() {
		
		Properties props = new Properties();
    	props.put("mail.smtp.auth", mailProps.isSmtp_auth());
    	props.put("mail.smtp.starttls.enable", mailProps.isSmtp_starttls_enabled());
    	props.put("mail.smtp.host", mailProps.getSmtp_host());
    	props.put("mail.smtp.port", mailProps.getSmtp_port());
		
		return props;
	}
	
	@Override
	public Properties getEmailCrashlyticsProperties() {
		
		Properties props = new Properties();
    	props.put("mail.smtp.auth", crashProps.isSmtp_auth());
    	props.put("mail.smtp.starttls.enable", crashProps.isSmtp_starttls_enable());
    	props.put("mail.smtp.host", crashProps.getSmtp_host());
    	props.put("mail.smtp.port", crashProps.getSmtp_port());
		
		return props;
	}
	
	@Override
	public Properties createOwnEmailProperties(HashMap<String, String> props_values) {
		
		Properties props = new Properties();
		props_values.forEach((k, v) -> props.put(k, v));
		
		return props;
	}
    
    @Override
    public void sendDynamicEmail(Properties props, Mail_server mail_server, String attach_base_path) throws MessagingException, IOException {
    	
    	Session mail_session = Session.getInstance(props, new Authenticator() {
    		protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mail_server.getEmail_sender(), mail_server.getEmail_password());
    		}
    	});
    	
    	Message msg = new MimeMessage(mail_session);
    	
    	msg.setFrom(new InternetAddress(mail_server.getEmail_sender()));
    	msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail_server.getAddr_recipients()));
    	msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mail_server.getAddr_recipients_cc()));
    	msg.setSubject(mail_server.getSubject());
    	msg.setContent(mail_server.getText(), "text/html");
    	
    	MimeBodyPart messageBodyPart = new MimeBodyPart();
    	messageBodyPart.setContent(mail_server.getText(), "text/html");
    	
    	Multipart multipart = new MimeMultipart();
    	multipart.addBodyPart(messageBodyPart);
    	MimeBodyPart attachPart = new MimeBodyPart();

    	StringBuilder response_error = new StringBuilder(128);
    	for (Mail_attachment mail_attachment : mail_server.getAttachments()) {
    		
    		File file = new File(attach_base_path + mail_attachment.getData_path());
    		
        	if (!file.exists() || file.isDirectory()) { // se il file non esiste o è una directory
            	response_error.append("File " + mail_attachment.getData_path() + " non trovato o inesistente o è una directory");
            	continue;
        	}
    		
    		attachPart.attachFile(attach_base_path + mail_attachment.getData_path());
    		multipart.addBodyPart(attachPart);
    		msg.setContent(multipart);
    	}
    	
		Transport.send(msg);
	}
	
	@Override
	public void sendEmail(Mail_server mail_server, String attach_base_path)
					throws MailAuthenticationException, MailSendException, MailException, MessagingException, IOException {
		
		MimeMessage message = sender.createMimeMessage();
		
        // Enable the multipart flag!
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setFrom(mail_server.getEmail_sender());
        helper.setTo(mail_server.getAddr_recipients());
        helper.setCc(mail_server.getAddr_recipients_cc());
        helper.setSubject(mail_server.getSubject());
        helper.setText(mail_server.getText(), true);
        
    	StringBuilder response_error = new StringBuilder(128);
        for (Mail_attachment mail_attachment : mail_server.getAttachments()) {
        	
        	File file = new File(attach_base_path + mail_attachment.getData_path());
    		
        	if (!file.exists() || file.isDirectory()) { // se il file non esiste o è una directory
            	response_error.append("File " + mail_attachment.getData_path() + " non trovato o inesistente o è una directory");
            	continue;
        	}
        	
            helper.addAttachment(mail_attachment.getAttachment_file_name(), file);
        }
        
        sender.send(message);
	}
    
	@Override
    public Mail_server saveEmail(Mail_server mail_server) {
		
		Mail_server mail_saved = mail_server_DAO.save(mail_server);
		
		List<Mail_attachment> mail_attachments = new ArrayList<Mail_attachment>(mail_server.getAttachments());
		mail_server.resetAttachments();
		
    	for (Mail_attachment mail_attachment : mail_attachments) {
    		
			mail_attachment.setMailServerId(mail_saved.getId());
			
    		Mail_attachment mail_attachment_saved = new Mail_attachment();
			mail_attachment_saved = mail_attachment_DAO.save(mail_attachment);
    		
    		mail_saved.addAttachment(mail_attachment_saved);
    	}
    	
    	return mail_saved;
    }
	
	@Override
	public boolean saveAndSendEmail(Mail_server mail_server, String attach_base_path, String username) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		mail_server.setText_byte(mail_server.getText().toString().getBytes(Charset.forName("UTF-8")));
		mail_server.setText("");
		
		// salva la mail per precauzione
		Mail_server mail_server_saved = this.saveEmail(mail_server);
		
		try {
			mail_server_saved.setText(new String(mail_server_saved.getText_byte()));
			
			// controlla se le Properties gia esistono per il mittente nel db; in caso positivo utilizza quelle, altrimenti recupera i valori da properties
			Properties props = this.fetchEmailProperties(mail_server.getEmail_sender());
	    	this.sendDynamicEmail((props != null) ? props : this.getEmailProperties(), mail_server, attach_base_path);
			
	    	String email_response = "Mail con id " + mail_server_saved.getId();
	    	
	    	String attachment_ids = "";
	    	for (Mail_attachment mail_attachment: mail_server_saved.getAttachments()) {
				attachment_ids += mail_attachment.getId() + ", ";
			}
	    	email_response += attachment_ids.equals("") ? "" :  " con allegati di id " + attachment_ids.substring(0, attachment_ids.length() - ", ".length());
	    	email_response +=  " inviata con successo";
	    	
			logService.printLog(LogUtils.INFO, email_response);
			
		} catch (Exception e) {
			
			String outcome_sending_email = "Errore invio mail con id " + mail_server_saved.getId();
			
			String attachment_ids = "";
			for (Mail_attachment mail_attachment: mail_server_saved.getAttachments()) {
				attachment_ids += mail_attachment.getId() + ", ";
			}
			outcome_sending_email += attachment_ids.equals("") ? " - " :  " con allegati di id " + attachment_ids.substring(0, attachment_ids.length() - ", ".length()) + " - ";
			
			logService.printAndSaveStoneLog(LogUtils.FATAL, outcome_sending_email + e, this.getClass().getName(), method_name, outcome_sending_email + e.getMessage(), "");
			
			mail_server_saved.setError(outcome_sending_email + " - " + e.getMessage());
			mail_server_saved.setSent(false);
			
			// aggiorna la mail nel db a seguito dell'esito dell'invio
			this.saveEmail(mail_server_saved);
			
			return false;
		}

		mail_server_saved.setError(null);
		mail_server_saved.setSent(true);
		mail_server_saved.setText("");
		
		// aggiorna la mail nel db a seguito dell'esito dell'invio
		this.saveEmail(mail_server_saved);
		
		// ripristino le condizioni iniziali della mail
		mail_server.setText(new String(mail_server.getText_byte()));
		mail_server.setText_byte(null);
		
		return true;
	}

	@Override
	public Mail_recipients getEmailRecipients() {
		
		return new Mail_recipients(mailProps.getRecipients_to(), mailProps.getRecipients_cc(), mailProps.getRecipients_bcc());
	}

	@Override
	public Mail_recipients getEmailCrashlyticsRecipients() {
		
		return new Mail_recipients(crashProps.getRecipients_to(), crashProps.getRecipients_cc(), crashProps.getRecipients_bcc());
	}
	
	@Override
	public void addMailRecipient(String mail_recipient_to) {
		
		mailProps.setRecipients_to(mailProps.getRecipients_to() + ", " + mail_recipient_to);
	}

	@Override
	public void addMailRecipientCc(String mail_recipient_cc) {
		
		mailProps.setRecipients_cc(mailProps.getRecipients_cc() + ", " + mail_recipient_cc);
	}

	@Override
	public void addMailRecipientBcc(String mail_recipient_bcc) {
		
		mailProps.setRecipients_bcc(mailProps.getRecipients_bcc() + ", " + mail_recipient_bcc);
	}

    @Override
    public String getMail_smtp_addr_sender() {
		return mailProps.getSmtp_addr_sender();
	}

    @Override
	public String getMail_smtp_addr_password() {
		return mailProps.getSmtp_addr_password();
	}
}
