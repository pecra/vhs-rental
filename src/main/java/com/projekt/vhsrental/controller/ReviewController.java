package com.projekt.vhsrental.controller;



import com.projekt.vhsrental.model.Review;
import com.projekt.vhsrental.model.ReviewDTO;
import com.projekt.vhsrental.service.ReviewService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewService serv;

    public ReviewController(ReviewService serv) {
        this.serv = serv;
    }

    @PostMapping
    public Review addReview(@RequestBody @Valid ReviewDTO reviewDTO) {

        log.debug("HTTP POST /api/reviews");
        return serv.addReview(reviewDTO.getVhsId(), reviewDTO.getUserId(),reviewDTO.getRating(), reviewDTO.getMessage());
    }

    @GetMapping("/vhs/{vhsId}")
    public List<Review> getReviewsForVhs(@PathVariable Integer vhsId) {
        log.debug("HTTP GET /api/reviews/vhs/{}", vhsId);
        return serv.getReviewsForVHS(vhsId);
    }

}

