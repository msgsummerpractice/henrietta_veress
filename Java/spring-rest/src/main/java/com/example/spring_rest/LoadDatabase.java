package com.example.spring_rest;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.spring_rest.model.User;
import com.example.spring_rest.repository.IUserRepository;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    CommandLineRunner initDatabase(IUserRepository repository) {

        return args -> {
            log.info("PreLoading " + repository.save(new User("bilbo", "bilbob@pelda.com", "alma123")));
            log.info("PreLoading " + repository.save(new User("frodo", "frodo@pelda.com", "korte123")));

        };
    }
}
