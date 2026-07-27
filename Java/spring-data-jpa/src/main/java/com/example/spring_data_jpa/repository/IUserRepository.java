package com.example.spring_data_jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.spring_data_jpa.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);  // -> driver query metodusok
    Optional<User> findbyEmail(String email);
}
