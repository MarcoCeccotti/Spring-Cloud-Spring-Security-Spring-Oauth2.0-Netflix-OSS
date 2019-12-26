package it.marco.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import it.marco.marco.cloud.config.security.SecurityConfig;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableCaching
@EnableJpaRepositories(basePackages = {"it.marco.products", "it.marco.marco"})
@EntityScan(basePackages = {"it.marco.products", "it.marco.marco"})
@ComponentScan(basePackages = {"it.marco.products", "it.marco.marco"},
			   excludeFilters = {
					   	@ComponentScan.Filter(type = FilterType.REGEX, pattern = "it\\.marco\\.marco\\.scheduler\\..*"),
					   	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityConfig.class)
			   }
)
@PropertySource({"classpath:application.properties",
	 			 "classpath:crashlytic.properties",
	 			 "classpath:db.properties",
	 			 "classpath:mail.properties",
	 			 "classpath:cache.properties",
	 			 "classpath:eureka.properties"})
public class ProductsApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProductsApplication.class);
	}
	
    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }
}