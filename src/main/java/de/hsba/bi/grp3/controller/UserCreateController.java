package de.hsba.bi.grp3.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/CreateUser")
public class UserCreateController {
    @GetMapping
    public String createUser() {
        //Todo check if already logged in
        return "user/createUser";
    }
}

