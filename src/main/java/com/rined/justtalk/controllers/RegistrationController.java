package com.rined.justtalk.controllers;

import com.rined.justtalk.model.User;
import com.rined.justtalk.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService service;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user,
                          Model model) {
        if (service.createUser(user)) {
            model.addAttribute("message", "User already exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable("code") String code) {
        boolean isActivated = service.activateUser(code);
        model.addAttribute("message",
                isActivated ? "User successfully activated" : "Activation code is not found!");
        return "login";
    }

}
