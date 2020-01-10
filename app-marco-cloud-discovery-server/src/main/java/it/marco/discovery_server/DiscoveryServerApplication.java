package it.marco.discovery_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@EnableEurekaServer
@ComponentScan(basePackages = {"it.marco.base.server"})
@PropertySources({
	@PropertySource(value = "classpath:eureka.properties"),
//	@PropertySource(value = "classpath:test/actuators.properties"),
//	@PropertySource(value = "classpath:test/admin.properties")
})
public class DiscoveryServerApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DiscoveryServerApplication.class);
	}
	
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
