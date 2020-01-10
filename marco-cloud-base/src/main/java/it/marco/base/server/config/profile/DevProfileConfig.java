package it.marco.base.server.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Profile("dev")
@PropertySources({
	@PropertySource(value = "classpath:server/dev/actuators.properties"),
	@PropertySource(value = "classpath:server/dev/admin.properties")
})
public class DevProfileConfig {}
