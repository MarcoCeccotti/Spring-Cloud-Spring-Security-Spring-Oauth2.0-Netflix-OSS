package it.marco.marco.cloud.config.properties.dev;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.CorsFilterPropertiesImpl;

@Component
@ConfigurationProperties(prefix = "cors-filter")
@Profile("dev")
@PropertySource("classpath:dev/cors_filter.properties")
public class CorsFilterPropertiesDev extends CorsFilterPropertiesImpl {}
