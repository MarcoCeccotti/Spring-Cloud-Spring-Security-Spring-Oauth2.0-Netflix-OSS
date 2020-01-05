package it.marco.marco.cloud.config.cors.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.CorsFilterProperties;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilterConfig implements Filter {
	
	@Inject
	private CorsFilterProperties corsFilterProps;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

	    HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
	    
    	response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    	response.setHeader("Access-Control-Allow-Credentials", String.valueOf(corsFilterProps.isAllowCredentials()));
    	response.setHeader("Access-Control-Allow-Methods", String.join(", ", corsFilterProps.getAllowedMethods()));
    	response.setHeader("Access-Control-Max-Age", String.valueOf(corsFilterProps.getMaxAge()));
    	response.setHeader("Access-Control-Allow-Headers", String.join(", ", corsFilterProps.getAllowedHeaders()));
	
	    chain.doFilter(req, res);
	}
	
	@Override
	public void init(FilterConfig filterConfig) {}
	
	@Override
	public void destroy() {}

}