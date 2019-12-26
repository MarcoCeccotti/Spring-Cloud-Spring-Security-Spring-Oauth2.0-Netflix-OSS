package it.marco.marco.service.feign.fallback;

import org.springframework.stereotype.Component;

import it.marco.auth.bean.auth.User_banned;
import it.marco.marco.service.feign.RemoteBanCallService;

@Component
public class HystrixFallback implements RemoteBanCallService {
	
	/**
	 * Fallback method for ban http request.
	 * @return a String containing the response for service down.
	 */
	@Override
	public String banUser(String token, User_banned user_banned) {
		
		String response = "Servizio ban utente down";
        
        return response;
	}
}
