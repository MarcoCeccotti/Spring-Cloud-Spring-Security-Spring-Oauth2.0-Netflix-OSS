package it.marco.auth.service.auth;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import it.marco.auth.bean.auth.AccessParameters;
import it.marco.auth.bean.auth.AccessTokens;
import it.marco.auth.bean.auth.Users;
import it.marco.auth.dao.auth.Users_DAO;
import it.marco.marco.bean.mail.Mail_server;
import it.marco.marco.cloud.config.properties.AuthProperties;
import it.marco.marco.cloud.security.token.AuthAdapter;
import it.marco.marco.cloud.security.token.TokenBuilder;
import it.marco.marco.cloud.service.log.LogService;
import it.marco.marco.cloud.session.Session_infos;
import it.marco.marco.service.mail.MailService;
import it.marco.marco.utils.utilities.LogUtils;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Inject
	private MailService mailService;
	@Inject
	private LogService logService;
	
	@Inject
	private Users_DAO users_DAO;

	@Inject
	private TokenBuilder tokenBuilder;
	@Inject
	private AuthAdapter authAdapter;
	
	@Inject
	private Session_infos session_infos;
	
	@Inject
	private AuthProperties authProps;
	
	@Override
	public String checkInputValues(String api_key, String api_secret, String type, AccessParameters access_params) {
		
		if (api_key == null || api_secret == null || !api_key.equals(authProps.getClientId()) || !BCrypt.checkpw(api_secret, authProps.getClientSecret())) {
			
			logService.printAndSaveLoginFailedLog(LogUtils.ERROR, "Client id e/o secret invalidi.", access_params.getPassword());
			
			return "Client id e/o secret invalidi.";
		}
		
		if (access_params.getGrant_type() == null || !access_params.getGrant_type().equals(type)) {

			logService.printAndSaveLoginFailedLog(LogUtils.ERROR, "Grant Type invalido o mancante.", access_params.getPassword());
			
			return "Grant Type invalido o mancante.";
		}
		
		return null;
	}
	
	@Override
	public HttpStatus checkCredentials(Optional<Users> user, AccessParameters access_params, String company_id) {
		
		// se l'utente non è presente nel db o la password inviata non è corretta
		if(!user.isPresent() || (user.get().getPassword() != null && !BCrypt.checkpw(access_params.getPassword(), user.get().getPassword()))) {
			logService.printAndSaveLoginLog(LogUtils.INFO, "Username e/o pssword invalidi");
			return HttpStatus.BAD_REQUEST;
		}
		
		// se l'utente è disabilitato
		if(user.get().isDisabled() != null && user.get().isDisabled()) {
			logService.printAndSaveLoginLog(LogUtils.INFO, "Utente " + user.get().getUsername() + " disabilitato");
			return HttpStatus.UNAUTHORIZED;
		}
		
		return HttpStatus.OK;
	}
	
	@Override
	public AccessTokens getAccessToken(Users user, String api_key, String api_secret, String company_id) {
		
		String login_msg = "Login di " + user.getUsername() + " avvenuto con successo";
		
		AccessTokens access_tokens = null;
		try {
			access_tokens = tokenBuilder.createTokens(user.getUsername(), user.getRoles_text(), api_key, authAdapter.getSigningKey());
		} catch (Exception e) {
			login_msg = "Errore nella creazione del token: " + e.getMessage();
		}
		
		logService.printAndSaveLoginLog(LogUtils.INFO, login_msg);
		
		return access_tokens;
	}
	
	@Override
	public AccessTokens refreshAccessToken(AccessParameters access_params, String refresh_token) {

		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		Object token_refreshed = null;
		try {
			token_refreshed = tokenBuilder.refreshAccessToken("", "", refresh_token, authAdapter.getSigningKey());
		
		} catch (Exception e) {
		
			String error_response = refresh_token == null || refresh_token.trim().equals("") ? (String) token_refreshed : "Refresh token " + refresh_token + " non valido - " + token_refreshed;
			logService.printAndSaveActionLog(LogUtils.ERROR, error_response, this.getClass().getName(), method_name, error_response);
			
			return null;
		}
		
		if (token_refreshed instanceof String) {
			logService.printAndSaveActionLog(LogUtils.ERROR, token_refreshed.toString(), this.getClass().getName(), method_name, token_refreshed.toString());
			
			return null;
		}
		
		String refresh_msg = "Refresh token per " + session_infos.getUsername() + " ricevuto con successo - " + new Gson().toJson(token_refreshed);
		logService.printAndSaveActionLog(LogUtils.INFO, refresh_msg, this.getClass().getName(), method_name, token_refreshed.toString());

		return (AccessTokens) token_refreshed;
	}
	
	@Override
	public String checkForgottenPassword(String username, String email, String company_id) {
		
		Optional<Users> user = null;
		
		if (username != null && !username.trim().equals("")) {
			if (email != null && !email.trim().equals("")) {
				user = users_DAO.findByUserTagAndEmail(username.toLowerCase() + "@" + company_id, email);
			} else {
				user = users_DAO.findById(username.toLowerCase() + "@" + company_id);
			}
		} else if (email != null && !email.trim().equals("")) {
			user = users_DAO.findByEmail(email);
		}
		
		if (!user.isPresent()) {
			return "Dati inseriti errati. Prego reimmettere nome utente e/o email";
		}
		
		// creo la mail da inviare
		Mail_server mail_server = new Mail_server(mailService.getMail_smtp_addr_sender(), user.get().getEmail(), "", "", mailService.getMail_smtp_addr_password(), "Recupero Password", "La password è: " + user.get().getPasswd(), false, "Salvataggio mail di sicurezza", session_infos.getTime_st(), "login - " + session_infos.getCompany_id() + "-login");
		
		if (!mailService.saveAndSendEmail(mail_server, "", username)) {
			return "Errore nel recupero password. Prego contattare l'assistenza se il problema persiste.";
		}
		
		return null;
	}
}