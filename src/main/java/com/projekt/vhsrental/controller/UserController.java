package com.projekt.vhsrental.controller;


import com.projekt.vhsrental.model.User;
import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.service.UserService;
import com.projekt.vhsrental.service.VHSService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService serv;

    public UserController(UserService serv) {
        this.serv = serv;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return serv.getAllUsers();
    }

    @GetMapping("/{UserId}")
    public User getUser(@PathVariable Integer UserId) {
        return serv.getUser(UserId);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return serv.addUser(user);
    }

}

