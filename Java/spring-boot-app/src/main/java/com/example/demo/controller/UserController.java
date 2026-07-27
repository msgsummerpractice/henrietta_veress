package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.config.AppSettings;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.validation.constraints.Min;

@RestController
@Validated
public class UserController {

    private final UserService userService;
    private final AppSettings appSettings;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${app.environment.name}")
    private String environmentName;


    public UserController(UserService userService, AppSettings appSettings) {
        this.userService = userService;
        this.appSettings = appSettings;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(required = false) @Min(0) Integer minId) {
        logger.info("Request received: GET /users called, minId={}", minId);
        logger.info("Request received: GET /users called, Enviroment: {}", environmentName);
        logger.info("Request received: GET /users called, Enviroment: {}", appSettings.getName());

        if (minId != null && minId < 0) {
            return ResponseEntity.badRequest().body("Cannot be negative number");
        }

        List<User> users = userService.getAllUsers();

        if (minId != null) {
            users = users.stream()
                .filter(u -> u.getId() >= minId)
                .toList();
        } 
        return ResponseEntity.ok(users);
    }
}
