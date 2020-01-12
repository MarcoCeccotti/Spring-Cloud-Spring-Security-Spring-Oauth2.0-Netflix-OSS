package it.marco.base.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Profile("dev")
@PropertySources({
	@PropertySource(value = "classpath:dev/db.properties"),
	@PropertySource(value = "classpath:dev/cache.properties"),
	@PropertySource(value = "classpath:dev/admin.properties"),
	@PropertySource(value = "classpath:dev/hystrix.properties"),
	@PropertySource(value = "classpath:dev/actuators.properties"),
	@PropertySource(value = "classpath:dev/ribbon.properties"),
	@PropertySource(value = "classpath:dev/eureka.properties")
})
public class DevProfileConfig {}
