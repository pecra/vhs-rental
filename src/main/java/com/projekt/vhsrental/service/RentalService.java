package com.projekt.vhsrental.service;


import com.projekt.vhsrental.exception.ForbiddenActionException;
import com.projekt.vhsrental.exception.NotFoundException;
import com.projekt.vhsrental.model.Rental;
import com.projekt.vhsrental.model.User;
import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.repository.RentalRepo;
import com.projekt.vhsrental.repository.UserRepo;
import com.projekt.vhsrental.repository.VHSRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
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
        log.debug("Getting all rentals");
        return rentalRepo.findAll();
    }

    public List<Rental> getAllActiveRentals(){
        log.debug("Getting all active rentals");
        return rentalRepo.findByReturnDateIsNull();
    }

    public Rental getRental(Integer rentalId){
        log.info("Getting rental by ID {}", rentalId);
        return rentalRepo.findById(rentalId).orElse(null);
    }

    public Rental addRental(Integer vhsId, Integer userId){

        log.info("Creating rental for user {} and vhs {}",userId, vhsId);
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("user.not.found"));


        VHS vhs = vhsRepo.findById(vhsId).orElseThrow(() -> new NotFoundException("vhs.not.found"));

        if (rentalRepo.existsByVhsAndReturnDateIsNull(vhs)) {
            throw new ForbiddenActionException("vhs.already.rented");
        }

        LocalDate now = LocalDate.now();
        Rental rental = new Rental();
        rental.setRentalDate(now);
        rental.setVhs(vhs);
        rental.setUser(user);
        rental.setDueDate(now.plusDays(2));

        log.info("Created rental {}", rental);
        return rentalRepo.save(rental);


    }

    public Rental returnRental(Integer rentalId) {

        log.info("Returning rental by ID {}", rentalId);
        Rental rental = rentalRepo.findById(rentalId).orElseThrow(() -> new NotFoundException("rental.not.found"));

        if(!(rental.getReturnDate() == null)){
            throw new ForbiddenActionException("vhs.not.rented");
        }

        LocalDate now = LocalDate.now();
        long diff = ChronoUnit.DAYS.between(now, rental.getDueDate());

        rental.setReturnDate(now);
        if(diff < 0){
            rental.setFee(BigDecimal.valueOf(Math.abs(diff)));
            log.info("Rental returned late, fee: {}", rental.getFee() );
        }
        else{
            rental.setFee(BigDecimal.valueOf(0));
        }

        return rentalRepo.save(rental);


    }

}
