package com.example.spring_rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.spring_rest.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUserName(String userName);  // -> driver query metodusok
    Optional<User> findByEmail(String email);
    boolean existsByUserName(String userName);

    @Query("SELECT u FROM User u ORDER BY LOWER(u.userName) ASC")
    List<User> findTop10UsersOrderedByUserName(Pageable pageable);

    @Query("SELECT COUNT(u) FROM User u")
    long countUsers();

}
