package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.environment")
public class AppSettings {

    private String name;
    
    public String getName() {
        return name;
    }
}
