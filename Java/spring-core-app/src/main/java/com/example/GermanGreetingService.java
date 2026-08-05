package com.example;

import org.springframework.stereotype.Component;

@Component
public class GermanGreetingService implements IGreetingService {
    
    @Override
    public String getMessage() {
        return "Hallo, Spring!";
    }
    
}
