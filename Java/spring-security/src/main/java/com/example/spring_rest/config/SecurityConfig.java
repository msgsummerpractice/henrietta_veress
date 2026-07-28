package com.example.spring_rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(HttpMethod.POST, "/api/users")
                .permitAll()

                .requestMatchers(HttpMethod.DELETE, "/api/users/**")
                .hasRole("ADMIN")

                .requestMatchers("/api/users/**")
                .hasAnyRole("ADMIN", "USER")

                .anyRequest()
                .authenticated()
            )

            // .formLogin(form -> form
            //     .loginPage("/login.html")
            //     .defaultSuccessUrl("/api/users", true)
            //     .permitAll()
            // )

            .httpBasic(httpBasic -> {});

        return http.build();
    }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//     http
//         .csrf(csrf -> csrf.disable())
//         .authorizeHttpRequests(auth -> auth
//             .anyRequest().permitAll()
//         );

//     return http.build();
// }

    // @Bean
    // public UserDetailsService userDetailsService() {

    //     UserDetails admin = User.builder()
    //             .username("admin")
    //             .password(passwordEncoder().encode("admin123"))
    //             .roles("ADMIN")
    //             .build();


    //     UserDetails user = User.builder()
    //             .username("user")
    //             .password(passwordEncoder().encode("user123"))
    //             .roles("USER")
    //             .build();


    //     return new InMemoryUserDetailsManager(admin, user);
    // }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
