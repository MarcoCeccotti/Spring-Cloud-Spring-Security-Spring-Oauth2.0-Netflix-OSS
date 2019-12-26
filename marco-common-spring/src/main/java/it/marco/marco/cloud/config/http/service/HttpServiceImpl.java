package it.marco.marco.cloud.config.http.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import it.marco.marco.cloud.security.token.AuthAdapter;
import it.marco.marco.cloud.security.token.TokenDecoder;
import it.marco.marco.utils.utilities.AuthUtils;
import it.marco.marco.utils.utilities.HeaderUtils;

@Service
public class HttpServiceImpl implements HttpService {
	
	@Inject
	private TokenDecoder tokenDecoder;
	@Inject
	private AuthAdapter authAdapter;
	
	@Override
	public String checkAccessTokenOnRequest(HttpServletRequest request) {
		
		if (request.getHeader(AuthUtils.AUTHORIZATION) != null) {
			if (request.getHeader(AuthUtils.AUTHORIZATION).contains(AuthUtils.BEARER_PREFIX)) {
				return request.getHeader(AuthUtils.AUTHORIZATION).split(" ")[1];
			}
			
			return "";
		}
		
		if (request.getHeader(AuthUtils.REFRESH_TOKEN) != null) {
			return request.getHeader(AuthUtils.REFRESH_TOKEN);
		}
		
		if (request.getQueryString() != null && request.getQueryString().contains(AuthUtils.ACCESS_TOKEN)) {
			return request.getQueryString().split("=")[1];
		}
		
		return "";
	}
	
	@Override
	public String getUsernameFromToken(HttpServletRequest request) {
		
		String access_token = this.checkAccessTokenOnRequest(request);
		try {
			return this.tokenDecoder.decodeJwt(access_token, this.authAdapter.getSigningKey()).getSubject();
		} catch (Exception e) {
			return "";
		}
	}
	
	@Override
	public String getClientIP(HttpServletRequest request) {
		
	    String xfHeader = request.getHeader(HeaderUtils.X_FORWARDED_FOR);
	    return (xfHeader == null) ? request.getRemoteAddr() : xfHeader.split(",")[0];
	}
}
