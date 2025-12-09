package com.projekt.vhsrental.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    @NotNull(message = "review.userId.notnull")
    @Min(value = 1, message = "review.userId.min")
    private Integer userId;

    @NotNull(message = "review.vhsId.notnull")
    @Min(value = 1, message = "review.vhsId.min")
    private Integer vhsId;

    @NotNull(message = "review.rating.notnull")
    @Min(value = 1,message = "review.rating.min")
    @Max(value = 5,message = "review.rating.max")
    private Integer rating;

    @Size(max = 500, message = "review.comment.max")
    private String comment;
}
