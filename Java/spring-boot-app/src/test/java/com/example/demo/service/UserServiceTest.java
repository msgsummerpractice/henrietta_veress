package com.example.demo.service;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.User;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_shouldReturnUsersFromRepository() {

        // Arrange eloszor
        List<User> fakeUsers = List.of(new User("Frodo", "Baggings"));
        when(userRepository.findAll()).thenReturn(fakeUsers);

        // majd Act
        List<User> result = userService.getAllUsers();

        //vegul Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("Frodo");
        verify(userRepository, times(1)).findAll(); // ellenorizzuk hogy tenyleg hivva lett
    }
}
