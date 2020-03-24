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
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/user-messages/{user}")
    public String userMessages(@AuthenticationPrincipal User currentUser,
                               @PathVariable("user") User user,
                               @RequestParam(value = "message", required = false) Message message,
                               Model model) {
        model.addAttribute("messages", user.getMessages());
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(@AuthenticationPrincipal User currentUser,
                                @PathVariable("user") Long id,
                                @RequestParam("id") Message message,
                                @RequestParam("text") String text,
                                @RequestParam("tag") String tag,
                                @RequestParam("file") MultipartFile file) {
        if (message.getAuthor().equals(currentUser)) {
            messageService.saveMessage(message, text, tag, file);
        }
        return String.format("redirect:/user-messages/%s", id);
    }
}
