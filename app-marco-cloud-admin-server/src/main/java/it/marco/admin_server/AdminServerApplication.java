package it.marco.admin_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"it.marco.admin_server"})
@PropertySources({
	@PropertySource(value = "classpath:admin.properties"),
	@PropertySource(value = "classpath:eureka.properties")
})
@EnableAdminServer
public class AdminServerApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AdminServerApplication.class);
	}
	
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }
}
