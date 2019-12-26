package it.marco.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = {"it.marco.gateway", "it.marco.marco", "it.marco.auth.dao.auth"})
@EntityScan(basePackages = {"it.marco.gateway", "it.marco.marco", "it.marco.auth.bean.auth"})
@ComponentScan(basePackages = {"it.marco.gateway", "it.marco.marco"},
			   excludeFilters = {
					   	@ComponentScan.Filter(type = FilterType.REGEX, pattern = "it.marco.marco.scheduler.*")
			   }
)
@EnableScheduling
@PropertySource({"classpath:application.properties",
				 "classpath:db.properties",
				 "classpath:api.properties",
				 //"classpath:cors_filter.properties",
	 			 "classpath:cache.properties",
				 "classpath:eureka.properties",
				 "classpath:zuul.properties"})
public class ApiGateway extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApiGateway.class);
	}

    public static void main(String[] args) {
        SpringApplication.run(ApiGateway.class, args);
    }
}