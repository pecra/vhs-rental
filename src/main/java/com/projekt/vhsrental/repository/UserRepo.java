package com.projekt.vhsrental.repository;

import com.projekt.vhsrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
}
