package com.example.spring_rest.config;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.spring_rest.model.Role;
import com.example.spring_rest.model.User;
import com.example.spring_rest.model.enums.RoleName;
import com.example.spring_rest.repository.IRoleRepository;
import com.example.spring_rest.repository.IUserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	private final IRoleRepository roleRepository;
	private final IUserRepository userRepository;
	private final SecurityConfig securityConfig;

	public DataInitializer(IRoleRepository roleRepository, IUserRepository userRepository,
						SecurityConfig securityConfig) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.securityConfig = securityConfig;
    }

    @Override
	public void run(String... args) throws Exception {
		
		for (RoleName roleName: RoleName.values()) {
			if (roleRepository.findByName(roleName).isEmpty()) {
				Role role = new Role();
				role.setName(roleName);
				roleRepository.save(role);
			}
		}

		if (userRepository.count() == 0) {
			Role adminRole = roleRepository.findByName(RoleName.ADMIN)
				.orElseThrow(() -> new RuntimeException("ADMIN role missing"));

			Role userRole = roleRepository.findByName(RoleName.USER)
				.orElseThrow(() -> new RuntimeException("USER role missing"));

			User admin = new User();
			admin.setUsername("admin");
			admin.setEmail("admin@mail.com");
			admin.setFirstName("Admin");
			admin.setLastName("Role");
			admin.setPassword(securityConfig.passwordEncoder().encode("admin123!"));
			admin.setCreatedAt(LocalDateTime.now());

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);
			roles.add(userRole);
			admin.setRoles(roles);

			userRepository.save(admin);
		}
	}

}
