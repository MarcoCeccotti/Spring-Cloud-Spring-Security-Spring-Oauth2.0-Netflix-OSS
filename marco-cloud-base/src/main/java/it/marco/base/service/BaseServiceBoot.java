package it.marco.base.service;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@EnableCircuitBreaker
@EnableCaching
@EnableDiscoveryClient
//@PropertySources({
//	@PropertySource(value = "classpath:crashlytic.properties"),
//	@PropertySource(value = "classpath:db.properties"),
//	@PropertySource(value = "classpath:mail.properties"),
//	@PropertySource(value = "classpath:cache.properties"),
//	@PropertySource(value = "classpath:admin.properties"),
//	@PropertySource(value = "classpath:hystrix.properties"),
//	@PropertySource(value = "classpath:actuators.properties"),
//	@PropertySource(value = "classpath:ribbon.properties"),
//	@PropertySource(value = "classpath:eureka.properties")
//})
@ComponentScan(basePackages = {"it.marco.marco"},
			   excludeFilters = {
					   @ComponentScan.Filter(type = FilterType.REGEX, pattern = "it.marco.marco.cloud.config.cors.*"),
					   @ComponentScan.Filter(type = FilterType.REGEX, pattern = "it.marco.marco.scheduler.*")
			   }
)
public class BaseServiceBoot {}
