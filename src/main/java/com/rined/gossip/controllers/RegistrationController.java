package com.rined.gossip.controllers;

import com.rined.gossip.model.User;
import com.rined.gossip.model.dto.CaptchaResponseDto;
import com.rined.gossip.properties.RecaptchaProperties;
import com.rined.gossip.services.UserService;
import com.rined.gossip.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService service;
    private final RecaptchaProperties recaptchaProperties;
    private final RestTemplate restTemplate;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("htmlSecret", recaptchaProperties.getHtmlSecret());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("passwordConfirmation") String passwordConfirmation,
                          @RequestParam("g-recaptcha-response") String captchaResponse,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        String url = String.format(recaptchaProperties.getUrlTemplate(), recaptchaProperties.getApiSecret(), captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, null, CaptchaResponseDto.class);

        if (Objects.nonNull(response) && !response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha!");
        }

        boolean isConfirmationEmpty = StringUtils.isEmpty(passwordConfirmation);

        if (isConfirmationEmpty) {
            model.addAttribute("passwordConfirmationError", "Password confirmation cannot be empty");
        }

        if (Objects.nonNull(user.getPassword()) && !user.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Passwords are not equals!");
        }

        if (isConfirmationEmpty || bindingResult.hasErrors()
                || Objects.nonNull(response) && !response.isSuccess()) {
            Map<String, String> errors = Utils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("htmlSecret", recaptchaProperties.getHtmlSecret());
            return "registration";
        }

        if (service.createUser(user)) {
            model.addAttribute("usernameError", "User already exists!");
            model.addAttribute("htmlSecret", recaptchaProperties.getHtmlSecret());
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable("code") String code) {
        boolean isActivated = service.activateUser(code);
        model.addAttribute("message",
                isActivated ? "User successfully activated" : "Activation code is not found!");
        model.addAttribute("messageType",
                isActivated ? "success" : "danger");
        return "login";
    }
}
