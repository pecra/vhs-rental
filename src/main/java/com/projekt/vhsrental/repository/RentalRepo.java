package com.projekt.vhsrental.repository;

import com.projekt.vhsrental.model.Rental;
import com.projekt.vhsrental.model.VHS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RentalRepo extends JpaRepository<Rental, Integer> {

    boolean existsByVhsAndReturnDateIsNull(VHS vhs);

    List<Rental> findByReturnDateIsNull();

}
