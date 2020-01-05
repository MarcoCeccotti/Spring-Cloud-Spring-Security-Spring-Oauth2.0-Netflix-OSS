package it.marco.marco.cloud.service.http;

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
		
		// controlla il refresh token
		if (request.getHeader(AuthUtils.REFRESH_TOKEN) != null) {
			return request.getHeader(AuthUtils.REFRESH_TOKEN);
		}
		
		// controlla l'access token nell'header
		if (request.getHeader(AuthUtils.AUTHORIZATION) != null) {
			if (request.getHeader(AuthUtils.AUTHORIZATION).contains(AuthUtils.BEARER_PREFIX)) {
				return request.getHeader(AuthUtils.AUTHORIZATION).split(" ")[1];
			}
			
			return null;
		}
		
		// controlla l'access token nel query param
		if (request.getQueryString() != null && request.getQueryString().contains(AuthUtils.ACCESS_TOKEN)) {
			return request.getQueryString().split("=")[1];
		}
		
		return null;
	}
	
	@Override
	public String getUsernameFromRequest(HttpServletRequest request) {
		
		String access_token = this.checkAccessTokenOnRequest(request);
		if (access_token == null) {
			return null;
		}
		try {
			return this.tokenDecoder.decodeJwt(access_token, this.authAdapter.getSigningKey()).getSubject();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Object getInfoFromToken(String token, String info_type) {
		
		return tokenDecoder.decodeJwt(token, authAdapter.getSigningKey()).get(info_type);
	}
	
	@Override
	public String getClientIP(HttpServletRequest request) {
		
	    String xfHeader = request.getHeader(HeaderUtils.X_FORWARDED_FOR);
	    return (xfHeader == null) ? request.getRemoteAddr() : xfHeader.split(",")[0];
	}
}
