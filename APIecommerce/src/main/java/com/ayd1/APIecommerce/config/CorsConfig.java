/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.config;

import com.ayd1.APIecommerce.models.noBD.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author luism
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Autowired
    private AppProperties appProperties;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:" + this.appProperties.getHostFront1(),
                        "http://localhost:" + this.appProperties.getHostFront1())
                .allowedMethods(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE")
                .allowCredentials(true);
    }
}
