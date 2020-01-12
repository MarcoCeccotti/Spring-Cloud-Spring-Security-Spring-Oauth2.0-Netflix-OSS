package it.marco.marco.cloud.config.properties.prod;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.CorsFilterPropertiesImpl;

@Component
@ConfigurationProperties(prefix = "cors-filter")
@Profile("prod")
@PropertySource("classpath:prod/cors_filter.properties")
public class CorsFilterPropertiesProd extends CorsFilterPropertiesImpl {}
