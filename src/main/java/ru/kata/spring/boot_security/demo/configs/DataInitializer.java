package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    public DataInitializer(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Bean
    public CommandLineRunner initializeData () {
        return args -> {
            Role userRole = new Role("ROLE_USER");
            Role adminRole = new Role("ROLE_ADMIN");
            roleService.save(userRole);
            roleService.save(adminRole);
            if (userService.getByUsername("user") == null) {
                User user = new User("user", "test");
                user.setPassword(passwordEncoder.encode("user"));
                user.setRoles(Arrays.asList(userRole));
                userService.save(user);
            }
            if (userService.getByUsername("admin") == null) {
                User admin = new User("admin", "test");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRoles(Arrays.asList(userRole,adminRole));
                userService.save(admin);
            }
        };
    }

}
