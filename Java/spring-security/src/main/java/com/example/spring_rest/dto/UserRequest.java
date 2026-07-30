package com.example.spring_rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    private String userName;
    
    @Email(message = "Email must be valid")
    @Size(min = 1, max = 50)
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be at least 8 characters")
    private String password;

    @Size(min = 2, max = 100, message = "FirstName can be maximum 255 characters")
    private String firstName;

    @Size(min = 2, max = 100, message = "LastName can be maximum 255 characters")
    private String lastName;
}
