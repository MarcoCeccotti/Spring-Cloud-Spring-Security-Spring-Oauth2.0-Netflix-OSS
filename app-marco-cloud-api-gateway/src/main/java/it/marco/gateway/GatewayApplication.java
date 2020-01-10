package it.marco.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import it.marco.marco.cloud.config.ribbon.CloudRibbonConfig;

@SpringBootApplication
@EnableZuulProxy
@ComponentScan(basePackages = {"it.marco.base.service", "it.marco.gateway", "it.marco.marco.cloud.config.cors"})
@EnableJpaRepositories(basePackages = {"it.marco.gateway", "it.marco.marco", "it.marco.auth.dao.auth"})
@EntityScan(basePackages = {"it.marco.gateway", "it.marco.marco", "it.marco.auth.bean.auth"})
@PropertySource({"classpath:zuul.properties"})
@EnableScheduling
@RibbonClients(defaultConfiguration = CloudRibbonConfig.class)
public class GatewayApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GatewayApplication.class);
	}

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}