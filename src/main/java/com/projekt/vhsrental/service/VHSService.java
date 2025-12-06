package com.projekt.vhsrental.service;


import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.repository.VHSRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VHSService {

    private VHSRepo repo;

    public VHSService(VHSRepo repo) {
        this.repo = repo;
    }

    public List<VHS> getAllVHS() {
        return repo.findAll();
    }

    public VHS getVHS(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public VHS addVHS(VHS vhs) {
        return repo.save(vhs);
    }

}
