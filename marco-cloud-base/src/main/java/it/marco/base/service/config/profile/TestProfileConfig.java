package it.marco.base.service.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Profile("test")
@PropertySources({
	@PropertySource(value = "classpath:service/test/db.properties"),
	@PropertySource(value = "classpath:service/test/cache.properties"),
	@PropertySource(value = "classpath:service/test/admin.properties"),
	@PropertySource(value = "classpath:service/test/hystrix.properties"),
	@PropertySource(value = "classpath:service/test/actuators.properties"),
	@PropertySource(value = "classpath:service/test/ribbon.properties"),
	@PropertySource(value = "classpath:service/test/eureka.properties")
})
public class TestProfileConfig {}
