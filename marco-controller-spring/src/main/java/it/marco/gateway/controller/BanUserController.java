package it.marco.gateway.controller;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.marco.auth.bean.auth.User_banned;
import it.marco.gateway.service.gateway.BlackListService;
import it.marco.marco.cloud.service.log.LogService;
import it.marco.marco.utils.utilities.LogUtils;

@RestController
public class BanUserController {
	
	@Inject
	private BlackListService blackListService;
	
	@Inject
	private LogService logService;

	@PostMapping(value = "/ban", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public String banUser(@RequestBody User_banned user_banned) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		logService.printAndSaveActionLog(LogUtils.INFO, "Ricezione nuovo utente da bannare", this.getClass().getName(), method_name, "Ricezione nuovo utente da bannare");
		
		blackListService.addBanned(user_banned.getUsername(), user_banned.getToken(), user_banned.getIp(), "Utente sloggato");
		
		return user_banned.getUsername() + " bannato";
	}
}
