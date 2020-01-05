package it.marco.marco.cloud.security.token.validator;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import it.marco.marco.cloud.security.token.AuthAdapter;
import it.marco.marco.cloud.security.token.TokenDecoder;
import it.marco.marco.cloud.service.log.LogService;
import it.marco.marco.utils.utilities.AuthUtils;
import it.marco.marco.utils.utilities.LogUtils;

@Service
public class TokenValidatorServiceImpl implements TokenValidatorService {

	@Inject
	private AuthAdapter authAdapter;
	@Inject
	private TokenDecoder tokenDecoder;
	
	@Inject
	private LogService logService;
	
	@Override
	public boolean validateToken(String token, String tokenType) {
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		String check_token = null;
		
		Claims claims = null;
		try {
			claims = tokenDecoder.decodeJwt(token, authAdapter.getSigningKey());
		} catch (ExpiredJwtException e) {
			check_token = tokenType + " expired";
		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
			check_token = "Generic error on " + tokenType;
		}
		
		if (tokenType.equalsIgnoreCase(AuthUtils.REFRESH_TOKEN) && 
				claims != null && claims.get(AuthUtils.CLAIM_KEY_REFRESH) == null) {
			
			check_token = "Can't refresh an access token, try using refresh token instead";
		}
		
		if (check_token != null) {

			logService.printAndSaveActionLog(LogUtils.ERROR, StringUtils.capitalize(check_token), this.getClass().getName(), method_name, StringUtils.capitalize(check_token));
			return false;
		}
		
		return true;
	}
	
}
