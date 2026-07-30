package com.example.spring_rest.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.spring_rest.security.MfaService;

import com.example.spring_rest.dto.MfaVerifyRequest;
import com.example.spring_rest.dto.RegisterRequest;
import com.example.spring_rest.dto.SignInRequest;
import com.example.spring_rest.dto.SignInResponse;
import com.example.spring_rest.model.Role;
import com.example.spring_rest.model.User;
import com.example.spring_rest.repository.IRoleRepository;
import com.example.spring_rest.repository.IUserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MfaService mfaService;   
    
    @Value("${jwt.secret}")
    private String jwtSecret;

    public AuthService(AuthenticationManager authenticationManager, IUserRepository userRepository, 
                        IRoleRepository roleRepository, PasswordEncoder passwordEncoder,
                        MfaService mfaService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mfaService = mfaService;
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
    public User register(RegisterRequest request) {
        
        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new RuntimeException("Username is taken");
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCreatedAt(LocalDateTime.now());

        Role userRole = roleRepository.findByName("USER")
            .orElseThrow(() -> new RuntimeException("No USER role in the database"));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public SignInResponse verifyMfa(MfaVerifyRequest request) {
        boolean valid = mfaService.verifyCode(request.getUserName(), request.getCode());
        if (!valid) {
            throw new RuntimeException("Ervenytelen vagy lejart MFA kod");
        }

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
}
