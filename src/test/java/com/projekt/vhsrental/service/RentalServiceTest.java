package com.projekt.vhsrental.service;


import com.projekt.vhsrental.exception.ForbiddenActionException;
import com.projekt.vhsrental.model.Rental;
import com.projekt.vhsrental.model.User;
import com.projekt.vhsrental.model.VHS;
import com.projekt.vhsrental.repository.RentalRepo;
import com.projekt.vhsrental.repository.UserRepo;
import com.projekt.vhsrental.repository.VHSRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {

    @Mock
    private RentalRepo rentalRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private VHSRepo vhsRepo;

    @InjectMocks
    private RentalService rentalService;
    @Mock
    private WaitlistEntryService  waitlistEntryService;

    @Nested
    @DisplayName("Add Rental tests")
    class AddRentalTests{

        @Test
        @DisplayName("Should create rental successfully")
        void shouldCreateRentalSuccessfully(){

            Integer userId = 2;
            Integer vhsId = 2;

            User user = new User();
            user.setUserId(userId);

            VHS vhs = new VHS();
            vhs.setVhsId(vhsId);

            when(userRepo.findById(userId)).thenReturn(Optional.of(user));
            when(vhsRepo.findById(vhsId)).thenReturn(Optional.of(vhs));
            when(rentalRepo.existsByVhsAndReturnDateIsNull(vhs)).thenReturn(false);
            when(rentalRepo.save(any(Rental.class))).thenAnswer(inv -> inv.getArgument(0));

            Rental rental = rentalService.addRental(vhsId, userId);

            assertNotNull(rental);
            assertEquals(user, rental.getUser());
            assertEquals(vhs, rental.getVhs());
            assertEquals(rental.getRentalDate().plusDays(2), rental.getDueDate());
        }

        @Test
        @DisplayName("Should set dueDate as friday + 3 ")
        void shouldSetDueDateAsMonday(){

            Integer userId = 3;
            Integer vhsId = 3;
            LocalDate friday = LocalDate.of(2025, 1, 10);

            User user = new User();
            user.setUserId(userId);

            VHS vhs = new VHS();
            vhs.setVhsId(vhsId);

            try (MockedStatic<LocalDate> mocked = Mockito.mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {

                mocked.when(LocalDate::now).thenReturn(friday);
                when(userRepo.findById(userId)).thenReturn(Optional.of(user));
                when(vhsRepo.findById(vhsId)).thenReturn(Optional.of(vhs));
                when(rentalRepo.existsByVhsAndReturnDateIsNull(vhs)).thenReturn(false);
                when(rentalRepo.save(any(Rental.class))).thenAnswer(inv -> inv.getArgument(0));

                Rental rental = rentalService.addRental(vhsId, userId);

                assertNotNull(rental);
                assertEquals(user, rental.getUser());
                assertEquals(vhs, rental.getVhs());
                assertEquals(rental.getRentalDate().plusDays(3), rental.getDueDate());
            }
        }

        @Test
        @DisplayName("Should throw ForbiddenActionException when VHS already has an active rental")
        void shouldThrowForbiddenWhenVhsAlreadyRented() {

            Integer userId = 2;
            Integer vhsId = 2;

            User user = new User();
            user.setUserId(userId);

            VHS vhs = new VHS();
            vhs.setVhsId(vhsId);

            when(userRepo.findById(userId)).thenReturn(Optional.of(user));
            when(vhsRepo.findById(vhsId)).thenReturn(Optional.of(vhs));
            when(rentalRepo.existsByVhsAndReturnDateIsNull(vhs)).thenReturn(true);

            assertThrows(ForbiddenActionException.class,
                    () -> rentalService.addRental(vhsId, userId));
        }
    }

    @Nested
    @DisplayName("Return rental tests")
    class ReturnRentalTests{

        @Test
        @DisplayName("Should apply late fee when returned after the due date")
        void shouldApplyLateFee() {

            Integer rentalId = 3;

            Rental rental = new Rental();
            rental.setRentalId(rentalId);
            rental.setDueDate(LocalDate.now().minusDays(5));
            rental.setReturnDate(null);

            when(rentalRepo.findById(rentalId)).thenReturn(Optional.of(rental));
            when(rentalRepo.save(any(Rental.class))).thenReturn(rental);

            Rental returned = rentalService.returnRental(rentalId);

            assertNotNull(returned.getReturnDate());
            assertEquals(new BigDecimal("5"), returned.getLateFee());
        }
    }



}
