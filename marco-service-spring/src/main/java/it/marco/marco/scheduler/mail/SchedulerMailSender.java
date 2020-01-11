package it.marco.marco.scheduler.mail;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.marco.auth.test.profile.EffettivaStringa;
import it.marco.marco.bean.mail.Mail_attachment;
import it.marco.marco.bean.mail.Mail_server;
import it.marco.marco.cloud.config.properties.StoneProperties;
import it.marco.marco.cloud.service.log.LogService;
import it.marco.marco.dao.mail.Mail_attachment_DAO;
import it.marco.marco.dao.mail.Mail_server_DAO;
import it.marco.marco.service.mail.MailService;
import it.marco.marco.utils.utilities.LogUtils;

@Service
public class SchedulerMailSender {

	@Inject
	private Mail_server_DAO mail_server_DAO;
	@Inject
	private Mail_attachment_DAO mail_attachment_DAO;
	
	@Inject
	private MailService mailService;
	
	@Inject
	private LogService logService;
	
	@Inject
	private StoneProperties stoneProps;
	
	@Inject
	private EffettivaStringa effettivaStringa;
	
	// properties per salvataggio mail
	@Value("${company_name}")
	private String company_name;

	@Scheduled(cron = "0 0/1 * * * ?") // ogni 10 minuti
	@Transactional(rollbackFor = Exception.class)
	public void retrySendingMail() {
		
		System.out.println("STRINGA EFFETTIVA = " + effettivaStringa.getStrigaEffettiva());
		
		String method_name =  new Throwable().getStackTrace()[0].getMethodName();
		
		// recupero le mail che non sono state inviate (la company viene presa da properties)
		List<Mail_server> mails_server = mail_server_DAO.findBySentAndCompany(false, company_name);
		
		if (mails_server.isEmpty()) { // se non ci sono reinvii da eseguire
			
			logService.printLog(LogUtils.INFO, "Nessuna mail da reinviare");
			return;
		}
		
		int goodAttempts = 0, badAttempts = 0;
		StringBuilder goodAttempts_id = new StringBuilder(128), badAttempts_id = new StringBuilder(128);
		StringBuilder error_collector = new StringBuilder(256);
		
		for (Mail_server mail_server : mails_server) {
			
			// variabile per determinare se la mail ha tutti gli allegati per essere reinviata oppure no
			boolean resend = true;
			
			List<Mail_attachment> mail_attachments = mail_attachment_DAO.findAllByMailServerId(mail_server.getId());
			
			// la mail ha degli allegati, ma neanche uno è presente nel db
			if (mail_attachments.isEmpty()) {
				
				badAttempts++;
				badAttempts_id.append(" " + mail_server.getId() + ",");
				
				error_collector.append("La mail con id " + mail_server.getId() + " necessita di allegati, ma non sono presenti nel database" + System.lineSeparator());
				continue;
			}
			
			for (Mail_attachment mail_attachment : mail_attachments) {
				
				File file = new File(stoneProps.getFile_path_base() + mail_attachment.getData_path());
				if (!file.exists() || file.isDirectory()) {	// se l'allegato non è presente sul file system
					resend = false;
					
					badAttempts++;
					badAttempts_id.append(" " + mail_server.getId() + ",");

					error_collector.append("L'allegato " + stoneProps.getFile_path_base() + mail_attachment.getData_path() + " con id " + mail_attachment.getId() + " della mail con id " + mail_server.getId() + " non è stato trovato" + System.lineSeparator());
					break;
				}
				
				mail_server.addAttachment(mail_attachment);
			}
			
			if (resend) { // se sono stati trovati tutti gli allegati richiesti
				if (this.sendAndSaveEmail(mail_server, new Timestamp(System.currentTimeMillis()), error_collector, goodAttempts_id, badAttempts_id)) {
					goodAttempts++;
				} else {
					badAttempts++;
				}
			}
		}
		
		this.saveLog(goodAttempts, badAttempts, goodAttempts_id, badAttempts_id, error_collector, method_name);
	}
	
	/**
	 * Sends an email and saves it on database even in case of failure
	 * @param mail_server the email to send
	 * @param error_collector StringBuilder to get the bad email sending attempt and create a complex error string
	 * @param goodAttempts_id StringBuilder to collect the ids of a possible good email sending attempt
	 * @param badAttempts_id StringBuilder to collect the ids of a possible bad email sending attempt
	 * @return false if something went wrong, true otherwise
	 */
	private boolean sendAndSaveEmail(Mail_server mail_server, Timestamp time_st, StringBuilder error_collector, StringBuilder goodAttempts_id, StringBuilder badAttempts_id) {

		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		mail_server.setText(mail_server.getText_byte() != null ? mail_server.getText_byte().toString() : "");
		
		try {
			// controlla se le Properties gia esistono per il mittente nel db; in caso positivo utilizza quelle, altrimenti recupera i valori da properties
			Properties props = mailService.fetchEmailProperties(mail_server.getEmail_sender());
	    	mailService.sendDynamicEmail((props != null) ? props : mailService.getEmailProperties(), mail_server, stoneProps.getFile_path_base());
			
		} catch (Exception e) {
			
			String outcome_sending_email = "Errore nel reinvio mail con id " + mail_server.getId();
			logService.printAndSaveSchedulerLog(LogUtils.FATAL, outcome_sending_email + " - " + e,
					this.getClass().getSimpleName(), this.getClass().getName(), method_name, outcome_sending_email + " - " + e.getMessage());
			
			error_collector.append(outcome_sending_email + " - " + e);
			badAttempts_id.append(" " + mail_server.getId() + ",");
			return false;
        }

		mail_server.setError(null);
		mail_server.setSent(true);
		mail_server.setText("");
		
		mailService.saveEmail(mail_server);
		
		goodAttempts_id.append(" " + mail_server.getId() + ",");
		
		return true;
	}
	
	/**
	 * Writes on logs the bad and good retry sending email attempts.
	 * @param goodAttempts counter of good mail sending attempts
	 * @param badAttempts counter of bad mail sending attempts
	 * @param goodAttempts_id StringBuilder collector of mail id of good mail sending attempts
	 * @param badAttempts_id StringBuilder collector of mail id of bad mail sending attempts
	 * @param error_collector StringBuilder collector of errors
	 * @param method_name name of the caller method
	 */
	private void saveLog(int goodAttempts, int badAttempts, StringBuilder goodAttempts_id, StringBuilder badAttempts_id, StringBuilder error_collector, String method_name) {
		
		StringBuilder to_log = new StringBuilder("Reinvii email eseguiti con successo: " + goodAttempts);
		to_log.append(goodAttempts > 0 ? " con id :" + goodAttempts_id.substring(0, Math.max(0, goodAttempts_id.length() - 1)) : "");
		
		to_log.append(" - Reinvii email non riusciti: " + badAttempts);
		to_log.append(badAttempts > 0 ? " con id :" + badAttempts_id.substring(0, Math.max(0, badAttempts_id.length() - 1)) : "");
		to_log.append(error_collector.length() > 0 ? System.lineSeparator() + error_collector : "");
		
		logService.printAndSaveSchedulerLog(LogUtils.INFO, to_log.toString(), this.getClass().getSimpleName(), this.getClass().getName(), method_name, to_log.toString());
	}
}