package com.projekt.vhsrental.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

    private String timestamp;
    private String message;
    private int status;

    public ErrorDTO(HttpStatus status, String message){
        this.message = message;
        this.status = status.value();
        this.timestamp = LocalDateTime.now().toString();

    }
}
