package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.Collection;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @GetMapping()
    public String getAdminPage (Model model) {
        model.addAttribute("userList", userService.getAll());
        return "admin";
    }

    @GetMapping("/add-user")
    public String showCreateUserForm(ModelMap model) {
        User user = new User();
        Collection<Role> roles = roleService.getAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "add-user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {
        if(userService.getByUsername(user.getUsername()) == null) {
            userService.save(user);
        } else {
            return "redirect:/error";
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/update-user/{id}")
    public String updateUserPage (Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.getById(id));
        return "update-user";
    }

    @PatchMapping("/update")
    public String updateUser (@ModelAttribute User user) {
        userService.update(user);
        return "redirect:/admin";
    }

}
