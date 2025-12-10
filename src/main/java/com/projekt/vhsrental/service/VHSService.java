package com.projekt.vhsrental.service;


import com.projekt.vhsrental.exception.ForbiddenActionException;
import com.projekt.vhsrental.exception.NotFoundException;
import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.model.VHSDetailsDTO;
import com.projekt.vhsrental.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class VHSService {

    private final VHSRepo repo;
    private final ReviewService reviewService;
    private final RentalRepo rentalRepo;
    private final WaitlistEntryRepo waitlistEntryRepo;
    private final ReviewRepo reviewRepo;

    public VHSService(VHSRepo repo, ReviewService reviewService, RentalRepo rentalRepo, WaitlistEntryRepo waitlistEntryRepo, ReviewRepo reviewRepo) {

        this.repo = repo;
        this.reviewService = reviewService;
        this.rentalRepo = rentalRepo;
        this.waitlistEntryRepo = waitlistEntryRepo;
        this.reviewRepo = reviewRepo;
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

    @Transactional
    public void deleteVHS(Integer vhsId) {

        log.info("Deleting VHS with id: {}", vhsId);
        VHS vhs = repo.findById(vhsId).orElseThrow(() -> new NotFoundException("vhs.not.found"));

        if(rentalRepo.existsByVhsAndReturnDateIsNull(vhs)){
            throw new ForbiddenActionException("vhs.already.rented");
        }
        waitlistEntryRepo.deleteByVhs(vhs);
        reviewRepo.deleteByVhs(vhs);
        repo.delete(vhs);
    }
}
