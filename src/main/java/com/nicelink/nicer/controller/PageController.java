package com.nicelink.nicer.controller;


import com.nicelink.nicer.config.security.AuthenticatedUserService;
import com.nicelink.nicer.exeptions.link.LinkNotFoundException;
import com.nicelink.nicer.exeptions.link.UserDoesNotOwnThisLinkException;
import com.nicelink.nicer.model.Action;
import com.nicelink.nicer.model.Link;
import com.nicelink.nicer.model.LinkResult;
import com.nicelink.nicer.model.User;
import com.nicelink.nicer.service.LinkService;
import com.nicelink.nicer.service.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class PageController {

    @Autowired
    private AuthenticatedUserService authService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private ActionService actionService;

    @GetMapping("/")
    public String tohome() {
        return "redirect:/home";
    }

    @GetMapping("")
    public String tohome2() {
        return "redirect:/home";
    }

    @GetMapping("/actions")
    public String actionsPage(@RequestParam Integer link_id, Model model) {
        log.info("got request on actions page link_id: {}", link_id);

        Link linkToSearch = new Link();
        linkToSearch.setId(link_id);
        Link link = linkService.getAllLinksByParams(linkToSearch).stream().findFirst().orElseThrow(()->
            new LinkNotFoundException("can't find link with id " + link_id));
        log.info("link: {}", link);

        User authUser = authService.getCurrentUserAuthenticated();
        linkService.validateOwnerShip(link_id ,authUser.getId());

        log.info("starting to search in bd");
        List<Action> actionList = actionService.getAllActionsOnLinkById(link_id);

        model.addAttribute("nice_link", link.getNice_link());
        model.addAttribute("actionList", actionList);

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
    public String profilePage(Model model) {
        log.info("got request on profile page");

        User authUser = authService.getCurrentUserAuthenticated();

        model.addAttribute("username",authUser.getUsername());
        model.addAttribute("email", authUser.getEmail());
        model.addAttribute("role", authUser.getRole());
        model.addAttribute("level", authUser.getLevel());

        return "profilepage";
    }

    @GetMapping("/mylinks")
    public String myLinksPage(Model model) {
        User authUser = authService.getCurrentUserAuthenticated();
        List<LinkResult> linkResults = linkService.getAllLinksForUserByUsername(authUser.getUsername());

        model.addAttribute("linkResults", linkResults);
        return "mylinks";
    }
}