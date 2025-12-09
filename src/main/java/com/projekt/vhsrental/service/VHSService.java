package com.projekt.vhsrental.service;


import com.projekt.vhsrental.exception.NotFoundException;
import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.model.VHSDetailsDTO;
import com.projekt.vhsrental.repository.RentalRepo;
import com.projekt.vhsrental.repository.UserRepo;
import com.projekt.vhsrental.repository.VHSRepo;
import com.projekt.vhsrental.repository.WaitlistEntryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VHSService {

    private final VHSRepo repo;
    private final ReviewService reviewService;

    public VHSService(VHSRepo repo, ReviewService reviewService) {

        this.repo = repo;
        this.reviewService = reviewService;
    }

    public List<VHS> getAllVHS() {

        log.debug("Getting all VHS");
        return repo.findAll();
    }

    public VHS getVHS(Integer id) {

        log.info("Getting VHS with id: {}", id);
        return repo.findById(id).orElseThrow(() -> new NotFoundException("vhs.not.found"));
    }

    public VHSDetailsDTO getVHSDetails(Integer vhsId) {

        log.info("Getting VHSDetails for VHS with id: {}", vhsId);

        VHS vhs = getVHS(vhsId);
        Double avgRating = reviewService.getRating(vhsId);

        return new VHSDetailsDTO(
                vhs.getVhsId(),
                vhs.getTitle(),
                vhs.getGenre(),
                vhs.getReleaseYear(),
                avgRating
        );
    }

    public VHS addVHS(VHS vhs) {

        log.info("Adding VHS: {}", vhs.getTitle());
        return repo.save(vhs);
    }

    @Slf4j
    @Service
    public class WaitlistEntryService {

        private final WaitlistEntryRepo waitlistEntryRepo;
        private final UserRepo userRepo;
        private final VHSRepo vhsRepo;
        private final RentalRepo rentalRepo;


        public WaitlistEntryService(WaitlistEntryRepo waitlistRepo, UserRepo userRepo, VHSRepo vhsRepo, RentalRepo rentalRepo) {
            this.waitlistEntryRepo = waitlistRepo;
            this.userRepo = userRepo;
            this.vhsRepo = vhsRepo;
            this.rentalRepo = rentalRepo;
        }}
}
