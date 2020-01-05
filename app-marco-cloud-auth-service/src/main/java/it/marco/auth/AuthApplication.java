package it.marco.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import it.marco.marco.cloud.config.ribbon.CloudRibbonConfig;

@SpringBootApplication
@ComponentScan(basePackages = {"it.marco.auth", "it.marco.base", "it.marco.marco.scheduler"})
@EnableJpaRepositories(basePackages = {"it.marco.auth", "it.marco.marco"})
@EntityScan(basePackages = {"it.marco.auth", "it.marco.marco"})
@EnableFeignClients(basePackages = {"it.marco.marco.service.feign"})
@EnableScheduling
@RibbonClients(defaultConfiguration = CloudRibbonConfig.class)
//@RibbonClients({
//	@RibbonClient(name = "gateway", configuration = CloudRibbonConfig.class)
//})
public class AuthApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AuthApplication.class);
	}
	
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}