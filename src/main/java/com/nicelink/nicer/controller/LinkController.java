package com.nicelink.nicer.controller;

import com.nicelink.nicer.model.Action;
import com.nicelink.nicer.model.Link;
import com.nicelink.nicer.model.dto.LinkDTO;
import com.nicelink.nicer.model.dto.UpdateLinkDTO;
import com.nicelink.nicer.repository.ActionRepository;
import com.nicelink.nicer.service.ActionService;
import com.nicelink.nicer.service.ClientsDetailsService;
import com.nicelink.nicer.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/nl/**")
    public ResponseEntity<String> redirect(HttpServletRequest request) throws IOException {
        String path = request.getRequestURI();

        log.info("got request for redirect on path: " + path);
        log.info("trying to get orig_link from db");

        String orig_link = linkService.getOrigLinkByNiceLinkFAST(path);

        log.info("orig link: " + orig_link);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Refresh", "5; URL=" + orig_link);

        String clientsIpAddress = ClientsDetailsService.getClientIp(request);
        ZonedDateTime time_stamp = ZonedDateTime.now();
        Integer linkId = linkService.getLinkIdByNiceLink(path);

        Action action = new Action(time_stamp,linkId,clientsIpAddress);

        log.info("action: " + action);

        try{
            actionService.addAction(action);
        }catch (Exception e){
            log.error("error accused wile adding action: "+e.getMessage());
        }

        String body = "<h1>Hello in 5 sek you will be redirected</h1><p>Here could be your add</p>"+"<p>your ip adress: "+clientsIpAddress+"</p>";

        return ResponseEntity.ok().headers(headers).body(body);
    }

    @PostMapping("/createlink")
    public ResponseEntity<String> createLink(@Valid @RequestBody LinkDTO linkDTO) {

        log.info("Got request post on /createlink: "+linkDTO.toString());

        Link link = linkDTO.convertToLink();

        log.info("Converted link: "+link.toString());

        log.info("trying to create a new link");
        return linkService.createLink(link,linkDTO.getOwner_name()) ? ResponseEntity.ok("created successfully")
                : ResponseEntity.ok("created failed");
    }

    @PutMapping("/createlink")
    public ResponseEntity<String> updateLink(@Valid @RequestBody UpdateLinkDTO updateLinkDTO) {
        log.info("Got request put on /createlink: "+updateLinkDTO.toString());

        log.info("trying to change a link");
        return linkService.updateNiceLinkByNiceLink(updateLinkDTO) ? ResponseEntity.ok("changed successfully")
                : ResponseEntity.ok("changed failed");

    }

}
