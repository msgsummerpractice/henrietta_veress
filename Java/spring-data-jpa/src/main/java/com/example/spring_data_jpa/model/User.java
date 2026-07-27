package com.example.spring_data_jpa.model;

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

@Entity(name="user")
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
    @Column
    private Long id;

    @Column(name="USERNAME", length = 255, nullable = false, unique = false)
    private String username;

    @Column(name="USERNAME", length = 255, nullable = false, unique = false)
    private String email;

    @Column(name="PASSWORD", length = 255, nullable = false, unique = false)
    private String password;

    @Column(name="FIRSTNAME", length = 255, nullable = true, unique = false)
    private String firstname;

    @Column(name="LASTNAME", length = 255, nullable = true, unique = false)
    private String lastname;
}
