package org.example.config;

import org.example.service.CoworkingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.example")
public class AppConfig {
    @Bean(initMethod = "init", destroyMethod = "cleanup")
    public CoworkingService coworkingService() {
        return new CoworkingService();
    }
}
