package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String home() {
        return "index";
    }

    @GetMapping("/users/register")
    public String showRegisterForm() {
        return "register";
    }
    
    @GetMapping("/users/login")
    public String showLoginForm() {
        return "login";
    }
}
