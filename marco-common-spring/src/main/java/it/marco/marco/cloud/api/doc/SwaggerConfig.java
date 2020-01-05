package it.marco.marco.cloud.api.doc;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiModelProperty;
import it.marco.marco.cloud.config.properties.ApiProperties;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Inject
	private ApiProperties apiProps;
	
    @Bean
    @ApiModelProperty()
    public Docket productApi() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			.select()
    			.apis(RequestHandlerSelectors.basePackage(apiProps.getBasePackage()))
    			.paths(PathSelectors.any())
    			.build()
    			.apiInfo(apiInfo());
    }
    
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(apiProps.getTitle())
            .description(apiProps.getDescription())
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .termsOfServiceUrl("")
            .version(apiProps.getVersion())
            .build();
    }
}