package com.example.spring_rest.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.spring_rest.dto.SignInRequest;
import com.example.spring_rest.dto.SignInResponse;
import com.example.spring_rest.model.User;
import com.example.spring_rest.repository.IUserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;
    
    @Value("${jwt.secret}")
    private String jwtSecret;

    public AuthService(AuthenticationManager authenticationManager, IUserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Override
    public SignInResponse login(SignInRequest request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
        );

        User user = userRepository.findByUserName(request.getUserName())
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roleNames = user.getRoles().stream()
            .map(role -> role.getName())
            .collect(Collectors.toList());

        String token = Jwts.builder()
            .subject(user.getUserName())
            .claim("roles", roleNames)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(getKey())
            .compact();

        return new SignInResponse(token, user.getRoles());
    }

    @Override
    public SignInResponse register(SignInRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

}
