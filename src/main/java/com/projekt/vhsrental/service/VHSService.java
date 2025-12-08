package com.projekt.vhsrental.service;


import com.projekt.vhsrental.exception.NotFoundException;
import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.model.VHSDetailsDTO;
import com.projekt.vhsrental.repository.VHSRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VHSService {

    private VHSRepo repo;
    private  ReviewService reviewService;

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

}
