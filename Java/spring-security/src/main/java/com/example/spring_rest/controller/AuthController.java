package com.example.spring_rest.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_rest.dto.SignInRequest;
import com.example.spring_rest.dto.SignInResponse;
import com.example.spring_rest.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public SignInResponse login(@Valid @RequestBody SignInRequest request) {
        return authService.login(request);
    }
}
