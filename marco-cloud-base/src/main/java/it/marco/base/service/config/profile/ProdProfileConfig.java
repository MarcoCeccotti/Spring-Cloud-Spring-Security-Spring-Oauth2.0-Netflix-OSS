package it.marco.base.service.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Profile("prod")
@PropertySources({
	@PropertySource(value = "classpath:service/prod/db.properties"),
	@PropertySource(value = "classpath:service/prod/cache.properties"),
	@PropertySource(value = "classpath:service/prod/admin.properties"),
	@PropertySource(value = "classpath:service/prod/hystrix.properties"),
	@PropertySource(value = "classpath:service/prod/actuators.properties"),
	@PropertySource(value = "classpath:service/prod/ribbon.properties"),
	@PropertySource(value = "classpath:service/prod/eureka.properties")
})
public class ProdProfileConfig {}
