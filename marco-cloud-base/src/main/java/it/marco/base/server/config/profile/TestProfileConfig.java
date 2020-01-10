package it.marco.base.server.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Profile("test")
@PropertySources({
	@PropertySource(value = "classpath:server/test/actuators.properties"),
	@PropertySource(value = "classpath:server/test/admin.properties")
})
public class TestProfileConfig {}
