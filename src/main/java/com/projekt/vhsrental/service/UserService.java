package com.projekt.vhsrental.service;


import com.projekt.vhsrental.model.User;
import com.projekt.vhsrental.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepo repo;

    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getUser(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public User addUser(User user) {
        return repo.save(user);
    }

}
