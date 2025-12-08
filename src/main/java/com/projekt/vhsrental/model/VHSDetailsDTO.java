package com.projekt.vhsrental.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "vhs.title.notblank")
    @Column(nullable = false)
    private String title;

    private String genre;

    @NotNull(message = "vhs.releaseyear.notnull")
    @Min(value = 1800, message = "vhs.releaseyear.min")
    private Integer releaseYear;

    private Double averageRating;
}