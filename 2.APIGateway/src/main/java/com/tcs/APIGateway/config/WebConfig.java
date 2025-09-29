package com.tcs.APIGateway.config; // Defines the package where the WebConfig class is located

import org.springframework.context.annotation.Bean; // Imports the @Bean annotation to define a bean in the Spring context
import org.springframework.context.annotation.Configuration; // Imports the @Configuration annotation to mark this class as a configuration class
import org.springframework.web.cors.CorsConfiguration; // Imports the CorsConfiguration class to set up CORS policies
import org.springframework.web.cors.reactive.CorsWebFilter; // Imports CorsWebFilter, a filter from Spring WebFlux to handle CORS
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource; // Imports UrlBasedCorsConfigurationSource to assign CORS configuration to specific routes

@Configuration // Marks this class as a Spring configuration class
public class WebConfig {

    @Bean // Defines a bean for CorsWebFilter, which manages CORS requests in the application
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration(); // Creates a new CORS configuration
        config.setAllowCredentials(true); // Allows credentials (such as cookies) in CORS requests
        config.addAllowedOrigin("http://localhost:4200"); // Specifies the allowed origin (frontend on localhost:4200)
        config.addAllowedHeader("*"); // Allows all headers in CORS requests
        config.addAllowedMethod("*"); // Allows all HTTP methods (GET, POST, PUT, DELETE, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Creates a URL-based CORS configuration source
        source.registerCorsConfiguration("/**", config); // Applies the CORS configuration to all routes on the server

        return new CorsWebFilter(source); // Returns the configured CORS filter
    }
}
