package it.marco.marco.service.feign.fallback;

import org.springframework.stereotype.Component;

import it.marco.auth.bean.auth.User_banned;
import it.marco.marco.service.feign.ban.RemoteBanCallService;

@Component
public class HystrixFallback implements RemoteBanCallService {
	
	@Override
	public String banUser(String token, User_banned user_banned) {

		String response = "Servizio ban utente down";
        
        return response;
	}
}
