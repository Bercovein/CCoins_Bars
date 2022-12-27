package com.ccoins.bars.configuration;

import com.ccoins.bars.controller.BarsController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket swaggerDocket() {

        return new Docket(SWAGGER_2)
                .apiInfo(apiInfo())         // infomacion adicional
                .useDefaultResponseMessages(false)
                .select()
                .apis(basePackage(BarsController.class.getPackage().getName()))     // package base donde va a encontrar los controladores
                .paths(PathSelectors.any())        // para cualquier path menos el de HomeController
                .build();

    }


    private ApiInfo apiInfo(){

        return new ApiInfoBuilder()
                .title("Chopp Coins - Bars-app")
                .description("Backend API for Tesis, UTN MDP")
                //.termsOfServiceUrl("")
                .contact(new Contact("Manigima", "", "manigima.dev@gmail.com"))
                //.license("Apache License Version 2.0")
                //.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .version("1.0.0")
                .build();
    }

}
