package com.projekt.vhsrental.service;


import com.projekt.vhsrental.exception.AlreadyExistsException;
import com.projekt.vhsrental.exception.NotFoundException;
import com.projekt.vhsrental.model.Review;
import com.projekt.vhsrental.model.User;
import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.repository.ReviewRepo;
import com.projekt.vhsrental.repository.UserRepo;
import com.projekt.vhsrental.repository.VHSRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ReviewService {

    private final ReviewRepo reviewRepo;
    private final UserRepo userRepo;
    private final VHSRepo vhsRepo;

    public ReviewService(ReviewRepo reviewRepo, UserRepo userRepo, VHSRepo vhsRepo) {
        this.reviewRepo = reviewRepo;
        this.userRepo = userRepo;
        this.vhsRepo = vhsRepo;
    }

    public List<Review> getReviewsForVHS(Integer vhsId){
        log.info("Getting reviews for VHSId {}", vhsId);
        VHS vhs = vhsRepo.findById(vhsId).orElseThrow(() -> new NotFoundException("vhs.not.found"));
        return reviewRepo.findAllByVhs(vhs);
    }

    public Review addReview(Integer vhsId, Integer userId, Integer rating, String comment){

        log.info("Creating review for user {} and vhs {}",userId, vhsId);
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("user.not.found"));
        VHS vhs = vhsRepo.findById(vhsId).orElseThrow(() -> new NotFoundException("vhs.not.found"));

        if (reviewRepo.existsByVhsAndUser(vhs,user)) {
            throw new AlreadyExistsException("review.already.exists");
        }

        Review review = new Review();
        review.setVhs(vhs);
        review.setUser(user);
        review.setReviewDate(LocalDate.now());
        review.setRating(rating);
        review.setComment(comment);

        log.info("Created review: user={}, vhs={}, rating={}", userId, vhsId, rating);
        return reviewRepo.save(review);


    }

    public Double getRating(Integer vhsId) {
        VHS vhs = vhsRepo.findById(vhsId).orElseThrow(() -> new NotFoundException("vhs.not.found"));

        List<Review> reviews = reviewRepo.findAllByVhs(vhs);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        return  reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

}
