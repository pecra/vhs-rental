package com.projekt.vhsrental.service;


import com.projekt.vhsrental.exception.ForbiddenActionException;
import com.projekt.vhsrental.exception.NotFoundException;
import com.projekt.vhsrental.model.Rental;
import com.projekt.vhsrental.model.User;
import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.model.WaitlistEntry;
import com.projekt.vhsrental.repository.RentalRepo;
import com.projekt.vhsrental.repository.UserRepo;
import com.projekt.vhsrental.repository.VHSRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
public class RentalService {

    private final RentalRepo rentalRepo;
    private final UserRepo userRepo;
    private final VHSRepo vhsRepo;
    private final WaitlistEntryService waitlistEntryService;

    public RentalService(RentalRepo rentalRepo, UserRepo userRepo, VHSRepo vhsRepo, WaitlistEntryService waitlistEntryService) {
        this.rentalRepo = rentalRepo;
        this.userRepo = userRepo;
        this.vhsRepo = vhsRepo;
        this.waitlistEntryService = waitlistEntryService;
    }

    public List<Rental> getAllRentals(){
        log.debug("Getting all rentals");
        return rentalRepo.findAll();
    }

    public List<Rental> getAllActiveRentals(){
        log.debug("Getting all active rentals");
        return rentalRepo.findByReturnDateIsNull();
    }

    public List<Rental> getRentalsForUser(Integer userId){
        log.info("Getting rentals for user {}", userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("user.not.found"));

        return rentalRepo.findByUser(user);
    }

    public Rental getRental(Integer rentalId){
        log.info("Getting rental by ID {}", rentalId);
        return rentalRepo.findById(rentalId).orElseThrow(() -> new NotFoundException("rental.not.found"));
    }

    public Rental addRental(Integer vhsId, Integer userId){

        log.info("Creating rental for user {} and vhs {}",userId, vhsId);
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("user.not.found"));
        VHS vhs = vhsRepo.findById(vhsId).orElseThrow(() -> new NotFoundException("vhs.not.found"));

        if (rentalRepo.existsByVhsAndReturnDateIsNull(vhs)) {
            throw new ForbiddenActionException("vhs.already.rented");
        }

        LocalDate now = LocalDate.now();
        LocalDate dueDate;

        if (now.getDayOfWeek() == DayOfWeek.FRIDAY) {
            dueDate = now.plusDays(3);
        } else {
            dueDate = now.plusDays(2);
        }

        Rental rental = new Rental();
        rental.setRentalDate(now);
        rental.setDueDate(dueDate);
        rental.setVhs(vhs);
        rental.setUser(user);

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
        long diff = ChronoUnit.DAYS.between( rental.getDueDate(), now);
        rental.setReturnDate(now);

        if(diff > 0){
            rental.setLateFee(BigDecimal.valueOf(diff));  //1e po danu
            log.info("Rental returned late, fee: {}", rental.getLateFee() );
        }
        else{
            rental.setLateFee(BigDecimal.ZERO);
        }

        WaitlistEntry waitlist = waitlistEntryService.getNextInWaitlist(rental.getVhs());
        if(waitlist != null){
            log.info("Notify user {} on email {} that VHS is available.",waitlist.getUser().getUserId(), waitlist.getUser().getEmail());
        }

        return rentalRepo.save(rental);


    }

}
