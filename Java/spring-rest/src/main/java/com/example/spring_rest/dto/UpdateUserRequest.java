package com.example.spring_rest.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest {
    private String username;

    @Email(message = "Email not valid")
    private String email;

    private String firstname;
    private String lastname;
}
