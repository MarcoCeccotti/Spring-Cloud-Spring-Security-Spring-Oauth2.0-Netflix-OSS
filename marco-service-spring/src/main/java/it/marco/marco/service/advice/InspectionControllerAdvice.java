package it.marco.marco.service.advice;

import java.sql.Timestamp;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.common.base.Throwables;

import it.marco.marco.bean.http.Outcome;
import it.marco.marco.bean.http.Type;
import it.marco.marco.bean.http.Widget;
import it.marco.marco.bean.http.WrapperResponse;
import it.marco.marco.bean.mail.Mail_server;
import it.marco.marco.cloud.config.properties.CrashlyticProperties;
import it.marco.marco.cloud.service.http.HttpService;
import it.marco.marco.cloud.session.Session_infos;
import it.marco.marco.service.mail.MailService;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InspectionControllerAdvice {

	@Inject
	private MailService mailService;
	
	@Inject
	private HttpService httpService;
	
	@Inject
	private Session_infos session_infos;
	
	@Inject
	private CrashlyticProperties crashProps;
	
	// property per il nome dell'applicazione
	@Value("${app_name}")
	private String app_name;
	
	private Properties crash_props;
	private String subject_mail;
	
	@PostConstruct
	public void init() {
		
		// controlla se le proprties gia esistono per il mittente, in caso positivo utilizza quelle, altrimenti prende quelle da properties
		crash_props = mailService.fetchEmailProperties(crashProps.getSender());
		crash_props = (crash_props != null) ? crash_props : mailService.getEmailCrashlyticsProperties();
		
		// setto il subject della mail per uniformarla
		subject_mail = "Crashlytics " + app_name;
	}
	
	@ExceptionHandler(value = {Throwable.class})
	public ResponseEntity<?> exceptionResponse(Exception exception, HttpServletRequest request) {
		
		String mail_text = this.getTextMail(exception, request);
		
		Mail_server mail_server = new Mail_server(crashProps.getSender(), crashProps.getPassword(), mailService.getEmailCrashlyticsRecipients(), subject_mail, mail_text, true, exception.getMessage(), session_infos.getTime_st(), session_infos.getCompany_id());
		
		try {
			mailService.sendDynamicEmail(crash_props, mail_server, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new WrapperResponse(null, new Outcome(HttpStatus.BAD_REQUEST, exception.getMessage(), Type.ERROR, null, Widget.ALERT)));
	}
	
	/**
	 * Obtains the text to insert in the crashlytic mail based on exception thrown.
	 * @param exception the Throwable exception throwed
	 * @param request the HttpServletRequest request
	 * @return the text of the mail.
	 */
	private String getTextMail(Throwable exception, HttpServletRequest request) {
		
		return "Utente: " + (session_infos.getUsername() != null ? session_infos.getUsername() : this.httpService.getUsernameFromRequest(request)) + 
			   "<br/>" + "Orario: " + (session_infos.getTime_st() != null ? session_infos.getTime_st() : new Timestamp(System.currentTimeMillis())) + 
			   "<br/>" + "URL: " 	+ request.getRequestURL() + 
			   "<br/>" + "Ip: " 	+ (session_infos.getClient_ip() != null ? session_infos.getClient_ip() : this.httpService.getClientIP(request)) + 
			   "<br/>" + "Classe: " + exception.getStackTrace()[0].getClassName() +
			   "<br/>" + "Metodo: " + exception.getStackTrace()[0].getMethodName() + ":" + exception.getStackTrace()[0].getLineNumber() + 
			   "<br/>" + "Errore: " + exception.getMessage() + 
			   "<br/>" + "Errore completo: " + Throwables.getStackTraceAsString(exception);
	}
}
