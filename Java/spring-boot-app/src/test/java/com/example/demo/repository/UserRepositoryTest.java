package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import com.example.demo.model.User;

public class UserRepositoryTest {

    private final UserRepository userRepository = new UserRepository();

    @Test
    void findAll_shouldReturnThreeUsers() {
        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(3);
        assertThat(users).extracting(User::getFirstName).containsExactly("John", "Jane", "Alice");
    }

}
