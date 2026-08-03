package com.example.spring_rest.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.spring_rest.dto.PatchUserRequest;
import com.example.spring_rest.dto.UpdateUserRequest;
import com.example.spring_rest.dto.UserRequest;
import com.example.spring_rest.dto.UserResponse;
import com.example.spring_rest.exception.ResourceNotFoundException;
import com.example.spring_rest.model.Role;
import com.example.spring_rest.model.User;
import com.example.spring_rest.repository.IRoleRepository;
import com.example.spring_rest.repository.IUserRepository;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;


@Service  // -> handles conversion btween dtos and entities
public class UserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired   // -> konstruktor injection
    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username taken: " + request.getUsername());
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + request.getEmail());
        }

        // Convert DTO to Entity
        User user = new User(); 
        user.setUsername(request.getUsername()); 
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());  
        user.setPassword(encryptPassword(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        Role userRole = roleRepository.findByName("USER")
                                      .orElseThrow(() -> new IllegalStateException("USER role not found, check database"));

        user.setRoles(Set.of(userRole));

        // Save entity to database
        User savedUser = userRepository.save(user);

        // Convert Entity to Response DTO
        return convertToResponse(savedUser);
    }

    public List<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        return users.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + id));

        // Convert Entity to Response DTO (password never exposed!)
        return convertToResponse(user);
    }

    public Optional<UserResponse> getUserByUsername(String userName) {
        return userRepository.findByUsername(userName).map(this::convertToResponse);
    }

    public Optional<UserResponse> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::convertToResponse);
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest updateRequest) {
        User currentUser = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + id));

        currentUser.setUsername(updateRequest.getUsername());
        currentUser.setEmail(updateRequest.getEmail());
        currentUser.setFirstName(updateRequest.getFirstName());
        currentUser.setLastName(updateRequest.getLastName());

        User updatedUser = userRepository.save(currentUser);
        return convertToResponse(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with this id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserResponse partialUpdateUser(Long id, PatchUserRequest patchRequest) {
        User currentUser = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + id));
        
        if (patchRequest.getUsername() != null) {
            currentUser.setUsername(patchRequest.getUsername());
        }
        if (patchRequest.getEmail() != null) {
            currentUser.setEmail(patchRequest.getEmail());
        }
        if (patchRequest.getFirstName() != null) {
            currentUser.setFirstName(patchRequest.getFirstName());
        }
        if (patchRequest.getLastName() != null) {
            currentUser.setLastName(patchRequest.getLastName());
        }

        User updatedUser = userRepository.save(currentUser);

        return convertToResponse(updatedUser);
    }

    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setCreatedAt(user.getCreatedAt());
        // Notice: password and internalNotes are NOT included
        return response;
    }

    private String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
