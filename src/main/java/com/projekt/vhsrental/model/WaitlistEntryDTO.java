package com.projekt.vhsrental.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WaitlistEntryDTO {

    @NotNull(message = "waitlist.userId.notnull")
    @Min(value = 1, message = "waitlist.userId.min")
    private Integer userId;

    @NotNull(message = "waitlist.vhsId.notnull")
    @Min(value = 1, message = "waitlist.vhsId.min")
    private Integer vhsId;

}
