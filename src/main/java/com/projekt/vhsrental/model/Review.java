package com.projekt.vhsrental.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "vhs_id",nullable = false)
    private VHS vhs;

    @NotNull(message = "review.rating.notnull")
    @Min(value = 1,message = "review.rating.min")
    @Max(value = 5,message = "review.rating.max")
    private Integer rating;

    @Size(max = 500)
    private String comment;

    private LocalDate reviewDate;

}
