package it.marco.marco.cloud.config.http.service;

import javax.servlet.http.HttpServletRequest;

public interface HttpService {

	/**
	 * Checks if the access_token is present on the current request.
	 * @param request the current HttpServletRequest request
	 * @return a String containing the access_token if it is present in the request header; an empty String otherwise.
	 */
	public String checkAccessTokenOnRequest(HttpServletRequest request);
	
	/**
	 * Gets the username based on access token retrieved.
	 * @param request the current HttpServletRequest request
	 * @return a String containing the username if the access token decoding goes well, an empty String otherwise.
	 */
	public String getUsernameFromToken(HttpServletRequest request);
	
	/**
	 * Retrieves the client ip from request header.
	 * @param request the current HttpServletRequest request
	 * @return a String containing the ip of the client.
	 */
	public String getClientIP(HttpServletRequest request);
}
