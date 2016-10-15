package ru.urfu.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.urfu.user.model.Role;
import ru.urfu.user.model.User;
import ru.urfu.user.service.UserService;
import ru.urfu.user.validation.DuplicateUserException;
import ru.urfu.user.validation.UserValidator;

import javax.inject.Inject;

@Controller
public class UserController {


    @Inject
    UserService userService;
    @Inject
    UserValidator userValidator;

    @GetMapping(value = "/login")
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("logout", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;
    }

    @GetMapping(path = "/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping(path = "/register")
    public String postRegister(@ModelAttribute("user") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        User user = new User((String) userForm.getUsername(), (String) userForm.getPassword(), userForm.getRole());

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.save(user);
        } catch (DuplicateUserException e) {
            bindingResult.addError(new FieldError("user","username","Duplicate.userForm.username"));
            return "register";
        }
        return "redirect:/login";
    }
}
