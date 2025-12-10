package com.projekt.vhsrental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VHSDetailsDTO {

    private Integer vhsId;
    private String title;
    private String genre;
    private Integer releaseYear;
    private Double averageRating;
}