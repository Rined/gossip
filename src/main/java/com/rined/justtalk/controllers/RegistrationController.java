package com.rined.justtalk.controllers;

import com.rined.justtalk.model.User;
import com.rined.justtalk.services.UserService;
import com.rined.justtalk.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService service;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("passwordConfirmation") String passwordConfirmation,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        boolean isConfirmationEmpty = StringUtils.isEmpty(passwordConfirmation);

        if(isConfirmationEmpty){
            model.addAttribute("passwordConfirmationError", "Password confirmation cannot be empty");
        }

        if (Objects.nonNull(user.getPassword()) && !user.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Passwords are not equals!");
        }

        if (isConfirmationEmpty || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }

        if (service.createUser(user)) {
            model.addAttribute("usernameError", "User already exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable("code") String code) {
        boolean isActivated = service.activateUser(code);
        model.addAttribute("message",
                isActivated ? "User successfully activated" : "Activation code is not found!");
        model.addAttribute("messageType", isActivated ? "success" : "danger");
        return "login";
    }

}
