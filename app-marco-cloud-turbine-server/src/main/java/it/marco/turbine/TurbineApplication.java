package it.marco.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"it.marco.turbine", "it.marco.base.server"})
@PropertySources({
	@PropertySource(value = "classpath:eureka.properties"),
	@PropertySource(value = "classpath:turbine.properties")
})
@EnableHystrixDashboard
@EnableTurbine
public class TurbineApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TurbineApplication.class);
	}
	
    public static void main(String[] args) {
        SpringApplication.run(TurbineApplication.class, args);
    }
}
