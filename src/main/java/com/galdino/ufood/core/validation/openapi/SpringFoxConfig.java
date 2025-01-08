package com.galdino.ufood.core.validation.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                        .apis(RequestHandlerSelectors.basePackage("com.galdino.ufood.api"))
//                                                      .paths(PathSelectors.ant("/restaurants/*"))
                                                        .build()
                                                      .useDefaultResponseMessages(false)
                                                      .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                                                      .apiInfo(apiInfo())
                                                      .tags(new Tag("Cities", "Operations about cities"));
    }

    private List<ResponseMessage> globalGetResponseMessages() {
        return List.of(new ResponseMessageBuilder()
                           .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                           .message("Internal Server Error")
                           .build(),
                       new ResponseMessageBuilder()
                           .code(HttpStatus.NOT_ACCEPTABLE.value())
                           .message("Consumer representation not acceptable")
                           .build()
                      );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Ufood API")
                                   .description("Public API for customers and restaurants")
                                   .version("1.0")
                                   .contact(new Contact("Ufood", "https://www.ufood.com", "info@ufood.com"))
                                   .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
