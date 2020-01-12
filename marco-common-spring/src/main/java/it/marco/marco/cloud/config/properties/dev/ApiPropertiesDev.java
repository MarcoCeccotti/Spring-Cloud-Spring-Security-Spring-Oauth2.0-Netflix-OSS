package it.marco.marco.cloud.config.properties.dev;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.ApiPropertiesImpl;

@Component
@ConfigurationProperties(prefix = "api")
@Profile("dev")
@PropertySource("classpath:dev/api.properties")
public class ApiPropertiesDev extends ApiPropertiesImpl {}
