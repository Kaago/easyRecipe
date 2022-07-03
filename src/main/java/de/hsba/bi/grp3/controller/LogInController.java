package de.hsba.bi.grp3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/LogIn")
public class LogInController {
    @GetMapping
    public String login() {
        //Todo check if already logged in
        return "/LogIn";
    }

}
