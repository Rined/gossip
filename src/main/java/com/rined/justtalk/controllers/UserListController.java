package com.rined.justtalk.controllers;

import com.rined.justtalk.model.Role;
import com.rined.justtalk.model.User;
import com.rined.justtalk.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserListController {
    private final UserService userService;

    @GetMapping
    public String userList(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 25) Pageable pageable,
                           Model model) {
        model.addAttribute("page", userService.findAll(pageable));
        model.addAttribute("url", "/users");
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
        userService.updateUser(user, username, form);
        return "redirect:/users";
    }

    @PostMapping("{user}/kick")
    public String kickUser(@PathVariable(name = "user") User user) {
        userService.kickUser(user);
        return "redirect:/users";
    }
}
