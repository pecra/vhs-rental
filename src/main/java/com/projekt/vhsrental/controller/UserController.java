package com.projekt.vhsrental.controller;


import com.projekt.vhsrental.model.User;
import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.service.UserService;
import com.projekt.vhsrental.service.VHSService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService serv;

    public UserController(UserService serv) {
        this.serv = serv;
    }

    @GetMapping
    public List<User> getAllUsers() {

        log.debug("HTTP GET /api/users");

        return serv.getAllUsers();
    }

    @GetMapping("/{UserId}")
    public User getUser(@PathVariable Integer userId) {

        log.debug("HTTP GET /api/users/{}", userId);
        return serv.getUser(userId);
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {


        log.debug("HTTP POST /api/rentals");
        return serv.addUser(user);
    }

}

