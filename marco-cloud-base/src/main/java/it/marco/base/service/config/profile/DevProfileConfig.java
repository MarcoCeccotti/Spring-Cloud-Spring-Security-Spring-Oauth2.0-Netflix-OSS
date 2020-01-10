package it.marco.base.service.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Profile("dev")
@PropertySources({
	@PropertySource(value = "classpath:service/dev/db.properties"),
	@PropertySource(value = "classpath:service/dev/cache.properties"),
	@PropertySource(value = "classpath:service/dev/admin.properties"),
	@PropertySource(value = "classpath:service/dev/hystrix.properties"),
	@PropertySource(value = "classpath:service/dev/actuators.properties"),
	@PropertySource(value = "classpath:service/dev/ribbon.properties"),
	@PropertySource(value = "classpath:service/dev/eureka.properties")
})
public class DevProfileConfig {}
