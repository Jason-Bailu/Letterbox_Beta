package com.dxy.letterboxbackstage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author: JasonD
 * @date: 2023/5/9 20:04
 * @Description: swagger3配置类
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    /**
     * 创建api应用
     * select()返回一个ApiSelectorBuilder实例，控制接口暴露给Swagger使用
     * apis()扫描api包
     * @return
     */
    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("标准接口")
                .apiInfo(apiInfo("LetterBox后台接口", "1.0"))
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dxy.letterboxbackstage.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建api基本信息
     * @param title
     * @param version
     * @return
     */
    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfoBuilder()
                .title(title)
                .description("这是用于LetterBox后台管理的相关接口开发说明")
                .termsOfServiceUrl("www.bailublog.cn")
                .contact(new Contact("Design By Bailu", "www.bailublog.cn", "1242857354@qq.com"))
                .version(version)
                .build();
    }
}
