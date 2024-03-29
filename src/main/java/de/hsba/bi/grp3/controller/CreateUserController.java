package de.hsba.bi.grp3.controller;

import de.hsba.bi.grp3.form.*;
import de.hsba.bi.grp3.service.UserService;
import de.hsba.bi.grp3.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/create-user")
@RequiredArgsConstructor
public class CreateUserController {

    private final UserService userService;

    private final UserValidationFormConverter userValidationFormConverter;

    @ModelAttribute
    public void isUserAuthenticated (Model model){
        model.addAttribute("isUserAuthenticated", userService.isUserAuthenticated());
    }

    @GetMapping
    public String showCreateUser(Model model) {
        if (userService.isUserAuthenticated())return "redirect:/";
        model.addAttribute("userValidationForm", new UserValidationForm());

        return "user/createUser";
    }

    @PostMapping
    public String createUser(@Valid UserValidationForm userValidationForm, BindingResult userBinding) {
        if (userBinding.hasErrors()){
            return "redirect:/create-user?error";
        }
        if (userValidationForm.getPasswordFirst().equals(userValidationForm.getPasswordSecond()) && userService.isUsernameUnique(userValidationForm.getName())) {
            User newUser = userValidationFormConverter.userValidationFormToUser(userValidationForm);
            userService.saveUser(newUser);
            return "redirect:/login";
        }
        return "redirect:/create-user?error";
    }

    @PostMapping( params="action=error")
    public String falseRegister(){
        System.out.println("False");
        return null;
    }
}
