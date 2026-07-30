package com.example.spring_rest.dto;

import java.util.Set;

import com.example.spring_rest.model.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {

    private String token;
    private Set<Role> roles;
}