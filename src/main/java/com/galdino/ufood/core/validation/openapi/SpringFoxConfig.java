package com.galdino.ufood.core.validation.openapi;

import com.fasterxml.classmate.TypeResolver;
import com.galdino.ufood.api.exceptionhandler.Problem;
import com.galdino.ufood.api.v1.model.KitchenModel;
import com.galdino.ufood.api.v1.openapi.model.KitchensModelOpenApi;
import com.galdino.ufood.api.v1.openapi.model.PageableModelOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
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
    public Docket apiDocketV1() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2).groupName("V1").select()
                                                        .apis(RequestHandlerSelectors.basePackage("com.galdino.ufood.api"))
                                                        .paths(PathSelectors.ant("/v1/**"))
                                                        .build()
                                                      .useDefaultResponseMessages(false)
                                                      .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                                                      .globalResponseMessage(RequestMethod.POST, globalPostResponseMessages())
                                                      .globalResponseMessage(RequestMethod.PUT, globalPutResponseMessages())
                                                      .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
//                                                      .globalOperationParameters(Arrays.asList(
//                                                              new ParameterBuilder()
//                                                                      .name("fields")
//                                                                      .description("Fields names for filtering in the response, separated by commas")
//                                                                      .parameterType("query")
//                                                                      .modelRef(new ModelRef("string"))
//                                                                      .build()
//                                                      ))
                                                      .additionalModels(typeResolver.resolve(Problem.class))
                                                      .ignoredParameterTypes(ServletWebRequest.class)
                                                      .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                                                      .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Page.class, KitchenModel.class), KitchensModelOpenApi.class))
                                                      .apiInfo(apiInfoV1())
                                                      .tags(new Tag("Cities", "Operations about cities"),
                                                            new Tag("UGroup", "Operations about ugroups"),
                                                            new Tag("Kitchens", "Operations about kitchens"),
                                                            new Tag("Payment Method", "Operations about payment method"),
                                                            new Tag("UOrder", "Operations about uorders"));
    }

    @Bean
    public Docket apiDocketV2() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2).groupName("V2").select()
                .apis(RequestHandlerSelectors.basePackage("com.galdino.ufood.api"))
                .paths(PathSelectors.ant("/v2/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalPostResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
//                                                      .globalOperationParameters(Arrays.asList(
//                                                              new ParameterBuilder()
//                                                                      .name("fields")
//                                                                      .description("Fields names for filtering in the response, separated by commas")
//                                                                      .parameterType("query")
//                                                                      .modelRef(new ModelRef("string"))
//                                                                      .build()
//                                                      ))
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .apiInfo(apiInfoV2());
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
        return getNewResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
    }

    private ResponseMessage getNotAcceptableResponseMessage() {
        return new ResponseMessageBuilder()
                .code(HttpStatus.NOT_ACCEPTABLE.value())
                .message("Consumer representation not acceptable")
                .build();
    }

    private ResponseMessage getNotFoundResponseMessage() {
        return getNewResponseMessage(HttpStatus.NOT_FOUND.value(), "Resource not found");
    }

    private ResponseMessage getBadRequestResponseMessage() {
        return getNewResponseMessage(HttpStatus.BAD_REQUEST.value(), "One or more fields are invalid");
    }

    private static ResponseMessage getNewResponseMessage(int code, String message) {
        return new ResponseMessageBuilder()
                .code(code)
                .message(message)
                .responseModel(new ModelRef("Problem"))
                .build();
    }

    private ResponseMessage getUnsupportedMediaTypeResponseMessage() {
        return new ResponseMessageBuilder()
                   .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                   .message("Request body with unsupported type")
                   .responseModel(new ModelRef("Problem"))
                   .build();
    }

    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder().title("Ufood API (Deprecated)")
                                   .description("Public API for customers and restaurants <br> <strong> Legacy Version")
                                   .version("1.0")
                                   .contact(new Contact("Ufood", "https://www.ufood.com", "info@ufood.com"))
                                   .build();
    }

    private ApiInfo apiInfoV2() {
        return new ApiInfoBuilder().title("Ufood API")
                                   .description("Public API for customers and restaurants")
                                   .version("2.0")
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
