package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Knife4j API文档配置
 *
 * @author example
 * @version 1.0.0
 * @since 2025-01-11
 */
@Configuration
public class Knife4jConfig {

    /**
     * 创建API文档配置
     *
     * @return Docket实例
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API文档基本信息
     *
     * @return ApiInfo实例
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot Base API")
                .description("Spring Boot Base项目API文档")
                .contact(new Contact("Admin", "", "admin@example.com"))
                .version("1.0.0")
                .build();
    }
}
