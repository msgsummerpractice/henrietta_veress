package com.example.demo.controller;

import static org.mockito.Mockito.when;
import java.util.List;

import com.example.demo.config.AppSettings;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AppSettings appSettings;

    @Test
    void shouldReturnUsers() throws Exception {
        
        List<User> users = List.of(
            new User("Bilbo", "Baggins"),
            new User("Tom", "Bombadil")
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/json"))
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].firstName").value("Bilbo"))
               .andExpect(jsonPath("$[1].lastName").value("Bombadil"));

    }

    // ! Nem ertem miert nem megy, vissza kene terjen badrequest-el
    @Test  
    void getUsers_withNegativeMinId_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/users?minId=-1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getUsers_withValidMinId_filtersResults() throws Exception {
        List<User> fakeUsers = List.of(
            new User("Janos", "Nagy"),
            new User("Margit", "Kovacs")
        );
        when(userService.getAllUsers()).thenReturn(fakeUsers);

        mockMvc.perform(get("/users?minId=1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].firstName").value("Margit"));
    }

    
    @Test
    void getUsers_withoutMinId_returnAllUsers() throws Exception {
        List<User> fakeUsers = List.of(
            new User("Cecilia", "Szabo"),
            new User("Lilla", "Molnar")
        );
        when(userService.getAllUsers()).thenReturn(fakeUsers);

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }
}
