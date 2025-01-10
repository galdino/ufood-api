package com.galdino.ufood.core.validation.openapi;

import com.fasterxml.classmate.TypeResolver;
import com.galdino.ufood.api.exceptionhandler.Problem;
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
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2).select()
                                                        .apis(RequestHandlerSelectors.basePackage("com.galdino.ufood.api"))
//                                                      .paths(PathSelectors.ant("/restaurants/*"))
                                                        .build()
                                                      .useDefaultResponseMessages(false)
                                                      .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                                                      .globalResponseMessage(RequestMethod.POST, globalPostResponseMessages())
                                                      .globalResponseMessage(RequestMethod.PUT, globalPutResponseMessages())
                                                      .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
                                                      .additionalModels(typeResolver.resolve(Problem.class))
                                                      .apiInfo(apiInfo())
                                                      .tags(new Tag("Cities", "Operations about cities"));
    }

    private List<ResponseMessage> globalGetResponseMessages() {
        return List.of(getInternalServerErrorResponseMessage(),
                       getNotAcceptableResponseMessage(),
                       getNotFoundResponseMessage());
    }

    private List<ResponseMessage> globalPostResponseMessages() {
        return List.of(getInternalServerErrorResponseMessage(),
                       getNotAcceptableResponseMessage(),
                       getBadRequestResponseMessage(),
                       getUnsupportedMediaTypeResponseMessage());
    }

    private List<ResponseMessage> globalPutResponseMessages() {
        return List.of(getInternalServerErrorResponseMessage(),
                       getNotAcceptableResponseMessage(),
                       getBadRequestResponseMessage(),
                       getNotFoundResponseMessage(),
                       getUnsupportedMediaTypeResponseMessage());
    }

    private List<ResponseMessage> globalDeleteResponseMessages() {
        return List.of(getInternalServerErrorResponseMessage(),
                       getNotAcceptableResponseMessage(),
                       getNotFoundResponseMessage());
    }

    private ResponseMessage getInternalServerErrorResponseMessage() {
        return new ResponseMessageBuilder()
                   .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                   .message("Internal Server Error")
                   .build();
    }

    private ResponseMessage getNotAcceptableResponseMessage() {
        return new ResponseMessageBuilder()
                   .code(HttpStatus.NOT_ACCEPTABLE.value())
                   .message("Consumer representation not acceptable")
                   .build();
    }

    private ResponseMessage getNotFoundResponseMessage() {
        return new ResponseMessageBuilder()
                   .code(HttpStatus.NOT_FOUND.value())
                   .message("Resource not found")
                   .build();
    }

    private ResponseMessage getBadRequestResponseMessage() {
        return new ResponseMessageBuilder()
                   .code(HttpStatus.BAD_REQUEST.value())
                   .message("One or more fields are invalid")
                   .build();
    }

    private ResponseMessage getUnsupportedMediaTypeResponseMessage() {
        return new ResponseMessageBuilder()
                   .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                   .message("Request body with unsupported type")
                   .build();
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
