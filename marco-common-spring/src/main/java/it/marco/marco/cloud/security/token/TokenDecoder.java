package it.marco.marco.cloud.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public interface TokenDecoder {

	/**
	 * Decodes a jwt token.
	 * @param jwtToken the token to deocde
	 * @param signign_key the key used to decode the token. Usually the same signin-key is firstly used to encode the token in building phase. If the token comes from an external service it should be provided by the developer(s) of the service.
	 * @return the Claim object obtained from token decoding.
	 * @throws UnsupportedJwtException a generic error on token
	 * @throws MalformedJwtException when the token is not correct 
	 * @throws SignatureException when the signature cannot be decode (uncorrect signin-key)
	 * @throws ExpiredJwtException when the token is correct but expired
	 */
	public Claims decodeJwt(String jwtToken, String signign_key) throws UnsupportedJwtException, MalformedJwtException, SignatureException, ExpiredJwtException;
}
