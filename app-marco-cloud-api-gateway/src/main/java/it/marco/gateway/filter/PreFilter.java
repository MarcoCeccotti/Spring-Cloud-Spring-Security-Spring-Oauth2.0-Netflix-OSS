package it.marco.gateway.filter;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import it.marco.gateway.service.gateway.BlackListService;
import it.marco.gateway.service.gateway.FilterGatewayService;
import it.marco.marco.bean.http.Type;
import it.marco.marco.bean.http.Widget;
import it.marco.marco.cloud.service.http.HttpService;
import it.marco.marco.cloud.service.log.LogService;
import it.marco.marco.cloud.session.Session_infos;
import it.marco.marco.utils.utilities.AuthUtils;
import it.marco.marco.utils.utilities.LogUtils;

@Component
public class PreFilter extends ZuulFilter {
	
	@Inject
	private BlackListService blackListService;
	@Inject
	private FilterGatewayService filterGatewayService;
	
	@Inject
	private HttpService httpService;
	@Inject
	private LogService logService;
	
	@Inject
	private Session_infos session_infos;
	
	@Value("${spring.application.name}")
	private String gateway;
	@Value("${zuul.free_routes}")
	private List<String> free_routes;
	
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		
		final RequestContext context = RequestContext.getCurrentContext();
		final HttpServletRequest request = context.getRequest();
		
		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		session_infos.setSessionFields(request);

        String req = this.getRequestLog();
        logService.printAndSaveActionLog(LogUtils.INFO, req, this.getClass().getSimpleName(), method_name, req);

    	String requestURI = request.getRequestURI();
//    	context.put(ZuulUtils.FORWARD_TO_KEY, "/" /*+ context.get("serviceId").toString()*/ + requestURI.replace(gateway, ""));
		
    	// determina quale tipo di token deve essere controllato
		String tokenType = !this.checkFreeRoutes(requestURI) ? AuthUtils.ACCESS_TOKEN : null;
		tokenType = requestURI.contains("refresh") ? AuthUtils.REFRESH_TOKEN : tokenType;
		
		if (!(requestURI.contains("logout"))){
			
			// controlla se l'ip è bannato
			if (blackListService.isBanned(session_infos.getClient_ip(), session_infos.getUsername())) {
				String error_response = "Ip " + session_infos.getClient_ip() + " and/or username " + session_infos.getUsername() + " Banned";
				filterGatewayService.setErrorResponse(context, null, null, error_response, Type.ERROR, Widget.ALERT, null, HttpStatus.FORBIDDEN);
				return null;
			}

			if (!filterGatewayService.checkIsBannedAndToken(context, session_infos.getClient_ip(), tokenType, httpService.checkAccessTokenOnRequest(request))) {
				return null;
			}
		}
		
        return null;
	}
	
	/**
	 * Checks if the current URI is free from access token.
	 * @param requestURI the current URI
	 * @return true if the URI is free from access token; false otherwise.
	 */
	private boolean checkFreeRoutes(String requestURI) {
		
		return (this.free_routes.stream().filter(route -> requestURI.contains(route)).findAny().orElse(null)) != null;
	}
	
	/**
	 * Gets the String to log. The String is based by the request.
	 * @return the String to log.
	 */
	private String getRequestLog() {
		
		return this.getClass().getSimpleName() + ": " + 												// name of the class
   			   session_infos.getRequest().getMethod() + " request" + 									// type of HTTP method
   			   (session_infos.getUsername() != null ? (" by " + session_infos.getUsername()) : "") + 	// username of the caller
   			   " from " + session_infos.getClient_ip() + 												// ip of the caller
   			   " to " + session_infos.getRequest().getRequestURL() + 									// URL of the caller
   			   " through user-agent " + session_infos.getUser_agent(); 									// user-agent of the caller
	}
}