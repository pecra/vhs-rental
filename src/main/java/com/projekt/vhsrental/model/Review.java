package com.projekt.vhsrental.model;


import jakarta.persistence.*;
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
@Table(name = "reviews", uniqueConstraints = {@UniqueConstraint(columnNames = {"vhs_id","user_id"})})
public class Review {

    @Id
    @GeneratedValue
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "vhs_id",nullable = false)
    private VHS vhs;

    private Integer rating;

    private String comment;

    private LocalDate reviewDate;

}
