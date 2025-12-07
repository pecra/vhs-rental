package com.projekt.vhsrental.controller;



import com.projekt.vhsrental.model.Rental;
import com.projekt.vhsrental.model.RentalDTO;
import com.projekt.vhsrental.service.RentalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private RentalService serv;


    public RentalController(RentalService serv) {
        this.serv = serv;
    }

    @GetMapping
    public List<Rental> getAllRentals() {

        return serv.getAllRentals();
    }

    @GetMapping("/active")
    public List<Rental> getAllActiveRentals() {
        return serv.getAllActiveRentals();
    }

    @GetMapping("/{rentalId}")
    public Rental getRental(@PathVariable Integer rentalId) {
        return serv.getRental(rentalId);
    }

    @PostMapping
    public Rental addRental(@RequestBody RentalDTO dto) {
        return serv.addRental(dto.getVhsId(), dto.getUserId());
    }

    @PostMapping("/return/{rentalId}")
    public Rental returnRental(@PathVariable Integer rentalId) {
        return serv.returnRental(rentalId);
    }

}

