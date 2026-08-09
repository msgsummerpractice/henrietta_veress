package com.example.spring_rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_rest.dto.MfaVerifyRequest;
import com.example.spring_rest.dto.RegisterRequest;
import com.example.spring_rest.dto.SignInRequest;
import com.example.spring_rest.dto.SignInResponse;
import com.example.spring_rest.dto.UserResponse;
import com.example.spring_rest.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<SignInResponse> login(@Valid @RequestBody SignInRequest request) {
        SignInResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse createdUser = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/verify-mfa")
    public ResponseEntity<SignInResponse> verifyMfa(@Valid @RequestBody MfaVerifyRequest request) {
        return ResponseEntity.ok(authService.verifyMfa(request));
    }
}
