package com.projekt.vhsrental.controller;


import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.service.VHSService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vhs")
public class VHSController {

    private VHSService serv;

    public VHSController(VHSService serv) {
        this.serv = serv;
    }

    @GetMapping
    public List<VHS> getAllVHS() {
        return serv.getAllVHS();
    }

    @GetMapping("/{VHSId}")
    public VHS getVHSById(@PathVariable Integer VHSId) {
        return serv.getVHS(VHSId);
    }

    @PostMapping
    public VHS addVHS(@RequestBody VHS vhs) {
        return serv.addVHS(vhs);
    }

}
