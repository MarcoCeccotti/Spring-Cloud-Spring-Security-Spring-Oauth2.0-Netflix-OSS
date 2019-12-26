package it.marco.stone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableCaching
@EnableJpaRepositories(basePackages = {"it.marco.stone", "it.marco.marco"})
@EntityScan(basePackages = {"it.marco.stone", "it.marco.marco"})
@ComponentScan(basePackages = {"it.marco.stone", "it.marco.marco"})
@PropertySource({"classpath:application.properties",
				 "classpath:auth.properties",
	 			 "classpath:crashlytic.properties",
	 			 "classpath:db.properties",
	 			 "classpath:mail.properties",
	 			 "classpath:eureka.properties"})
public class StoneApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(StoneApplication.class);
	}
	
    public static void main(String[] args) {
        SpringApplication.run(StoneApplication.class, args);
    }
}