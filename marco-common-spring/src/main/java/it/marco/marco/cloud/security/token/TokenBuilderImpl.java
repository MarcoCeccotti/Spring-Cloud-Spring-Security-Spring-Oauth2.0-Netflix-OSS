package it.marco.marco.cloud.security.token;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.marco.auth.bean.auth.AccessTokens;
import it.marco.marco.cloud.config.properties.AuthProperties;
import it.marco.marco.cloud.security.token.validator.TokenValidatorService;
import it.marco.marco.utils.utilities.AuthUtils;

@Component
public class TokenBuilderImpl implements TokenBuilder {
	
	@Inject
	private TokenDecoder tokenDecoder;
	@Inject
	private TokenValidatorService tokenValidatorService;
	
	@Inject
	private AuthProperties authProps;
	
	public Map<String, Object> header = new HashMap<String, Object>();
	
	@PostConstruct
	public void init() {

		header.put("typ", "JWT");
		header.put("alg", "HS256");
	}

	@Override
	public AccessTokens createTokens(String username, String roles, String api_key, String signin_key) throws JsonProcessingException, UnsupportedEncodingException {
		
		AccessTokens accessTokens = new AccessTokens();
		
		String jti = UUID.randomUUID().toString();
		
		accessTokens.setAccess_token(this.createToken(username, roles, AuthUtils.GRANT_TYPE_PASSWORD, jti, api_key, signin_key, null));
		accessTokens.setToken_type(AuthUtils.BEARER_PREFIX.toLowerCase());
		accessTokens.setRefresh_token(this.createToken(username, roles, AuthUtils.GRANT_TYPE_REFRESH_TOKEN, jti, api_key, signin_key, null));
		accessTokens.setExpires_in(authProps.getAccessTokenValiditySeconds());
		accessTokens.setScope(AuthUtils.SCOPE_READ + ", " + AuthUtils.SCOPE_WRITE + ", " +  AuthUtils.TRUST);
		accessTokens.setJti(jti);
		
		return accessTokens;
	}

	@Override
	public String createToken(String username, String roles, String type, String jti, String client_id, String signin_key, Map<String, Object> claims) throws JsonProcessingException, UnsupportedEncodingException {
		
		if (claims == null) {
			claims = createClaims(username, roles);
			claims.put(AuthUtils.CLAIM_KEY_CLIENT_ID, client_id);
		}
		
		Long timeExp = ((long) authProps.getAccessTokenValiditySeconds()) * 1000L;
		
		if (type.equals(AuthUtils.GRANT_TYPE_REFRESH_TOKEN)) {
			claims.put(AuthUtils.CLAIM_KEY_REFRESH, UUID.randomUUID().toString());
			
			timeExp = ((long) authProps.getRefreshTokenValiditySeconds()) * 1000L;
			
		} else {
			claims.remove(AuthUtils.CLAIM_KEY_REFRESH);
		}

		Date exp = new Date(System.currentTimeMillis() + timeExp);
		
		return Jwts.builder()
					.setHeader(header)
					.setClaims(claims)
					.setExpiration(exp)
					.setId(jti)
					.signWith(SignatureAlgorithm.HS256, signin_key.getBytes("UTF-8"))
					.compact();
	}

	@Override
	public Object refreshAccessToken(String username, String roles, String refresh_token, String signin_key) throws JsonProcessingException, UnsupportedEncodingException {
		
		if (refresh_token == null || refresh_token.trim().equals("")) {
			return "Missing refresh token on header";
		}
		
		if (!tokenValidatorService.validateToken(refresh_token, AuthUtils.REFRESH_TOKEN)) {
			return "Invalid refresh_token";
		}
		
		Claims claims = tokenDecoder.decodeJwt(refresh_token, signin_key);
        claims.put(AuthUtils.CLAIM_KEY_CREATED, new Date());
		
		String jti = UUID.randomUUID().toString();
        
		AccessTokens accessTokens = new AccessTokens();
        accessTokens.setAccess_token(createToken(username, roles, AuthUtils.GRANT_TYPE_PASSWORD, jti, claims.get("client_id").toString(), signin_key, claims));
		accessTokens.setToken_type("bearer");
        accessTokens.setRefresh_token(createToken(username, roles, AuthUtils.GRANT_TYPE_REFRESH_TOKEN, jti, claims.get("client_id").toString(), signin_key, claims));
		accessTokens.setExpires_in(authProps.getAccessTokenValiditySeconds());
		accessTokens.setScope(AuthUtils.SCOPE_READ + ", " + AuthUtils.SCOPE_WRITE + ", " +  AuthUtils.TRUST);
		accessTokens.setJti(jti);
    	
        return accessTokens;
    }

	@Override
	public Map<String, Object> createClaims(String username, String roles) throws JsonProcessingException {
		
		Map<String, Object> claims = new HashMap<>();
        claims.put(AuthUtils.CLAIM_KEY_USERNAME, username);
        claims.put(AuthUtils.CLAIM_KEY_SCOPES, Arrays.asList(AuthUtils.SCOPE_READ, AuthUtils.SCOPE_WRITE, AuthUtils.TRUST));
        claims.put(AuthUtils.CLAIM_KEY_CREATED, new Date());
        
        List<String> auth = new ArrayList<String>(getAuthority(roles));
        claims.put(AuthUtils.CLAIM_KEY_AUTHORITIES, auth);
        
        return claims;
    }

	/**
	 * Gets the authorites of the user
	 * @param user the user with its authorities
	 * @return the list of user's authorites
	 */
	private List<String> getAuthority(String user_roles) {
		
		String[] roles = user_roles.trim().split(" ");

		List<String> authorities = new ArrayList<String>();
		
		for(String role : roles) {
			authorities.add("ROLE_" + role);
		}
		
		return authorities;
	}
}