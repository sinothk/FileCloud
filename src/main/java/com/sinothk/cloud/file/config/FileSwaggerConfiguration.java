package com.sinothk.cloud.file.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class FileSwaggerConfiguration {

    @Bean(value = "systemGroupRestApi")
    public Docket systemGroupRestApi() {
        // http://localhost:7000/fileCloud/doc.html
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("文件系统接口")
                .description("接口包括：文件管理！")
                .termsOfServiceUrl("http://www.sinothk.com/")
                .contact(new Contact("LiangYT", "", "381518188@qq.com"))
                .version("1.0")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("文件系统接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sinothk.cloud.file.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
