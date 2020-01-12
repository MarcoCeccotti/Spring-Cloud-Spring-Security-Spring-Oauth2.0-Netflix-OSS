package it.marco.marco.cloud.config.properties.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.ApiPropertiesImpl;

@Component
@ConfigurationProperties(prefix = "api")
@Profile("test")
@PropertySource("classpath:test/api.properties")
public class ApiPropertiesTest extends ApiPropertiesImpl {}
