package com.projekt.vhsrental.service;


import com.projekt.vhsrental.model.Rental;
import com.projekt.vhsrental.model.User;
import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.repository.RentalRepo;
import com.projekt.vhsrental.repository.UserRepo;
import com.projekt.vhsrental.repository.VHSRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalService {

    private RentalRepo rentalRepo;
    private UserRepo userRepo;
    private VHSRepo vhsRepo;

    public RentalService(RentalRepo rentalRepo, UserRepo userRepo, VHSRepo vhsRepo) {
        this.rentalRepo = rentalRepo;
        this.userRepo = userRepo;
        this.vhsRepo = vhsRepo;
    }

    public List<Rental> getAllRentals(){
        return rentalRepo.findAll();
    }

    public List<Rental> getAllActiveRentals(){
        return rentalRepo.findByReturnDateIsNull();
    }

    public Rental getRental(Integer rentalId){
        return rentalRepo.findById(rentalId).orElse(null);
    }

    public Rental addRental(Integer vhsId, Integer userId){

        User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        VHS vhs = vhsRepo.findById(vhsId).orElseThrow(() -> new IllegalArgumentException("VHS not found: " + vhsId));

        if (rentalRepo.existsByVhsAndReturnDateIsNull(vhs)) {
            throw new IllegalStateException("VHS tape is currently rented out.");
        }

        LocalDate now = LocalDate.now();
        Rental rental = new Rental();
        rental.setRentalDate(now);
        rental.setVhs(vhs);
        rental.setUser(user);
        rental.setDueDate(now.plusDays(2));

        return rentalRepo.save(rental);


    }

    public Rental returnRental(Integer rentalId) {

        Rental rental = rentalRepo.findById(rentalId).orElseThrow(() -> new IllegalArgumentException("Rental not found: " + rentalId));

        if(!(rental.getReturnDate() == null)){
            throw new IllegalStateException("Already returned!");
        }

        LocalDate now = LocalDate.now();
        long diff = ChronoUnit.DAYS.between(now, rental.getDueDate());

        rental.setReturnDate(now);
        if(diff < 0){
            rental.setFee(((float) Math.abs(diff)));
        }
        return rentalRepo.save(rental);


    }

}
