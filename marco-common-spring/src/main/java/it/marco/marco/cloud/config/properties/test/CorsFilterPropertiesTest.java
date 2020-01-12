package it.marco.marco.cloud.config.properties.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.CorsFilterProperties;

@Component
@ConfigurationProperties(prefix = "cors-filter")
@Profile("test")
@PropertySource("classpath:test/cors_filter.properties")
public class CorsFilterPropertiesTest extends CorsFilterProperties {}
