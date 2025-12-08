package com.projekt.vhsrental.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue
    private Integer rentalId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "vhs_id",nullable = false)
    private VHS vhs;


    private LocalDate rentalDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private BigDecimal fee;


}
