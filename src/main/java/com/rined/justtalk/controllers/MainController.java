package com.rined.justtalk.controllers;

import com.rined.justtalk.model.Message;
import com.rined.justtalk.model.User;
import com.rined.justtalk.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String addMessage(@AuthenticationPrincipal User user,
                             @RequestParam(name = "text") String text,
                             @RequestParam(name = "tag", required = false) String tag,
                             @RequestParam("file") MultipartFile file) {
        messageService.saveMessage(text, tag, user, file);
        return "redirect:/main";
    }

}
