package com.rined.justtalk.controllers;

import com.rined.justtalk.model.Message;
import com.rined.justtalk.model.User;
import com.rined.justtalk.services.MessageService;
import com.rined.justtalk.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class MainController {
    private final MessageService messageService;

    @GetMapping("/main")
    public String main(@RequestParam(name = "filter", required = false, defaultValue = "") String filter,
                       Model model) {
        List<Message> messagesByText = messageService.getMessagesByTag(filter);
        model.addAttribute("messages", messagesByText);
        if (Objects.nonNull(filter))
            model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String addMessage(@Valid Message message,
                             BindingResult bindingResult,   // после бина!
                             @AuthenticationPrincipal User user,
                             Model model,
                             @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
                             @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", null);
            messageService.saveMessage(message, user, file);
        }

        if (Objects.nonNull(filter))
            model.addAttribute("filter", filter);

        model.addAttribute("messages", messageService.getMessagesByTag(filter));
        return "main";
    }

}
