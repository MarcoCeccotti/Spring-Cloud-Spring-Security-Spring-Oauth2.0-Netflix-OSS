package it.marco.marco.service.feign;

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

	@PostMapping(value = "/ban", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String banUser(@RequestParam(AuthUtils.ACCESS_TOKEN) String token, 
						  @RequestBody User_banned user_banned);
}
