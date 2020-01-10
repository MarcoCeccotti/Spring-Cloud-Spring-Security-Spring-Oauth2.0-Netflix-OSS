package it.marco.base.server.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Profile("prod")
@PropertySources({
	@PropertySource(value = "classpath:server/prod/actuators.properties"),
	@PropertySource(value = "classpath:server/prod/admin.properties")
})
public class ProdProfileConfig {}
