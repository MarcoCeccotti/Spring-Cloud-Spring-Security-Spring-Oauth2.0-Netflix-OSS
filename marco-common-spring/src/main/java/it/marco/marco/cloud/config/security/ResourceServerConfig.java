package it.marco.marco.cloud.config.security;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import it.marco.marco.cloud.config.properties.ApiProperties;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Inject
	private ApiProperties apiProps;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.cors().and()
	        .csrf().disable()
	        .anonymous().disable()
	        .authorizeRequests()
	        .antMatchers(apiProps.getAntPathAuthenticated().replaceAll("\\s", "").split(",")).authenticated()
	        	.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}