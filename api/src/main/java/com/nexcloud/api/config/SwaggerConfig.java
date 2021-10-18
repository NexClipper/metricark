package com.nexcloud.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig extends WebMvcConfigurerAdapter{

    @Bean
    public Docket api() {
		return new Docket(DocumentationType.OAS_30)
                .select()                                      
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Metricark swagger")
                .description("Metricark swagger")
                .version("1.0")
                .build();
    }

    // ---------------------------------------------------------------

//    @Primary
//    @Bean
//    public SwaggerResourcesProvider swaggerResourcesProvider(
//            InMemorySwaggerResourcesProvider defaultResourcesProvider) {
//        return () -> {
//            List<SwaggerResource> resources = new ArrayList<>();
//            resources.add(loadResource("apis"));
//            return resources;
//        };
//    }
//
//    private SwaggerResource loadResource(String resource) {
//        SwaggerResource wsResource = new SwaggerResource();
//        wsResource.setName(resource);
//        wsResource.setSwaggerVersion("2.0");
////        wsResource.setLocation("/static/swagger-apis/api1/swagger.yaml");
//        wsResource.setLocation("/swagger.yaml");
////        wsResource.setLocation("/v2/api-docs");
////        wsResource.setLocation("/META-INF/resources/swagger-ui.html");
//        return wsResource;
//    }
}