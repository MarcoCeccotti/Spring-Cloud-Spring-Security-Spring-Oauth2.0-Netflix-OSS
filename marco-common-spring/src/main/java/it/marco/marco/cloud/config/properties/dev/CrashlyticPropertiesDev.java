package it.marco.marco.cloud.config.properties.dev;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.CrashlyticPropertiesImpl;

@Component
@ConfigurationProperties(prefix = "crashlytic")
@Profile("dev")
@PropertySource("classpath:dev/crashlytic.properties")
public class CrashlyticPropertiesDev extends CrashlyticPropertiesImpl {}
