package com.projekt.vhsrental.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vhs")
public class VHS {

    @Id
    @GeneratedValue
    private Integer vhsId;

    @Column(nullable = false)
    private String title;

    private String genre;

    private int releaseYear;


}
