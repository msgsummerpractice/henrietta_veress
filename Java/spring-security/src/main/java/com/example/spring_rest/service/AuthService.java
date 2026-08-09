package com.example.spring_rest.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.example.spring_rest.security.MfaService;
import com.example.spring_rest.config.SecurityConfig;
import com.example.spring_rest.dto.MfaVerifyRequest;
import com.example.spring_rest.dto.RegisterRequest;
import com.example.spring_rest.dto.SignInRequest;
import com.example.spring_rest.dto.SignInResponse;
import com.example.spring_rest.dto.UserResponse;
import com.example.spring_rest.mapper.UserMapper;
import com.example.spring_rest.model.Role;
import com.example.spring_rest.model.User;
import com.example.spring_rest.model.enums.RoleName;
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
    private final SecurityConfig securityConfig;
    private final MfaService mfaService;
    private final UserMapper userMapper;   
    
    @Value("${jwt.secret}")
    private String jwtSecret;

    public AuthService(AuthenticationManager authenticationManager, IUserRepository userRepository, 
                        IRoleRepository roleRepository, SecurityConfig securityConfig,
                        MfaService mfaService, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.securityConfig = securityConfig;
        this.mfaService = mfaService;
        this.userMapper = userMapper;
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public SignInResponse login(SignInRequest request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<RoleName> roleNames = user.getRoles().stream()
            .map(Role::getName)
            .collect(Collectors.toList());

        String token = Jwts.builder()
            .subject(user.getEmail())
            .claim("roles", roleNames)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(getKey())
            .compact();

        return new SignInResponse(token, user.getRoles());
    }

    @Override
    public UserResponse register(RegisterRequest request) {
        
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username is taken");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is taken");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(securityConfig.passwordEncoder().encode(request.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.USER)
            .orElseThrow(() -> new RuntimeException("No USER role in the database"));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        User saved = userRepository.save(user);

        return userMapper.toResponse(saved);
    }

    @Override
    public SignInResponse verifyMfa(MfaVerifyRequest request) {
        boolean valid = mfaService.verifyCode(request.getEmail(), request.getCode());
        if (!valid) {
            throw new RuntimeException("Ervenytelen vagy lejart MFA kod");
        }

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<RoleName> roleNames = user.getRoles().stream()
            .map(role -> role.getName())
            .collect(Collectors.toList());

        String token = Jwts.builder()
            .subject(user.getEmail())
            .claim("roles", roleNames)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(getKey())
            .compact();

        return new SignInResponse(token, user.getRoles());
    }
}
