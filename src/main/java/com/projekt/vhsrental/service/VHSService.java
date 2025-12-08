package com.projekt.vhsrental.service;


import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.repository.VHSRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VHSService {

    private VHSRepo repo;

    public VHSService(VHSRepo repo) {
        this.repo = repo;
    }

    public List<VHS> getAllVHS() {

        log.debug("Getting all VHS");
        return repo.findAll();
    }

    public VHS getVHS(Integer id) {

        log.info("Getting VHS with id: {}", id);
        return repo.findById(id).orElse(null);
    }

    public VHS addVHS(VHS vhs) {

        log.info("Adding VHS: {}", vhs.getTitle());
        return repo.save(vhs);
    }

}
