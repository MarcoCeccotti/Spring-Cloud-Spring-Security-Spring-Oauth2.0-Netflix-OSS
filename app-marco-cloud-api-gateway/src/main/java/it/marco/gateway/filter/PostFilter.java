package it.marco.gateway.filter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import it.marco.gateway.service.gateway.AttemptService;
import it.marco.gateway.service.gateway.BlackListService;
import it.marco.marco.cloud.service.http.HttpService;
import it.marco.marco.cloud.service.log.LogService;
import it.marco.marco.utils.utilities.BannedUtils;
import it.marco.marco.utils.utilities.LogUtils;

@Component
public class PostFilter extends ZuulFilter {
	
	@Inject
	private AttemptService attemptService;
	@Inject
	private BlackListService accessControlList;
	
	@Inject
	private HttpService httpService;
	@Inject
	private LogService logService;
 
	@Override
	public String filterType() {
		return FilterConstants.POST_TYPE;
	}
 
	@Override
	public int filterOrder() {
	    return 1;
	}
 
	@Override
	public boolean shouldFilter() {
	    return true;
	}
 
	@Override
	public Object run() {
		
		final RequestContext context = RequestContext.getCurrentContext();
		final HttpServletRequest request = context.getRequest();
	    final HttpServletResponse response = context.getResponse();
	    
	    String method_name = new Throwable().getStackTrace()[0].getMethodName();

	    String gateway_msg = this.getClass().getSimpleName() + ": response's content type is " + response.getStatus();
	    logService.printAndSaveActionLog(LogUtils.INFO, gateway_msg, this.getClass().getName(), "run", gateway_msg);
    	
	    String ip = this.httpService.getClientIP(request);
	    
    	if (this.getHttpStatusCode(response.getStatus()).is4xxClientError()) {
	    	
    		logService.printAndSaveActionLog(LogUtils.INFO, "Bad Attempt", this.getClass().getName(), method_name, "Bad Attempt");
	    	
	    	if (request.getRequestURI().contains("/login")) {
	    		if (attemptService.loginFailed(ip)) {
	    			
	    			gateway_msg = "Ip: " + ip + " has been banned";
	    		    logService.printAndSaveActionLog(LogUtils.INFO, gateway_msg, this.getClass().getName(), method_name, gateway_msg);
	    		    
	    		    accessControlList.addBanned(this.httpService.getUsernameFromRequest(request), null, ip, BannedUtils.REASON_LOGIN);
		    	}
	    	}
	    	
    	} else {
    		
    		logService.printAndSaveActionLog(LogUtils.INFO, "Good Attempt", this.getClass().getName(), method_name, "Good Attempt");

    		attemptService.loginSucceeded(ip);
    	}
	    
	    return null;
	}
	
	/**
	 * Obtaines the HttpStatus object given the value.
	 * @param httpStatus the httpStatus value
	 * @return the HttpStatus object.
	 */
	private HttpStatus getHttpStatusCode(int httpStatus) {
		return HttpStatus.valueOf(httpStatus);
	}
}