package com.cbs.cbs.config;

import com.cbs.cbs.controller.CourtBookingController;
import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = CourtBookingController.class)
public class SwaggerConfiguration {
    @Value("${swagger.host}")
    private String swaggerHost;

    @PostConstruct
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerHost)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cbs.cbs"))
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
               // .apiInfo(metaData())
                //.globalOperationParameters(Collections.singletonList(defaultAuthHeader()))
    }
}
