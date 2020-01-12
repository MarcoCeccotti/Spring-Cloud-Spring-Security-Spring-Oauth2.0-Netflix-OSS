package it.marco.marco.cloud.config.properties.prod;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.MailProperties;

@Component
@ConfigurationProperties(prefix = "mail")
@Profile("prod")
@PropertySource("classpath:prod/mail.properties")
public class MailPropertiesProd extends MailProperties {}
