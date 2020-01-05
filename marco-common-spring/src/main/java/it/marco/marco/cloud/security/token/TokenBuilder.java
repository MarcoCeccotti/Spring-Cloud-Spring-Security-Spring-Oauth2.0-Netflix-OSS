package it.marco.marco.cloud.security.token;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.marco.auth.bean.auth.AccessTokens;

public interface TokenBuilder {
	
	/**
	 * Creates both the access token and the refresh token.
	 * @param username the username of the requester
	 * @param roles the roles of the user requester
	 * @param api_key the client id
	 * @param signin_key the key to signin the tokens
	 * @return the AccessTokens object with access and refresh token.
	 * @throws JsonProcessingException
	 * @throws UnsupportedEncodingException
	 */
	public AccessTokens createTokens(String username, String roles, String api_key, String signin_key) throws JsonProcessingException, UnsupportedEncodingException;
	
	/**
	 * Creates a specific token, based on claims. If claims are null it creates new ones.
	 * @param username the username
	 * @param roles the roles
	 * @param type the ype of token requested
	 * @param jti the jti, a random access number to serialize the token
	 * @param client_id the client id
	 * @param signin_key the signin key
	 * @param claims the claims
	 * @return the String containing the specific token requested
	 * @throws JsonProcessingException
	 * @throws UnsupportedEncodingException
	 */
	public String createToken(String username, String roles, String type, String jti, String client_id, String signin_key, Map<String, Object> claims) throws JsonProcessingException, UnsupportedEncodingException;
	
	/**
	 * Refreshes the access token. First step validate the refresh token, if it's ok creates new access and refresh token.
	 * @param username the username
	 * @param roles the roles
	 * @param refresh_token the refresh token to validate
	 * @param signin_key the signin key to decode jwt
	 * @return if the refresh_token us valid, the new access and refresh tokens are provided, an error string response otherwise.
	 * @throws JsonProcessingException
	 * @throws UnsupportedEncodingException
	 */
	public Object refreshAccessToken(String username, String roles, String refresh_token, String signin_key) throws JsonProcessingException, UnsupportedEncodingException;
	
	/**
	 * Creates the claims for the token based on user credentials.
	 * @param user the user with its credentials
	 * @return the claims for the token.
	 * @throws JsonProcessingException
	 */
	public Map<String, Object> createClaims(String username, String roles) throws JsonProcessingException;
}
