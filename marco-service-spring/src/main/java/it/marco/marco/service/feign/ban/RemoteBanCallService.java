package it.marco.marco.service.feign.ban;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import it.marco.auth.bean.auth.User_banned;
import it.marco.marco.service.feign.fallback.HystrixFallback;
import it.marco.marco.utils.utilities.AuthUtils;

@FeignClient(name = "gateway", fallback = HystrixFallback.class)
public interface RemoteBanCallService {

	/**
	 * Fallback method for ban http request.
	 * @param token the token to access reserved api
	 * @param user_banned the user to ban
	 * @return a String containing the response for service down.
	 */
	@PostMapping(value = "/ban", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String banUser(@RequestParam(AuthUtils.ACCESS_TOKEN) String token, 
						  @RequestBody User_banned user_banned);
}
