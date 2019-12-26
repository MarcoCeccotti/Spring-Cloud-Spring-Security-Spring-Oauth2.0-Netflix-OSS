package it.marco.gateway.service.gateway;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import com.netflix.zuul.context.RequestContext;

import it.marco.marco.bean.http.Type;
import it.marco.marco.bean.http.Widget;

public interface FilterGatewayService {

	/**
	 * Checks the header values from request. It' not checked their validity but only if them are empty.
	 * The service called will check their validity.
	 * @param context the current context
	 * @param request the request
	 * @param headerValues the list of header values to be checked
	 */
	public void checkHeader(RequestContext context, HttpServletRequest request, List<String> headerValues);
	
	/**
	 * Checks token validity, and ip the ip/username is banned or not.
	 * @param context the current context
	 * @param ip the ip to check if is banned or not (could be null)
	 * @param tokenType the type of the token (if its an access or refresh token)
	 * @param tokenHeader the token contained on header
	 * @return true if ip/username and token are ok, false is ip/username is/are banned or token is invalid
	 */
	public boolean checkIsBannedAndToken(RequestContext context, String ip, String tokenType, String tokenHeader);
	
	/**
	 * Sets the error response. The request is not re-routed to the requested service. Body response is filled with a WrapperResponse.
	 * Additionally, a specific pair name, value is set on zuul response header, to be used on PostFilter
	 * @param context the current context
	 * @param payload the payload of the response
	 * @param msg the message to view
	 * @param type the error type
	 * @param widget the error widget
	 * @param severity the error severity
	 * @param code the code error
	 * @param httpStatus the status of http response
	 * @param headerName name of the zuul header to set
	 * @param headerValue value of the previous headerName
	 */
	public void setErrorResponse(RequestContext context, Object payload, String msg, Type type, Widget widget, String severity, HttpStatus httpStatus, String headerName, String headerValue);
}