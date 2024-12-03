package com.nicelink.nicer.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/actions")
    public String actionsPage() {
        return "actionspage";
    }

    @GetMapping("/createlink")
    public String createLinkPage() {

        return "createlinkpage";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "loginpage";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "registerpage";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/notfound")
    public String notfoundPage() {
        return "notfound";
    }

    @GetMapping("/profile")
    public String profilePage() {
        return "profilepage";
    }
}