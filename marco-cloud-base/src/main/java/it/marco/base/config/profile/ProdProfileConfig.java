package it.marco.base.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Profile("prod")
@PropertySources({
	@PropertySource(value = "classpath:prod/db.properties"),
	@PropertySource(value = "classpath:prod/cache.properties"),
	@PropertySource(value = "classpath:prod/admin.properties"),
	@PropertySource(value = "classpath:prod/hystrix.properties"),
	@PropertySource(value = "classpath:prod/actuators.properties"),
	@PropertySource(value = "classpath:prod/ribbon.properties"),
	@PropertySource(value = "classpath:prod/eureka.properties"),
	@PropertySource(value = "classpath:prod/api.properties")
})
public class ProdProfileConfig {}
