package com.billpin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
public class SpringbootApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApp.class, args);
	}
	
	
	@Configuration
	@EnableSwagger2
	public class SwaggerConfig {
	    @Bean
	    public Docket productApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	        		.select()                                  
	                .apis(RequestHandlerSelectors.basePackage("com.billpin."))              
	                .paths(PathSelectors.any())                          
	                .build()
	                .apiInfo(apiRestInfo());
	             
	    }
	}
	
	 private ApiInfo apiRestInfo() {
	        return new ApiInfoBuilder()
	        		.title("BillPin")
	                .description("splitwise ")
	                .version("0.0.1")
	                .build();
	    }
}
