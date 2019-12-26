package it.marco.marco.cloud.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import it.marco.marco.cloud.session.Session_infos;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

	@Inject
	private Session_infos session_infos;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		session_infos.setSessionFields(request);
		
		return super.preHandle(request, response, handler);
	}
}