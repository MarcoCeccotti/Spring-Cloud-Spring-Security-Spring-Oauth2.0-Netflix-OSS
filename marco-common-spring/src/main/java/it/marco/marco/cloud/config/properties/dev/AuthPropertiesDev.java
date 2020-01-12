package it.marco.marco.cloud.config.properties.dev;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.AuthProperties;

@Component
@ConfigurationProperties(prefix = "auth")
@Profile("dev")
@PropertySource("classpath:dev/auth.properties")
public class AuthPropertiesDev extends AuthProperties {}
