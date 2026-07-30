package com.example.spring_rest.service;

import com.example.spring_rest.dto.SignInRequest;
import com.example.spring_rest.dto.SignInResponse;

public interface IAuthService {

    SignInResponse login(SignInRequest request);
    SignInResponse register(SignInRequest request);
}
