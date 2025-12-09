package com.projekt.vhsrental.controller;


import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.model.VHSDetailsDTO;
import com.projekt.vhsrental.service.ReviewService;
import com.projekt.vhsrental.service.VHSService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/vhs")
public class VHSController {

    private VHSService serv;

    public VHSController(VHSService serv,  ReviewService reviewService) {
        this.serv = serv;
    }

    @GetMapping
    public List<VHS> getAllVHS() {

        log.debug("HTTP GET /api/vhs");
        return serv.getAllVHS();
    }

    @GetMapping("/{VHSId}")
    public VHS getVHSById(@PathVariable Integer VHSId) {

        log.debug("HTTP GET /api/vhs/{}", VHSId);
        return serv.getVHS(VHSId);
    }

    @GetMapping("/{VHSId}/details")
    public VHSDetailsDTO getVHSDetailsById(@PathVariable Integer VHSId) {

        log.debug("HTTP GET /api/vhs/{}/details", VHSId);
        return serv.getVHSDetails(VHSId);
    }

    @PostMapping
    public VHS addVHS(@RequestBody @Valid VHS vhs) {

        log.debug("HTTP POST /api/vhs");
        return serv.addVHS(vhs);
    }

}
