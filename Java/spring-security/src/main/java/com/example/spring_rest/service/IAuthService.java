package com.example.spring_rest.service;

import com.example.spring_rest.dto.RegisterRequest;
import com.example.spring_rest.dto.SignInRequest;
import com.example.spring_rest.dto.SignInResponse;
import com.example.spring_rest.model.User;

public interface IAuthService {

    SignInResponse login(SignInRequest request);
    User register(RegisterRequest request);


}
