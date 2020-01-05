package it.marco.auth.service.auth;

import java.util.Optional;

import org.springframework.http.HttpStatus;

import it.marco.auth.bean.auth.AccessParameters;
import it.marco.auth.bean.auth.AccessTokens;
import it.marco.auth.bean.auth.Users;

public interface LoginService {
	
	/**
	 * Checks the validity of input value of the request
	 * @param api_key the api_key
	 * @param api_secret the api_secret
	 * @param type the correct grant_type to compare with the one passed by the request
	 * @param access_params the access parameters
	 * @return a ResponseEntity object in case of error; null otherwise
	 */
	public String checkInputValues(String api_key, String api_secret, String type, AccessParameters access_params);
	
	/**
	 * Check User credentials.
	 * @param user the User to check
	 * @param access_parameters the parameters used to access the service
	 * @param company_id the id of the company
	 * @return an HttpStatus if something goes wrong; null otherwise.
	 */
	public HttpStatus checkCredentials(Optional<Users> user, AccessParameters access_parameters, String company_id);
	
	/**
	 * Checks the user credentials. If everything is ok, a complete access token is build. A refresh token is provided, too. 
	 * @param accessParameters the access parameters
	 * @param api_key the client id
	 * @param api_secret the client secret
	 * @return a ResponseEntity object. If everything is ok, the body of the object is filled with access and refresh tokens, null otherwise
	 */
	public AccessTokens getAccessToken(Users user, String api_key, String api_secret, String company_id);
	
	/**
	 * Refreshes the access token, providing a completely new one. Previously, user credentials are checked.
	 * @@param accessParameters the access parameters
	 * @param refresh_token the token to be refreshed. Only refresh token is admitted
	 * @return a ResponseEntity object. If everything is ok, the body of the object is filled with new access and refresh tokens, null otherwise
	 */
	public AccessTokens refreshAccessToken(AccessParameters accessParameters, String refresh_token);
	
	/**
	 * Retrieves the password forgotten. The minimum data needed to retrieve it is username or email.
	 * @param username the username
	 * @param email the email
	 * @param company_id the name of the company
	 * @return a ResponseEntity object response.
	 */
	public String checkForgottenPassword(String username, String email, String company_id);
}