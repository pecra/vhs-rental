package com.projekt.vhsrental.service;


import com.projekt.vhsrental.exception.AlreadyExistsException;
import com.projekt.vhsrental.exception.NotFoundException;
import com.projekt.vhsrental.model.User;
import com.projekt.vhsrental.repository.UserRepo;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


import java.util.List;

@Slf4j
@Service
public class UserService {

    private UserRepo repo;

    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    public List<User> getAllUsers() {

        log.debug("Getting all users");
        return repo.findAll();
    }

    public User getUser(Integer id) {
        log.info("Getting user with id: {}", id);
        return repo.findById(id).orElseThrow(() -> new NotFoundException("user.not.found"));
    }

    public User addUser(User user) {

        log.info("Adding user: {}, {}", user.getName(), user.getEmail());
        if (repo.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("user.already.exists");
        }
        return repo.save(user);
    }

}
