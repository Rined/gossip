package com.rined.justtalk.controllers;

import com.rined.justtalk.model.Role;
import com.rined.justtalk.model.User;
import com.rined.justtalk.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEdit(@PathVariable(name = "user") User user,
                           Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUser(@RequestParam Map<String, String> form, //Переменное число полей, получаем всё из формы. Чекбоксы передаются только если выбраны
                             @RequestParam("username") String username,
                             @RequestParam("id") User user) {
        userService.updateUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(@AuthenticationPrincipal User user,
                             Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam("password") String password,
                                @RequestParam("email") String email) {
        userService.updateProfile(user, password, email);
        return "redirect:/user/profile";
    }

    @GetMapping("subscribe/{user}")
    public String subscribe(@AuthenticationPrincipal User currentUser,
                            @PathVariable("user") User user) {
        userService.subscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }


    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(@AuthenticationPrincipal User currentUser,
                              @PathVariable("user") User user) {
        userService.unsubscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(@PathVariable("user") User user,
                           @PathVariable("type") String type,
                           Model model) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);
        model.addAttribute("users",
                "subscriptions".equals(type) ? user.getSubscriptions() : user.getSubscribers()
        );
        return "subscriptions";
    }

}
