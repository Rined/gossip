package com.rined.justtalk.controllers;

import com.rined.justtalk.model.User;
import com.rined.justtalk.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

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
        return String.format("redirect:/user-messages/%d", user.getId());
    }


    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(@AuthenticationPrincipal User currentUser,
                              @PathVariable("user") User user) {
        userService.unsubscribe(currentUser, user);
        return String.format("redirect:/user-messages/%d", user.getId());
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
