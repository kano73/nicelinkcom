package com.nicelink.nicer.controller;

import com.nicelink.nicer.config.security.AuthenticatedUserService;
import com.nicelink.nicer.exeptions.link.InvalidLinkException;
import com.nicelink.nicer.exeptions.user.ValidationException;
import com.nicelink.nicer.model.Action;
import com.nicelink.nicer.model.Link;
import com.nicelink.nicer.model.User;
import com.nicelink.nicer.model.dto.LinkDTO;
import com.nicelink.nicer.model.dto.PostLinkDTO;
import com.nicelink.nicer.model.dto.UpdateLinkDTO;
import com.nicelink.nicer.service.ActionService;
import com.nicelink.nicer.service.ClientsDetailsService;
import com.nicelink.nicer.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetTime;

import java.io.IOException;
import java.time.ZonedDateTime;

@Slf4j
@RestController
@RequestMapping
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private ActionService actionService;

    @Lazy
    @Autowired
    private AuthenticatedUserService authService;

    @GetMapping("/nl/**")
    public ResponseEntity<String> redirect(HttpServletRequest request) throws IOException {
        String path = request.getRequestURI();

        log.info("got request for redirect on path: " + path);
        log.info("trying to get orig_link from db");

        String orig_link = linkService.getOrigLinkByNiceLinkFAST(path);

        log.info("orig link: " + orig_link);


        String clientsIpAddress = ClientsDetailsService.getClientIp(request);
        Integer linkId = linkService.getLinkIdByNiceLink(path);

        Action action = new Action(linkId,clientsIpAddress);

        log.info("action: " + action);

        try{
            actionService.addAction(action);
        }catch (Exception e){
            log.error("error accused wile adding action: "+e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Refresh", "5; URL=" + orig_link);
        String body = "<h1>Hello in 5 sek you will be redirected</h1><p>Here could be your add</p>"+"<p>your ip adress: "+clientsIpAddress+"</p>";

        return ResponseEntity.ok().headers(headers).body(body);
    }

    @PostMapping("/createlink")
    public ResponseEntity<String> createLink(@Valid @RequestBody PostLinkDTO postLinkDTO) {
        log.info("Got request post on /createlink: "+postLinkDTO.toString());

        User authuser = authService.getCurrentUserAuthenticated();
        if(authuser.getId()==null) throw new ValidationException("unexpected situation:","lost users id");
        postLinkDTO.setOwner_id(authuser.getId());

        Link link = postLinkDTO.toLink();
        log.info("Converted link: "+ link.toString());

        log.info("trying to create a new link");
        return linkService.createLink(link) ? ResponseEntity.ok("created successfully")
                : ResponseEntity.ok("created failed");
    }

    @PutMapping("/mylinks")
    public ResponseEntity<String> updateLink(@Valid @RequestBody UpdateLinkDTO updateLinkDTO) {
        log.info("Got request put on /createlink: "+updateLinkDTO.toString());

        log.info("trying to change a link");
        return linkService.updateNiceLinkByNiceLink(updateLinkDTO) ? ResponseEntity.ok("changed successfully")
                : ResponseEntity.ok("changed failed");

    }

    @DeleteMapping("/mylinks")
    public ResponseEntity<String> deleteLink(@Valid @RequestBody LinkDTO linkDTO) throws InvalidLinkException {
        log.info("Got request delete on /mylinks: "+linkDTO.toString());

        log.info("trying to delete a link");
        return linkService.deleteLinkByNiceLink(linkDTO.getNice_link()) ? ResponseEntity.ok("changed successfully")
                : ResponseEntity.ok("changed failed");
    }

}
