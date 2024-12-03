package com.nicelink.nicer.service;

import com.nicelink.nicer.model.User;
import com.nicelink.nicer.model.details.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = Optional.ofNullable(userService.getUserByUsername(username));

        return user.map(MyUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("no user with such name: "));
    }
}
