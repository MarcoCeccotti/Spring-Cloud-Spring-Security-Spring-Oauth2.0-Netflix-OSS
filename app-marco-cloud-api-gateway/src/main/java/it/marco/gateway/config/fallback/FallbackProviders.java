package it.marco.gateway.config.fallback;

import javax.annotation.PostConstruct;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.marco.gateway.fallback.FallbackManager;
import it.marco.marco.bean.http.Outcome;
import it.marco.marco.bean.http.Type;
import it.marco.marco.bean.http.WrapperResponse;

@Configuration
public class FallbackProviders {
	
	private Gson gson;
	
	@PostConstruct
	private void init() {
		GsonBuilder gsonBuilder = new GsonBuilder();  
		gsonBuilder.serializeNulls();  
		this.gson = gsonBuilder.create();
	}

	@Bean
	public FallbackProvider authFallback() {
		
		String response = "Il Server di autenticazione è al momento offline. Prego ritentare in un secondo momento.";
		
		FallbackManager route = new FallbackManager();
		route.setRoute("auth-server");
		route.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
		route.setResponseBody(this.gson.toJson(new WrapperResponse(null, new Outcome(HttpStatus.SERVICE_UNAVAILABLE, response, Type.INFO, null, null))));
		
		return route;
	}
	
	@Bean
	public FallbackProvider stoneFallback() {
		
		String response = "Il Servizio Stone è al momento offline. Prego ritentare in un secondo momento.";
		
		FallbackManager route = new FallbackManager();
		route.setRoute("stone-service");
		route.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
		route.setResponseBody(this.gson.toJson(new WrapperResponse(null, new Outcome(HttpStatus.SERVICE_UNAVAILABLE, response, Type.INFO, null, null))));
		
		return route;
	}
	
	@Bean
	public FallbackProvider productsFallback() {
		
		String response = "Il Servizio Products è al momento offline. Prego ritentare in un secondo momento.";
		
		FallbackManager route = new FallbackManager();
		route.setRoute("products-service");
		route.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
		route.setResponseBody(this.gson.toJson(new WrapperResponse(null, new Outcome(HttpStatus.SERVICE_UNAVAILABLE, response, Type.INFO, null, null))));
		
		return route;
	}
}