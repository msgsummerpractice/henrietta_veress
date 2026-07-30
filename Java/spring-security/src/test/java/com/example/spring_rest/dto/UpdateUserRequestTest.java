package com.example.spring_rest.dto;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@ExtendWith(MockitoExtension.class)
public class UpdateUserRequestTest {
    
    private Validator validator;
    
    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidUpdateUserRequest() {
        UpdateUserRequest request = new UpdateUserRequest(
                "user1", "johndoe@example.com", "John", "Doe");
        Set<ConstraintViolation<UpdateUserRequest>> violations = validator.validate(request);
        
        assertThat(violations).isEmpty();
    }

    @Test
    void testInvalidEmailThrowsValidationError() {
        UpdateUserRequest request = new UpdateUserRequest(
                "user1", "invalid email", "John", "Doe"
        );
        Set<ConstraintViolation<UpdateUserRequest>> violations = validator.validate(request);

        assertThat(violations)
            .hasSize(1)
            .first()
            .extracting(v -> v.getPropertyPath().toString())
            .isEqualTo("email");
    }
}