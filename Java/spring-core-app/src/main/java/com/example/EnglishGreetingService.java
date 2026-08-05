package com.example;

import org.springframework.stereotype.Component;

@Component
public class EnglishGreetingService implements IGreetingService {
    
    @Override
    public String getMessage() {
        return "Hello, Spring!";
    }
}
