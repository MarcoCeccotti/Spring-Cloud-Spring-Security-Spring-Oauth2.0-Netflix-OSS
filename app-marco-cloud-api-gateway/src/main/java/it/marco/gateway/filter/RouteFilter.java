package it.marco.gateway.filter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import it.marco.marco.cloud.service.log.LogService;
import it.marco.marco.cloud.session.Session_infos;
import it.marco.marco.utils.utilities.LogUtils;
import it.marco.marco.utils.utilities.ZuulUtils;

@Component
public class RouteFilter extends ZuulFilter {
	
	@Inject
	private LogService logService;
	
	@Inject
	private Session_infos session_infos;
	
	@Value("${spring.application.name}")
	private String gateway;
	
	@Override
	public String filterType() {
		return FilterConstants.ROUTE_TYPE;
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
		
        String req = this.getRequestLog();
        logService.printAndSaveActionLog(LogUtils.INFO, req, this.getClass().getSimpleName(), method_name, req);
		
		session_infos.setSessionFields(request);

		String requestURI = request.getRequestURI();
    	context.put(ZuulUtils.FORWARD_TO_KEY, "/" /*+ context.get("serviceId").toString()*/ + requestURI.replace(gateway, ""));
		
        return null;
	}
	
	/**
	 * Gets the String to log. The String is based by the request.
	 * @return the String to log.
	 */
	private String getRequestLog() {
		// TODO PROVARE A CREARE UN SERVIZIO PER NON REPLICARLO OGNI VOLTA
		return this.getClass().getSimpleName() + ": " + 												// name of the class
   			   session_infos.getRequest().getMethod() + " request" + 									// type of HTTP method
   			   (session_infos.getUsername() != null ? (" by " + session_infos.getUsername()) : "") + 	// username of the caller
   			   " from " + session_infos.getClient_ip() + 												// ip of the caller
   			   " to " + session_infos.getRequest().getRequestURL() + 									// URL of the caller
   			   " through user-agent " + session_infos.getUser_agent(); 									// user-agent of the caller
	}
}