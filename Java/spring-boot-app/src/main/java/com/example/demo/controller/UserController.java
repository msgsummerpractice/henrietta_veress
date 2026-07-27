package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Min;

@RestController
@Validated
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(required = false) @Min(0) Integer minId) {

        try {
            logger.info("Request received: GET /users called, minId={}", minId);

            List<User> users = userService.getAllUsers();
            if (minId != null) {
                users = users.stream()
                    .filter(u -> u.getId() >= minId)
                    .toList();
            } 
            return ResponseEntity.ok(users);
        } catch (ConstraintViolationException ex) {
                return ResponseEntity.badRequest().body("Wrong minId, it cannot be negative");
        }
        
    }
}
