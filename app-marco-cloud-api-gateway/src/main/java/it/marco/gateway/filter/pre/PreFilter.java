package it.marco.gateway.filter.pre;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import it.marco.gateway.service.gateway.FilterGatewayService;
import it.marco.marco.cloud.session.Session_infos;
import it.marco.marco.service.log.LogService;
import it.marco.marco.utils.utilities.AuthUtils;
import it.marco.marco.utils.utilities.LogUtils;
import it.marco.marco.utils.utilities.ZuulUtils;

@Component
@RequestScope
public class PreFilter extends ZuulFilter {
	
	@Inject
	private FilterGatewayService filterGatewayService;
	@Inject
	private LogService logService;

	@Inject
	private Session_infos session_infos;
	
	@Value("${spring.application.name}")
	private String gateway;
	
	private List<String> headersToCheck;
	
	@PostConstruct
	private void init() {
		headersToCheck = new ArrayList<String>();
	}
	
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
    	context.put(ZuulUtils.FORWARD_TO_KEY, "/" /*+ context.get("serviceId").toString()*/ + requestURI.replace(gateway, ""));
		
    	// controlla quale tipo di token deve essere controllato
		boolean accessTokenRequired = !(requestURI.contains("login") || requestURI.contains("refresh"));
		String tokenType = accessTokenRequired ? AuthUtils.ACCESS : null;
		boolean refresh = requestURI.contains("refresh");
		tokenType = refresh ? AuthUtils.REFRESH : tokenType;
		
		String tokenHeader = refresh ? AuthUtils.REFRESH_TOKEN : AuthUtils.AUTHORIZATION;
		
		this.insertHeadersToCheck(requestURI);

		if(!filterGatewayService.checkIsBannedAndToken(context, session_infos.getClient_ip(), tokenType, request.getHeader(tokenHeader))) return null;
		filterGatewayService.checkHeader(context, request, headersToCheck);
		
        return null;
	}
	
	/**
	 * Inserts headers to be checked in a specific array. The inserting is based by the URI requested.
	 * @param requestURI the URI requested
	 */
	private void insertHeadersToCheck(String requestURI) {

		headersToCheck.add(ZuulUtils.COMPANY_ID);
		
		if (requestURI.contains("login")) {
			headersToCheck.add(ZuulUtils.API_KEY);
			headersToCheck.add(ZuulUtils.API_SECRET);
		}
	}
	
	/**
	 * Gets the String to log. The String is based by the request.
	 * @return the String to log.
	 */
	private String getRequestLog() {
		
		return this.getClass().getSimpleName() + ": " + 												// nome della classe
   			   session_infos.getRequest().getMethod() + " request" + 									// tipo del metodo HTTP
   			   " from " + session_infos.getClient_ip() + 												// ip del chiamante
   			   " to " + session_infos.getRequest().getRequestURL() + 									// URL del chiamante
   			   (!session_infos.getUsername().equals("") ? " by " + session_infos.getUsername() : "") + 	// username del chiamante
   			   " through user-agent " + session_infos.getUser_agent(); 									// user-agent del chiamante
	}
}