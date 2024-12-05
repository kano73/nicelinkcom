package com.nicelink.nicer.controller;

import com.nicelink.nicer.config.security.AuthenticatedUserService;
import com.nicelink.nicer.exeptions.user.InvalidUserException;
import com.nicelink.nicer.model.User;
import com.nicelink.nicer.model.dto.UserDTO;
import com.nicelink.nicer.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private AuthenticatedUserService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) {

        log.info("Got request post on /register :" + userDTO.toString());

        User user = userDTO.convertToUser();

        log.info("converted user: " + user.toString());

        return userService.postUser(user) ? ResponseEntity.ok("registered successfully")
                :ResponseEntity.ok("registration failed");
    }

    @PutMapping("/profile")
    public ResponseEntity<String> profile(@Valid @RequestBody UserDTO userDTO) throws InvalidUserException {
        User authUser = authService.getCurrentUserAuthenticated();

        log.info("auth user: " + authUser.toString());

        log.info("Got request put on profile: " + userDTO.toString());
        User user = userDTO.convertToUser();
        log.info("converted user: " + user.toString());

        user.setId(authUser.getId());

        log.info("converted user: " + user.toString());

        userService.updateUserInfo(user);
        log.info("updated successfully ");
        return ResponseEntity.ok("updated info successfully");
    }

    @DeleteMapping("/profile")
    public ResponseEntity<String> profileDelete(@Valid @RequestBody UserDTO userDTO) throws InvalidUserException {
        log.info("Got request delete on /profile: " + userDTO.toString());

        User authUser = authService.getCurrentUserAuthenticated();
        User user = userDTO.convertToUser();

        log.info("converted user: {}; \n authuser: {}", user.toString(), authUser.toString());

        return userService.deleteUser(user, authUser) ? ResponseEntity.ok("deleted successfully") : ResponseEntity.ok("deletion failed");
    }

}
