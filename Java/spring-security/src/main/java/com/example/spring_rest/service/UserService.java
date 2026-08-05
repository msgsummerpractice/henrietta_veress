package com.example.spring_rest.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.spring_rest.config.SecurityConfig;
import com.example.spring_rest.dto.PatchUserRequest;
import com.example.spring_rest.dto.UpdateUserRequest;
import com.example.spring_rest.dto.UserRequest;
import com.example.spring_rest.dto.UserResponse;
import com.example.spring_rest.exception.ResourceNotFoundException;
import com.example.spring_rest.mapper.UserMapper;
import com.example.spring_rest.model.Role;
import com.example.spring_rest.model.User;
import com.example.spring_rest.repository.IRoleRepository;
import com.example.spring_rest.repository.IUserRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final SecurityConfig securityConfig;
    private final UserMapper userMapper;

    @Autowired   // -> konstruktor injection
    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, 
        SecurityConfig securityConfig, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.securityConfig = securityConfig;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username taken: " + request.getUsername());
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + request.getEmail());
        }

        User user = userMapper.toEntity(request);
        user.setPassword(securityConfig.passwordEncoder().encode(request.getPassword()));

        Role userRole = roleRepository.findByName("USER")
                                      .orElseThrow(() -> new IllegalStateException("USER role not found, check database"));

        user.setRoles(Set.of(userRole));

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    public List<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        return users.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }
    

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + id));

       return userMapper.toResponse(user);
    }

    public Optional<UserResponse> getUserByUsername(String userName) {
        return userRepository.findByUsername(userName).map(userMapper::toResponse);
    }

    public Optional<UserResponse> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toResponse);
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest updateRequest) {
        User currentUser = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + id));

        userMapper.updateFromUpdateRequest(currentUser, updateRequest);

        User updatedUser = userRepository.save(currentUser);
        return userMapper.toResponse(updatedUser);
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
        
        userMapper.patchFromPatchUserRequest(currentUser, patchRequest);

        User updatedUser = userRepository.save(currentUser);

        return userMapper.toResponse(updatedUser);
    }
}
