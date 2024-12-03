package com.nicelink.nicer.config.security;

import com.nicelink.nicer.exeptions.user.UserNotFoundException;
import com.nicelink.nicer.model.User;
import com.nicelink.nicer.model.details.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticatedUserService {

    public User getCurrentUserAuthenticated() throws UserNotFoundException, AuthenticationException {
        log.info("getCurrentUserAuthenticated");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("name of class: "+authentication.getPrincipal().getClass().getName());
        log.info("to string: "+authentication.getPrincipal().toString());

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        if(userDetails == null) {
            throw new UserNotFoundException("connected user not found");
        }

        return userDetails.getUser();
    }
}
