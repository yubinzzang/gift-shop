package com.cg.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

  /**
   * @api /swagger-ui/index.html
   * @api /v3/api-docs
   */

  @Bean
  public Docket api() {                
      return new Docket(DocumentationType.SWAGGER_2)     
        .select().apis(RequestHandlerSelectors.basePackage("com.cg")).build()       
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Gift Shop - Auth Service")
      .description("Perform Auth operation such as login, register, change password etc.")
      .license("Apache 2.0")
      .licenseUrl("https://github.com/Gagandeep39/gift-shop")
      .version("1.0")
      .contact(
        new Contact(
          "Gagandeep Singh", 
          "www.github.com/gagandeep39", 
          "Doesn't Exist")
      )
      .build();
  }

}
