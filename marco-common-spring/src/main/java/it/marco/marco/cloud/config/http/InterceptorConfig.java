package it.marco.marco.cloud.config.http;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import it.marco.marco.cloud.interceptor.RequestInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Inject
	private RequestInterceptor controllerInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(controllerInterceptor);
	}
}
