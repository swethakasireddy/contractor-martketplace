/**
 * @author swethakasireddy
 * This class is for Swagger configuration
 */
package com.intuit.marketplace.contractor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * Swagger config.
 *
 * @author Swetha Kasireddy
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Intuit MarketPlace API")
                        .description(" Intuit MarketPlace RESTful service for contractor bidding."));
    }
}
