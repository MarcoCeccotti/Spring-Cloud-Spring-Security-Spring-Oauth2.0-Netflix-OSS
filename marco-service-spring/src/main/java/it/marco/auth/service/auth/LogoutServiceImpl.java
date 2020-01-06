package it.marco.auth.service.auth;

import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import it.marco.auth.bean.auth.User_banned;
import it.marco.marco.cloud.service.log.LogService;
import it.marco.marco.cloud.session.Session_infos;
import it.marco.marco.service.feign.ban.RemoteBanCallService;
import it.marco.marco.utils.utilities.LogUtils;

@Service
public class LogoutServiceImpl implements LogoutService {
	
	@Inject
	private RemoteBanCallService remoteBanCallService;
	
	@Inject
	private LogService logService;
	
	@Inject
	private Session_infos session_infos;
	
	@Override
	public String logout() {

		logService.printAndSaveLoginLog(LogUtils.INFO, "L'utente " + session_infos.getUsername() + " si è disconnesso");
		
		// ban dell'utente in modalità asincrona
		Executors.newCachedThreadPool().submit(() -> {
			
			// quando un utente esegue il logout deve essere bannato il token utilizzato, di modo che sia obbligato a rifare il login
			String ban_response = remoteBanCallService.banUser(session_infos.getAccess_token(), new User_banned(null, session_infos.getAccess_token(), null, ""));
			
			if (ban_response.contains("down")) {
				logService.printAndSaveLoginLog(LogUtils.ERROR, ban_response);
				
			} else {
	
				ban_response = "Token " + session_infos.getAccess_token() + " dell'utente " + session_infos.getUsername() + " bannato con successo";
				logService.printAndSaveLoginLog(LogUtils.INFO, ban_response);
			}
		});
		
		return null;
	}
}
