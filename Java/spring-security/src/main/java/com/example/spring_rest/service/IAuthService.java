package com.example.spring_rest.service;

import com.example.spring_rest.dto.MfaVerifyRequest;
import com.example.spring_rest.dto.RegisterRequest;
import com.example.spring_rest.dto.SignInRequest;
import com.example.spring_rest.dto.SignInResponse;
import com.example.spring_rest.dto.UserResponse;

public interface IAuthService {

    SignInResponse login(SignInRequest request);
    UserResponse register(RegisterRequest request);
    SignInResponse verifyMfa(MfaVerifyRequest request);

}
