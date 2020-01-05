package it.marco.gateway.service.gateway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.google.gson.Gson;
import com.netflix.zuul.context.RequestContext;

import it.marco.marco.bean.http.Outcome;
import it.marco.marco.bean.http.Type;
import it.marco.marco.bean.http.Widget;
import it.marco.marco.bean.http.WrapperResponse;
import it.marco.marco.cloud.security.token.validator.TokenValidatorService;
import it.marco.marco.cloud.service.http.HttpService;
import it.marco.marco.utils.utilities.AttemptsUtils;
import it.marco.marco.utils.utilities.AuthUtils;
import it.marco.marco.utils.utilities.ZuulUtils;

@Service
@RequestScope
public class FilterGatewayServiceImpl implements FilterGatewayService {
	
	@Inject
	private HttpService httpService;
	@Inject
	private TokenValidatorService tokenValidatorService;
	
	private Map<String, String> responseHeaders;
	
	@PostConstruct
	private void init () {
		responseHeaders = new HashMap<String, String>();
	}
	
	@Override
	public void checkHeader(RequestContext context, HttpServletRequest request, List<String> headerValues) {
		
		String reportHeader = "";
		for (String value : headerValues) {
			if (request.getHeader(value) == null) {
				reportHeader += value + ", ";
			}
		}
    	
    	if (!reportHeader.equals("")) {
    		responseHeaders.put(ZuulUtils.HEADER_VALUES, AttemptsUtils.MISSING);
    		reportHeader = reportHeader.substring(0, Math.max(0, reportHeader.length() - 2));
			setErrorResponse(context, responseHeaders, null, "Missing " + reportHeader + " on header.", Type.ERROR, Widget.ALERT, null, HttpStatus.BAD_REQUEST);
	    }
	}

	@Override
	public boolean checkIsBannedAndToken(RequestContext context, String ip, String tokenType, String tokenToCheck) {
		
		// se la chiamata non necessita di un token
		if (tokenType == null) {
			return true;
		}
		
		// se la chiamata necessita di un token, ma questo non è presente
		if (tokenToCheck == null) {
			responseHeaders.put(ZuulUtils.TOKEN, AttemptsUtils.MISSING);
			setErrorResponse(context, responseHeaders, null, tokenType + " missing.", Type.ERROR, Widget.ALERT, null, HttpStatus.BAD_REQUEST);			
			return false;
		}
		
		// controlla se il token utilizzato è valido
		if (!tokenValidatorService.validateToken(tokenToCheck, tokenType)) {
			responseHeaders.put(ZuulUtils.TOKEN, AttemptsUtils.INVALID);
			setErrorResponse(context, responseHeaders, null, "Invalid " + tokenType, Type.ERROR, Widget.ALERT, null, HttpStatus.BAD_REQUEST);
			return false;
		}
		
		// recupera lo username del richiedente dal token
		String username = (String) httpService.getInfoFromToken(tokenToCheck, AuthUtils.CLAIM_KEY_USERNAME);
		if (username == null) {
			responseHeaders.put(ZuulUtils.TOKEN, AttemptsUtils.MISSING + " " + AttemptsUtils.INVALID);
			setErrorResponse(context, responseHeaders, null, tokenType + " expired or invalid", Type.ERROR, Widget.ALERT, null, HttpStatus.BAD_REQUEST);
		}
		
		return true;
	}
	
	@Override
	public void setErrorResponse(RequestContext context, Map<String, String> responseHeaders, Object payload, String msg, Type type, Widget widget, String severity, HttpStatus httpStatus) {
		
		context.setSendZuulResponse(false);
		if (responseHeaders != null) {
		    responseHeaders.forEach((k,v) -> context.addZuulResponseHeader(k, v));
		}
		context.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
		context.setResponseStatusCode(httpStatus.value());
		context.setResponseBody(new Gson().toJson(new WrapperResponse(payload, new Outcome(httpStatus, msg, type, severity, widget))));
	}
}