package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example")
public class AppConfig {  // this replaces the xml
    
    @Bean
    public HelloService helloService() {
        return new HelloService();
    }
}
