package com.example.demo.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.example.demo.model.User;

@Repository
public class UserRepository implements IUserRepository {

    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Override
    public List<User> findAll() {

        logger.info("Fetching data");

        return List.of(
            new User("John", "Doe"),
            new User("Jane", "Smith"),
            new User("Alice", "Johnson")
        );
    }
}
