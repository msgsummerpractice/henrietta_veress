package com.example.spring_rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_rest.dto.MfaVerifyRequest;
import com.example.spring_rest.dto.RegisterRequest;
import com.example.spring_rest.dto.SignInRequest;
import com.example.spring_rest.dto.SignInResponse;
import com.example.spring_rest.dto.UserResponse;
import com.example.spring_rest.model.User;
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

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
         User createdUser = authService.register(request);

        UserResponse response = new UserResponse(
            createdUser.getId(),
            createdUser.getUsername(),
            createdUser.getEmail(),
            createdUser.getFirstName(),
            createdUser.getLastName(),
            createdUser.getCreatedAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/verify-mfa")
        public SignInResponse verifyMfa(@RequestBody MfaVerifyRequest request) {
            return authService.verifyMfa(request);
    }
}
