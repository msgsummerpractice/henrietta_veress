package com.example.spring_rest.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="username", length = 255, nullable = false, unique = false)
    private String username;

    @Column(name="email", length = 255, nullable = false, unique = false)
    private String email;

    @Column(name="password", length = 255, nullable = false, unique = false)
    private String password;

    @Column(name="firstname", length = 255, nullable = true, unique = false)
    private String firstname;

    @Column(name="lastname", length = 255, nullable = true, unique = false)
    private String lastname;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
