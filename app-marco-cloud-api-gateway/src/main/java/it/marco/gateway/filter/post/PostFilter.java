package it.marco.gateway.filter.post;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import it.marco.gateway.service.gateway.BlackListService;
import it.marco.gateway.service.gateway.LoginAttemptService;
import it.marco.marco.utils.utilities.AttemptsUtils;
import it.marco.marco.utils.utilities.BannedUtils;
import it.marco.marco.utils.utilities.ZuulUtils;

@Component
@RefreshScope
public class PostFilter extends ZuulFilter {
	
	@Inject
	private LoginAttemptService loginAttemptService;

	@Inject
	private BlackListService accessControlList;
	
	private Map<String, String> mapHeader = new HashMap<String, String>();
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@PostConstruct
	public void init() {
		mapHeader.put(ZuulUtils.TOKEN, ""); mapHeader.put(ZuulUtils.USERNAME, "");
		mapHeader.put(ZuulUtils.BANNED, ""); mapHeader.put(ZuulUtils.HEADER_VALUES, "");
		mapHeader.put(ZuulUtils.LOGIN, "");
	}
 
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
	    final HttpServletResponse response = context.getResponse();
	    
	    log.info(this.getClass().getSimpleName() + ": response's content type is " + response.getStatus());
	    
	    mapHeader.replaceAll((k, v) -> "");
	    context.getZuulResponseHeaders().forEach( item -> mapHeader.put(item.first(), item.second()) );
    	
    	if (mapHeader.get(ZuulUtils.TOKEN).equals(AttemptsUtils.EXPIRED) || mapHeader.get(ZuulUtils.BANNED).equals(Boolean.TRUE.toString())) {
    		return null;
    	}

	    String ip = context.getRequest().getRemoteAddr();
	    
    	if (mapHeader.get(ZuulUtils.TOKEN).equals(AttemptsUtils.INVALID) || mapHeader.get(ZuulUtils.TOKEN).equals(AttemptsUtils.MISSING)) {
//	    	accessControlList.addBanned(null, ip, BannedUtils.REASON_TOKEN);
	    	return null;
    	}
    	
    	if (mapHeader.get(ZuulUtils.HEADER_VALUES).equals(AttemptsUtils.MISSING)) {
    		accessControlList.addBanned(null, null, ip, BannedUtils.REASON_HEADER);
    		return null;
    	}
    	
	    if (response.getStatus() >= HttpStatus.MULTIPLE_CHOICES.value() &&
    	   response.getStatus() < HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	    	
			log.info("Bad Attempt");
			
	    	if (loginAttemptService.loginFailed(ip)) {
	    		if (mapHeader.get(ZuulUtils.LOGIN).equals(Boolean.TRUE.toString())) {
		    		accessControlList.addBanned(mapHeader.get(ZuulUtils.USERNAME), null, ip, BannedUtils.REASON_LOGIN);
					log.info("Ip: " + ip + " has been banned");
	    		} else {
		    		//accessControlList.addBanned(mapHeader.get(ZuulUtils.USERNAME), ip, BannedUtils.REASON_GENERAL);
					log.info("User: " + mapHeader.get(ZuulUtils.USERNAME) + " with Ip: " + ip + " has been banned");
	    		}
	    	}
	    } else {
	    	log.info("Good Attempt");
	    }
	    
	    return null;
	}
}