package com.rined.gossip.controllers;

import com.rined.gossip.model.Message;
import com.rined.gossip.model.User;
import com.rined.gossip.model.dto.MessageDto;
import com.rined.gossip.services.MessageService;
import com.rined.gossip.utils.Utils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user,
                       @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        Page<MessageDto> messagesByFilter = messageService.getMessages(filter, user, pageable);
        model.addAttribute("page", messagesByFilter);
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
            Map<String, String> errorMap = Utils.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("message", message);
        }
        messageService.saveMessage(message, user, file);
        return "redirect:/main";
    }

    @GetMapping("/user-messages/{user}")
    public String userMessages(@AuthenticationPrincipal User currentUser,
                               @PathVariable("user") User messageAuthor,
                               @RequestParam(value = "message", required = false) Message message,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                               Model model) {
        Page<MessageDto> page = messageService.userMessageList(pageable, messageAuthor, currentUser);
        model.addAttribute("userChannel", messageAuthor);
        model.addAttribute("subscriptionsCount", messageAuthor.getSubscriptions().size());
        model.addAttribute("subscribersCount", messageAuthor.getSubscribers().size());
        model.addAttribute("page", page);
        model.addAttribute("message", message);
        model.addAttribute("isSubscriber", messageAuthor.getSubscribers().contains(currentUser));
        model.addAttribute("isCurrentUser", currentUser.equals(messageAuthor));
        model.addAttribute("url", "/user-messages/" + messageAuthor.getId());
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

    @GetMapping("/messages/{message}/like")
    public String like(@AuthenticationPrincipal User currentUser,
                       @PathVariable("message") Message message,
                       RedirectAttributes redirectAttributes,               // для передачи параметров с одной страницы на другую
                       @RequestHeader(required = false, name = "referer") String referer) {  // откуда пришли
        messageService.like(message, currentUser);
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(referer).build();
        redirectAttributes.addAllAttributes(uriComponents.getQueryParams());
        return "redirect:" + uriComponents.getPath();
    }
}
