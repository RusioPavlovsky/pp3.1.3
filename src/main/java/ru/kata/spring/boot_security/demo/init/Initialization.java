package ru.kata.spring.boot_security.demo.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;


@Component
public class Initialization implements ApplicationListener<ContextRefreshedEvent> {

    private RoleService roleService;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public Initialization(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
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
            admin.setRoles(Arrays.asList(adminRole));
            userService.save(admin);
        }
    }
}
