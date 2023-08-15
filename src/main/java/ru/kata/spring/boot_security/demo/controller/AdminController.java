package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collection;
import java.util.List;

@Controller
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @GetMapping("/admin")
    public String getAdminPage (Model model) {
        model.addAttribute("userList", userService.getAll());
        return "admin";
    }

    @GetMapping("/admin/add-user")
    public String showCreateUserForm(ModelMap model) {
        User user = new User();
        Collection<Role> roles = roleService.getRoleNames();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "add-user";
    }

    @PostMapping("/admin/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/";
    }

    @PostMapping("/admin/delete-user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        roleService.deleteById(id);
        userService.deleteById(id);
        return "redirect:/admin";
    }

}
