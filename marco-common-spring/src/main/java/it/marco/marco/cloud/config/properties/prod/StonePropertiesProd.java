package it.marco.marco.cloud.config.properties.prod;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.StoneProperties;

@Component
@ConfigurationProperties(prefix = "stone")
@Profile("prod")
@PropertySource("classpath:prod/stone.properties")
public class StonePropertiesProd extends StoneProperties {}
