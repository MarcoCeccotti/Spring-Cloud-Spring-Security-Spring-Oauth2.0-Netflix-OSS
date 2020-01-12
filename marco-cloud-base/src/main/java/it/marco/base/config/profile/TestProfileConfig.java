package it.marco.base.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Profile("test")
@PropertySources({
	@PropertySource(value = "classpath:test/db.properties"),
	@PropertySource(value = "classpath:test/cache.properties"),
	@PropertySource(value = "classpath:test/admin.properties"),
	@PropertySource(value = "classpath:test/hystrix.properties"),
	@PropertySource(value = "classpath:test/actuators.properties"),
	@PropertySource(value = "classpath:test/ribbon.properties"),
	@PropertySource(value = "classpath:test/eureka.properties")
})
public class TestProfileConfig {}
