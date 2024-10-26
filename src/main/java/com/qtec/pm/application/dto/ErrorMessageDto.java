package com.qtec.pm.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ErrorMessageDto {

    private int statusCode;
    private boolean result;
    private String message;

}
