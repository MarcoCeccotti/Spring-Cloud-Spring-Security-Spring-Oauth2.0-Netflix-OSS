package it.marco.marco.cloud.service.http;

import javax.servlet.http.HttpServletRequest;

public interface HttpService {

	/**
	 * Checks if the access_token is present on the current request.
	 * @param request the current HttpServletRequest request
	 * @return a String containing the access_token if it is present in the request header; null otherwise.
	 */
	public String checkAccessTokenOnRequest(HttpServletRequest request);
	
	/**
	 * Gets the username based on the request.
	 * @param request the current HttpServletRequest request
	 * @return a String containing the username if the access token decoding goes well; null otherwise.
	 */
	public String getUsernameFromRequest(HttpServletRequest request);

	/**
	 * Gets the info based on access token passed.
	 * @param token the token used to fetch info
	 * @param info_type the type of the information to retrieve from token. Advise is to use AuthUtils utility class
	 * @return an Object containing the info requested from the token passed; null otherwise.
	 */
	public Object getInfoFromToken(String token, String info_type);
	
	/**
	 * Retrieves the client ip from request header.
	 * @param request the current HttpServletRequest request
	 * @return a String containing the ip of the client.
	 */
	public String getClientIP(HttpServletRequest request);
}
