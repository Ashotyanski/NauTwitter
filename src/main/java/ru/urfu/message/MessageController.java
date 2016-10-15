package ru.urfu.message;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.urfu.message.Message;
import ru.urfu.message.service.MessageService;
import ru.urfu.message.validator.MessageValidator;
import ru.urfu.user.model.Role;

import javax.inject.Inject;
import javax.inject.Named;

@Controller
public class MessageController {

    @Inject
    private MessageValidator messageValidator;

    @Inject
    @Named("crud")
    private MessageService messageService;


    @RequestMapping("/")
    String index(Map<String, Object> model) {
        return "redirect:/messages";
    }

    @RequestMapping("/messages")
    String renderAllMessages(Model model) {
        model.addAttribute("messages", messageService.getAllMessages());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (hasAdmin(authentication.getAuthorities())) {
            model.addAttribute("isAdmin", Boolean.TRUE);
        }
        return "messages";
    }

    @RequestMapping("/message/{id}")
    String showMessage(@PathVariable("id") Long id, Model model) {
        Message message = messageService.getMessageById(id);
        if (message != null) {
            model.addAttribute("message", message);
            return "message";
        }
        return "redirect:/error";
    }

    @GetMapping(path = "/message/create")
    String getMessageForm(Model model) {
        model.addAttribute("message", new Message());
        return "create";
    }

    @PostMapping(path = "/message/create", params = "body")
    String createMessage(@ModelAttribute("message") Message msg, BindingResult bindingResult, Model model, Authentication authentication) {
        messageValidator.validate(msg, bindingResult);

        if (bindingResult.hasErrors()) {
            return "create";
        }

        Message message = new Message(msg.getBody(), ((User) authentication.getPrincipal()).getUsername());

        messageService.addMessage(message);

        return "redirect:/messages";
    }

    @RequestMapping(path = "/message/delete", params = "id")
    String deleteMessage(Long id) {
        Message message = messageService.getMessageById(id);
        if (message == null)
            return "redirect:/error";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.equals(message.getAuthor(), ((User) authentication.getPrincipal()).getUsername()) ||
                hasAdmin(authentication.getAuthorities())) {
            messageService.deleteMessage(id);
            return "redirect:/messages";
        }
        return "redirect:/error";
    }

    private boolean hasAdmin(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority :
                authorities) {
            if (authority.getAuthority().equals("ADMIN")) {
                return true;
            }
        }
        return false;
    }
}