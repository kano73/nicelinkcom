package com.nicelink.nicer.service;

import com.nicelink.nicer.exeptions.user.InvalidPasswordException;
import com.nicelink.nicer.exeptions.user.InvalidUserException;
import com.nicelink.nicer.model.Link;
import com.nicelink.nicer.model.User;
import com.nicelink.nicer.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

//    select

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public List<Link> getAllLinksForUser(String username) {
        return userRepository.getAllLinksForUser(username);
    }

//    UPDATE

    public boolean updateUserInfo(User user) throws InvalidUserException {

        return userRepository.updateUserInfoById(user);
    }

//  INSERT

    public boolean postUser(User user) {
        log.info("starting user repository");

        return userRepository.postUser(user);
    }

//    DELETE

    public boolean deleteUser(User user, User authUser) throws InvalidUserException {

        log.info("starting user repository");

        if(user.getUsername()!=authUser.getUsername()) {
            throw new InvalidUserException("username is not right");
        }

        if(passwordEncoder.matches(user.getPassword(), authUser.getPassword())) {
            return userRepository.deleteUserByUsername(user.getUsername());
        }else {
            throw new InvalidPasswordException("Invalid password");
        }
    }

}
