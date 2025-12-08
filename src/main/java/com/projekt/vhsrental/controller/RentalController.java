package com.projekt.vhsrental.controller;



import com.projekt.vhsrental.model.Rental;
import com.projekt.vhsrental.model.RentalDTO;
import com.projekt.vhsrental.service.RentalService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private RentalService serv;


    public RentalController(RentalService serv) {
        this.serv = serv;
    }

    @GetMapping
    public List<Rental> getAllRentals() {

        log.debug("HTTP GET /api/rentals");
        return serv.getAllRentals();
    }

    @GetMapping("/active")
    public List<Rental> getAllActiveRentals() {

        log.debug("HTTP GET /api/rentals/active");
        return serv.getAllActiveRentals();
    }

    @GetMapping("/{rentalId}")
    public Rental getRental(@PathVariable Integer rentalId) {

        log.debug("HTTP GET /api/rentals/{}", rentalId);
        return serv.getRental(rentalId);
    }

    @PostMapping
    public Rental addRental(@RequestBody @Valid RentalDTO dto) {
        log.debug("HTTP POST /api/rentals userId={} vhsId={}", dto.getUserId(), dto.getVhsId());
        return serv.addRental(dto.getVhsId(), dto.getUserId());
    }

    @PostMapping("/return/{rentalId}")
    public Rental returnRental(@PathVariable Integer rentalId) {

        log.debug("HTTP POST /api/return/{}", rentalId);
        return serv.returnRental(rentalId);
    }

}

