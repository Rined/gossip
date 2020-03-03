package com.rined.justtalk.controllers;

import com.rined.justtalk.model.Role;
import com.rined.justtalk.model.User;
import com.rined.justtalk.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserRepository repository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", repository.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEdit(@PathVariable(name = "user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    public String updateUser(@RequestParam Map<String, String> form, //Переменное число полей, получаем всё из формы. Чекбоксы передаются только если выбраны
                             @RequestParam("username") String username,
                             @RequestParam("id") User user) {

        return "redirect:/user";
    }
}
