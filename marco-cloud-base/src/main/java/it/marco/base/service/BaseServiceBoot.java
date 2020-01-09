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
@ComponentScan(basePackages = {"it.marco.marco"},
			   excludeFilters = {
					   @ComponentScan.Filter(type = FilterType.REGEX, pattern = "it.marco.marco.cloud.config.cors.*"),
					   @ComponentScan.Filter(type = FilterType.REGEX, pattern = "it.marco.marco.scheduler.*")
			   }
)
public class BaseServiceBoot {}
