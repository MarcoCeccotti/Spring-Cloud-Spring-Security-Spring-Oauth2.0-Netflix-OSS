package it.marco.gateway.service.gateway;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.netflix.zuul.context.RequestContext;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import it.marco.marco.bean.http.Outcome;
import it.marco.marco.bean.http.Type;
import it.marco.marco.bean.http.Widget;
import it.marco.marco.bean.http.WrapperResponse;
import it.marco.marco.cloud.security.token.AuthAdapter;
import it.marco.marco.cloud.security.token.TokenDecoder;
import it.marco.marco.utils.utilities.AttemptsUtils;
import it.marco.marco.utils.utilities.AuthUtils;
import it.marco.marco.utils.utilities.ZuulUtils;

@Service
public class FilterGatewayServiceImpl implements FilterGatewayService {
	
	@Inject
	private BlackListService blackListService;
	
	@Inject
	private TokenDecoder tokenDecoder;
	@Inject
	private AuthAdapter authAdapter;
	
	@Override
	public void checkHeader(RequestContext context, HttpServletRequest request, List<String> headerValues) {
		
		String reportHeader = "";
		for(String value : headerValues) {
			if (request.getHeader(value) == null) {
				reportHeader += value + ", ";
			}
		}
    	
    	if (!reportHeader.equals("")) {
    		reportHeader = reportHeader.substring(0, Math.max(0, reportHeader.length() - 2));
			setErrorResponse(context, null, "Missing " + reportHeader + " on header.", Type.ERROR, Widget.ALERT, null, HttpStatus.BAD_REQUEST, ZuulUtils.HEADER_VALUES, AttemptsUtils.MISSING);
	    }
	}

	@Override
	public boolean checkIsBannedAndToken(RequestContext context, String ip, String tokenType, String tokenHeader) {
		
		// controlla se l'ip è bannato
		if (blackListService.isBanned(ip, null)) {
			setErrorResponse(context, null, "Ip and/or username Banned", Type.ERROR, Widget.ALERT, null, HttpStatus.FORBIDDEN, ZuulUtils.BANNED, Boolean.TRUE.toString());
			return false;
		}
		
		if (tokenType == null) {
			return true;
		}
		
		if (tokenHeader == null) {
			setErrorResponse(context, null, tokenType + " Token missing.", Type.ERROR, Widget.ALERT, null, HttpStatus.BAD_REQUEST, ZuulUtils.TOKEN, AttemptsUtils.MISSING);
			return false;
		}
		
		String token = "";
		switch(tokenType) {
	        case AuthUtils.ACCESS:
	        	if (!tokenHeader.contains(AuthUtils.BEARER_PREFIX) || tokenHeader.split(" ").length != 2) {
	        		setErrorResponse(context, null, "Invalid " + tokenType + " Token.", Type.ERROR, Widget.ALERT, null, HttpStatus.BAD_REQUEST, ZuulUtils.TOKEN, AttemptsUtils.INVALID);
					return false;
				}
	        
				token = tokenHeader.split(" ")[1];
				break;
	                 		
	        case AuthUtils.REFRESH:
	        	if (tokenHeader.trim().equals("")) {
					setErrorResponse(context, null, "Invalid " + tokenType + " Token.", Type.ERROR, Widget.ALERT, null, HttpStatus.BAD_REQUEST, ZuulUtils.TOKEN, AttemptsUtils.INVALID);
	 			 	return false;
	 			 }
	        
		 		 token = tokenHeader;
		 		 break;
		}
		
		String username = "";
		try {
			username = tokenDecoder.decodeJwt(token, authAdapter.getSigningKey()).getSubject();
		} catch (ExpiredJwtException exp) {
			setErrorResponse(context, null, tokenType + " Token expired.", Type.ERROR, Widget.ALERT, null, HttpStatus.BAD_REQUEST, ZuulUtils.TOKEN, AttemptsUtils.EXPIRED);
			return false;
		} catch (SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException exc) {
			setErrorResponse(context, null, "Generic error on " + tokenType + " Token.", Type.ERROR, Widget.ALERT, null, HttpStatus.BAD_REQUEST, ZuulUtils.TOKEN, AttemptsUtils.INVALID);
			return false;
		}
		
		context.addZuulResponseHeader(ZuulUtils.USERNAME, username);
		
		// controlla se l'utente ottenuto dal token è bannato
		if (blackListService.isBanned(ip, username)) {
			setErrorResponse(context, null, "Ip " + ip + " and username " + username + " Banned", Type.ERROR, Widget.ALERT, null, HttpStatus.FORBIDDEN, ZuulUtils.BANNED, Boolean.TRUE.toString());
			return false;
		}
		
		return true;
	}
	
	@Override
	public void setErrorResponse(RequestContext context, Object payload, String msg, Type type, Widget widget, String severity, HttpStatus httpStatus, String headerName, String headerValue) {
		
		context.setSendZuulResponse(false);
		context.addZuulResponseHeader(headerName, headerValue);
		context.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
		context.setResponseStatusCode(httpStatus.value());
		context.setResponseBody(new Gson().toJson(new WrapperResponse(payload, new Outcome(httpStatus, msg, type, severity, widget))));
	}
}