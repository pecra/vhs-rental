package com.projekt.vhsrental.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO {

    @NotNull(message = "rental.userId.notnull")
    @Min(value = 1, message = "rental.userId.min")
    private Integer userId;

    @NotNull(message = "rental.vhsId.notnull")
    @Min(value = 1, message = "rental.vhsId.min")
    private Integer vhsId;

}
