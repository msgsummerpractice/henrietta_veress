package com.example.spring_rest.repository;

import com.example.spring_rest.model.OneTimeToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OneTimeTokenRepository extends JpaRepository<OneTimeToken, Long> {
    
    Optional<OneTimeToken> findByEmailAndCodeAndUsedFalse(String email, String code);
}