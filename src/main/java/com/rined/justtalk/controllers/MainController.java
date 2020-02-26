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

import java.util.List;

@Controller
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class MainController {
    private final MessageService messageService;

    @GetMapping("/main")
    public String main(Model model) {
        List<Message> allMessages = messageService.getAllMessages();
        model.addAttribute("messages", allMessages);
        return "main";
    }

    @PostMapping("/main")
    public String addMessage(@AuthenticationPrincipal User user,
                             @RequestParam(name = "text") String text,
                             @RequestParam(name = "tag", required = false) String tag) {
        messageService.saveMessage(text, tag, user);
        return "redirect:/main";
    }

    @PostMapping(path = "/filter")
    public String filter(@RequestParam(name = "filter") String filter,
                         Model model) {
        List<Message> messagesByText = messageService.getMessagesByTag(filter);
        model.addAttribute("messages", messagesByText);
        return "main";
    }

}
