package it.marco.gateway.fallback.config;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import it.marco.gateway.fallback.FallbackManager;
import it.marco.marco.bean.http.Outcome;
import it.marco.marco.bean.http.Type;
import it.marco.marco.bean.http.WrapperResponse;

@Configuration
public class FallbackProviders {
	
	@Bean
	public FallbackProvider genericFallback() {
		
		FallbackManager route = new FallbackManager();
		route.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
		route.setResponse(new WrapperResponse(null, new Outcome(HttpStatus.SERVICE_UNAVAILABLE, "", Type.INFO, null, null)));
		
		return route;
	}
}