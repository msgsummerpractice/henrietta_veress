package com.example.spring_rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.spring_rest.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);  // -> driver query metodusok
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u ORDER BY LOWER(u.username) ASC")
    List<User> findTop10UsersOrderedByUsername(Pageable pageable);

    @Query("SELECT u FROM User u ORDER BY LOWER(u.email) ASC")
    List<User> findTop10UsersOrderedByEmail(Pageable pageable);

    @Query("SELECT COUNT(u) FROM User u")
    long countUsers();

}
