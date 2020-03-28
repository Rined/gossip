package com.rined.justtalk.controllers;

import com.rined.justtalk.model.Message;
import com.rined.justtalk.model.User;
import com.rined.justtalk.services.MessageService;
import com.rined.justtalk.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class MainController {
    private final MessageService messageService;

    @GetMapping("/main")
    public String main(Model model,
                       @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Message> messagesByText = messageService.getMessagesByTag(filter, pageable);
        model.addAttribute("page", messagesByText);
        model.addAttribute("filter", filter);
        model.addAttribute("url", "/main");
        return "main";
    }

    @PostMapping("/main")
    public String addMessage(Model model,
                             @Valid Message message,
                             BindingResult bindingResult,   // после бина!
                             @AuthenticationPrincipal User user,
                             @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("message", message);
        }
        messageService.saveMessage(message, user, file);
        return "redirect:/main";
    }

    @GetMapping("/user-messages/{user}")
    public String userMessages(@AuthenticationPrincipal User currentUser,
                               @PathVariable("user") User user,
                               @RequestParam(value = "message", required = false) Message message,
                               Model model) {
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("messages", user.getMessages());
        model.addAttribute("message", message);
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
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
