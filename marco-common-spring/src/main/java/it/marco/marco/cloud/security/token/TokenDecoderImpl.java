package it.marco.marco.cloud.security.token;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenDecoderImpl implements TokenDecoder {

	@Override
	public Claims decodeJwt(String jwtToken, String signign_key) throws UnsupportedJwtException, MalformedJwtException, SignatureException, ExpiredJwtException {
		
		Claims claims = Jwts.parser()
	            			.setSigningKey(signign_key.getBytes())
	            			.parseClaimsJws(jwtToken).getBody();
		
	    return claims;
	}
}