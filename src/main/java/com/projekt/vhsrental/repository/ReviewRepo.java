package com.projekt.vhsrental.repository;

import com.projekt.vhsrental.model.Review;
import com.projekt.vhsrental.model.User;
import com.projekt.vhsrental.model.VHS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Integer> {

    boolean existsByVhsAndUser (VHS vhs, User user);
    List<Review> findAllByVhs (VHS vhs);
    void deleteByVhs(VHS vhs);

}
