package ru.kata.spring.boot_security.demo.service;



import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    void save (User user);
    User getById (Long id);
    List<User> getAll ();
    void update (User user);
    void deleteById (Long id);
    User getByUsername(String username);

}
