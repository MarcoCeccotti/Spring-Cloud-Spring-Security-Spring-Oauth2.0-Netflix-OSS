package it.marco.marco.cloud.config.properties.prod;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.CrashlyticProperties;

@Component
@ConfigurationProperties(prefix = "crashlytic")
@Profile("prod")
@PropertySource("classpath:prod/crashlytic.properties")
public class CrashlyticPropertiesProd extends CrashlyticProperties {}
