package com.mos.bsd.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Sets;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author hero
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	  @Bean
	    public Docket configSpringfoxDocket_all(ApiInfo apiInfo) {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .produces(Sets.newHashSet("application/json"))
	                .consumes(Sets.newHashSet("application/json"))
	                .protocols(Sets.newHashSet("http", "https"))
	                .apiInfo(apiInfo)
	                .forCodeGeneration(true)
	                .select().paths(regex("/api.*"))
	                .build();
	    }

	    @Bean
	    public Docket createRestApi(ApiInfo apiInfo) {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .groupName("user")
	                .produces(Sets.newHashSet("application/json"))
	                .consumes(Sets.newHashSet("application/json"))
	                .protocols(Sets.newHashSet("http", "https"))
	                .apiInfo(apiInfo)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.mos.gateway.archive.service.impl"))
	                .apis(RequestHandlerSelectors.basePackage("com.mos.gateway.bills.service.impl"))
	                .paths(regex("/api.*"))
	                .build();
	    }

	    @Bean
	    public ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	        		.title("X2 REST API")
	                .description("API文档。")
	                .termsOfServiceUrl("http://www.x2erp.com/")
	                .version("1.0")
	                .build();
	    }
}
