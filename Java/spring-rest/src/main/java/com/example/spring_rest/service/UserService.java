package com.example.spring_rest.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_rest.dto.UpdateUserRequest;
import com.example.spring_rest.dto.UserRequest;
import com.example.spring_rest.dto.UserResponse;
import com.example.spring_rest.exception.ResourceNotFoundException;
import com.example.spring_rest.model.User;
import com.example.spring_rest.repository.IUserRepository;

@Service  // -> handles conversion btween dtos and entities
public class UserService {

    private final IUserRepository userRepository;

    @Autowired   // -> konstruktor injection
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest request) {
        // Convert DTO to Entity
        User user = new User(); 
        user.setUsername(request.getUsername()); 
        user.setEmail(request.getEmail());  
        user.setPassword(encryptPassword(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        // Save entity to database
        User savedUser = userRepository.save(user);

        // Convert Entity to Response DTO
        return convertToResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + id));

        // Convert Entity to Response DTO (password never exposed!)
        return convertToResponse(user);
    }

    public Optional<UserResponse> getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(this::convertToResponse);
    }

    public Optional<UserResponse> getUserByEmail(String email) {
        return userRepository.findbyEmail(email).map(this::convertToResponse);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest updateRequest) {
        User currentUser = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + id));

        currentUser.setUsername(updateRequest.getUsername());
        currentUser.setEmail(updateRequest.getEmail());
        currentUser.setFirstname(updateRequest.getFirstname());
        currentUser.setLastname(updateRequest.getLastname());

        User updatedUser = userRepository.save(currentUser);
        return convertToResponse(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse partialUpdateUser(Long id, UpdateUserRequest updateRequest) {
        User currentUser = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + id));
        
        if (updateRequest.getUsername() != null) {
            currentUser.setUsername(updateRequest.getUsername());
        }
        if (updateRequest.getEmail() != null) {
            currentUser.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getFirstname() != null) {
            currentUser.setFirstname(updateRequest.getFirstname());
        }
        if (updateRequest.getLastname() != null) {
            currentUser.setLastname(updateRequest.getLastname());
        }

        User updatedUser = userRepository.save(currentUser);

        return convertToResponse(updatedUser);
    }

    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setCreatedAt(user.getCreatedAt());
        // Notice: password and internalNotes are NOT included
        return response;
    }

    private String encryptPassword(String rawPassword) {
        return rawPassword;
    }
}
