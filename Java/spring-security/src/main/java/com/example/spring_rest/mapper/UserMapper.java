package com.example.spring_rest.mapper;

import org.springframework.stereotype.Component;

import com.example.spring_rest.dto.PatchUserRequest;
import com.example.spring_rest.dto.RegisterRequest;
import com.example.spring_rest.dto.UpdateUserRequest;
import com.example.spring_rest.dto.UserRequest;
import com.example.spring_rest.dto.UserResponse;
import com.example.spring_rest.model.User;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        if (user == null) return null;

        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getCreatedAt()
        );
    }

    public User toEntity(UserRequest request) {
        if (request == null) return null;

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    public User toEntity(RegisterRequest request) {
        if (request == null) return null;

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    public void updateFromUpdateRequest(User user, UpdateUserRequest req) {
        if (user == null || req == null) return;
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
    }

    public void patchFromPatchUserRequest(User user, PatchUserRequest req) {
        if (user == null || req == null) return;
        if (req.getUsername() != null) user.setUsername(req.getUsername());
        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getFirstName() != null) user.setFirstName(req.getFirstName());
        if (req.getLastName() != null) user.setLastName(req.getLastName());
    }
}
