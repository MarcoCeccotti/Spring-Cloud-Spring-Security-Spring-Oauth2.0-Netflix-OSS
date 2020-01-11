package it.marco.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"it.marco.products", "it.marco.base"})
@EnableJpaRepositories(basePackages = {"it.marco.products", "it.marco.marco"})
@EntityScan(basePackages = {"it.marco.products", "it.marco.marco"})
public class ProductsApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProductsApplication.class);
	}
	
    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }
}