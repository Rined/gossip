package com.rined.justtalk.controllers;

import com.rined.justtalk.model.Role;
import com.rined.justtalk.model.User;
import com.rined.justtalk.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserService service;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", service.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEdit(@PathVariable(name = "user") User user,
                           Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    public String updateUser(@RequestParam Map<String, String> form, //Переменное число полей, получаем всё из формы. Чекбоксы передаются только если выбраны
                             @RequestParam("username") String username,
                             @RequestParam("id") User user) {
        service.updateUser(user, username, form);
        return "redirect:/user";
    }
}
