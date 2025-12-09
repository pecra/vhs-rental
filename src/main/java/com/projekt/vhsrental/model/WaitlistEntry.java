package com.projekt.vhsrental.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rentals", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "vhs_id"}))
public class WaitlistEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer waitlistId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vhs_id")
    private VHS vhs;

    @Column(nullable = false)
    private LocalDateTime addedAt = LocalDateTime.now();
}
