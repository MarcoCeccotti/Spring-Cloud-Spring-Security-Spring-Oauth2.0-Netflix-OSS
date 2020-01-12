package it.marco.marco.cloud.config.properties.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.AuthPropertiesImpl;

@Component
@ConfigurationProperties(prefix = "auth")
@Profile("test")
@PropertySource("classpath:test/auth.properties")
public class AuthPropertiesTest extends AuthPropertiesImpl {}
