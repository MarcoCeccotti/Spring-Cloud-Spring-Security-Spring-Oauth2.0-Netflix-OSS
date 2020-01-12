package it.marco.marco.cloud.config.properties.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import it.marco.marco.cloud.config.properties.MailPropertiesImpl;

@Component
@ConfigurationProperties(prefix = "mail")
@Profile("test")
@PropertySource("classpath:test/mail.properties")
public class MailPropertiesTest extends MailPropertiesImpl {}
